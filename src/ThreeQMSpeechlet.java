/**
    Copyright 2014-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.

    Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with the License. A copy of the License is located at

        http://aws.amazon.com/apache2.0/

    or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
import com.fasterxml.jackson.databind.JsonMappingException;
import parliament2profile.*;

/**
 * This sample shows how to create a simple speechlet for handling speechlet requests.
 */
public class ThreeQMSpeechlet implements Speechlet {
	
	List<String> categories_list;
	Parliament2Profile Bundestag;
	
	private static final Logger log = LoggerFactory.getLogger(ThreeQMSpeechlet.class);
    private String chosen_category;     public void setChosen_category(String cat){chosen_category=cat; String candidates;}
    
    String getDataUrl1;
    String getDataUrl2;
    
	public ThreeQMSpeechlet(){
		
	//set categories_list from constant list and jsonString from remote URL	

	String[] categories={"Arbeit","Integration", "Sicherheit", "Aussenpolitik","Finanzen","Bildung"};
    this.categories_list = Arrays.asList(categories); 
    
    Client client = ClientBuilder.newClient( new ClientConfig() ); //String category = "sport";
	WebTarget webTarget = client.target("https://www.abgeordnetenwatch.de/api/parliament/Bundestag/candidates.json");
	Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
	Response response = invocationBuilder.get();
	Parliament2Profile Bundestag = response.readEntity(Parliament2Profile.class);
	
	//set format for URL String
	this.getDataUrl1 ="https://www.abgeordnetenwatch.de/api/profile/" ;
	//+angela-merkel+
	this.getDataUrl2="/profile.json";
	}
   
    
    @Override
    public void onSessionStarted(final SessionStartedRequest request, final Session session){    }

    //SpeechletResponse onLaunch
    @Override
    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session){    return null;   }

    //SpeechletResponse onIntent
    @Override
    public SpeechletResponse onIntent(final IntentRequest request, final Session session)
            throws SpeechletException {
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());

        // get Values from Alexa
        Intent getData = request.getIntent();
        String intent_name= getData.getName();
        System.out.println(intent_name);        
        
        String content_of_category = getData.getSlot("category").getValue();
		String content_of_firstname = getData.getSlot("vorname").getValue();
		String content_of_lastname = getData.getSlot("nachname").getValue();

		 // local value strings for output to Alexa
        SsmlOutputSpeech text =new SsmlOutputSpeech(); 
        text.setSsml("Text not set"); //dummy
        String set; //string for input to Alexa
        
        
		try {
			
			// correct category?
			if (categories_list.contains(content_of_category)){
				
				// get correct Profile p
				ListIterator<Profile> it = Bundestag.getProfiles().listIterator();
				Profile p;
				
				while (it.hasNext()){
					p =it.next();
					if (p.getPersonal().getFirstName().equals(content_of_firstname) && p.getPersonal().getLastName().equals(content_of_lastname )){
						break;
					}
					
					// profile not found
					if (!it.hasNext()){
						set = SpeechHelper.wrapInSpeak(wrongname(content_of_firstname +"-"+ content_of_lastname));
					}
				}
				
				//create URL string from format and name
				String getDataUrl = getDataUrl1+content_of_firstname +"-"+ content_of_lastname+getDataUrl2;	
				Klient k= new Klient();
				set= k.getData(content_of_category,getDataUrl, this);	

				// category not possible
			}else{
			set =SpeechHelper.wrapInSpeak( wrongcategory(content_of_category) );	

			}

			// set output text for Alexa
			text.setSsml(set);
			
		//catch blocks
		} catch (JsonParseException e) {e.printStackTrace();
		} catch (JsonMappingException e) {e.printStackTrace();
		} catch (MalformedURLException e) {e.printStackTrace();
		} catch (IOException e) {e.printStackTrace();}
        
		//output to alexa
        SpeechletResponse r = new SpeechletResponse ();
        r.setOutputSpeech(text);
		return r;
    }

	
	public boolean noAnswer( ) {//called by client to check if Speechhelper wants to create an alternative Answer
	return false;		
	}
	
	public String alternativeAnswerfromSpeechelper( ) {//called by client to get an alternative Answer
	return "alternative Answer Dummy";		
	}
	
	public String wrongname(String fullname){
		return fullname+"not found in Database";
	}
	public String wrongcategory(String category){
		return category+"not found in Database";
	}
	
	
    // onSessionEnded
    @Override
    public void onSessionEnded(final SessionEndedRequest request, final Session session)
            throws SpeechletException {
        /*log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());*/
        // any cleanup logic goes here
    }

    /**
     * Creates and returns a {@code SpeechletResponse} with a welcome message.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getWelcomeResponse() {   	return null;    }

    /**
     * Creates a {@code SpeechletResponse} for the hello intent.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getHelloResponse() {    	return null;    }

    /**
     * Creates a {@code SpeechletResponse} for the help intent.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getHelpResponse() {    	return null;    }


	
}
