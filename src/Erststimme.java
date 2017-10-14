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
 * accesses remote  json data at abgeordnetenwatch.de or local for retrieving  information about the candidates of a parliament.
 * If local, the parliament data is all contained in src/resources/parliament2profile.json.
 * If remote, the json file is mapped after creating the url. For creating the url the parliamentName is necessary.
 * It is possible to call() the Erststimme to receive a string of an answer to a political category for Alexa output.
 * Common category have a common top level Group. Mapping is done via class Themen, which has a GroupMapping attribute.
 * A question includes answer and category according to json profile of abgeordnetenwatch.de.
 * A Klient instance is created with a category and used with a url for retrieving the answer to a question concerning this category.
 * @author Rainer Wichmann, Severin Engelmann
 * @version 1.1, 13.10.2017
 */

public class Erststimme {

	// Attribs
	/**
	 * requested parliament
	 */
    private Parliament parliament; 
	private Parliament2Profile parliamentProfiles; //all profile from the requested parliament
	private CaseInsensitiveMap<String, String> mapCategorysToGroup; //mapping of Categorys to top level groups
	private String chosenCategory; //contains the Category the client has asked for
	private String getDataUrl1, getDataUrl2; //URL String for constructing questionUrl
	private String questionUrl; // URL where questions of a profile are stored
	private String thesesUrl;  // URL where theses of a profile are stored
	
	/**
	 * chosenCategory can be set to enable flexible answer to a different, hopefully similar issue
	 * @param category String representing the context
	 */
	public void setChosenCategory(String category) { this.chosenCategory = category; }

	//
	/**
	 * Creates a Erststimme by setting Attribs: mapCategorysToGroup, String getDataUrl1, String getDataUrl2 , Parliament parliament, parliamentProfile
	 * and stores the profiles of the Parliament in profiles.json locally
	 * @param parliamentName type String, Name of parliament as used in abgeordnetenwatch.de, mainly necessary for accessing parliament2profile.json from remote to create url
	 * @param mapping GroupMapping of political contexts, created in class Themen 
	 * @throws MalformedURLException if url is not correct, when accessing parliament2profile object remotely
	 * @throws IOException if Scanner cannot read src/resources/parliament2profile.json
	 */
	public Erststimme(String parliamentName, GroupMapping mapping) throws MalformedURLException, IOException {
		//set Parliament 
		parliament = new Parliament(); parliament.setName(parliamentName); 
		
		
		// set mapCategorysToGroup from list and 
		this.mapCategorysToGroup =mapping.mapCategoryToGroup;
		
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
	
	public Erststimme() {}

	/**
	 * Erststimme is called in this method to retrieve the information 
	 * @param category String representing political category
	 * @param candidateFullname String representing the fullname of the candidate
	 * calls setText with the same params
	 * @return String in ssml for audio output to Alexa
	 */
	public String call(String category, String candidateFullname) {
		/* testing purpose
		String contentOfCategory = "Familie";
		String contentOfFirstname = parliamentProfile.getProfiles().get(0).getPersonal().getFirstName();
		String contentOfLastname = parliamentProfile.getProfiles().get(0).getPersonal().getLastName();
		 String contentOfCategory = getData.getSlot("category").getValue();
		 String contentOfFirstname = getData.getSlot("vorname").getValue();
		 String contentOfLastname = getData.getSlot("nachname").getValue();
		 */
		// set selected
		this.chosenCategory=category;
		
		// local value strings for output to Alexa including dummy ;
		String set = new String("Text not set");
		
		try {	// get String in format for Alexa from textFromProfile, using Klient
		set = textFromProfile(candidateFullname);
		} catch (IOException e) {		e.printStackTrace();	}
		//System.out.println(set);

		return set;
	}

	/**
	 * setText() is usable for defining the String set of a SsmlOutputSpeech variable
	 * @param candidateFullname String  of Firstname and Lastname of person in profile
	 ** Urls are hardcoded here
	 * @throws JsonParseException ,when Json Parsing fails
	 * @throws JsonMappingException , when JsonMapping fails
	 * @throws MalformedURLException , when URL is malformed
	 * @throws IOException , IOException occurs
	 * @return String set
	 */
	private String textFromProfile(String candidateFullname)
			throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
		String set;

		// category is available
		if (mapCategorysToGroup.containsKey(chosenCategory)){

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
					return SpeechHelper.wrapInSpeak(wrongName("Kandidat" + candidateFullname));
				}
			}

			// create URLs 
			
			// url for answer to questions
			
			// set jsonString from remote URL
			// set format for URL String for questionUrl
			this.getDataUrl1 = "https://www.abgeordnetenwatch.de/api/profile/"; // +angela-merkel+ = content from profile.meta.username
			this.getDataUrl2 = "/profile.json";
			this.questionUrl = getDataUrl1 + p.getMeta().getUsername()+ getDataUrl2;
			//System.out.println(questionUrl);
			
			 // url for answer to theses
			this.thesesUrl = p.getMeta().getUrl();
			//System.out.println(thesesUrl);
			
			
			//create Klient, use it to get answer to Question concerning Category
			Klient k = new Klient(chosenCategory);
			set = k.getText(questionUrl, this);

			
			
		// Category not available
		} else {
			set = SpeechHelper.wrapInSpeak(wrongCategory(chosenCategory));
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
	public String alternativeAnswerfromSpeechlet() {	return "alternativer Answer Dummy";}
	
	/**
	 * called to get a response, when Erststimme is asked for a name not included in database of profiles
	 * @param candidateFullname String of the Full name of the candidate
	 * @return String , which represents the explanation
	 */
	public String wrongName(String candidateFullname) {return candidateFullname + " wurde leider in der Datenbank des Parlaments" + parliament.getName() + " nicht gefunden.";}
	
	/**
	 * called to get a response, when Erststimme is askeded for a category not included in database of categories
	 * @param category representing context, which is not found
	 * @return String , which represents the explanation
	 */
	public String wrongCategory(String category) {return category + " wurde leider in der Themen-Datenbank nicht gefunden.";	}
	
	
}
