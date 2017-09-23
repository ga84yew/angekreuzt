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

 public static String wrapInSpeak(String s) {
	 return "<speak>" + s + "</speak>";}
 
 public static String interpretAs(String s, String type)
 {return "<say-as interpret-as=\""+type+"\">"+s+"</say-as>";}


 public static String createBreak(double seconds)
 {return "<break time=\""+seconds+"s\"/>";	}
 
 }
