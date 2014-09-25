package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class WriteCSV {
	
	
	public WriteCSV(ArrayList<ArrayList<String>> collection, File saveFile) {
		SXSSFWorkbook workbook = new SXSSFWorkbook(100);
		Sheet sheet = workbook.createSheet();
		
		StringBuffer data = new StringBuffer();
		
		for (int i=0; i<collection.size(); i++) {
	    	if (i > 0) {
	    		data.append("\r\n");
	    	}
	    	for (int x=0; x<collection.get(i).size(); x++){
	    		if (x > 0){
	    			data.append(",");
	    		}
	    		if(collection.get(i).get(x) != null){
	    			data.append(collection.get(i).get(x));
	    		}
	    	}
	    }		
		
		
		
		
		//useful for creating numbered columns
//	    for (int i=0; i<collection.size(); i++) {
//	    	Row row = sheet.createRow(i);
//	    	for (int x=0; x<collection.get(i).size(); x++){
//	    		Cell cell=row.createCell(x);
//	    		cell.setCellValue(collection.get(i).get(x) +" "+x);
//	    	}
//	    }	
		
		//this area is useful for speed but limited on size
//		HSSFWorkbook workbook = new HSSFWorkbook();
//		HSSFSheet sheet = workbook.createSheet("Output");
		
		
//		This is the format for xlsx sheet creation
//	    for (int i=0; i<collection.size(); i++) {
//	    	Row row = sheet.createRow(i);
//	    	for (int x=0; x<collection.get(i).size(); x++){
//	    		Cell cell=row.createCell(x);
//	    		cell.setCellValue(collection.get(i).get(x));
//	    	}
//	    }		
		try {
		    FileOutputStream out = new FileOutputStream(new File(saveFile+".csv"));
//		    workbook.write(out);
		    out.write(data.toString().getBytes());
		    out.close();
		    GuiFunctions.setMessage("Your file was created successfully", "#339933");
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		    GuiFunctions.setMessage("Your file was not created", "#FF0000");
		} catch (IOException e) {
		    e.printStackTrace();
		    GuiFunctions.setMessage("Your file was not created", "#FF0000");
		}
	}
}
