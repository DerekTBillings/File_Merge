package application;

import java.io.File;
import java.util.ArrayList;

public class SystemInterfaces {

	public SystemInterfaces(ArrayList<ArrayList<String>> fileInformation, ArrayList<ArrayList<String>> abeInformation, File saveFile) {
		ArrayList<ArrayList<String>> collection = new ArrayList<ArrayList<String>>();
		for (int i=0; i<fileInformation.size(); i++){
			ArrayList<String> modifications = new ArrayList<String>();
			if (i == 0){
				modifications.add("Name");
				modifications.add("Description");
				modifications.add("SystemSource");
				modifications.add("SystemTarget");
				modifications.add("Interface Control Document");
				modifications.add("Transfer Agent");
				modifications.add("Check here if this is a (bi-directional) interface");
				modifications.add("Source about interface");
				modifications.add("Interface (Port)");
				modifications.add("Protocol");
			} else {
				
				String name = "";
				if (fileInformation.get(i).get(16) != null){
					name = fileInformation.get(i).get(13)+" - "+fileInformation.get(i).get(19)+" via "+fileInformation.get(i).get(16);
				} else{
					name = fileInformation.get(i).get(13)+" - "+fileInformation.get(i).get(19);
				}
				
				String controlDocument = "";
				boolean iChanged = false;
				String nextName = "";
				String controlDocumentNext = "";
				
				for (int x=i; x<fileInformation.size(); x++){
					if (fileInformation.get(x).get(16) != null){
						nextName = fileInformation.get(x).get(13)+" - "+fileInformation.get(x).get(19)+" via "+fileInformation.get(x).get(16);
					} else{
						nextName = fileInformation.get(x).get(13)+" - "+fileInformation.get(x).get(19);
					}
					if (nextName.equals(name)){
						if (x==i){
							controlDocument = fileInformation.get(x).get(3);
						}else if (!nextName.equals(fileInformation.get(x).get(3)) && !controlDocumentNext.equals(fileInformation.get(x).get(3))){
							controlDocument += "+"+fileInformation.get(x).get(3);
						}
						controlDocumentNext = fileInformation.get(x).get(3);
					}
					else if (!iChanged){
						i = (x-1);
						iChanged= !iChanged;
						break;
					}
				}
				if (!iChanged){
					i = fileInformation.size()-1;
				}
				
				
				modifications.add(name); //Name
				modifications.add(""); //Description
				
				String source = fileInformation.get(i).get(13);
				String target = fileInformation.get(i).get(19);
				
				boolean foundSource = false;
				boolean foundTarget = false;
				if (abeInformation != null){
					for (int y=0; y<abeInformation.size(); y++){
						if (abeInformation.get(y).get(0).toLowerCase().equals(source.toLowerCase())){
							source = "Definition:\"System (DM2)\":"+abeInformation.get(y).get(4)+".\""+source+"\"";
							foundSource = true;
							break;
						}
					}
					for (int y=0; y<abeInformation.size(); y++){
						if (abeInformation.get(y).get(0).toLowerCase().equals(target.toLowerCase())){
							target = "Definition:\"System (DM2)\":"+abeInformation.get(y).get(4)+".\""+target+"\"";
							foundTarget = true;
							break;
						}
					}
				}
				if (!foundSource){
					source = "Definition:\"System (DM2)\":\"  \".\""+source+"\"";
				}
				if (!foundTarget){
					target = "Definition:\"System (DM2)\":\"  \".\""+target+"\"";
				}
				
				modifications.add(source); //SystemSource
				modifications.add(target); //SystemTarget
				modifications.add(controlDocument); //Interface Control Document
				modifications.add(fileInformation.get(i).get(16)); //Transfer Agent
				modifications.add(""); //Check here if this is a (bi-directional) interface
				modifications.add("CDRS"); //Source about interface
				modifications.add(""); //Interface (Port)
				modifications.add(""); //Protocol
			}
			collection.add(modifications);
		}		
		WriteCSV excel = new WriteCSV(collection, saveFile);
		if (abeInformation == null){
			GuiFunctions.setMessage("Your file was created without ABE information", "#339933");
		}
	}
	
	

}
