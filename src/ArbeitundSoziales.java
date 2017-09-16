
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ArbeitundSoziales {
	public static String getText(String partei)  {
		String result = "";
		Document doc = null; 

		try {
			doc = Jsoup.connect("http://www.bundestagswahl-bw.de/arbeit_soziales_btwahl2017.html").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (partei.toLowerCase().equals("cdu"))  {
			if (doc != null) {
				result = extractInformation(result, doc, 2, 13);
			}
		} else if (partei.toLowerCase().equals("spd")) {
			if (doc != null) {
				result = extractInformation(result, doc, 16, 26);
			} 
		} else if (partei.toLowerCase().equals("linke")) {
			if (doc != null) {
				result = extractInformation(result, doc, 28, 37);
			}
		} else if (partei.toLowerCase().equals("gr�nen"))  {
			if (doc != null) {
				result = extractInformation(result, doc, 38, 47);
			}
		} else if (partei.toLowerCase().equals("fdp"))  {
			if (doc != null) {
				result = extractInformation(result, doc, 49, 58);
			}
		} else { 
			if (doc != null) {
				result = extractInformation(result, doc, 60, 71);	
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
