import java.io.IOException;
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

public class AngekreuztSpeechlet implements Speechlet {

	private static final Logger log = LoggerFactory.getLogger(AngekreuztSpeechlet.class);

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
		Reprompt reprompt = createReprompt(longRepromptString());
		
		String first = "Willkommen, du kannst nach Parteien, Themen und Kandidaten fragen";
		String second = "zum Beispiel";
		String third = "was denkt die Partei XY zum Thema Integration,Sicherheit, E U Aussenpolitik, Bildung, Arbeit, Finanzen und Steuern?";
		String fourth = "Du kannst auch jeden Direktkandidaten zu den Themen fragen";

		String text = SpeechHelper.wrapInSpeak(first);
		// + SpeechHelper.createBreak(1)
		// +second
		// + SpeechHelper.createBreak(1)
		// +third
		// + SpeechHelper.createBreak(1)
		// + fourth);

		SsmlOutputSpeech output = new SsmlOutputSpeech();
		output.setSsml(text);

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("Angekreuzt");
		card.setContent(first + second + third + fourth);

		return SpeechletResponse.newAskResponse(output, reprompt);
	}

	/**
	 * Die drei unterschiedlichen Intents werden in drei Bedinungen abgefragt:
	 * Das Intent "wahlsys" �bergibt den Slottypen "wahlsystem" als String an
	 * die Methode "auswaehlen in der Klasse "Wahlthema" und ruft diese auf.
	 * Schlie�lich wird das Result dann wieder an das "SpeechletResponse"-Objekt
	 * "r" �bergeben.
	 * 
	 * @param arg0
	 *            IntentRequest pipa
	 * @return SpeechletResponse for out to Alexa
	 */
	public SpeechletResponse onIntent(IntentRequest arg0, Session arg1) throws SpeechletException {
		// define Themen
		Themen themes = new Themen();
		Reprompt longreprompt = createReprompt(longRepromptString());

		Intent intent = arg0.getIntent();
		String input = intent.getName();
		PlainTextOutputSpeech text = new PlainTextOutputSpeech();
		String result = "";

		if (input.equals("wahlsys")) {
			result = Wahlsystem.getText();
			text.setText(result);
			return SpeechletResponse.newAskResponse(text, createReprompt(shortRepromptWahlsysString()));

		} else if (input.equals("zweitstimme")) {
			String themen = intent.getSlot("themen").getValue();
			String partei = intent.getSlot("partei").getValue();
			result = ZweitStimme.auswahl(themen, partei, themes.mapping);
			text.setText(result);
			return SpeechletResponse.newAskResponse(text,  createReprompt(shortRepromptZweitstimmeString()));

		} else if (input.equals("erststimme")) {

			String themen = intent.getSlot("themen").getValue();
			String fullname = intent.getSlot("kandidat").getValue();

			try {
				Erststimme erstestimme = new Erststimme("Bundestag", themes.mapping);
				return SpeechletResponse.newAskResponse(erstestimme.call(themen, fullname), createReprompt(shortRepromptErststimmeString()));

			} catch (IOException e) {
				e.printStackTrace();
			}

			/*
			 * } else if(input.equals("nichterkannt")) {
			 * 
			 * text.
			 * setText("Bitte wiederhole deine Anfrage und nuschel nicht so.");
			 * return SpeechletResponse.newAskResponse(text, reprompt);
			 */

		} else if (input.equals("AMAZON.StopIntent") || input.equals("AMAZON.CancelIntent")) {

			text.setText("Servus");
			return SpeechletResponse.newTellResponse(text);

		} else {

			text.setText("Bitte wiederhole deine Anfrage");
			return SpeechletResponse.newAskResponse(text, createReprompt("Leider habe ich dich nicht verstanden"));
		}
		return SpeechletResponse.newAskResponse(text, longreprompt);
	}

	/**
	 * @return reprompt for output in Alexa
	 */
	private Reprompt createReprompt(String gegenantwort) {
		SsmlOutputSpeech antworten = new SsmlOutputSpeech();		antworten.setSsml(gegenantwort);
		Reprompt reprompt = new Reprompt();		reprompt.setOutputSpeech(antworten);
		return reprompt;

	}

	/**
	 * @return
	 */
	private String longRepromptString() {
		// Create reprompt
		String firstRe = "Sch�n, dass du dich �ber die Wahl am 24.September informieren willst. Das ist Super. W�hle ja keinen Schmarrn.";

		String secondRe = "Ok also, frag mich zum Beispiel wie funktioniert die Wahl in Deutschland oder frage zum Beispiel was denkt Kandidat xy �ber Bildung, Arbeit und Soziales, Integration und "
				+ "Asyl, E U und Aussenpolitik, Sicherheit und Steuern und Finanzen.";
		String thirdRe = "Oder denk an deine Zweitstimme und frage ganz einfach:"
				+ "was sagt die Partei xy �ber das thema Bildung, Sicherheit, Steuern und Finanzen, Integration und Asyl"
				+ ", E U und Aussenpolitik und Arbeit und Soziales.";
		String fourthRe = "Du kannst also direkt alle Kandidaten aller Wahlkreise oder die Parteien befragen. Yeah man.";

		String gegenantwort = SpeechHelper.wrapInSpeak(firstRe + SpeechHelper.createBreak(1) + secondRe
				+ SpeechHelper.createBreak(1) + thirdRe + SpeechHelper.createBreak(1) + fourthRe);
		return gegenantwort;
	}

	
	private String shortRepromptErststimmeString() {
		// Create reprompt
		String firstRe = "Es sind die Themen Bildung, Soziales, Integration, Aussenpolitik, Sicherheit und Steuern und Finanzen erfasst";

		String secondRe = "Beispiel zum Direktkandaten: was denkt Kandidat, z.B. Florian Post �ber Integration";
		String thirdRe = "";
		String fourthRe = "Du kannst also direkt alle Kandidaten aller Wahlkreise mit vollem Namen ausw�hlen";

		String gegenantwort = SpeechHelper.wrapInSpeak(firstRe + SpeechHelper.createBreak(1) + secondRe
				+ SpeechHelper.createBreak(1) + thirdRe + SpeechHelper.createBreak(1) + fourthRe);
		return gegenantwort;
	}
	
	private String shortRepromptZweitstimmeString() {
		// Create reprompt
		String firstRe = "Es sind die Themen Bildung, Soziales, Integration, Aussenpolitik, Sicherheit und Steuern und Finanzen erfasst";

		String secondRe = "Beispiel zur Zweitstimme: wie m�chte die CDU die Wirtschaft st�rken";
		String thirdRe = "";
		String fourthRe = "Du kannst also direkt alle Kandidaten aller Wahlkreise mit vollem Namen ausw�hlen";

		String gegenantwort = SpeechHelper.wrapInSpeak(firstRe + SpeechHelper.createBreak(1) + secondRe
				+ SpeechHelper.createBreak(1) + thirdRe + SpeechHelper.createBreak(1) + fourthRe);
		return gegenantwort;
	}
	
	private String shortRepromptWahlsysString() {
		// Create reprompt
		String firstRe = "";
		String secondRe = "Beispiel zum Wahlsystem: Wie wird gew�hlt?";
		String thirdRe = "";
		String fourthRe = "";

		String gegenantwort = SpeechHelper.wrapInSpeak(firstRe + SpeechHelper.createBreak(1) + secondRe
				+ SpeechHelper.createBreak(1) + thirdRe + SpeechHelper.createBreak(1) + fourthRe);
		return gegenantwort;
	}
	
	public void onSessionEnded(SessionEndedRequest arg0, Session arg1) throws SpeechletException {
	}

	public void onSessionStarted(SessionStartedRequest arg0, Session arg1) throws SpeechletException {
	}

}
