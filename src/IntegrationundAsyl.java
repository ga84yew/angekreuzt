
import java.io.IOException;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class IntegrationundAsyl {
	public static String getText(String partei)  {
		String result = "";
		Document doc = null; 	
		try {
			doc = Jsoup.connect("http://www.bundestagswahl-bw.de/integration_asyl_btwahl2017.html").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (partei.equals("CDU") || partei.equals("cdu")) {
			if (doc != null) {
				result = extractInformation(result, doc, 2, 3);
			}
		} else if (partei.equals("SPD") || partei.equals("spd")) {
			if (doc != null) {
				result = extractInformation(result, doc, 6, 7);
			} 
		} else if (partei.equals("Linke") || partei.equals("linke")) {
			if (doc != null) {
				result = extractInformation(result, doc, 10, 11);
			}
		} else if (partei.equals("Grüne") || partei.equals("gruene"))  {
			if (doc != null) {
				result = extractInformation(result, doc, 14, 15);
			}
		} else if (partei.equals("FDP")  || partei.equals("fdp"))  {
			if (doc != null) {
				result = extractInformation(result, doc, 18, 19);
			}
		} else { 
			if (doc != null) {
				result = extractInformation(result, doc, 20, 22);	
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




