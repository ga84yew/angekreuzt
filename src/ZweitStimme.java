
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.map.CaseInsensitiveMap;
/**
 * This public class is used to determine from the input given topic class (SteuernundFinanzen, Sicherheit, Bildung etc.) is called.
 * Consequently, it passes on the name of the party (String partei) to the appropriate topic class. 
 * @author severin engelmann and rainer wichmann
 * @version 1.0
 */
public class ZweitStimme {
	
	 //mapping of categorys to top level groups	
	private static CaseInsensitiveMap<String, String> mapCategoryToGroup;
		
	/**
	 * The method auswahl receives the two parameters category and partei, which are defined slots in the alexa skill builder. 
	 * For each political top level group, a list contains certain categorys values, representing members of the Group. 
	 * The method thus checks whether the input category is included in one of the specified
	 * Groups. It selects the Group name String and checks if it is equal to any of some Strings representing the name of the Groups.
	 * If it equals, the value of partei is passed to the corresponding class, that delivers the opinion of the party to the group topic. 
	 * @param category The value of category is taken from the slot thema in Alexa
	 * As defined in Amazon Developer portal, thema depends on a slot type called Thema that contains a collection of String values used in request utterances. 
	 * @param partei If the method finds a match between values in the ArrayList and in the slot type Thema, it calls the getText method of the appropriate 
	 * topic class and passes the name of the party.
	 * as a String.
	 * getText is meant to retrieve the correct opinion of the party partei from the defined source
	 * @return the result
	 */
	public static String auswahl(String category, String partei,GroupMapping mapping) {
	String result = "";	//result STring
		
	System.out.println(category);
	// get group of thema
	mapCategoryToGroup =mapping.mapCategoryToGroup; // get mapping Category to group
	String group= mapCategoryToGroup.get(category); // retrieve from map
	
	if(group.equals(("finanzen"))){result = SteuernundFinanzen.getText(partei);}
	else if(group.equals(("sicherheit"))){	result = Sicherheit.getText(partei);}
	else if(group.equals(("bildung"))){	result = Bildung.getText(partei);}
	else if(group.equals(("arbeitundsoziales"))){	result = ArbeitundSoziales.getText(partei);}
	else if(group.equals(("euundaussenpolitik"))){	result = EUundAussenpolitik.getText(partei);}
	else if(group.equals(("integrationundasyl"))){	result = IntegrationundAsyl.getText(partei);}
	
	return result;
}
}
