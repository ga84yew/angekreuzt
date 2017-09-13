/**
    Copyright 2014-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.

    Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with the License. A copy of the License is located at

        http://aws.amazon.com/apache2.0/

    or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.client.ClientConfig;
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
import com.amazon.speech.ui.SsmlOutputSpeech;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import parliament2profile.*;

/**
 * This sample shows how to create a simple speechlet for handling speechlet
 * requests.
 */
public class ThreeQMSpeechlet implements Speechlet {
	// Attribs
	List<String> categoriesList;
	Parliament2Profile bundestagsProfile;
	Parliament paulskirche;
	private static final Logger log = LoggerFactory.getLogger(ThreeQMSpeechlet.class);

	public void setChosenCategory(String cat) {
		chosenCategory = cat;
	}

	private String chosenCategory, getDataUrl1, getDataUrl2;

	public ThreeQMSpeechlet() throws URISyntaxException, JsonParseException, JsonMappingException, IOException {

		// define Parliament
		Parliament paulskirche = new Parliament();
		paulskirche.setName("Hamburg");

		// set categoriesList from constant list and jsonString from remote URL
		String[] categories = { "Arbeit", "Integration", "Sicherheit", "Aussenpolitik", "Finanzen", "Bildung" };
		this.categoriesList = Arrays.asList(categories);

		// JSON from file to Object
		File f = new File("profiles.json");
		FileUtils.copyURLToFile(new URL("https://www.abgeordnetenwatch.de/api/parliament/hamburg/profiles.json"), f);
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		Parliament2Profile bundestagsProfile = mapper.readValue(f, Parliament2Profile.class);

		// set format for URL String
		this.getDataUrl1 = "https://www.abgeordnetenwatch.de/api/profile/";
		// +angela-merkel+
		this.getDataUrl2 = "/profile.json";
	}

	@Override
	public void onSessionStarted(final SessionStartedRequest request, final Session session) {
	}

	// SpeechletResponse onLaunch
	@Override
	public SpeechletResponse onLaunch(final LaunchRequest request, final Session session) {
		return null;
	}

	// SpeechletResponse onIntent
	public SpeechletResponse onIntent(final IntentRequest request, final Session session) throws SpeechletException {//
		//request and session
		log.info("onIntent requestId={}, sessionId={}",
		request.getRequestId(),
		session.getSessionId());

		// get Values from Alexa
		
		Intent getData = request.getIntent(); String intent_name=
		getData.getName(); System.out.println(intent_name);
		
		String contentOfCategory = "Familie";
		String contentOfFirstname = bundestagsProfile.getProfiles().get(0).getPersonal().getFirstName();
		String contentOfLastname = bundestagsProfile.getProfiles().get(0).getPersonal().getLastName();
		/*
		 * String contentOfCategory = getData.getSlot("category").getValue();
		 * String contentOfFirstname = getData.getSlot("vorname").getValue();
		 * String contentOfLastname = getData.getSlot("nachname").getValue();
		 */
		// local value strings for output to Alexa including dummy texts
		SsmlOutputSpeech text = new SsmlOutputSpeech();	text.setSsml(SpeechHelper.wrapInSpeak("Text not set")); 
		String set=new String("Text not set"); // string for input into setSsml()

		//call to define text
		try {
			set = setText(contentOfCategory, contentOfFirstname, contentOfLastname);
		} catch (IOException e) {e.printStackTrace();}
		System.out.println(set);
		
		//set text
		text.setSsml(set);

		// output to alexa
		SpeechletResponse r = new SpeechletResponse();
		r.setOutputSpeech(text);
		return r;
	}

	private String setText(String contentOfCategory, String contentOfFirstname, String contentOfLastname)
			throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
		String set;

		// correct category?
		if (categoriesList.contains(contentOfCategory)) {

			// get correct Profile p
			ListIterator<Profile> it = bundestagsProfile.getProfiles().listIterator();
			Profile p;

			while (it.hasNext()) {
				p = it.next();

				if (p.getPersonal().getFirstName().equals(contentOfFirstname)
						&& p.getPersonal().getLastName().equals(contentOfLastname)) {
					break; // profile found
				}

				// profile not found
				if (!it.hasNext()) {
					return SpeechHelper.wrapInSpeak(wrongname(contentOfFirstname + "-" + contentOfLastname));
				}
			}

			// create URL string from format and name
			String getDataUrl = getDataUrl1 + contentOfFirstname + "-" + contentOfLastname + getDataUrl2;
			System.out.println(getDataUrl);
			Klient k = new Klient();
			set = k.getData(contentOfCategory, getDataUrl, this);

			// category not possible
		} else {
			set = SpeechHelper.wrapInSpeak(wrongcategory(contentOfCategory));
		}
		return set;
	}

	public boolean noAnswer() {// called by client to check if Speechhelper
								// wants to create an alternative Answer
		return false;
	}

	public String alternativeAnswerfromSpeechelper() {// called by client to get
														// an alternative Answer
		return "alternative Answer Dummy";
	}

	public String wrongname(String fullname) {
		return fullname + " wurde leider in der Datebank des Parlaments" + paulskirche.getName() + " nicht gefunden.";
	}

	public String wrongcategory(String category) {
		return category + " wurde leider in der Themen-Datebank nicht gefunden.";
	}

	// onSessionEnded
	@Override
	public void onSessionEnded(final SessionEndedRequest request, final Session session) throws SpeechletException {
		/*
		 * log.info("onSessionEnded requestId={}, sessionId={}",
		 * request.getRequestId(), session.getSessionId());
		 */
		// any cleanup logic goes here
	}

	/**
	 * Creates and returns a {@code SpeechletResponse} with a welcome message.
	 *
	 * @return SpeechletResponse spoken and visual response for the given intent
	 */
	private SpeechletResponse getWelcomeResponse() {
		return null;
	}

	/**
	 * Creates a {@code SpeechletResponse} for the hello intent.
	 *
	 * @return SpeechletResponse spoken and visual response for the given intent
	 */
	private SpeechletResponse getHelloResponse() {
		return null;
	}

	/**
	 * Creates a {@code SpeechletResponse} for the help intent.
	 *
	 * @return SpeechletResponse spoken and visual response for the given intent
	 */
	private SpeechletResponse getHelpResponse() {
		return null;
	}
}
