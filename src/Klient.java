import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import singleprofile.*;
/* imports only necessary when access url with jersey client 
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
*/

/**
 * accesses remote json data at abgeordnetenwatch.de for retrieving answer to a political context. 
 * A context is called subGroup and they have common top level Groups
 * A question includes answer and subGruop, which is called category according to json profile of abgeordnetenwatch.de
 * @author Rainer Wichmann
 * @version 1.0, 15.9.2017
 */

public class Klient {
	
	//attribs
	private String subGroup;
	private String firstname, lastname; 
	private SingleProfile candidateProfile; // candidate Profile containing all questions from the candidate 
	private Question question; //answer and subGruop are contained here, getAnswer() uses this attribute to concatenate answer String
	private boolean subGroupReplaced=false; // true if subGroup was replaced by similar subGroup
	
	/**
	 * constructor Klient(subGroup)  sets attributes subGroup,
	 * @param subGroup String representing the political context
	 */
	public Klient(String subGroup){
		this.subGroup=subGroup;
	}
	
	/**
	 * Klient.getText() is called to return a question to the category from a profile, which is available via the String questionUrl
	 * attributes candidateProfile, firstname and lastname are set
	 * is asking to receive a SingleProfile from getDataUrl, uses a mapper or a client+webtarget+invocationbuilder+response 
	 * if no answer is availabe, it asks the Caller , a Erststimme erst, via erst.noAnswer() erst.alternativeAnswerfromSpeechelper() to return an alternative answer
	 * if erst has no alternative answer, a simple dummy answer is returned
	 * @param questionUrl String of the url where the questions of the candidate can be found
	 * @param erst may be used for asking for the Erststimme erst, if no answer at all is available. Not implemented yet
	 * @return String representing the answer
	 * @throws IOException during json mapping in mapper.readValue
	 * @throws JsonParseException during json mapping in mapper.readValue
	 * @throws JsonMappingException during json mapping in mapper.readValue
	 */
	public String getText(String questionUrl, Erststimme erst) throws IOException,   JsonParseException,   JsonMappingException {	
			
		/* version  for accessing questionUrl with client
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
	
	private boolean answerIsNotEmpty(Question localQ){
		// question found:
			if (!localQ.getAnswers().isEmpty()){
				if (!localQ.getAnswers().get(0).getSummary().isEmpty()){
					//Answer not empty
					this.question=localQ; // set question
					return true;  //question found return true
				}
			}	
			return false;
	}
	
	
	
	/**
	 * check if it is possible to get an answer from the person, defined in the SingleProfile sp, to a question from the subGroup
	 * using attributes candidateProfile and subGroup
	 * sets attribute question if there is an answer to the Question
	 * @return boolean
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
				
				if (!localQ.getAnswers().isEmpty()){
					if (!localQ.getAnswers().get(0).getSummary().isEmpty()){
						//Answer not empty
						this.question=localQ; // set question
						return true;  //question found return true
					}
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


	/**
	 * looking for alternative questions relating to subGroups from combination of all groups from Themen and allAnsweredQuestionGroups
	 * @param ArrayList<Question> allAnsweredQuestions
	 * @param ArrayList<Question> allAnsweredQuestionGroups
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
							
							if (answerIsNotEmpty(allAnsweredQuestions.get(i))){
							index=i;
							this.question=allAnsweredQuestions.get(index); // set question to the new question, which answers to new SubGroup
							this.subGroup=this.question.getCategory().toLowerCase(); //set SubGroup to Category contained in selected question
							
							subGroupReplaced=true; //indicate that subGroup has been replaced
							return true; //question found return true
							}
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
	 * @return String representing the answer
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
	 * @return String representing the Dummy a answer
	 */
	private String noAnswerfromKlient(){
		return (SpeechHelper.wrapInSpeak("Hello,"+ SpeechHelper.createBreak(1) 
		+firstname +" "+lastname
		+" hat das Thema " +this.subGroup+" nicht beantwortet."));
	}
}