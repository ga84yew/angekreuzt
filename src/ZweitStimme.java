
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.map.CaseInsensitiveMap;
/**
 * This public class is used to determine the party's opinion concerning a political category.
 * Input given are category, party and a mapping possibility.
 * The category, e.g. Steuern, is mapped to a group, e.g. SteuernundFinanzen.
 * Consequently, it passes on the name of the party (String partei) to the appropriate topic class of the group. 
 * @author severin engelmann and rainer wichmann
 * @version 1.1, 13.10.2017
 */
public class ZweitStimme {
	
	 //mapping of categorys to top level groups	
	private static CaseInsensitiveMap<String, String> mapCategoryToGroup;
		
	/**
	 * The method auswahl receives the two parameters category and partei, which are defined slots in the alexa skill builder. 
	 * For each political top level group, a list contains certain categorys values, representing members of the Group. 
	 * The method thus checks whether the input category is included in one of the specified.
	 * Groups. It selects the Group name String and checks if it is equal to any of some Strings representing the name of the Groups.
	 * If it equals, the value of partei is passed to the corresponding class, that delivers the opinion of the party to the group topic. 
	 * @param category selected category by user.
	 * The value of category is taken from the slot thema in Alexa.
	 * As defined in Amazon Developer portal, thema depends on a slot type called Thema.
	 * Slot type Thema contains a collection of String values used in request utterances. 
	 * @param partei selected party by user.
	 * If the method finds a match of the category in the mapping, it calls the getText method of the appropriate 
	 * group class and passes the name of the party as a String.
	 * @param mapping GroupMapping enabling to find the top level issue group of a category.
	 * GetText is meant to retrieve the correct opinion of the party partei from the defined source
	 * @return a text string, representing the opinion of the party partei to the topic category.
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
