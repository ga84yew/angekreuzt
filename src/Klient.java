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
	
	String category, firstname, lastname;
	SingleProfile sp;
	Question q;
	String outputstring;
	
	public boolean questionAnswered(){
		// get correct Question q
		ListIterator<Question> it = sp.getProfile().getQuestions().listIterator();
		Question localQ=new Question();
		
		while (it.hasNext()){
			localQ =it.next();
			
			// question found:
			if (localQ.getCategory().contains(category)){ //category ist contained
				if (!localQ.getAnswers().isEmpty()){ //Answer not empty
					this.q=localQ;
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
	
	public String getData(String category, String getDataUrl, ThreeQMSpeechlet t) throws JsonParseException, JsonMappingException, MalformedURLException, IOException {	
		
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
			 
			 if (t.noAnswer()){  // Speechhelper wants to do a new answer
				 return t.alternativeAnswerfromSpeechelper();}
			 
			 else{  // no available answer from Klient
				 return noAnswerfromKlient();} 
		 }
	 }

	
	
	//called to get answer
	public String getAnswer(){
		String s=this.q.getAnswers().get(0).getSummary();
		
		return
		(SpeechHelper.wrapInSpeak("Hallo "+ SpeechHelper.createBreak(1) 
		+firstname +" "+lastname
		+" hat eine Meinung zum Thema " +category 
		+ SpeechHelper.createBreak(1) 
		+s
		));

	}
	
    //called if no answer is available
	public String noAnswerfromKlient(){
		return (SpeechHelper.wrapInSpeak("Hello,"+ SpeechHelper.createBreak(1) 
		+firstname +" "+lastname
		+" hat das Thema " +this.category+" nicht beantwortet."));
	}
}