package application;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

public class CSVReader {

	private static CSVReader instance = null;
	 protected CSVReader() {
		 // Exists only to defeat instantiation.
	 }
	 public static CSVReader getInstance() {
		 if(instance == null) {
			 instance = new CSVReader();
		 }
		 return instance;
	 }
	 
	 public ArrayList<ArrayList<String>> getCSV(String filePath, final CellProcessor[] PROCESSORS){
//		 final CellProcessor[] PROCESSORS = Processor; 
//	 	 final CellProcessor[] PROCESSORS = new CellProcessor[] { 
//	 		null, null, null, null, null, null, null, null, null, null, null, null, null,
//	 		null, null, null, null, null, null, null, null, null, null, null, null, null,
//	 		null, null, null, null, null, null, null, null, null, null, null};
	
	 	ICsvListReader listReader = null;
	 	ArrayList<ArrayList<String>> allRows = new ArrayList<ArrayList<String>>();
	 	try {
	 		listReader = new CsvListReader(new FileReader(filePath), CsvPreference.STANDARD_PREFERENCE);
//	 		listReader.getHeader(); // skip the header (can't be used with CsvListReader)
	 		final CellProcessor[] processors = PROCESSORS;	 		
	 		ArrayList<Object> csvList;
	 		while((csvList = (ArrayList<Object>) listReader.read(processors)) != null) {
	 			allRows.add(convertToArrayListOfStrings(csvList));
	 		}                
	 	} catch (Exception e) {
	 		e.printStackTrace();
	 	}
	 	finally {
	 		if( listReader != null ) {
	 			try {
	 				listReader.close();
	 			} catch (IOException e) {
	 				e.printStackTrace();
				}
           }
       }
	 	return allRows;
	}
	private static ArrayList<String> convertToArrayListOfStrings (ArrayList<Object> objArl){
		ArrayList<String> strArl = new ArrayList<String>();
		for (int i = 0; i<objArl.size();i++){
			strArl.add((String)objArl.get(i));
		}
		return strArl;		
	}
}
