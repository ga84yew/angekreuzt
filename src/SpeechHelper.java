	/**
	 * Speechhelper has methods wrapInSpeak, interpretAs and createBreak for Alexa ssml speech
	 *  @author Jan Knobloch
	 * @version 1.1, 13.10.2017
	 */

public class SpeechHelper {

 public static final String CARDINAL = "cardinal";
 public static final String ORDINAL = "ordinal";
 public static final String DIGITS = "digits";
 public static final String FRACTION = "fraction";
 public static final String UNIT = "unit";
 public static final String DATE = "date";
 public static final String TIME = "time";
 public static final String TELEPHONE = "telephone";
 public static final String ADDRESS = "address";

 /**
	 * wraps the input string into speak's for ssml
	 * @param s input string
	 * @return wrapped input String 
	 */
 public static String wrapInSpeak(String s) {
	 return "<speak>" + s + "</speak>";}
 
 /**
	 * creates a string, which defines for ssml how a certain string shall be interpreted
	 * @param s string to be interpreted in certain type
	 * @param type string representing the way according to ssml definition, the first string is to be interpreted
	 * @return  String: break time = seconds in correct format for ssml 
	 */
 public static String interpretAs(String s, String type)
 {return "<say-as interpret-as=\""+type+"\">"+s+"</say-as>";}

 /**
	 * creates a time break notated in string for ssml
	 * @param seconds double value representing the time lag
	 * @return  String: break time = seconds in correct format for ssml 
	 */
 public static String createBreak(double seconds)
 {return "<break time=\""+seconds+"s\"/>";	}
 
 }
