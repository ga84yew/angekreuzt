
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
/**
 * The public class Wahlsystem collects information on the election process in Germany as described in URL "http://www.bundestagswahl-bw.de/wahlsystem_btw.html" by using the Jsoup package.
 * By iterating through specified indices, the class returns the desired part of the URL and sends it to the SpeechletResponse to create a speech output.
 * @author severin engelmann , rainer wichmann
 * @version 1.1, 13.10.2017
 *
 */
public class Wahlsystem {
	
	/**
	 * This method creates a connection to a defined URL and subsequently calls the "extractInformation" method and returns its result to create a 
	 * speech output in the AngekreutSpeechlet class.
	 * @return returns the desired part of the document as a String.
	 */
	public static String getText() {
		String result = "";
		Document doc = null; 
		try {
			doc = Jsoup.connect("http://www.bundestagswahl-bw.de/wahlsystem_btw.html").get();
			result = extractInformation(result, doc, 1, 11);
		} catch (Exception e) {
			e.printStackTrace();
			
		}	
		return result;
	}
	
	/**
	 * This method is called by the getText method. A for-loop is used to define the parts of the document to be retrieved.
	 * @param result String , which is returned, comprises a specified part of a document's body text.
	 * @param doc, which is the HTML document.
	 * @param start, 
	 * @param ende, start and ende are int, define indices to determine the desired extract of the document. 
	 * @return returns the desired part of the document as a String.
	 */
	private static String extractInformation(String result, Document doc, int start, int ende) {
		Elements party = doc.body().select("p.bodytext");

		for(int index = start; index <=ende;index++) {//for each block
			
			String text=Delegate.html2text(party.get(index).text()); //get simple text
			if(text.charAt(text.length()-1)=='.'){
				text=text.substring(0,text.length()-1)+SpeechHelper.createBreak(1);//insert break
			}
			result += text; //add each block
		}
		//System.out.println(result);
		return SpeechHelper.wrapInSpeak(result);
	}
	

}