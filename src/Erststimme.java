import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ListIterator;
import java.util.Scanner;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.SsmlOutputSpeech;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/* JSON from file to Object: imports only necessary when remote access to parliament2profile.json in constructor
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
*/

import parliament2profile.*;
/**
 * accesses remote  json data at abgeordnetenwatch.de or local for retrieving  information about the candidates of a parliament
 * if local, the parliament data is all contained in src/resources/parliament2profile.json
 * if remote the json file is mapped after creating the url. For creating the url the parliamentName is necessary.
 * It is possible to call the Erststimme to receive a SpeechletResponse including a answer to a political context for Alexa output
 * A context is called subGroup and they have common top level Groups. Mapping is done via class Themen, which has a GroupMapping attribute.
 * A question includes answer and subGruop, which is called category according to json profile of abgeordnetenwatch.de
 * A Klient instance is created with a subGruop and used with a url for retrieving the answer to a questions from this context.
 * @author Rainer Wichmann
 * @version 1.0, 15.9.2017
 */

public class Erststimme {

	// Attribs
	/**
	 * requested parliament
	 */
    private Parliament parliament; 
	private Parliament2Profile parliamentProfiles; //all profile from the requested parliament
	private CaseInsensitiveMap<String, String> mapSubGroupsToGroup; //mapping of subgroups to top level groups
	private String chosenSubGroup; //contains the subgroup the client has asked for
	private String getDataUrl1, getDataUrl2; //URL String for constructing questionUrl
	private String questionUrl; // URL where questions of a profile are stored
	private String thesesUrl;  // URL where theses of a profile are stored
	
	/**
	 * chosenCategory can be set to enable flexible answer to a different, hopefully similar issue
	 * @param subGroup String representing the context
	 */
	public void setChosenSubGroup(String subGroup) { this.chosenSubGroup = subGroup; }

	//
	/**
	 * Creates a Erststimme by setting Attribs: mapSubGroupsToGroup, String getDataUrl1, String getDataUrl2 , Parliament parliament, parliamentProfile
	 * and stores the profiles of the Parliament in profiles.json locally
	 * @param parliamentName type String, Name of parliament as used in abgeordnetenwatch.de, mainly necessary for accessing parliament2profile.json from remote to create url
	 * @param mapping GroupMapping of political contexts, created in class Themen 
	 * @throws MalformedURLException if url is not correct, when accessing parliament2profile object remotely
	 * @throws IOException if Scanner cannot read src/resources/parliament2profile.json
	 */
	public Erststimme(String parliamentName, GroupMapping mapping) throws MalformedURLException, IOException {
		//set Parliament 
		parliament = new Parliament(); parliament.setName(parliamentName); 
		
		
		// set mapSubGroupsToGroup from list and 
		this.mapSubGroupsToGroup =mapping.mapSubGroupsToGroup;
		
		//set attrib parliamentProfile:
		//define mapper
		ObjectMapper mapper = new ObjectMapper();
  		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
  		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
  		
		/* JSON from file to Object: remote access to parliament2profile.json
		File file = new File("profiles.json");
		FileUtils.copyURLToFile(new URL("https://www.abgeordnetenwatch.de/api/parliament/"+parliament.getName()+"/profiles.json"), file);
		this.parliamentProfile = mapper.readValue(file, Parliament2Profile.class);
		*/

		// JSON from file to Object: local access to parliament2profile.json
  		File file= new File("src/resources/parliament2profile.json");
  		Scanner s =new Scanner(file);
		String fileString=s.useDelimiter("\\Z").next();
		s.close();
		this.parliamentProfiles = mapper.readValue(fileString, Parliament2Profile.class);

	}
	
