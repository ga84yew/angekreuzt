import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
/**
 *Main class is the responsible SpeechletRequestStreamHandler for an Alexa Skill
 * @author Rainer Wichmann, Severin Engelmann
 * @version 1.1, 13.10.2017
 */
public final class Main extends SpeechletRequestStreamHandler {

    private static final Set<String> supportedApplicationIds = new HashSet<String>();
    static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
        supportedApplicationIds.add("amzn1.ask.skill.fee99074-c788-424e-ae7e-307aa9bfdd41");
    }

    public Main() throws JsonParseException, JsonMappingException, URISyntaxException, IOException {
        super(new AngekreuztSpeechlet(), supportedApplicationIds);
    }
}
