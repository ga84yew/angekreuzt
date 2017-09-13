import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.SsmlOutputSpeech;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import parliament2profile.Parliament;
import parliament2profile.Parliament2Profile;
import parliament2profile.Profile;

public class Erststimme {

	// Attribs
	private String chosenCategory, getDataUrl1, getDataUrl2;
	List<String> categoriesList;
	Parliament2Profile bundestagsProfile;
	Parliament paulskirche;
	private static final Logger log = LoggerFactory.getLogger(ThreeQMSpeechlet.class);


	/**
	 * chosenCategory can be set to enable flexible answer
	 * @param String cat
	 */
	public void setChosenCategory(String cat) {
		chosenCategory = cat;
	}

	//
	/**
	 * Creates a Erststimme by setting Attribs: List<String> categoriesList, String getDataUrl1, String getDataUrl2 , Parliament paulskirche, bundestagsProfile
	 * and stores the profiles of the Parliament in profiles.json locally
	 * @param String parliamentName: Name of parliament as used in abgeordnetenwatch.de
	 * @param String[] categories as used in abgeordnetenwatch.de
	 * 
	 */
	public Erststimme(String parliamentName, String[] categories) throws MalformedURLException, IOException {
		// define Parliament
		Parliament paulskirche = new Parliament();
		paulskirche.setName("parliamentName)");

		// set categoriesList from constant list and jsonString from remote URL
		//String[] categories = { "Arbeit", "Integration", "Sicherheit", "Aussenpolitik", "Finanzen", "Bildung" };
		this.categoriesList = Arrays.asList(categories);

		// set format for URL String
		this.getDataUrl1 = "https://www.abgeordnetenwatch.de/api/profile/";
		// +angela-merkel+
		this.getDataUrl2 = "/profile.json";
		
		// JSON from file to Object
		File f = new File("profiles.json");
		FileUtils.copyURLToFile(new URL("https://www.abgeordnetenwatch.de/api/parliament/hamburg/profiles.json"), f);
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		this.bundestagsProfile = mapper.readValue(f, Parliament2Profile.class);

	}
	
	/**
	 * Erststimme is called with 
	 * @param String contentOfCategory: name of category
	 * @param String contentOfFirstname : Firstname of person in profile
	 *@param String contentOfLastnam: Lastname of person in profile
	 * calls setText with the same params
	 * @return SpeechletResponse for audio output to Alexa
	 */
	public SpeechletResponse call(String contentOfCategory, String contentOfFirstname, String contentOfLastname) {
		String contentOfCategory = "Familie";
		String contentOfFirstname = bundestagsProfile.getProfiles().get(0).getPersonal().getFirstName();
		String contentOfLastname = bundestagsProfile.getProfiles().get(0).getPersonal().getLastName();
		/*
		 * String contentOfCategory = getData.getSlot("category").getValue();
		 * String contentOfFirstname = getData.getSlot("vorname").getValue();
		 * String contentOfLastname = getData.getSlot("nachname").getValue();
		 */
		// local value strings for output to Alexa including dummy texts
		SsmlOutputSpeech text = new SsmlOutputSpeech();
		text.setSsml(SpeechHelper.wrapInSpeak("Text not set"));
		String set = new String("Text not set"); // string for input into
													// setSsml()

		// call to define text
		try {
			set = setText(contentOfCategory, contentOfFirstname, contentOfLastname);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	 * @return String set
	 */
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

	/**
	 * called by Klient to check if Speechhelper wants to create an alternative Answer if Klient gets no answer
	 * @return boolean
	 */
	public boolean noAnswer() {
								 
		return false;
	}
	/**
	 * called by Klient to get the alternative Answer from Erststimme
	 * @return String , which represents the alternative Answer
	 */
	public String alternativeAnswerfromSpeechelper() {// called by client to get
														// an alternative Answer
		return "alternative Answer Dummy";
	}
	
	/**
	 * called to get a response, when Erststimme is asked for a name not included in database of profiles
	 * @return String , which represents the explanation
	 */
	public String wrongname(String fullname) {
		return fullname + " wurde leider in der Datebank des Parlaments" + paulskirche.getName() + " nicht gefunden.";
	}
	
	/**
	 * called to get a response, when Erststimme is askeded for a category not included in database of categories
	 * @return String , which represents the explanation
	 */
	public String wrongcategory(String category) {
		return category + " wurde leider in der Themen-Datebank nicht gefunden.";
	}
}