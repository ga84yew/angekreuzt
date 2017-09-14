import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.util.Pair;
import parliament2profile.Parliament2Profile;
import singleprofile.*;

public class Klient {
	
	//attribs
	String category, firstname, lastname;
	SingleProfile sp;
	Question question;
	String outputstring;
	
	
	/**
	 * check if it is possible to get an answer from the person, defined in the SingleProfile sp, to a question from the category
	 * using attributes sp and category
	 * sets attribute question if there is an answer to the Question
	 * @ return boolean
	 */
	public boolean questionAnswered(){
		// get correct Question q
		ListIterator<Question> itQ  = sp.getProfile().getQuestions().listIterator();
		Question localQ=new Question();
		
		//save all categories answered, step1
		ArrayList<Question> allAnsweredQuestions=new ArrayList<Question> (); 
		ArrayList<String> allAnsweredQuestionCategories=new ArrayList<String>();	
		
		while (itQ.hasNext()){//iterate over all Questions in profile
			localQ =itQ.next();
			
			// question found:
			if (localQ.getCategory().contains(category)){ //category is contained
				
				if (!localQ.getAnswers().isEmpty()){ //Answer not empty
					this.question=localQ;
					return true;  //no action more necessary!
				}			
			}
			//save all categories answered, step2
			if ( !localQ.getAnswers().isEmpty()	){
				allAnsweredQuestions.add(localQ);
				allAnsweredQuestionCategories.add(localQ.getCategory());
			}
		}
		
		//looking for alternative themes from combination of all Themes and  allAnsweredQuestioncategories
		Themen Topthemes = new Themen(); ListIterator<ArrayList<String>>  itTopthemen = Topthemes.getListofTopthemes().listIterator();
		ArrayList<String>TThemes = new ArrayList<String>();
		
		Iterator<String> itThemen; String cat = "";
		
		while (itTopthemen.hasNext()){ //iterate over all Topthemes
			TThemes=itTopthemen.next();
			if (TThemes.contains(category)){ //if category is part of Topthemes
			itThemen=TThemes.iterator();
				while (itThemen.hasNext()){ //iterate over all themes in here
					cat=itThemen.next();
					if (allAnsweredQuestionCategories.contains(cat)){ // theme here is the same as in 
						int i =allAnsweredQuestionCategories.indexOf(cat); //get index
						this.question=allAnsweredQuestions.get(i); // set question
						return true;
					}
				}
			}
				
		}
		
		
		// question not found
		return false;
	}
	
	/**
	 * Klient.getData() is called to return an answer from a profile, which is available via the String getDataUrl, to the category
	 * attributes sp and category  and firstname and lastname are set
	 * creates a client asking to receive a SingleProfile from getDataUrl
	 * if no answer is availabe, it asks the Caller , a Erststimme erst, via erst.noAnswer() erst.alternativeAnswerfromSpeechelper() to return an alternative answer
	 * if erst has no alternative answer, a simple dummy answer is returned
	 * @ param String category
	 * @ param String getDataUrl
	 * @ param Erststimme erst
	 * @ return String representing the answer
	 */
	public String getData(String category, String getDataUrl, Erststimme erst) throws JsonParseException, JsonMappingException, MalformedURLException, IOException {	
		
		this.category=category;

		 Client client = ClientBuilder.newClient( new ClientConfig() );
		 WebTarget webTarget = client.target(getDataUrl);
		 Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
		 Response response = invocationBuilder.get();
	        
	     this.sp=response.readEntity(SingleProfile.class); 
		 this.firstname=this.sp.getProfile().getPersonal().getFirstName();
		 this.lastname=this.sp.getProfile().getPersonal().getLastName();
		 
		 // question answered?
		 if (questionAnswered()){ //yes
			 return getAnswer(); 
			 
		 } else { //no
			 
			 if (erst.noAnswer()){  // Speechhelper wants to do a new answer
				 return erst.alternativeAnswerfromSpeechelper();}
			 
			 else{  // no available answer from Klient
				 return noAnswerfromKlient();} 
		 }
	 }

	
	/**
	 * Klient.getAnswer() called to concatenate the answer
	 * uses attribute question 
	 * @ return String representing the answer
	 */
	
	public String getAnswer(){
		String s=this.question.getAnswers().get(0).getSummary();
		
		return
		(SpeechHelper.wrapInSpeak("Hallo "+ SpeechHelper.createBreak(1) 
		+firstname +" "+lastname
		+" hat eine Meinung zum Thema " +category 
		+ SpeechHelper.createBreak(1) 
		+s
		));

	}
	
	/**
	 * Klient.noAnswer() called to a Dummy answer if there is no answer from the profile sp
	 * uses attribute firstname, lastname, category
	 * @ return String representing the Dummy a answer
	 */
	public String noAnswerfromKlient(){
		return (SpeechHelper.wrapInSpeak("Hello,"+ SpeechHelper.createBreak(1) 
		+firstname +" "+lastname
		+" hat das Thema " +this.category+" nicht beantwortet."));
	}
}