//package application;
//
//import java.io.File;
//import java.util.ArrayList;
//
//public class InformationExchanges {
//
//	public InformationExchanges(ArrayList<ArrayList<String>> fileInformation, File saveFile) {
//		ArrayList<ArrayList<String>> collection = new ArrayList<ArrayList<String>>();
//		DeleteDuplicateRows ddr = new DeleteDuplicateRows();
//		ArrayList<ArrayList<String>> alterList = ddr.initializeDuplicateRowsCheck(fileInformation);
//		
//		for (int i=0; i<fileInformation.size(); i++){
//			ArrayList<String> modifications = new ArrayList<String>();
//			if (i == 0){
//				modifications.add("Name");
//				modifications.add("Description");
//				modifications.add("Record Name");
//				modifications.add("Controlling ICDs");
//				modifications.add("Referenced Data");
//				modifications.add("Purpose / Trigger Event");
//				modifications.add("Mission/Scenario");
//				modifications.add("Language");
//				modifications.add("Content");
//				modifications.add("Size/Units");
//				modifications.add("Media");
//				modifications.add("Collaborative or One-Way");
//				modifications.add("LISI Level Req'd");
//				modifications.add("Frequency");
//				modifications.add("Timeliness");
//				modifications.add("Throughput");
//				modifications.add("Other");
//				modifications.add("Classification/Declassification Restrictions");
//				modifications.add("Criticality/Priority");
//				modifications.add("Integrity Checks Required");
//				modifications.add("Assured Authorization TO Send/Receive");
//				modifications.add("Physical");
//				modifications.add("Electronic");
//				modifications.add("Political/Economic");
//				modifications.add("Weather");
//				modifications.add("Terrain");
//			} else {
//				modifications.add(fileInformation.get(i).get(26)); //Name
//				
//				String record = fileInformation.get(i).get(26);
//				String description = "";
//				boolean updatedi = false;
//				
//				for (int x = i; x < fileInformation.size(); x++){
//					if (fileInformation.get(x).get(26).equals(record)){
//						if (x == i) {
//							description += "\""+ fileInformation.get(x).get(31) + " for "+fileInformation.get(x).get(26)+"\"";
//						}
//						else {
//							description += "+\""+ fileInformation.get(x).get(31) + " for "+fileInformation.get(x).get(26)+"\"";
//						}
//					}
//					else if (!updatedi){
//						i = x-1;
//						updatedi = !updatedi;
//						break;
//					}
//				}
//				modifications.add(description); //Description
//				modifications.add(fileInformation.get(i).get(27)); //Record Name
//				modifications.add(fileInformation.get(i).get(3)); //Controlling ICDs
//				modifications.add(""); //Referenced Data
//				modifications.add(""); //Purpose / Trigger Event
//				modifications.add(""); //Mission/Scenario
//				modifications.add(""); //Language
//				modifications.add(""); //Content
//				
//				int quantity = 0;
//				int length = 0;
//				
//				if (fileInformation.get(i).get(22) != null){
//					quantity = Integer.parseInt(fileInformation.get(i).get(22));
//				}
//				if (fileInformation.get(i).get(28) != null){
//					length = Integer.parseInt(fileInformation.get(i).get(28));
//				}
//				int size = (quantity*length*8);
//				
//				modifications.add(Integer.toString(size)); //Size/Units
//				modifications.add(""); //Media
//				modifications.add(""); //Collaborative or One-Way
//				modifications.add(""); //LISI Level Req'd
//				modifications.add(""); //Frequency
//				modifications.add(""); //Timeliness
//				modifications.add(""); //Throughput
//				modifications.add(""); //Other
//				modifications.add(""); //Classification/Declassification Restrictions
//				modifications.add(""); //Criticality/Priority
//				modifications.add(""); //Integrity Checks Required
//				modifications.add(""); //Assured Authorization TO Send/Receive
//				modifications.add(""); //Physical
//				modifications.add(""); //Electronic
//				modifications.add(""); //Political?Economic
//				modifications.add(""); //Weather
//				modifications.add(""); //Terrain
//				
//				
//				
//			}
//			collection.add(modifications);
//		}		
//		WriteCSV excel = new WriteCSV(collection, saveFile);
//	}
//
//}
//






package application;

import java.io.File;
import java.util.ArrayList;

public class InformationExchanges {

