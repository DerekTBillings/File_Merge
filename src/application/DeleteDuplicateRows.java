package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DeleteDuplicateRows {

	public ArrayList<ArrayList<String>> initializeDuplicateRowsCheck(ArrayList<ArrayList<String>> fileInformation) {

		ArrayList<ArrayList<String>> compressedList = CompressArrayList(fileInformation);
		ArrayList<ArrayList<String>> duplicateRows = FindDuplicateRows(compressedList);
		ArrayList<ArrayList<String>> alterList = SetUpIgnoreList(duplicateRows, fileInformation);
		
		return alterList;
	}
	
	
	private ArrayList<ArrayList<String>> CompressArrayList(ArrayList<ArrayList<String>> fileInformation){

		ArrayList<ArrayList<String>> compressedList = new ArrayList<ArrayList<String>>();
		ArrayList<String> compressionRow = new ArrayList<String>();
		
		String recName = "";
		String sendRec = "";
		String status = "";
		for (int i=0; i<fileInformation.size(); i++){
			if (i == 0){
				recName = fileInformation.get(i).get(26);
				sendRec = fileInformation.get(i).get(29);
				status = fileInformation.get(i).get(1);
				compressionRow.add(fileInformation.get(i).get(26)); //0 recName
				compressionRow.add(Integer.toString(i)); //1 Start
			}
			else{
				if (recName.equals(fileInformation.get(i).get(26)) && sendRec.equals(fileInformation.get(i).get(29)) && status.equals(fileInformation.get(i).get(1))){
				} else{
					compressionRow.add(Integer.toString(i-1)); //2 End
					compressedList.add(compressionRow);
					compressionRow = new ArrayList<String>();
					recName = fileInformation.get(i).get(26);
					sendRec = fileInformation.get(i).get(29);
					status = fileInformation.get(i).get(1);
					compressionRow.add(fileInformation.get(i).get(26)); //0 recName
					compressionRow.add(Integer.toString(i)); //1 Start
				}
			}
			if (i == fileInformation.size()-1){
				compressionRow.add(Integer.toString(i-1)); //2 End
				compressedList.add(compressionRow);
				compressionRow = new ArrayList<String>();
			}
		}
		return compressedList;
	}
	
	private ArrayList<ArrayList<String>> FindDuplicateRows(ArrayList<ArrayList<String>> compressedList){
		
		ArrayList<ArrayList<String>> duplicateRows = new ArrayList<ArrayList<String>>();
	
		for (int i=0; i<compressedList.size(); i++){
			String currentRecName = compressedList.get(i).get(0);
			boolean addedToDouble = false;
			for (int x=0; x<compressedList.size(); x++){
				if (x != i && !addedToDouble && compressedList.get(x).get(0).equals(currentRecName)){
					duplicateRows.add(compressedList.get(i));
					addedToDouble = true;
					break;
				}
			}
		}
		return duplicateRows;
	}
	
	
	
	
	private ArrayList<ArrayList<String>> SetUpIgnoreList(ArrayList<ArrayList<String>> duplicateList, ArrayList<ArrayList<String>> fileInformation){
		
		ArrayList<ArrayList<String>> alterList = new ArrayList<ArrayList<String>>();
		ArrayList<String> alterRow = new ArrayList<String>();
		for(int i=0; i<duplicateList.size(); i++){
			for(int x=i; x<duplicateList.size(); x++){		
				if(i != x && duplicateList.get(i).get(0).equals(duplicateList.get(x).get(0))){
					int ii = Integer.parseInt(duplicateList.get(i).get(1));
					int xx = Integer.parseInt(duplicateList.get(x).get(1));
					ArrayList<String> iList = fileInformation.get(ii);
					ArrayList<String> xList = fileInformation.get(xx);
					String compareResults = CompareRows(iList, xList);					
					
					switch(compareResults){
					case "same":
						
						alterRow.add("same");
						alterRow.add(duplicateList.get(i).get(2));
						alterRow.add(duplicateList.get(x).get(1));
						alterRow.add(duplicateList.get(x).get(2));
						alterList.add(alterRow);
						alterRow = new ArrayList<String>();
						
						alterRow.add("ignore");
						alterRow.add(duplicateList.get(x).get(1));
						alterRow.add(duplicateList.get(x).get(2));
						alterList.add(alterRow);
						alterRow = new ArrayList<String>();
						break;
						
					case "iappend s": 
						alterRow.add("S");
						alterRow.add(duplicateList.get(i).get(1));
						alterList.add(alterRow);
						alterRow = new ArrayList<String>();
						
						alterRow.add("R");
						alterRow.add(duplicateList.get(x).get(1));
						alterList.add(alterRow);
						alterRow = new ArrayList<String>();
						break;
						
					case "iappend r": 
						alterRow.add("R");
						alterRow.add(duplicateList.get(i).get(1));
						alterList.add(alterRow);
						alterRow = new ArrayList<String>();
						
						alterRow.add("S");
						alterRow.add(duplicateList.get(x).get(1));
						alterList.add(alterRow);
						alterRow = new ArrayList<String>();
						break;
						
					case "ignore x": 
						alterRow.add("ignore");
						alterRow.add(duplicateList.get(x).get(1));
						alterRow.add(duplicateList.get(x).get(2));
						alterList.add(alterRow);
						alterRow = new ArrayList<String>();
						break;
						
					case "ignore i": 
						alterRow.add("ignore");
						alterRow.add(duplicateList.get(i).get(1));
						alterRow.add(duplicateList.get(i).get(2));
						alterList.add(alterRow);
						alterRow = new ArrayList<String>();
						break;
					}
				}			
			}
		}
		return alterList;
	}
	
	private String CompareRows(ArrayList<String> iList, ArrayList<String> xList){
		String results = "";
		if (iList.get(1).equals(xList.get(1))){
			
			if (iList.get(4) != null && xList.get(4) != null && iList.get(4).equals(xList.get(4))){
				
				if (iList.get(29).equals(xList.get(29))){
					results = "same";
				} else if(iList.get(29).equals("N")) {
					results = "iappend s";
				} else {
					results = "iappend r";
				}
				
			} else if (iList.get(4) != null && xList.get(4) != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MMM-YY", Locale.ENGLISH);
				Date iDate = null;
				Date xDate = null;
				try {
					iDate = dateFormat.parse(iList.get(4));
					xDate = dateFormat.parse(xList.get(4));
				} catch (ParseException e) {}
				
				if(iDate.after(xDate)){
					results = "ignore x";
				} else {
					results = "ignore i";
				}
			}
			
		} else if (iList.get(1).equals("P")) {
			results = "ignore x";
		} else {
			results = "ignore i";
		}
		return results;
	}
	

}





































