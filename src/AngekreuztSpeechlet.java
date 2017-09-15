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
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;


public class AngekreuztSpeechlet implements Speechlet {
    private static final Logger log = LoggerFactory.getLogger(AngekreuztSpeechlet.class);
	public SpeechletResponse onIntent(IntentRequest arg0, Session arg1) throws SpeechletException {

		Intent intent = arg0.getIntent();
		String input = intent.getName();
		SpeechletResponse r = new SpeechletResponse();
		PlainTextOutputSpeech text = new PlainTextOutputSpeech();
		String result = "";
		//Document doc = null;
		//System.out.println(input);
	
		System.out.println("Intent: "+ input);
		if(input.equals("wahlsys")){
			String wahlsystemString = intent.getSlot("wahlsystem").getValue();
			System.out.println("Input slot value: "+wahlsystemString );
			result = Wahlthema.auswaehlen(wahlsystemString );
			System.out.println("Result: "+result);
			text.setText(result);
			r.setOutputSpeech(text);

		} else if (input.equals("zweitstimme")) {
			String themen = intent.getSlot("themen").getValue();
			String partei = intent.getSlot("partei").getValue();
			String parteien = intent.getSlot("Parteien").getValue();
			System.out.println("Themen: " + themen);
			System.out.println("Partei: " + partei);
			System.out.println("Parteien: " + parteien);
			result = ZweitStimme.auswahl(themen, partei);
			text.setText(result);
			r.setOutputSpeech(text);

		} else if (input.equals("erststimme")) {

			String themen = intent.getSlot("themen").getValue();
			String fullname = intent.getSlot("kandidat").getValue();
			Themen themes = new Themen();
			try {
				Erststimme erstestimme = new Erststimme("Bundestag",themes.mapping);
			r= erstestimme.call(themen, fullname);
			} catch (IOException e) {e.printStackTrace();}
		}

		return r;
	}

	   public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
		         throws SpeechletException {
		    	log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),session.getSessionId());
		        return getWelcomeResponse();
		    }

	   public void onSessionEnded(SessionEndedRequest arg0, Session arg1) throws SpeechletException {}
	   public void onSessionStarted(SessionStartedRequest arg0, Session arg1) throws SpeechletException {}
	
	private SpeechletResponse getWelcomeResponse() {
		String first="Willkommen, du kannst nach Parteien, Themen und Kandidaten fragen.";
		String second="zum Beispiel";
		String third="was denkt die AÖP zum Thema Wandfarbe?";
	        String speechText = first+second+third;

	        // Create the Simple card content.
	        SimpleCard card = new SimpleCard();
	        card.setTitle("Angekreuzt");
	        card.setContent(speechText);

	        // Create the plain text output.
	        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
	        speech.setText(speechText);

	        // Create reprompt
	        Reprompt reprompt = new Reprompt();
	        reprompt.setOutputSpeech(speech);

	        return SpeechletResponse.newAskResponse(speech, reprompt, card);
	}
	
	/* Testing purpose for Erststimme*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String themen = "Familie";
		String fullname = "Jens-Eberhard Jahn";
		Themen themes = new Themen();
		SpeechletResponse r = new SpeechletResponse();
		try {
			Erststimme erstestimme = new Erststimme("Bundestag",themes.mapping);
			r= erstestimme.call(themen, fullname);
		} catch (IOException e) {e.printStackTrace();}

	}
	
	
}


