
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Wahlsystem {
	public static String getText()  {
		String result = "";
		Document doc = null; 
		try {
			doc = Jsoup.connect("http://www.bundestagswahl-bw.de/wahlsystem_btw.html").post();
		} catch (Exception e) {
			e.printStackTrace();
			result = extractInformation(result, doc, 0, 11);
		}	
		return result;
	}

	private static String extractInformation(String result, Document doc, int start, int ende) {
		Elements party = doc.body().select("p.bodytext");
		for(int index = start; index <=ende;index++) {
			result += party.get(index).text();
		}
		return result;
	}
}