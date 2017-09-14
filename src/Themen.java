import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Themen {
	
	ArrayList<String> finanzen= new ArrayList<String>();
	ArrayList<String> sicherheit= new ArrayList<String>();
	ArrayList<String> bildung= new ArrayList<String>();
	ArrayList<String> arbeitundsoziales= new ArrayList<String>();
	ArrayList<String> euundaussenpolitik= new ArrayList<String>();
	ArrayList<String> integrationundasyl= new ArrayList<String>();
	public Themen() {
	finanzen.addAll(Arrays.asList("Finanzen", "Steuern", "Finanzpolitik", "Steuerpolitik", "Steuer", "Wirtschaft", "Verbraucherschutz", 
			"Verkehr und Infrastruktur", "St�dtebau"));

	sicherheit.addAll(Arrays.asList("innere-Sicherheit", "Milit�r", "Demokratie", "Terror", "Bundeswehr", "Verteidigung", "Schutz der Bev�lkerung"));

	bildung.addAll(Arrays.asList("Bildung", "Digitalisierung", "Kita", "Kindergarten", "Schulbildung", 
			"Schule", "Ausbildung", "Universit�t", "Forschung", "Schulsystem", "Hochschule", "Studium", "Kultur", "Kinder", "Jugend"));

	arbeitundsoziales.addAll(Arrays.asList("Arbeit", "Arbeitslosigkeit", "Familenpolitik", "Familie", "Kinder", "Kindergeld", 
			"Arbeitslosengeld", "Eltern","Frauen", "Rente", "Krankenversicherung", "Gesundheit", "Rentenversicherung", "Ehe"));
	euundaussenpolitik.addAll(Arrays.asList("Europa", "euro�ische Union", "Ausland", "Auslandspolitik","EU", "Russland", "USA", 
			"Amerika", "Aussenpolitik", "T�rkei", "Internationales"));

	integrationundasyl.addAll(Arrays.asList("Integration", "Asyl", "Fl�chtlinge", "Immigration", "Einwanderung", 
			"Asylbewerber", "Einwanderungspolitik"));
	}
	public List<ArrayList<String>> getListofTopthemes(){

	List<ArrayList<String>> out= new ArrayList<ArrayList<String>>();
	out.add(finanzen);out.add(bildung);out.add(arbeitundsoziales);out.add(euundaussenpolitik);out.add(integrationundasyl);
	return out;	
	}
	
}
