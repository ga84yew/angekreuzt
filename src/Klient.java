import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ListIterator;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
		ListIterator<Question> it = sp.getProfile().getQuestions().listIterator();
		Question localQ=new Question();
		
		while (it.hasNext()){
			localQ =it.next();
			
			// question found:
			if (localQ.getCategory().contains(category)){ //category is contained
				if (!localQ.getAnswers().isEmpty()){ //Answer not empty
					this.question=localQ;
					return true;
				}
				else{
					return false;
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