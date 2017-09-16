
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SteuernundFinanzen {
	public static String getText(String partei)  {
		String result = "";
		Document doc = null; 
			try {
				doc = Jsoup.connect("http://www.bundestagswahl-bw.de/steuern_finanzen_btwahl2017.html").get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if (partei.toLowerCase().equals("cdu"))  {
			if (doc != null) {
				result = extractInformation(result, doc, 10, 12);
			}
		} else if (partei.toLowerCase().equals("spd")) {
			if (doc != null) {
				result = extractInformation(result, doc, 14, 16);
			} 
		} else if (partei.toLowerCase().equals("linke")) {
			if (doc != null) {
				result = extractInformation(result, doc, 18, 21);
			}
		} else if (partei.toLowerCase().equals("grünen"))  {
			if (doc != null) {
				result = extractInformation(result, doc, 23, 25);
			}
		} else if (partei.toLowerCase().equals("fdp"))  {
			if (doc != null) {
				result = extractInformation(result, doc, 27, 28);
			}
		} else { 
			if (doc != null) {
				result = extractInformation(result, doc, 30, 33);
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



