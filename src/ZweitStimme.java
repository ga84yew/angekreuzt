
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZweitStimme {
	public static String auswahl(String themen, String partei) {
		String result = "";	
	ArrayList<String> finanzen = new ArrayList<String>();
	finanzen.addAll(Arrays.asList("finanzen", "steuern", "finanzpolitik", "steuerpolitik", "steuer", "wirtschaft", "verbraucherschutz", 
			"verkehr und infrastruktur", "staedtebau"));
	if (finanzen.contains(themen.toLowerCase())) {
		result = SteuernundFinanzen.getText(partei);
	}	
	ArrayList<String> sicherheit = new ArrayList<String>();
	sicherheit.addAll(Arrays.asList("innere sicherheit", "militär", "demokratie", "terror", "bundeswehr", "verteidigung", "schutz der bevölkerung"));
	if (sicherheit.contains(themen.toLowerCase())) {
		result = Sicherheit.getText(partei);
	}
	ArrayList<String> bildung = new ArrayList<String>();
	bildung.addAll(Arrays.asList("bildung", "digitalisierung", "kita", "kindergarten", "schulbildung", 
			"schule", "ausbildung", "universität", "forschung", "schulsystem", "hochschule", "studium", "kultur", "kinder", "jugend"));
	if (bildung.contains(themen.toLowerCase())) {
		result = Bildung.getText(partei);	
	}
	ArrayList<String> arbeitundsoziales = new ArrayList<String>();
	arbeitundsoziales.addAll(Arrays.asList("arbeit", "arbeitslosigkeit", "familenpolitik", "familie", "kinder", "kindergeld", 
			"arbeitslosengeld", "eltern","frauen", "rente", "krankenversicherung", "gesundheit", "rentenversicherung", "ehe"));
	if (arbeitundsoziales.contains(themen.toLowerCase())) {
		result = ArbeitundSoziales.getText(partei);
	}
	List<String> euundaussenpolitik = new ArrayList<String>();
	euundaussenpolitik.addAll(Arrays.asList("europa", "europaeische union", "ausland", "auslandspolitik","eu", "russland", "usa", 
			"amerika", "aussenpolitik", "tuerkei", "internationales"));
	if (euundaussenpolitik.contains(themen.toLowerCase())) {
		result = EUundAussenpolitik.getText(partei);
	}
	ArrayList<String> integrationundasyl = new ArrayList<String>();
	integrationundasyl.addAll(Arrays.asList("integration", "asyl", "fluechtlinge", "immigration", "einwanderung", 
			"Asylbewerber", "Einwanderungspolitik"));
	if (integrationundasyl.contains(themen.toLowerCase())) {
		result = IntegrationundAsyl.getText(partei);
	}
	return result;
}
}
