import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
/**
 * Delegate is used for String retrieving and manipulation
 * @author Rainer Wichmann
 * @version 1.0, 15.9.2017
 */


public final class Delegate {

	/**
	 * @param urlString String, where the file is located
	 * @return String from the file at the url "urlString"
	 * @throws IOException while reading with InputStream
	 */
	public static String readUrl(String urlString) throws IOException {
	    BufferedReader reader = null;
	    try {
	        URL url = new URL(urlString);
	        reader = new BufferedReader(new InputStreamReader(url.openStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        return buffer.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }
	}
	
	/**
	 * converts complex Strings to simple Strings by 
	 * -lowercase each character
	 * -eliminating all non alphabetic characters
	 * -trimming the output string to the smaller size
	 * @param text input String
	 * @return simple String
	 */
	public static String lowercase(String text){
		char[] chars = text.toCharArray();
		char[] out = new char[chars.length]; 
		int o=0;
	    for (int i = 0; i < chars.length; i++)
	    {
	        char c = chars[i];
	        
	        if (Character.isUpperCase(c))
	        {
	        	c= Character.toLowerCase(c);
	        }
	        /*
	        else if (Character.isLowerCase(c))
	        {
	            chars[i] = Character.toUpperCase(c);
	        }
	        */
	        if (Character.isAlphabetic(c)){
	        	 out[o]=c;
	        	 o++;		 
	        }
	        
	    }
	    String s =new String(out);
	    return s.trim();
	}

	/**
	 * converts complex Strings copied from html pages 
	 * -using Jsoup parser
	 * -deleting all enties in brackets
	 * @param text input String
	 * @return simple String
	 */
	public static String html2text(String text) {
		String result= Jsoup.parse(text).text();
		return result.replaceAll("\\((.+?)\\)", "");
	}
	
	/**
	* This method is called by the getText method. A for-loop is used to define the parts of the document to be retrieved.
	* @param String result, which is returned, comprises a specified part of a document's body text.
	* @param doc, which is the HTML document.
	* @param int start and int ende, which define indices to determine the desired extract of the document. 
	* @return returns the desired part of the document as a String.
	*/
		public static String extractInformation(String result, Document doc, int start, int ende) {
			Elements party = doc.body().select("p.bodytext");
			for(int index = start; index <=ende;index++) {
				result += party.get(index).text();
				result = result.replaceAll("\\((.+?)\\)", "");
			}
			return result;
		}
}
