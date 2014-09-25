package application;

import java.io.File;
import java.util.ArrayList;

public class InterfaceControl {

	public InterfaceControl(ArrayList<ArrayList<String>> fileInformation, File saveFile) {
		ArrayList<ArrayList<String>> collection = new ArrayList<ArrayList<String>>();
		for (int i=0; i<fileInformation.size(); i++){
			ArrayList<String> modifications = new ArrayList<String>();
			if (i == 0){
				modifications.add("Name");
				modifications.add("Description");
				modifications.add("ICD Number");
				modifications.add("ICD Authorization");
				modifications.add("Interface Status");
				modifications.add("Information Exchange");
			} else {
				
				String description = "";
				if (fileInformation.get(i).get(6) != null){
					description = fileInformation.get(i).get(6).replaceAll("\"", "'");
				}
				
				modifications.add(fileInformation.get(i).get(3)); //Name
				modifications.add("\""+description+"\""); //Description
				modifications.add(fileInformation.get(i).get(0));  //ICD Number
				
				String icdDescription = "";
				if (fileInformation.get(i).get(5) != null){
					description = fileInformation.get(i).get(5).replaceAll("\"", "'");
				}
				
				modifications.add("\""+icdDescription+"\""); //ICD Authorization
				
				String status = fileInformation.get(i).get(1);
				if (status.equals("P")) status = "Production";
				else if (status.equals("D")) status = "Draft";
				
				modifications.add(status); //Interface Status
				
				String icdNumber = fileInformation.get(i).get(0);
				String information = "";
				String previousRec = "";
				boolean changedi = false;
				for (int x=i; x<fileInformation.size(); x++){
					if (fileInformation.get(x).get(0).equals(icdNumber)){
						if (x==i){
							information = fileInformation.get(x).get(26);
							previousRec = information;
						} else if (!previousRec.equals(fileInformation.get(x).get(26))) {
							information += "+"+fileInformation.get(x).get(26);
							previousRec = fileInformation.get(x).get(26);
						}
					}
					else if (!changedi){
						i=(x-1);
						changedi = !changedi;
						break;
					}
				}
				
				modifications.add(information); //Information Exchange
				
				
				
			}
			collection.add(modifications);
		}		
		WriteCSV excel = new WriteCSV(collection, saveFile);
	}

}
