import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.collections4.*;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
/**
	 * Themen creas Array list of political contexts=subgroups and maps them to top level contexts=groups
	 * public attribute mapping is a public GroupMapping
	 * it uses GroupMapping for Mapping of String subgroup to String group and from  String groups to ArrayList of Strings of subgroups
	 * Each map is a CaseInsensitiveMap since alexa does not care about cases.
	 * @author Rainer Wichmann
	 * @version 1.0, 15.9.2017
	 */	
public class Themen {
	
public GroupMapping mapping = new GroupMapping();
	
	ArrayList<String> finanzenL= new ArrayList<String>();
	ArrayList<String> sicherheitL= new ArrayList<String>();
	ArrayList<String> bildungL= new ArrayList<String>();
	ArrayList<String> arbeitundsozialesL= new ArrayList<String>();
	ArrayList<String> euundaussenpolitikL= new ArrayList<String>();
	ArrayList<String> integrationundasylL= new ArrayList<String>();
	
	
	public Themen() {
		
	//value									//keys	
	finanzenL.addAll(Arrays.asList("Finanzen", "Steuern", "Finanzpolitik", "Steuerpolitik", "Steuer", "Wirtschaft", "Verbraucherschutz", 
			"Verkehr und Infrastruktur", "St�dtebau"));

	sicherheitL.addAll(Arrays.asList("innere-Sicherheit", "Milit�r", "Demokratie", "Terror", "Bundeswehr", "Verteidigung", "Schutz der Bev�lkerung"));

	bildungL.addAll(Arrays.asList("Bildung", "Digitalisierung", "Kita", "Kindergarten", "Schulbildung", 
			"Schule", "Ausbildung", "Universit�t", "Forschung", "Schulsystem", "Hochschule", "Studium", "Kultur", "Kinder", "Jugend"));

	arbeitundsozialesL.addAll(Arrays.asList("Arbeit", "Arbeitslosigkeit", "Familenpolitik", "Familie", "Kinder", "Kindergeld", 
			"Arbeitslosengeld", "Eltern","Frauen", "Rente", "Krankenversicherung", "Gesundheit", "Rentenversicherung", "Ehe"));
	euundaussenpolitikL.addAll(Arrays.asList("Europa", "euro�ische Union", "Ausland", "Auslandspolitik","EU", "Russland", "USA", 
			"Amerika", "Aussenpolitik", "T�rkei", "Internationales"));

	integrationundasylL.addAll(Arrays.asList("Integration", "Asyl", "Fl�chtlinge", "Immigration", "Einwanderung", 
			"Asylbewerber", "Einwanderungspolitik"));
	
	forArraytoMap( finanzenL, "finanzen");
		forArraytoMap( sicherheitL, "sicherheit");
		forArraytoMap( bildungL, "bildung");
		forArraytoMap( arbeitundsozialesL, "arbeitundsoziales");
		forArraytoMap( euundaussenpolitikL, "euundaussenpolitik");
		forArraytoMap( integrationundasylL, "integrationundasyl");	
	}
	
	
	public void forArraytoMap(ArrayList<String> l, String listname){
		for (int i=0; i<l.size(); i++){
			mapping.mapSubGroupsToGroup.put(l.get(i),listname );
		}
		mapping.mapGroupToSubGroups.put(listname ,l);
	}
	
	
}