	public InformationExchanges(ArrayList<ArrayList<String>> fileInformation, File saveFile) {
		ArrayList<ArrayList<String>> collection = new ArrayList<ArrayList<String>>();
		DeleteDuplicateRows ddr = new DeleteDuplicateRows();
		ArrayList<ArrayList<String>> alterList = ddr.initializeDuplicateRowsCheck(fileInformation);
		
		for (int i=0; i<fileInformation.size(); i++){
			ArrayList<String> modifications = new ArrayList<String>();
			if (i == 0){
				modifications.add("Name");
				modifications.add("Description");
				modifications.add("Record Name");
				modifications.add("Controlling ICDs");
				modifications.add("Referenced Data");
				modifications.add("Purpose / Trigger Event");
				modifications.add("Mission/Scenario");
				modifications.add("Language");
				modifications.add("Content");
				modifications.add("Size/Units");
				modifications.add("Media");
				modifications.add("Collaborative or One-Way");
				modifications.add("LISI Level Req'd");
				modifications.add("Frequency");
				modifications.add("Timeliness");
				modifications.add("Throughput");
				modifications.add("Other");
				modifications.add("Classification/Declassification Restrictions");
				modifications.add("Criticality/Priority");
				modifications.add("Integrity Checks Required");
				modifications.add("Assured Authorization TO Send/Receive");
				modifications.add("Physical");
				modifications.add("Electronic");
				modifications.add("Political/Economic");
				modifications.add("Weather");
				modifications.add("Terrain");
			} else {
				
				String alertCase = "none";
				int alertLocation = 0;
				for (int y=0; y<alterList.size(); y++){
					if (alterList.get(y).get(1).equals(Integer.toString(i))){
						alertCase = alterList.get(y).get(0);
						alertLocation = y;
						break;
					}
				}
				if (alertCase.equals("ignore")){
					i = Integer.parseInt(alterList.get(alertLocation).get(2))+1;
				}
				
				if (i <= fileInformation.size()){
					
					
					modifications.add(fileInformation.get(i).get(26)); //Name
					
					String record = fileInformation.get(i).get(26);
					String description = "";
					boolean updatedi = false;
					
					for (int x = i; x < fileInformation.size(); x++){
						if (fileInformation.get(x).get(26).equals(record)){
							if (x == i) {
								description += "\""+ fileInformation.get(x).get(31) + " for "+fileInformation.get(x).get(26)+"\"";
							}
							else {
								description += "+\""+ fileInformation.get(x).get(31) + " for "+fileInformation.get(x).get(26)+"\"";
							}
						}
						else if (!updatedi){
								i = x-1;
							updatedi = !updatedi;
							break;
						}
					}
					if (!updatedi) {
						i = fileInformation.size()-1;
					}
					
					if (alertCase.equals("same")){
						int start = Integer.parseInt(alterList.get(alertLocation).get(2));
						int end = Integer.parseInt(alterList.get(alertLocation).get(3));
						for (int z= start; z<end; z++){
							description += "+\""+ fileInformation.get(z).get(31) + " for "+fileInformation.get(z).get(26)+"\"";
						}
					}
					
					modifications.add(description); //Description
					modifications.add("\""+fileInformation.get(i).get(27)+"\""); //Record Name
					if (alterList.equals("S") || alterList.equals("R")){
						modifications.add(fileInformation.get(i).get(3)+ " ("+alterList+")"); //Controlling ICDs
					} else {
						modifications.add(fileInformation.get(i).get(3)); //Controlling ICDs
					}
					modifications.add(""); //Referenced Data
					modifications.add(""); //Purpose / Trigger Event
					modifications.add(""); //Mission/Scenario
					modifications.add(""); //Language
					modifications.add(""); //Content
					
					int quantity = 0;
					int length = 0;
					
					if (fileInformation.get(i).get(22) != null){
						quantity = Integer.parseInt(fileInformation.get(i).get(22));
					}
					if (fileInformation.get(i).get(28) != null){
						length = Integer.parseInt(fileInformation.get(i).get(28));
					}
					int size = (quantity*length*8);
					
					modifications.add(Integer.toString(size)); //Size/Units
					modifications.add(""); //Media
					modifications.add(""); //Collaborative or One-Way
					modifications.add(""); //LISI Level Req'd
					modifications.add(""); //Frequency
					modifications.add(""); //Timeliness
					modifications.add(""); //Throughput
					modifications.add(""); //Other
					modifications.add(""); //Classification/Declassification Restrictions
					modifications.add(""); //Criticality/Priority
					modifications.add(""); //Integrity Checks Required
					modifications.add(""); //Assured Authorization TO Send/Receive
					modifications.add(""); //Physical
					modifications.add(""); //Electronic
					modifications.add(""); //Political?Economic
					modifications.add(""); //Weather
					modifications.add(""); //Terrain
				
				}
				
			}
			collection.add(modifications);
		}		
		WriteCSV excel = new WriteCSV(collection, saveFile);
	}

}









