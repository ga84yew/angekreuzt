import java.io.IOException;
import java.net.MalformedURLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.OutputSpeech;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import com.amazon.speech.ui.SsmlOutputSpeech;

import parliament2profile.Parliament;

public class AngekreuztSpeechlet implements Speechlet {

	private static final Logger log = LoggerFactory.getLogger(AngekreuztSpeechlet.class);
	private static String first = "Willkommen, du kannst nach Parteien, Themen und Kandidaten fragen";
	private static String second = "zum Beispiel";
	private static String third = "was denkt die Partei X zum Thema Integration,Sicherheit, E U Aussenpolitik, Bildung, Arbeit, Finanzen und Steuern?";
	private static String fourth = "Du kannst auch jeden Direktkandidaten zu den Themen fragen";
	private static String firstRe = "Es sind die Themen Bildung, Soziales, Integration, Aussenpolitik, Sicherheit und Steuern und Finanzen erfasst";

	
	public SpeechletResponse onLaunch(final LaunchRequest request, final Session session) throws SpeechletException {
		log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
		return getWelcomeResponse();
	}

	/**
	 * Es wird wiederum ein neues PlainTextOutputSpeech-Objekt "text"
	 * initialisiert, das den "Willkommen bei angekreuzt"-String als Text liest,
	 * wenn der User die App startet.
	 * 
	 * @return
	 */
	private SpeechletResponse getWelcomeResponse() {
				
		String output = SpeechHelper.wrapInSpeak(first);

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("Angekreuzt");
		card.setContent(first + second + third + fourth);

		return newAskResponse(output, true, longRepromptString());
	}

