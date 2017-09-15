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
    
	/**

		 * Die drei unterschiedlichen Intents werden in drei Bedinungen abgefragt: 
		 * Das Intent "wahlsys" übergibt den Slottypen "wahlsystem" als String an die Methode "auswaehlen in der Klasse "Wahlthema" und ruft diese auf.
		 * Schließlich wird das Result dann wieder an das "SpeechletResponse"-Objekt "r" übergeben.
		
	 * @param arg0 IntentRequest pipa
	 * @return SpeechletResponse for out to Alexa
	 */
	public SpeechletResponse onIntent(IntentRequest arg0, Session arg1) throws SpeechletException {
		
		// Create reprompt
        String  firstRe= "Schön, dass du dich über die Wahl am 24.September informieren willst. Das ist Super. Wähle ja keinen Schmarrn.";
        		
		String secondRe= "Ok also, frag mich zum Beispiel wie funktioniert die Wahl in Deutschland oder frage zum Beispiel was denkt Kandidat xy über Bildung, Arbeit und Soziales, Integration und "
				+ "Asyl, E U und Aussenpolitik, Sicherheit und Steuern und Finanzen.";
		String	thirdRe= "Oder denk an deine Zweitstimme und frage ganz einfach:"
				+ "was sagt die Partei xy über das thema Bildung, Sicherheit, Steuern und Finanzen, Integration und Asyl"+ ", E U und Aussenpolitik und Arbeit und Soziales.";
		String	fourthRe="Du kannst also direkt alle Kandidaten aller Wahlkreise oder die Parteien befragen. Yeah man.";
    	
        String gegenantwort=SpeechHelper.wrapInSpeak(firstRe
    			+ SpeechHelper.createBreak(1) 
    			+secondRe
    			+ SpeechHelper.createBreak(1)
    			+thirdRe
    			+ SpeechHelper.createBreak(1)
    			+ fourthRe);
               
        SsmlOutputSpeech antworten = new SsmlOutputSpeech();
        antworten.setSsml(gegenantwort);
		Reprompt reprompt = new Reprompt(); 
		reprompt.setOutputSpeech(antworten);
	
		
		Intent intent = arg0.getIntent();
		String input = intent.getName();
		SpeechletResponse r = new SpeechletResponse();
		PlainTextOutputSpeech text = new PlainTextOutputSpeech();
		String result = "";

		if(input.equals("wahlsys")){
			result = Wahlsystem.getText();
			text.setText(result);
			return SpeechletResponse.newAskResponse(text, reprompt);

		} else if (input.equals("zweitstimme")) {
			String themen = intent.getSlot("themen").getValue();
			String partei = intent.getSlot("partei").getValue();
			result = ZweitStimme.auswahl(themen, partei);
			text.setText(result);
			return SpeechletResponse.newAskResponse(text, reprompt);

		} else if (input.equals("erststimme")) {

			String themen = intent.getSlot("themen").getValue();
			String fullname = intent.getSlot("kandidat").getValue();
			Themen themes = new Themen();
			try {
				Erststimme erstestimme = new Erststimme("Bundestag",themes.mapping);
			return SpeechletResponse.newAskResponse(erstestimme.call(themen, fullname),reprompt);
				
			} catch (IOException e) {e.printStackTrace();}
			
		} else if(input.equals("nichterkannt")) {
			
			text.setText("Bitte wiederhole deine Anfrage und nuschel nicht so.");
			return SpeechletResponse.newAskResponse(text, reprompt);     
			
		} else if( input.equals("AMAZON.StopIntent") || input.equals("AMAZON.CancelIntent") ) {
			
			text.setText("Servus");
			return SpeechletResponse.newTellResponse(text);
			
		}
		
		text.setText(result);
		return SpeechletResponse.newAskResponse(text, reprompt);
	}
	/**
	 * Es wird wiederum ein neues PlainTextOutputSpeech-Objekt "text" initialisiert, das den "Willkommen bei angekreuzt"-String als Text liest, wenn der User die App startet.
*/
	   public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
		         throws SpeechletException {
		    	log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),session.getSessionId());
		        return getWelcomeResponse();
		    }
//Es muss noch ein Reprompt PlainTextOutputSpeech-Objekt initialisiert werden, falls der User nach der "welcome"-Nachricht nicht antwortet.
	  
	   public void onSessionEnded(SessionEndedRequest arg0, Session arg1) throws SpeechletException {}
	   public void onSessionStarted(SessionStartedRequest arg0, Session arg1) throws SpeechletException {}
	
	private SpeechletResponse getWelcomeResponse() {
		String first="Willkommen, du kannst nach Parteien, Themen und Kandidaten fragen";
		String second="zum Beispiel";
		String third="was denkt die Partei XY zum Thema Integration,Sicherheit, E U Aussenpolitik, Bildung, Arbeit, Finanzen und Steuern?";
		String fourth = "Du kannst auch jeden Direktkandidaten zu den Themen fragen";
		
		String text=SpeechHelper.wrapInSpeak(first);
		//+ SpeechHelper.createBreak(1) 
		//+second 
		//+ SpeechHelper.createBreak(1)
		//+third 
		//+ SpeechHelper.createBreak(1)
		//+ fourth);
	
	        SsmlOutputSpeech output= new SsmlOutputSpeech();
			output.setSsml(text);
 
	        // Create the Simple card content.
	        SimpleCard card = new SimpleCard();
	        card.setTitle("Angekreuzt");
	        card.setContent(first+second+third+fourth);

        

	    	// Create reprompt
	        String  firstRe= "Schön, dass du dich über die Wahl am 24.September informieren willst. Das ist Super. Wähle ja keinen Schmarrn.";
	        		
			String secondRe= "Ok also, frag mich zum Beispiel wie funktioniert die Wahl in Deutschland oder frage zum Beispiel was denkt Kandidat xy über Bildung, Arbeit und Soziales, Integration und "
					+ "Asyl, E U und Aussenpolitik, Sicherheit und Steuern und Finanzen.";
			String	thirdRe= "Oder denk an deine Zweitstimme und frage ganz einfach:"
					+ "was sagt die Partei xy über das thema Bildung, Sicherheit, Steuern und Finanzen, Integration und Asyl"+ ", E U und Aussenpolitik und Arbeit und Soziales.";
			String	fourthRe="Du kannst also direkt alle Kandidaten aller Wahlkreise oder die Parteien befragen. Yeah man.";
	    	
	        String gegenantwort=SpeechHelper.wrapInSpeak(firstRe
	    			+ SpeechHelper.createBreak(1) 
	    			+secondRe
	    			+ SpeechHelper.createBreak(1)
	    			+thirdRe
	    			+ SpeechHelper.createBreak(1)
	    			+ fourthRe);
	        
	        
	        SsmlOutputSpeech antworten = new SsmlOutputSpeech();
	        antworten.setSsml(gegenantwort);
			Reprompt reprompt = new Reprompt(); 
			reprompt.setOutputSpeech(antworten);

	        return SpeechletResponse.newAskResponse(output, reprompt, card);
	}

}


