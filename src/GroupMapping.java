import java.util.ArrayList;

import org.apache.commons.collections4.map.CaseInsensitiveMap;
/**
 * GroupMapping is used for Mapping of String Category to String group and from  String Group to ArrayList of Strings of Categorys.
 * For each political top level group, a list contains certain categorys values, representing members of the Group. 
 * Each map is a CaseInsensitiveMap since alexa does not care about cases.
 * mapping can be done in both directions: 
 * From Category to Group in attribute mapCategoryToGroup.
 * From Group to ArrayList of Categories in attribute mapGroupToCategorys.
 * @author Rainer Wichmann, Severin Engelmann
 * @version 1.1, 13.10.2017
 */
public class GroupMapping {
	CaseInsensitiveMap<String,String> mapCategoryToGroup;
	CaseInsensitiveMap<String,ArrayList<String>> mapGroupToCategorys;
	/**
	 * constructor sets attributes mapCategorysToGroup and mapGroupToCategorys
	 */                                                     
	public GroupMapping() {
		super();
		this.mapCategoryToGroup = new CaseInsensitiveMap<String,String>();  
		this.mapGroupToCategorys = new CaseInsensitiveMap<String,ArrayList<String>>();  
	}                                                                          
	
}
