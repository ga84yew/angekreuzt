
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Bildung {
	public static String getText(String partei)  {
		String result = "";
		Document doc = null; 
		
			try {
				doc = Jsoup.connect("http://www.bundestagswahl-bw.de/bildung_btwahl2017.html").get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		 if (partei.toLowerCase().equals("cdu"))  {
			if (doc != null) {
				result = extractInformation(result, doc, 18, 19);
			}
		} else if (partei.toLowerCase().equals("spd")) {
			if (doc != null) {
				result = extractInformation(result, doc, 21, 22);
			} 
		} else if (partei.toLowerCase().equals("linke")) {
			if (doc != null) {
				result = extractInformation(result, doc, 24, 26);
			}
		} else if (partei.toLowerCase().equals("gruene")) {
			if (doc != null) {
				result = extractInformation(result, doc, 28, 28);
			}
		} else if (partei.toLowerCase().equals("fdp")) {
			if (doc != null) {
				result = extractInformation(result, doc, 30, 32);
			}
		} else { 
			if (doc != null) {
				result = extractInformation(result, doc, 34, 36);
			}
		}
		return result;
	}
	private static String extractInformation(String result, Document doc, int start, int ende) {
		Elements party = doc.body().select("p.bodytext");
		for(int index = start; index <=ende;index++) {
			result += party.get(index).text();
			result = result.replaceAll("\\((.+?)\\)", "");
		}
		return result;
	}
}
