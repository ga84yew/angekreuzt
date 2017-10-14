import java.io.IOException;

import org.eclipse.jetty.http.HttpGenerator.Result;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
/**
 * The public class Sicherheit collects information on the parties' views on security as described in the URL "http://www.bundestagswahl-bw.de/sicherheit_btwahl2017.html".
 * It creates a connection to this URL by using the Jsoup package, which includes the connect method. 
 * By iterating through specified indices, the class returns the desired part of the URL (corresponding to the String partei input, please open URL) and 
 * sends it to the SpeechletResponse to create a speech output. This marks one of six possible "endpoints" to the intent Zweitstimme.
 * @author severin engelmann , rainer wichmann
 * @version 1.1, 13.10.2017
 */
public class Sicherheit  {
	/**
	 * This method creates a connection to a defined URL (JSoup) and subsequently calls the "extractInformation" method and returns its result to create a 
	 * speech output in the AngekreutSpeechlet class. 
	 * @param partei the method receives the String parameter partei from the method auswahl of the class Zweitstimme. 
	 * The String partei corresponds to a defined alexa skill builder slot which depends on a slot type called Parteien. 
	 * The slot type Parteien contains all the names of the parties as Strings. W
	 * @return returns the desired part of the document as a String.
	 */
	public static String getText(String partei)  {
		String result = "";
		Document doc = null; 
		try {
			doc = Jsoup.connect("http://www.bundestagswahl-bw.de/sicherheit_btwahl2017.html").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (partei.toLowerCase().equals("cdu")|| partei.toLowerCase().equals("csu"))   {
			if (doc != null) {
				result = Delegate.extractInformation(result, doc, 18, 19);
			}
		} else if (partei.toLowerCase().equals("spd"))   {
			if (doc != null) {
				result = Delegate.extractInformation(result, doc, 22, 24);
			} 
		} else if (partei.toLowerCase().equals("linke"))  {
			if (doc != null) {
				result = Delegate.extractInformation(result, doc, 27, 28);
			}
		} else if (partei.toLowerCase().equals("grüne") ||partei.toLowerCase().equals("grünen")) {
			if (doc != null) {
				result = Delegate.extractInformation(result, doc, 30, 33);
			}
		} else if (partei.toLowerCase().equals("fdp"))  {
			if (doc != null) {
				result = Delegate.extractInformation(result, doc, 35, 37);
			}
		} else { 
			if (doc != null) {//afd is taken else
				result = Delegate.extractInformation(result, doc, 40, 41);
			}
		}
		return result;
	}
}




