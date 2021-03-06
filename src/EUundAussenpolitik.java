
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
/**
 * The public class EUundAussenpolitik collects information on the parties' views on the European Union and foreign affairs as described in the URL "http://www.bundestagswahl-bw.de/eu_aussenpolitik_btwahl2017.html".
 * It creates a connection to this URL by using the Jsoup package, which includes the connect method. 
 * By iterating through specified indices, the class returns the desired part of the URL (corresponding to the String partei input, please open URL) and 
 * sends it to the SpeechletResponse to create a speech output. This marks one of six possible "endpoints" to the intent Zweitstimme.
 * @author severin engelmann , rainer wichmann
 * @version 1.1, 13.10.2017
 */
public class EUundAussenpolitik {
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
			doc = Jsoup.connect("http://www.bundestagswahl-bw.de/eu_aussenpolitik_btwahl2017.html").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (partei.toLowerCase().equals("cdu"))  {
			if (doc != null) {
				result = Delegate.extractInformation(result, doc, 3, 4);
			}
		} else if (partei.toLowerCase().equals("spd")) {
			if (doc != null) {
				result = Delegate.extractInformation(result, doc, 6, 8);
			} 
		} else if (partei.toLowerCase().equals("linke"))  {
			if (doc != null) {
				result = Delegate.extractInformation(result, doc, 10, 13);
			}
		} else if (partei.toLowerCase().equals("gruene"))  {
			if (doc != null) {
				result = Delegate.extractInformation(result, doc, 16, 17);
			}
		} else if (partei.toLowerCase().equals("fdp"))  {
			if (doc != null) {
				result = Delegate.extractInformation(result, doc, 19, 21);
			}
		} else { //afd is taken else
			if (doc != null) {
				result = Delegate.extractInformation(result, doc, 23, 25);
			}
		}
		return result;
	}
}




