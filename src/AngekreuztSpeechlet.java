
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
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


public class AngekreuztSpeechlet implements Speechlet {
	public SpeechletResponse onIntent(IntentRequest arg0, Session arg1) throws SpeechletException {

		Intent intent = arg0.getIntent();
		String input = intent.getName();
		SpeechletResponse r = new SpeechletResponse();
		PlainTextOutputSpeech text = new PlainTextOutputSpeech();
		String result = "";
		Document doc = null;
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

	public SpeechletResponse onLaunch(LaunchRequest arg0, Session arg1) throws SpeechletException {return null;}
	public void onSessionEnded(SessionEndedRequest arg0, Session arg1) throws SpeechletException {}
	public void onSessionStarted(SessionStartedRequest arg0, Session arg1) throws SpeechletException {}
	
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


