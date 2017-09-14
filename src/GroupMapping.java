import java.util.ArrayList;

import org.apache.commons.collections4.map.CaseInsensitiveMap;

public class GroupMapping {
	CaseInsensitiveMap<String,String> mapSubGroupsToGroup;
	CaseInsensitiveMap<String,ArrayList<String>> mapGroupToSubGroups;
	                                                     
	public GroupMapping() {
		super();
		this.mapSubGroupsToGroup = new CaseInsensitiveMap<String,String>();  
		this.mapGroupToSubGroups = new CaseInsensitiveMap<String,ArrayList<String>>();  
	}                                                                          
	
}
