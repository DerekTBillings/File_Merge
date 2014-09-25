package application;

import java.io.File;
import java.util.ArrayList;

public class ColumnsImport {

	public ColumnsImport(ArrayList<ArrayList<String>> fileInformation, File saveFile) {
		ArrayList<ArrayList<String>> collection = new ArrayList<ArrayList<String>>();
		for (int i=0; i<fileInformation.size(); i++){
			ArrayList<String> modifications = new ArrayList<String>();
			if (i == 0){
				modifications.add("Name");
				modifications.add("SQL Data Type");
				modifications.add("SQL Type Qualifiers");
				modifications.add("Description");
				modifications.add("Model");
				modifications.add("Source Diagram Name");
				modifications.add("Table Name");
			} else {
				String description = "";
				if (fileInformation.get(i).get(32) != null){
					description = fileInformation.get(i).get(32).replaceAll("\"", "'");
				}
				modifications.add(fileInformation.get(i).get(31)+" for "+fileInformation.get(i).get(26)); //Name
				modifications.add(fileInformation.get(i).get(34)); //SQL Data Type
				modifications.add(fileInformation.get(i).get(33));  //SQL Type Qualifiers
				modifications.add("\""+description+"\""); //Description
				modifications.add(""); //Model
				modifications.add(""); //Source Diagram Name
				modifications.add("\""+fileInformation.get(i).get(13)+"-"+fileInformation.get(i).get(19)+" "+fileInformation.get(i).get(3)+"\""); //Table Name
			}
			collection.add(modifications);
		}		
		WriteCSV excel = new WriteCSV(collection, saveFile);
	}

}
