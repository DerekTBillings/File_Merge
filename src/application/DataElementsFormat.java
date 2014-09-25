package application;

import java.io.File;
import java.util.ArrayList;

public class DataElementsFormat {

	public DataElementsFormat(ArrayList<ArrayList<String>> fileInformation, File saveFile){
		
		ArrayList<ArrayList<String>> collection = new ArrayList<ArrayList<String>>();
		for (int i=0; i<fileInformation.size(); i++){
			ArrayList<String> modifications = new ArrayList<String>();
			if (i == 0){
				modifications.add("Name");
				modifications.add("SQL Data Type");
				modifications.add("SQL Type Qualifiers");
				modifications.add("Default Value");
				modifications.add("Domain");
				modifications.add("Business Unit");
				modifications.add("Description");
				modifications.add("System Default");
				modifications.add("Element Name");
				modifications.add("Text Description");
				modifications.add("Database Comment");
				modifications.add("Data Steward");
				modifications.add("Element Comments");
				modifications.add("Data Source");
				modifications.add("Word Class");
				modifications.add("Record Mnemonic");
				modifications.add("Character Type");
				modifications.add("Data Mnemonic");
				modifications.add("Data Type");
			} else {
				
				modifications.add(fileInformation.get(i).get(31)+" for "+fileInformation.get(i).get(26)); //Name
				modifications.add(fileInformation.get(i).get(34)); //SQL Data Type
				modifications.add(fileInformation.get(i).get(33));  //SQL Type Qualifiers
				modifications.add(""); //Default Value
				modifications.add(""); //Domain
				modifications.add(""); //Business Unit
				modifications.add(""); //Description
				modifications.add(""); //System Default
				modifications.add("\""+fileInformation.get(i).get(32)+"\""); //Element Name
				modifications.add(""); //Text Description
				modifications.add(""); //Database Comment
				modifications.add(""); //Data Steward
				modifications.add(""); //Element Comments
				modifications.add("CDRS"); //Data Source
				modifications.add(""); //Word Class
				modifications.add(fileInformation.get(i).get(26)); //Record Mnemonic
				modifications.add(""); //Character Type
				modifications.add(fileInformation.get(i).get(31)); //Data Mnemonic
				modifications.add(""); //Data Type
			}
			collection.add(modifications);
		}		
		WriteCSV excel = new WriteCSV(collection, saveFile);
		
	}
	
}
