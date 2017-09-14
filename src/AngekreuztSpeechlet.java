
import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.nio.file.Paths;

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
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import parliament2profile.Parliament2Profile;

public class AngekreuztSpeechlet implements Speechlet {
	public SpeechletResponse onIntent(IntentRequest arg0, Session arg1) throws SpeechletException {
		
		Intent intent = arg0.getIntent();
		
		ArrayList<String> categoriesList;
		String input = intent.getName();
		
		SpeechletResponse r = new SpeechletResponse();
		PlainTextOutputSpeech text = new PlainTextOutputSpeech();
		String result = "";
		Document doc = null; 
		//System.out.println(input);
		/**
		 * 
		 */
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
			result = ZweitStimme.auswahl(themen, partei);
			text.setText(result);
			r.setOutputSpeech(text);
			
		} else if (input.equals("erststimme")) {
	  		
			String themen = intent.getSlot("themen").getValue();
			String fullname = intent.getSlot("vorname").getValue()+intent.getSlot("nachname").getValue();
			Themen themes = new Themen();
			//categoriesList.addAll(Arrays.asList("Integration und Asyl","finanzen","sicherheit","bildung","Arbeit und Soziales","Eu und aussenpolitik"));
			try {
				Erststimme erstestimme = new Erststimme("Bundestag",themes.getListofTopthemes());
				r= erstestimme.call(themen, fullname);
			} catch (IOException e) {e.printStackTrace();}
		}

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