	/**
	 * Die drei unterschiedlichen Intents werden in drei Bedinungen abgefragt:
	 * Das Intent "wahlsys" übergibt den Slottypen "wahlsystem" als String an
	 * die Methode "auswaehlen in der Klasse "Wahlthema" und ruft diese auf.
	 * 	jeder fall ruft eine eigene newAskResponse auf
	 *  bei Alexa cancel oder stop wird beendet
	 *  bei Nichterkennugn wird eine Hilfestellug ausgegeben.
	 * "r" übergeben.
	 * 
	 * @param arg0
	 *            IntentRequest pipa
	 * @return SpeechletResponse for out to Alexa
	 */
	public SpeechletResponse onIntent(IntentRequest arg0, Session arg1) throws SpeechletException {
		// define Themen
		Themen themes = new Themen();
		//get intentName
		Intent intent = arg0.getIntent();String intentName = intent.getName();

		String result = "";

		if (intentName.equals("wahlsys")) {
			result = Wahlsystem.getText();
			return newAskResponse(result,false,shortRepromptWahlsysString());

		} else if (intentName.equals("zweitstimme")) {
			String themen = intent.getSlot("themen").getValue();
			String partei = intent.getSlot("partei").getValue();
			result = ZweitStimme.auswahl(themen, partei, themes.mapping);
			return newAskResponse(result,false,shortRepromptZweitstimmeString());

		} else if (intentName.equals("erststimme")) {
			String themen = intent.getSlot("themen").getValue();
			String fullname = intent.getSlot("kandidat").getValue();
			
				try {
				Parliament parliament = new Parliament(); parliament.setName("Bundestag");	
				Erststimme erstestimme = new Erststimme(parliament.getName(), themes.mapping);
				return newAskResponse(erstestimme.call(themen, fullname), true, shortRepromptErststimmeString());
				} catch (MalformedURLException e) {e.printStackTrace();
				} catch (IOException e) {e.printStackTrace();} 
				
				return newAskResponse("Bitte versuche es noch einmal",false,shortRepromptErststimmeString());

		}else if (intentName.equals("AMAZON.StopIntent") || input.equals("AMAZON.CancelIntent")) {
	
			PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
            outputSpeech.setText("Servus");
            return SpeechletResponse.newTellResponse(outputSpeech);

		}else{
			String speechOutput = second
					+SpeechHelper.createBreak(1) 
					+third
					+SpeechHelper.createBreak(1)
					+fourth;
 		    String repromptText =SpeechHelper.wrapInSpeak("Bitte stelle eine Frage");
		    return newAskResponse(speechOutput, true, repromptText);
		}
	}

	
	   /**
     * Wrapper for creating the Ask response from the input strings.
     * @param stringOutput the output to be spoken
     * @param isOutputSsml whether the output text is of type SSML
     * @param repromptText the reprompt for if the user doesn't reply or is misunderstood.
     * @param isRepromptSsml  whether the reprompt text is of type SSML
     * @return SpeechletResponse the speechlet response
     */
    private SpeechletResponse newAskResponse(String stringOutput, boolean isOutputSsml,
            String repromptText) {
    	boolean isRepromptSsml = true;
        OutputSpeech outputSpeech, repromptOutputSpeech;
        if (isOutputSsml) {
            outputSpeech = new SsmlOutputSpeech();
            ((SsmlOutputSpeech) outputSpeech).setSsml(stringOutput);
        } else {
            outputSpeech = new PlainTextOutputSpeech();
            ((PlainTextOutputSpeech) outputSpeech).setText(stringOutput);
        }

        if (isRepromptSsml) {
            repromptOutputSpeech = new SsmlOutputSpeech();
            ((SsmlOutputSpeech) repromptOutputSpeech).setSsml(repromptText);
        } else {
            repromptOutputSpeech = new PlainTextOutputSpeech();
            ((PlainTextOutputSpeech) repromptOutputSpeech).setText(repromptText);
        }
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptOutputSpeech);
        return SpeechletResponse.newAskResponse(outputSpeech, reprompt);
    }
	
	
	
	/**
	 * @return
	 */
	private String longRepromptString() {
		// Create reprompt
		String nice = "Schön, dass du dich über die Wahl am 24.September informieren willst. Das ist Super. Wähle ja keinen Schmarrn.";
		String secondRe = "Ok also, frag mich zum Beispiel wie funktioniert die Wahl in Deutschland oder frage zum Beispiel was denkt Kandidat X über Bildung, Arbeit und Soziales, Integration und "
				+ "Asyl, E U und Aussenpolitik, Sicherheit und Steuern und Finanzen.";
		String thirdRe = "Oder denk an deine Zweitstimme und frage ganz einfach:"
				+ "was sagt die Partei Y über das Thema Bildung, Sicherheit, Steuern und Finanzen, Integration und Asyl"
				+ ", E U und Aussenpolitik und Arbeit und Soziales.";
		String fourthRe = "Du kannst also direkt alle Kandidaten aller Wahlkreise oder die Parteien befragen. Yeah man.";

		String gegenantwort = SpeechHelper.wrapInSpeak(nice + SpeechHelper.createBreak(1) + secondRe
				+ SpeechHelper.createBreak(1) + thirdRe + SpeechHelper.createBreak(1) + fourthRe);
		return gegenantwort;
	}

	
	private String shortRepromptErststimmeString() {
		// Create reprompt
	    String secondRe = "Beispiel zum Direktkandidaten: was denkt Kandidat, z.B. Florian Post über Wirtschaft";
		String fourthRe = "Du kannst also direkt alle Kandidaten aller Wahlkreise mit vollem Namen auswählen";
		String gegenantwort = SpeechHelper.wrapInSpeak(firstRe + SpeechHelper.createBreak(1) + secondRe
				+ SpeechHelper.createBreak(1)  + SpeechHelper.createBreak(1) + fourthRe);
		return gegenantwort;
	}
	
	private String shortRepromptZweitstimmeString() {
		// Create reprompt
		String secondRe = "Beispiel zur Zweitstimme: wie möchte die CDU die Wirtschaft stärken?";
		String gegenantwort = SpeechHelper.wrapInSpeak(firstRe + SpeechHelper.createBreak(1) + secondRe);
		return gegenantwort;
	}
	
	private String shortRepromptWahlsysString() {
		String gegenantwort = SpeechHelper.wrapInSpeak("Beispiel zum Wahlsystem: " + SpeechHelper.createBreak(1) + " Wie wird gewählt?");
		return gegenantwort;
	}
	
	public void onSessionEnded(SessionEndedRequest arg0, Session arg1) throws SpeechletException {
	}

	public void onSessionStarted(SessionStartedRequest arg0, Session arg1) throws SpeechletException {
	}

}
