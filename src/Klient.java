import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ListIterator;

/* imports only necessary when access url with client
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
 */
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import singleprofile.*;

public class Klient {
	
	//attribs
	private String subGroup, firstname, lastname;
	private SingleProfile candidateProfile;
	private Question question; //answer, subGruop are contained here
	private String outputstring;
	private boolean subGroupReplaced=false;
	
	/**
	 * Klient.getData() is called to return a question to the category from a profile, which is available via the String questionUrl
	 * attributes candidateProfile, subGroup, firstname and lastname are set
	 * creates a client asking to receive a SingleProfile from getDataUrl
	 * if no answer is availabe, it asks the Caller , a Erststimme erst, via erst.noAnswer() erst.alternativeAnswerfromSpeechelper() to return an alternative answer
	 * if erst has no alternative answer, a simple dummy answer is returned
	 * @ param String category
	 * @ param String getDataUrl
	 * @ param Erststimme erst, will be used for asking for the Erststimme, if no answer at all is available
	 * @ return String representing the answer
	 */
	public String getData(String subGroup, String questionUrl, Erststimme erst) throws JsonParseException, JsonMappingException, MalformedURLException, IOException {	
		
		this.subGroup=subGroup;
			
		/* version with access url with client
		 Client client = ClientBuilder.newClient( new ClientConfig() );
		 WebTarget webTarget = client.target(questionUrl);
		 Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
		 Response response = invocationBuilder.get();
		 this.candidateProfile=response.readEntity(SingleProfile.class); 
	      */
		
		//version with mapper
		URL jsonUrl = new URL(questionUrl);
        ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
  		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
  		this.candidateProfile = mapper.readValue(jsonUrl, SingleProfile.class);
  		
  		//set firstname, lastname
        this.firstname=this.candidateProfile.getProfile().getPersonal().getFirstName();
		this.lastname=this.candidateProfile.getProfile().getPersonal().getLastName();
		 
		 // question answered?
		 if (questionAnswered()){  return getAnswer();    //yes
			 		 
		 } else { //no
			 
			 if (erst.noAnswer()){  // Speechlet Erststimme wants to do a new answer!
				 return erst.alternativeAnswerfromSpeechlet();}
			 else{  // no available answer from Klient
				 return noAnswerfromKlient();} 
		 }
	 }
	
	
	/**
	 * check if it is possible to get an answer from the person, defined in the SingleProfile sp, to a question from the subGroup
	 * using attributes candidateProfile and subGroup
	 * sets attribute question if there is an answer to the Question
	 * @ return boolean
	 */
	
	private boolean questionAnswered(){
		
		// get correct Question q
		ListIterator<Question> itQ  = candidateProfile.getProfile().getQuestions().listIterator();
		Question localQ=new Question();
		
		//save all subGroups answered, step1
		ArrayList<Question> allAnsweredQuestions=new ArrayList<Question> (); 
		ArrayList<String> allAnsweredQuestionGroups=new ArrayList<String>();	
		
		while (itQ.hasNext()){//iterate over all Questions in profile
			localQ =itQ.next();
			
			// question found:
			if (localQ.getCategory().toLowerCase().contains(subGroup.toLowerCase())){ //subGroup is contained
				
				if (!localQ.getAnswers().isEmpty()){ //Answer not empty
					this.question=localQ; // set question
					return true;  //question found return true
				}			
			}
			//save all subGroups answered, step2
			if ( !localQ.getAnswers().isEmpty()	){
				allAnsweredQuestions.add(localQ);
				allAnsweredQuestionGroups.add(localQ.getCategory().toLowerCase());
			}
		}
		//Question not yet found, look for alternative Questions, with a subGruop similar to the current subGroup
		return alternativeQuestion(allAnsweredQuestions, allAnsweredQuestionGroups);
	}


	/**looking for alternative questions relating to subGroups from combination of all groups from Themen and allAnsweredQuestionGroups
	 * 
	 * @param allAnsweredQuestions
	 * @param allAnsweredQuestionGroups
	 * @return boolean, true if Question and subGroup found and locally set
	 */
	private boolean alternativeQuestion(ArrayList<Question> allAnsweredQuestions,
			ArrayList<String> allAnsweredQuestionGroups) {
		
		//get mapping of groups and subgroups from class Themen
		Themen Topthemes = new Themen(); GroupMapping map = Topthemes.mapping;
	
		if (map.mapSubGroupsToGroup.containsKey(subGroup)){ //subgroup contained in any of the Groups?
			String group = map.mapSubGroupsToGroup.get(subGroup); //get corresponding group
			
			ArrayList<String> similarSubGroups =map.mapGroupToSubGroups.get(group); //get all similar subgroups from the selected group
			
			int i=0; int index=0;
			for (String similarSubGroup:similarSubGroups){ //iterate over similar subgroups
				for (String subGroupInQuestion:allAnsweredQuestionGroups){ //iterate over answered subGroups
						if(subGroupInQuestion.toLowerCase().equals(similarSubGroup.toLowerCase())){ //if any answered subGruoup Question equals any of the similar subgroups
							index=i;
							this.question=allAnsweredQuestions.get(index); // set question to the new question, which answers to new SubGroup
							this.subGroup=this.question.getCategory().toLowerCase(); //set SubGroup to Category contained in selected question
							subGroupReplaced=true; //indicate that subGroup has been replaced
							return true; //question found return true
						}
					i++;
				}
				i=0;
			}
		}
			// question not found
		return false;
	}
	
		
	/**
	 * Klient.getAnswer() called to concatenate the answer String
	 * uses attribute question 
	 * @ return String representing the answer
	 */
	
	private String getAnswer(){
		String s=this.question.getAnswers().get(0).getSummary();
		
		//include if subGroup was replaced with similar subgroup during Search
		if (this.subGroupReplaced) { s=s+ "Das ursprünglichl gewünschte Thema wurde leider nicht beantwortet."; } 
		
		return
		(SpeechHelper.wrapInSpeak("Hallo "+ SpeechHelper.createBreak(1) 
		+firstname +" "+lastname
		+" hat eine Meinung zum Thema " +subGroup 
		+ SpeechHelper.createBreak(1) 
		+Delegate.html2text(s)
		));

	}
	
	/**
	 * Klient.noAnswer() called if there is no answer from the profile candidateProfile, no other answer possible, 
	 * therefore return dummy answer
	 * uses attribute firstname, lastname, subGroup
	 * @ return String representing the Dummy a answer
	 */
	private String noAnswerfromKlient(){
		return (SpeechHelper.wrapInSpeak("Hello,"+ SpeechHelper.createBreak(1) 
		+firstname +" "+lastname
		+" hat das Thema " +this.subGroup+" nicht beantwortet."));
	}
}