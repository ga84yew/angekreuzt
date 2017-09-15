
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Wahlsystem {
	
	public static String getText() {
		String result = "";
		Document doc = null; 
		try {
			doc = Jsoup.connect("http://www.bundestagswahl-bw.de/wahlsystem_btw.html").get();
			result = extractInformation(result, doc, 0, 11);
		} catch (Exception e) {
			e.printStackTrace();
			
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

public static void main(String[] args) {
	System.out.println(getText());
}

}