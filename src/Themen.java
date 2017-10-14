import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.collections4.*;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
/**
	 * Themen creates a Group mapping of ArrayList of Strings. These strings represent political category. 
	 * It maps them to top level groups of categories.
	 * The categories and the group names are hardcoded here.
	 * public attribute mapping is a public GroupMapping, which can be accessed without an interface method.
	 * Each map is a CaseInsensitiveMap since alexa does not care about cases.
	 * @author Rainer Wichmann, Severin Engelmann
	 * @version 1.1, 13.10.2017
	 */	
public class Themen {
	
public GroupMapping mapping = new GroupMapping();
	
	public static ArrayList<String> finanzen= new ArrayList<String>();
	public static ArrayList<String> sicherheit= new ArrayList<String>();
	public static ArrayList<String> bildung= new ArrayList<String>();
	public static ArrayList<String> arbeitundsoziales= new ArrayList<String>();
	public static ArrayList<String> euundaussenpolitik= new ArrayList<String>();
	public static ArrayList<String> integrationundasyl= new ArrayList<String>();
	
	/**
	* Themen() constructor creates Array list of political categorys in category groups.
			 * It also creates the mapping possibility from category to category group and from category group to category
			 * by calling forArrayToMap.
			 * @version 1.1, 13.10.2017
			 */	
	public Themen() {
		
		//creates Array lists of political categorys. For every category group an array list is created.	
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
	
	//for every category group the mapping values are set in method forArrayToMap.
		forArrayToMap( finanzen, "finanzen");
		forArrayToMap( sicherheit, "sicherheit");
		forArrayToMap( bildung, "bildung");
		forArrayToMap( arbeitundsoziales, "arbeitundsoziales");
		forArrayToMap( euundaussenpolitik, "euundaussenpolitik");
		forArrayToMap( integrationundasyl, "integrationundasyl");	
	}
	/**
	* This method uses the following CaseInsensitiveMaps in public local attribute GroupMapping mapping: mapCategoryToGroup and mapGroupToCategory.
	* In mapCategoryToGroup the key is the category and the value is the group.
	* In mapGroupToCategory the key is the category Group and the value is the Array list of (Category) Strings. 
	* @param listname String representing the name of the category group.
	* @param l ArrayList of Strings containing all Strings (representing categories) that belong to the category group.
	* @version 1.1, 13.10.2017
	 */	
	public void forArrayToMap(ArrayList<String> l, String listname){
		// set key and value in mapping.mapCategoryToGroup
		for (int i=0; i<l.size(); i++){
			mapping.mapCategoryToGroup.put(l.get(i),listname );
		}
		// set key and value in mapping.mapGroupToCategory.
		mapping.mapGroupToCategorys.put(listname ,l);
	}
	
	
}
