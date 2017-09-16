
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.map.CaseInsensitiveMap;

public class ZweitStimme {
	
	private static CaseInsensitiveMap<String, String> mapSubGroupsToGroup; //mapping of subgroups to top level groups	
	
	public static String auswahl(String thema, String partei,GroupMapping mapping) {
	String result = "";	//result STring
		
	// get group of thema
	mapSubGroupsToGroup =mapping.mapSubGroupsToGroup; // get mapping subgroup to group
	String group= mapSubGroupsToGroup.get(thema); // retrieve from map
	
	if(group.equals(("finanzen"))){result = SteuernundFinanzen.getText(partei);}
	else if(group.equals(("sicherheit"))){	result = Sicherheit.getText(partei);}
	else if(group.equals(("bildung"))){	result = Bildung.getText(partei);}
	else if(group.equals(("arbeitundsoziales"))){	result = ArbeitundSoziales.getText(partei);}
	else if(group.equals(("euundaussenpolitik"))){	result = EUundAussenpolitik.getText(partei);}
	else if(group.equals(("integrationundasyl"))){	result = IntegrationundAsyl.getText(partei);}
	
	return result;
}
}


/*	
ArrayList<String> finanzen = Themen.finanzen;
=new ArrayList<String>();
finanzen.addAll(Arrays.asList("finanzen", "steuern", "finanzpolitik", "steuerpolitik", "steuer", "wirtschaft", "verbraucherschutz", 
"verkehr und infrastruktur", "staedtebau"));

if (Delegate.lowercase(group).equals("finanzen")){
r
}	
ArrayList<String> sicherheit = Themen.sicherheit ;
	=new ArrayList<String>();
sicherheit.addAll(Arrays.asList("innere sicherheit", "militaer", "demokratie", "terror", "bundeswehr", "verteidigung", "schutz der bevoelkerung"));
if (sicherheit.contains(themen.toLowerCase())) {

}



= new ArrayList<String>();
ArrayList<String> bildung   = Themen.bildung;
bildung.addAll(Arrays.asList("bildung", "digitalisierung", "kita", "kindergarten", "schulbildung", 
"schule", "ausbildung", "universität", "forschung", "schulsystem", "hochschule", "studium", "kultur", "kinder", "jugend"));

if (Delegate.lowercase(group).equals(bildung.contains(thema.toLowerCase())) {
;	
}
ArrayList<String> arbeitundsoziales =Themen.arbeitundsoziales;
= new ArrayList<String>();
arbeitundsoziales.addAll(Arrays.asList("arbeit", "arbeitslosigkeit", "familenpolitik", "familie", "kinder", "kindergeld", 
"arbeitslosengeld", "eltern","frauen", "rente", "krankenversicherung", "gesundheit", "rentenversicherung", "ehe"));


if (arbeitundsoziales.contains(thema.toLowerCase())) {

}
List<String> euundaussenpolitik = Themen.euundaussenpolitik;
		new ArrayList<String>();
euundaussenpolitik.addAll(Arrays.asList("europa", "europaeische union", "ausland", "auslandspolitik","eu", "russland", "usa", 
"amerika", "aussenpolitik", "tuerkei", "internationales"));

if (euundaussenpolitik.contains(thema.toLowerCase())) {

}
ArrayList<String> integrationundasyl = Themen.integrationundasyl;
new ArrayList<String>();
integrationundasyl.addAll(Arrays.asList("integration", "asyl", "fluechtlinge", "immigration", "einwanderung", 
"Asylbewerber", "Einwanderungspolitik"));

if (integrationundasyl.contains(thema.toLowerCase())) {
result 
}*/
