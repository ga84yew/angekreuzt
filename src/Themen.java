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
	
	ArrayList<String> finanzen= new ArrayList<String>();
	ArrayList<String> sicherheit= new ArrayList<String>();
	ArrayList<String> bildung= new ArrayList<String>();
	ArrayList<String> arbeitundsoziales= new ArrayList<String>();
	ArrayList<String> euundaussenpolitik= new ArrayList<String>();
	ArrayList<String> integrationundasyl= new ArrayList<String>();
	
	
	public Themen() {
		
	//value									//keys	
	finanzen.addAll(Arrays.asList("Finanzen", "Steuern", "Finanzpolitik", "Steuerpolitik", "Steuer", "Wirtschaft", "Verbraucherschutz", 
			"Verkehr und Infrastruktur", "Städtebau"));

	sicherheit.addAll(Arrays.asList("innere-Sicherheit", "Militär", "Demokratie", "Terror", "Bundeswehr", "Verteidigung", "Schutz der Bevölkerung"));

	bildung.addAll(Arrays.asList("Bildung", "Digitalisierung", "Kita", "Kindergarten", "Schulbildung", 
			"Schule", "Ausbildung", "Universität", "Forschung", "Schulsystem", "Hochschule", "Studium", "Kultur", "Kinder", "Jugend"));

	arbeitundsoziales.addAll(Arrays.asList("Arbeit", "Arbeitslosigkeit", "Familenpolitik", "Familie", "Kinder", "Kindergeld", 
			"Arbeitslosengeld", "Eltern","Frauen", "Rente", "Krankenversicherung", "Gesundheit", "Rentenversicherung", "Ehe"));
	euundaussenpolitik.addAll(Arrays.asList("Europa", "euroäische Union", "Ausland", "Auslandspolitik","EU", "Russland", "USA", 
			"Amerika", "Aussenpolitik", "Türkei", "Internationales"));

	integrationundasyl.addAll(Arrays.asList("Integration", "Asyl", "Flüchtlinge", "Immigration", "Einwanderung", 
			"Asylbewerber", "Einwanderungspolitik"));
	
	forArraytoMap( finanzen, "finanzen");
		forArraytoMap( sicherheit, "sicherheit");
		forArraytoMap( bildung, "bildung");
		forArraytoMap( arbeitundsoziales, "arbeitundsoziales");
		forArraytoMap( euundaussenpolitik, "euundaussenpolitik");
		forArraytoMap( integrationundasyl, "integrationundasyl");	
	}
	
	
	public void forArraytoMap(ArrayList<String> l, String listname){
		for (int i=0; i<l.size(); i++){
			mapping.mapSubGroupsToGroup.put(l.get(i),listname );
		}
		mapping.mapGroupToSubGroups.put(listname ,l);
	}
	
	
}
