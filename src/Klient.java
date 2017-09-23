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
 * accesses remote json data at abgeordnetenwatch.de for retrieving answer to a political category. 
 * common categories have a common top level Group, defined in Themen
 * A question includes answer and category according to json profile of abgeordnetenwatch.de
 * @author Rainer Wichmann
 * @version 1.0, 15.9.2017
 */

public class Klient {
	
	//attribs
	private String category;
	private String firstname, lastname; 
	private SingleProfile candidateProfile; // candidate Profile containing all questions from the candidate 
	private Question question; //answer and subGruop are contained here, getAnswer() uses this attribute to concatenate answer String
	private boolean categoryReplaced=false; // true if category was replaced by similar category
	private String categoryOld=""; // old category is stored here
	
	/**
	 * constructor Klient(category)  sets attributes category,
	 * @param category String representing the political context
	 */
	public Klient(String category){	this.category=category;	}
	
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
	
	/**
	 * check if it is possible to get an answer from the person, defined in the SingleProfile singleprofile, to a question from the category
	 * using attributes candidateProfile and category
	 *  prior to selecting the question, it is checked, if the answer or the summary in the answer is empty
	 * sets attribute question, when there is an answer to the Question
	 * allAnsweredCategorys and all AnsweredQuestions are used to store the questions and its categories answered in the profile
	 * @return boolean
	 */
	private boolean questionAnswered(){
		
		// get correct Question q
		ListIterator<Question> itQ  = candidateProfile.getProfile().getQuestions().listIterator();
		Question localQ=new Question();
		
		//save all categorys answered, step1
		ArrayList<Question> allAnsweredQuestions=new ArrayList<Question> (); 
		ArrayList<String> allAnsweredCategorys=new ArrayList<String>();	
		
		//System.out.println(category.toLowerCase());
		while (itQ.hasNext()){//iterate over all Questions in profile
			localQ =itQ.next();

			
			if (localQ.getCategory().toLowerCase().contains(category.toLowerCase())){ //category is contained
				
				if (answerIsNotEmpty(localQ)){ //true, if the answer (and Summary in the answer) in Question localQ is not empty.

						this.question=localQ; // set question
						return true;  //question found, return true
				}
				
			}
			//save all categorys answered, step2
			if ( answerIsNotEmpty(localQ) ){
				allAnsweredQuestions.add(localQ);
				allAnsweredCategorys.add(localQ.getCategory().toLowerCase());
			}
		}
		//Question not yet found, look for alternative Questions, with a subGruop similar to the current category
		return alternativeQuestion(allAnsweredQuestions, allAnsweredCategorys);
	}


	/**
	 * looking for alternative questions relating to the chosen category
	 * It selectes the group of the category from combination
	 *  and then checks, if there is a match between all categories in this group
	 *  and the categories contained in allAnsweredCategorys
	 *  the first match is used, no further search for matches are done
	 *  prior to selecting the question, it is checked, if the answer or the summary in the answer is empty
	 * @param ArrayList<Question> allAnsweredQuestions
	 * @param ArrayList<Question> allAnsweredCategorys
	 * @return boolean, true if Question and category found and locally set
	 */
	private boolean alternativeQuestion(ArrayList<Question> allAnsweredQuestions,
			ArrayList<String> allAnsweredCategorys) {
		
		//get mapping of groups and categorys from class Themen
		Themen Topthemes = new Themen(); GroupMapping map = Topthemes.mapping;
	
		if (map.mapCategoryToGroup.containsKey(category)){ //category contained in any of the Groups?
			String group = map.mapCategoryToGroup.get(category); //get corresponding group
			
			ArrayList<String> similarCategorys =map.mapGroupToCategorys.get(group); //get all similar Categorys from the selected group
			
			int i=0; int index=0;
			for (String similarCategory:similarCategorys){ //iterate over similar Categorys
				for (String categoryInQuestion:allAnsweredCategorys){ //iterate over answered Categorys
						if(categoryInQuestion.toLowerCase().equals(similarCategory.toLowerCase())){ // true if any answered category equals any of the similar categories
							
							if (answerIsNotEmpty(allAnsweredQuestions.get(i))){// true if the answer (and Summary in the answer) in the Question, that corresponds to the category is not empty.
							index=i;
							this.question=allAnsweredQuestions.get(index); // set question to the new question, which answers to new category
							this.categoryOld=this.category; //store category that will be replaced
							this.category=this.question.getCategory().toLowerCase(); //set category to Category contained in selected question
							categoryReplaced=true; //indicate that category has been replaced
							return true; //question found, return true
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
	 * check if answer or summary in answer are not empty
	 * @param localQ Question, which includes answer
	 * @return boolean , true if answer or summary in answer are not empty
	 */
	private boolean answerIsNotEmpty(Question localQ){
		// question found:
			if (!localQ.getAnswers().isEmpty()){
				if (!localQ.getAnswers().get(0).getSummary().isEmpty()){
					if (localQ.getAnswers().get(0).getSummary().length()>15){
					//Answer not empty and not short
					this.question=localQ; // set question
					//System.out.println(localQ.getAnswers().get(0).getSummary());
					return true;  //question found return true
					}
				}
			}	
			return false;
	}
	
		
	/**
	 * Klient.getAnswer() called to concatenate the answer String
	 * uses attribute question 
	 * @return String representing the answer
	 */
	private String getAnswer(){
		
		String s=this.question.getAnswers().get(0).getSummary();
		String opt="";
		//include if category was replaced with similar category during Search
		if (this.categoryReplaced) {
			opt="Das ursprünglichl gewünschte Thema "+categoryOld+" wurde leider nicht beantwortet."
			+ " das ähnliche Thema " + category + "aber schon" + SpeechHelper.createBreak(1);};
	
		return
		(SpeechHelper.wrapInSpeak("Hallo "+ SpeechHelper.createBreak(1) +opt
		+firstname +" "+lastname
		+" hat eine Meinung zum Thema " +category 
		+ SpeechHelper.createBreak(1) 
		+Delegate.html2text(s)
		));
	}
	
	/**
	 * Klient.noAnswer() called if there is no answer from the profile candidateProfile, no other answer possible, 
	 * therefore return dummy answer
	 * uses attribute firstname, lastname, category
	 * @return String representing the Dummy a answer
	 */
	private String noAnswerfromKlient(){
		
		//Sorry
		String result="Hallo,"+ SpeechHelper.createBreak(1) 
		+firstname +" "+lastname
		+" hat das Thema " +this.category+" laut Datenbank leider nicht beantwortet.";
		
		//webSite Alternative
		String websiteHelp=SpeechHelper.createBreak(0.2)+" Alternativ bitten wir Dich, Dich auf der Webseite "+ SpeechHelper.createBreak(0.1)+ "abgeordneten wotsch . d "+ SpeechHelper.createBreak(0.1)+" e "+ SpeechHelper.createBreak(0.1)+" umzusehen. "+ SpeechHelper.createBreak(1)
		+"Da dies kein kommerzielles Projekt ist und einige Politiker aufgrund Ihrer Ämter die Fragen nicht beantworten, ist die Datenlage nicht immer ausreichend.";
		

		// possible Partei - Alternative
		if (!candidateProfile.getProfile().getParty().isEmpty()) 
		{String partyHelp= SpeechHelper.createBreak(1)+
				"Aber du kannst nach der Meinung der zugehörigen Partei"+ SpeechHelper.createBreak(1)
				+ candidateProfile.getProfile().getParty()+" zu dem Thema fragen.";
		result = result +partyHelp;
		};
			
						
		return SpeechHelper.wrapInSpeak(result + websiteHelp);
		
	}
}