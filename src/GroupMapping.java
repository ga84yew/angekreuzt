import java.util.ArrayList;

import org.apache.commons.collections4.map.CaseInsensitiveMap;
/**
 * GroupMappoing is used for Mapping of String subgroup to String group and from  String groups to ArrayList of Strings of subgroups
 * Each map is a CaseInsensitiveMap since alexa does not care about cases.
 * @author Rainer Wichmann
 * @version 1.0, 15.9.2017
 */
public class GroupMapping {
	CaseInsensitiveMap<String,String> mapSubGroupsToGroup;
	CaseInsensitiveMap<String,ArrayList<String>> mapGroupToSubGroups;
	/**
	 * constructor sets attributes mapSubGroupsToGroup and mapGroupToSubGroups
	 */                                                     
	public GroupMapping() {
		super();
		this.mapSubGroupsToGroup = new CaseInsensitiveMap<String,String>();  
		this.mapGroupToSubGroups = new CaseInsensitiveMap<String,ArrayList<String>>();  
	}                                                                          
	
}
