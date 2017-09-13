
import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;

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
		Intent zweitstimme = arg0.getIntent();
		
		
		String input = zweitstimme.getName();
		Erststimme erstestimme = new Erststimme();
		
		Intent wahlsystem = arg0.getIntent();
		String input = wahlsystem.getname();
		Intent erststimme = arg0.getIntent();
		String input = arg0.getIntent();
		SpeechletResponse r = new SpeechletResponse();
		PlainTextOutputSpeech text = new PlainTextOutputSpeech();
		String result = "";
		Document doc = null; 
		System.out.println(input);
		/**
		 * 
		 */
		if {
			String wahlsystem = wahlsystem.getSlot("wahlsystem").getValue();
			result = Wahlthema.auswaehlen(wahlsystem);

		} else {
			String themen = zweitstimme.getSlot("themen").getValue();
			String partei = zweitstimme.getSlot("partei").getValue();
			result = ZweitStimme.auswahl(themen, partei);
		} 
		text.setText(result);
		r.setOutputSpeech(text);
		return r;
	}

	public SpeechletResponse onLaunch(LaunchRequest arg0, Session arg1) throws SpeechletException {
		// TODO Auto-generated method stub
		return null;
	}
	public void onSessionEnded(SessionEndedRequest arg0, Session arg1) throws SpeechletException {
		// TODO Auto-generated method stub
	}
	public void onSessionStarted(SessionStartedRequest arg0, Session arg1) throws SpeechletException {
		// TODO Auto-generated method stub
	}
}


