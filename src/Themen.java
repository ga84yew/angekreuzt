import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.collections4.*;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
/**
	 * Themen creates Array list of political contexts=subgroups and maps them to top level contexts=groups
	 * public attribute mapping is a public GroupMapping
	 * it uses GroupMapping for Mapping of String subgroup to String group and from  String groups to ArrayList of Strings of subgroups
	 * Each map is a CaseInsensitiveMap since alexa does not care about cases.
	 * @author Rainer Wichmann
	 * @version 1.0, 15.9.2017
	 */	
public class Themen {
	
public GroupMapping mapping = new GroupMapping();
	
	public static ArrayList<String> finanzen= new ArrayList<String>();
	public static ArrayList<String> sicherheit= new ArrayList<String>();
	public static ArrayList<String> bildung= new ArrayList<String>();
	public static ArrayList<String> arbeitundsoziales= new ArrayList<String>();
	public static ArrayList<String> euundaussenpolitik= new ArrayList<String>();
	public static ArrayList<String> integrationundasyl= new ArrayList<String>();
	
	
	public Themen() {
		
	//value									//keys	
	finanzen.addAll(Arrays.asList("Finanzen", "Steuern", "Finanzpolitik", "Steuerpolitik", "Steuer", "Wirtschaft", "Verbraucherschutz", 
			"Verkehr und Infrastruktur", "Städtebau"));

	sicherheit.addAll(Arrays.asList("Sicherheit", "Militär", "Demokratie", "Terror", "Bundeswehr", "Verteidigung", "Schutz der Bevölkerung"));

	bildung.addAll(Arrays.asList("Bildung", "Digitalisierung", "Kita", "Kindergarten", "Schulbildung", 
			"Schule", "Ausbildung", "Universität", "Forschung", "Schulsystem", "Hochschule", "Studium", "Kultur", "Kinder", "Jugend"));

	arbeitundsoziales.addAll(Arrays.asList("Arbeit", "Soziales","Arbeitslosigkeit", "Familenpolitik", "Familie", "Kinder", "Kindergeld", 
			"Arbeitslosengeld", "Eltern","Frauen", "Rente", "Krankenversicherung", "Gesundheit", "Rentenversicherung", "Ehe"));
	euundaussenpolitik.addAll(Arrays.asList("Europa", "europäische Union", "Ausland", "Auslandspolitik","EU", "Russland", "USA", 
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
	/**
	* Themen creates Array list of political contexts=categorys and maps them to top level contexts=groups
			 * public attribute mapping is a public GroupMapping
			 * it uses GroupMapping for Mapping of String category to String group and from  String groups to ArrayList of Strings of categorys
			 * Each map is a CaseInsensitiveMap since alexa does not care about cases.
			 * @param l , Array list of strings
			 * @param listname String name of an array list of Strings
			 * @version 1.0, 15.9.2017
			 */	
	public void forArraytoMap(ArrayList<String> l, String listname){
		for (int i=0; i<l.size(); i++){
			mapping.mapCategoryToGroup.put(l.get(i),listname );
		}
		mapping.mapGroupToCategorys.put(listname ,l);
	}
	
	
}