	/**
	 * Erststimme is called with 
	 * @param subGroup String representing context
	 * @param candidateFullname String representing the fullname of the candidate
	 * calls setText with the same params
	 * @return SpeechletResponse for audio output to Alexa
	 */
	public SpeechletResponse call(String subGroup, String candidateFullname) {
		/* testing purpose
		String contentOfCategory = "Familie";
		String contentOfFirstname = parliamentProfile.getProfiles().get(0).getPersonal().getFirstName();
		String contentOfLastname = parliamentProfile.getProfiles().get(0).getPersonal().getLastName();
		 String contentOfCategory = getData.getSlot("category").getValue();
		 String contentOfFirstname = getData.getSlot("vorname").getValue();
		 String contentOfLastname = getData.getSlot("nachname").getValue();
		 */
		// set selected
		this.chosenSubGroup=subGroup;
		
		// local value strings for output to Alexa including dummy texts
		SsmlOutputSpeech text = new SsmlOutputSpeech();
		text.setSsml(SpeechHelper.wrapInSpeak("Text not set"));
		String set = new String("Text not set"); // string for use setSsml()
		
		try {	// get String in format for Alexa from textFromProfile, using Klient
		set = textFromProfile(candidateFullname);
		} catch (IOException e) {		e.printStackTrace();	}
		System.out.println(set);

		// set text
		text.setSsml(set);

		// output to alexa
		SpeechletResponse r = new SpeechletResponse();
		r.setOutputSpeech(text);
		return r;
	}

	/**
	 * setText() is usable for defining the String set in a  setSsml(String s) function of a SsmlOutputSpeech variable
	 * @param String contentOfCategory: name of category
	 * @param String contentOfFirstname : Firstname of person in profile
	 * @param String contentOfLastnam: Lastname of person in profile
	 ** Urls are hardcoded here
	 * @return String set
	 */
	private String textFromProfile(String candidateFullname)
			throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
		String set;

		// subGroup is available
		if (mapSubGroupsToGroup.containsKey(chosenSubGroup)){

			// get correct Profile p
			ListIterator<Profile> it = parliamentProfiles.getProfiles().listIterator();
			Profile p=new Profile();

			while (it.hasNext()) {//iterate over over profiles contained in parliamentProfile
				p = it.next();

				if (p.getPersonal().getSimpleFullName().equals(Delegate.lowercase(candidateFullname))){
					break; //profile found
				}
				// profile not found
				if (!it.hasNext()) {
					return SpeechHelper.wrapInSpeak(wrongname("Kandidat" + candidateFullname));
				}
			}

			// create URLs 
			
			// url for answer to questions
			
			// set jsonString from remote URL
			// set format for URL String for questionUrl
			this.getDataUrl1 = "https://www.abgeordnetenwatch.de/api/profile/"; // +angela-merkel+ = content from profile.meta.username
			this.getDataUrl2 = "/profile.json";
			this.questionUrl = getDataUrl1 + p.getMeta().getUsername()+ getDataUrl2;
			System.out.println(questionUrl);
			
			 // url for answer to theses
			this.thesesUrl = p.getMeta().getUrl();
			System.out.println(thesesUrl);
			
			
			//create Klient, use it to get answer to Question concerning subGroup
			Klient k = new Klient(chosenSubGroup);
			set = k.getText(questionUrl, this);

			
			
		// subGroup not available
		} else {
			set = SpeechHelper.wrapInSpeak(wrongSubGroup(chosenSubGroup));
		}
		return set;
	}

	/**
	 * called by Klient to check if Speechhelper wants to create an alternative Answer if Klient gets no answer
	 * @return boolean
	 */
	public boolean noAnswer() {
								 
		return false;
	}
	/**
	 * called to get the alternative Answer from Erststimme
	 * @return String , which represents the alternative Answer
	 */
	public String alternativeAnswerfromSpeechlet() {	return "alternative Answer Dummy";}
	
	/**
	 * called to get a response, when Erststimme is asked for a name not included in database of profiles
	 * @param candidateFullname String of the Full name of the candidate
	 * @return String , which represents the explanation
	 */
	public String wrongname(String candidateFullname) {return candidateFullname + " wurde leider in der Datebank des Parlaments" + parliament.getName() + " nicht gefunden.";}
	
	/**
	 * called to get a response, when Erststimme is askeded for a category not included in database of categories
	 * @param subGroup representing context, which is not found
	 * @return String , which represents the explanation
	 */
	public String wrongSubGroup(String subGroup) {return subGroup + " wurde leider in der Themen-Datebank nicht gefunden.";	}
	
	
}
