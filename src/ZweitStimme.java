
import java.util.ArrayList;
import java.util.Arrays;

public class ZweitStimme {
	public static String auswahl(String themen, String partei) {
		String result = "";	
	ArrayList<String> finanzen = new ArrayList<String>();
	finanzen.addAll(Arrays.asList("Finanzen", "Steuern", "Finanzpolitik", "Steuerpolitik", "Steuer", "Wirtschaft", "Verbraucherschutz", 
			"Verkehr und Infrastruktur", "Städtebau"));
	if (finanzen.contains(themen)) {
		result = SteuernundFinanzen.getText(partei);
	}	
	ArrayList<String> sicherheit = new ArrayList<String>();
	sicherheit.addAll(Arrays.asList("innere Sicherheit", "Militär", "Demokratie", "Terror", "Bundeswehr", "Verteidigung", "Schutz der Bevölkerung"));
	if (sicherheit.contains(themen)) {
		result = Sicherheit.getText(partei);
	}
	ArrayList<String> bildung = new ArrayList<String>();
	bildung.addAll(Arrays.asList("Bildung", "Digitalisierung", "Kita", "Kindergarten", "Schulbildung", 
			"Schule", "Ausbildung", "Universität", "Forschung", "Schulsystem", "Hochschule", "Studium", "Kultur", "Kinder", "Jugend"));
	if (bildung.contains(themen)) {
		result = Bildung.getText(partei);	
	}
	ArrayList<String> arbeitundsoziales = new ArrayList<String>();
	arbeitundsoziales.addAll(Arrays.asList("Arbeit", "Arbeitslosigkeit", "Familenpolitik", "Familie", "Kinder", "Kindergeld", 
			"Arbeitslosengeld", "Eltern","Frauen", "Rente", "Krankenversicherung", "Gesundheit", "Rentenversicherung", "Ehe"));
	if (arbeitundsoziales.contains(themen)) {
		result = ArbeitundSoziales.getText(partei);
	}
	ArrayList<String> euundaussenpolitik = new ArrayList<String>();
	euundaussenpolitik.addAll(Arrays.asList("Europa", "europäisches Union", "Ausland", "Auslandspolitik","EU", "Russland", "USA", 
			"Amerika", "Aussenpolitik", "Türkei", "Internationales"));
	if (euundaussenpolitik.contains(themen)) {
		result = EUundAussenpolitik.getText(partei);
	}
	ArrayList<String> integrationundasyl = new ArrayList<String>();
	integrationundasyl.addAll(Arrays.asList("Integration", "Asyl", "Flüchtlinge", "Immigration", "Einwanderung", 
			"Asylbewerber", "Einwanderungspolitik"));
	if (integrationundasyl.contains(themen)) {
		result = IntegrationundAsyl.getText(partei);
	}
	return result;
}
}
