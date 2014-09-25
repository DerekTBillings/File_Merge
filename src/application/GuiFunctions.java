package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.supercsv.cellprocessor.ift.CellProcessor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Paint;
import javafx.stage.*;



public class GuiFunctions implements Initializable {

	@FXML
	public Button iBrowse;
	public Button generate; 
	public Button ImportAbe;
	
	public RadioButton DataElements;
	public RadioButton ColumnsImport;
	public RadioButton InformationExchanges;
	public RadioButton InterfaceControl;
	public RadioButton SystemInterfaces;
	
	public Window stage;
	
	public TextField importLocation;
	public TextField abeTextField;
	
	public static Label alertNotice = new Label();
	public Label AbeLabel;
	

	private String openFilePath;
	private String abeImportPath;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		final ToggleGroup rdoGroup = new ToggleGroup();
		DataElements.setToggleGroup(rdoGroup);
		ColumnsImport.setToggleGroup(rdoGroup);
		InformationExchanges.setToggleGroup(rdoGroup);
		InterfaceControl.setToggleGroup(rdoGroup);
		SystemInterfaces.setToggleGroup(rdoGroup);
		
		iBrowse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	File openFile = showDialog("open");
            	
            	if (openFile != null && openFile.canRead()){
            		
            		openFilePath = openFile.getPath();
	            	importLocation.setText(openFilePath);
	            	setMessage("", "#000000");
            	} else{
            		setMessage("The operation was cancelled.", "#FF0000");
            	}
            }
        });
		
		generate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Toggle radio = rdoGroup.getSelectedToggle();
            	if (openFilePath == null){
            		setMessage("You must import a file", "#FF0000");
            	}
            	else {
	            	if (radio == null) {
	            		setMessage("You must select an output format", "#FF0000");
	            	}
	            	else {
	            		Generate(radio);
	            	}
            	}
            }
        });
		
		ImportAbe.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				File openFile = showDialog("open");
				if (openFile.canRead() && openFile != null){
					abeImportPath = openFile.getPath();
					abeTextField.setText(abeImportPath);
					setMessage("", "#000000");	
				}
					
			}
			
		});
		
		SystemInterfaces.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				toggleAbeImport(false, 1);
			}
		});
		
		DataElements.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				toggleAbeImport(true, .5);
			}
		});
		
		ColumnsImport.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				toggleAbeImport(true, .5);
			}
		});
		
		InformationExchanges.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				toggleAbeImport(true, .5);
			}
		});
		
		InterfaceControl.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				toggleAbeImport(true, .5);
			}
		});
		
	}
	
	
	public File showDialog (String openType){
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("CSV Chooser");
        try {
			File defaultDirectory = new File(new File(".").getCanonicalPath());
			fileChooser.setInitialDirectory(defaultDirectory);
		} catch (IOException e) {
			e.printStackTrace();
		}
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(filter);
        if(openType.equals("open")){
        	File file = fileChooser.showOpenDialog(stage);
        	return file;
        } else{
        	File file = fileChooser.showSaveDialog(stage);
        	return file;
        }
	}
	
	public void Generate(Toggle radioSelected){
		
    	setMessage("Generating your document...", "#FF6600");
		
    	File saveFile = showDialog("save");
    	if (saveFile != null){
    		
    		ArrayList<ArrayList<String>> fileInformation = generateArray("import");
    		
    		if (fileInformation != null){
	    		if (radioSelected == DataElements){
	    			DataElementsFormat def = new DataElementsFormat(fileInformation, saveFile);
	//	    			WriteExcel write = new WriteExcel(fileInformation, saveFile);
	    		}
	    		else if (radioSelected == ColumnsImport){
	    			ColumnsImport ci = new ColumnsImport(fileInformation, saveFile);
	    		}
	    		else if (radioSelected == InformationExchanges){
	    			InformationExchanges ie = new InformationExchanges(fileInformation, saveFile);
	    		}
	    		else if (radioSelected == InterfaceControl){
	    			InterfaceControl ic = new InterfaceControl(fileInformation, saveFile);
	    		}
	    		else if (radioSelected == SystemInterfaces){
	    			if (abeImportPath != null && !abeImportPath.equals("")){
		    			ArrayList<ArrayList<String>> abeInformation = generateArray("");
		    			SystemInterfaces si = new SystemInterfaces(fileInformation, abeInformation, saveFile);
	    			} else {
	    				setMessage("Could not read ABE file.", "#FF0000");
	    				SystemInterfaces si = new SystemInterfaces(fileInformation, null, saveFile);
	    			}
	    		}
    		}
    		
    	}else {
    		setMessage("Operation was cancelled.", "#FF0000");
    	}
	}
	
	
	public static void setMessage(String notice, String color){
		Paint c = Paint.valueOf(color);
		alertNotice.setTextFill(c);
		alertNotice.setText(notice);
		alertNotice.setAlignment(Pos.CENTER);
	}
	
	
	private void toggleAbeImport(boolean disable, double opacity){
		ImportAbe.setDisable(disable);
		ImportAbe.setOpacity(opacity);
		AbeLabel.setOpacity(opacity);
	}
	
	
	private ArrayList<ArrayList<String>> generateArray(String type){
		try{
			if (type.equals("import")){
				CellProcessor[] PROCESSORS = new CellProcessor[] { 
				 		null, null, null, null, null, null, null, null, null, null, null, null, null,
				 		null, null, null, null, null, null, null, null, null, null, null, null, null,
				 		null, null, null, null, null, null, null, null, null, null, null};
				CSVReader reader = CSVReader.getInstance();
				return reader.getCSV(openFilePath, PROCESSORS);
			}
		} catch(Exception e) {
			setMessage("The uploaded CDRS file could not be read.", "#FF0000");
		}
		try{
				CellProcessor[] PROCESSORS = new CellProcessor[] { 
			 		null, null, null, null, null, null, null, null, null, null, null,
			 		null, null, null, null, null, null, null, null, null, null, null,
			 		null, null, null, null, null, null, null, null, null, null, null,
			 		null, null, null, null, null, null, null, null, null, null, null,
			 		null, null, null, null, null, null, null, null, null, null,};
				CSVReader reader = CSVReader.getInstance();
				return reader.getCSV(abeImportPath, PROCESSORS);
		} catch(Exception e) {
			setMessage("The uploaded ABE file could not be read.", "#FF0000");
		}
		return null;
	}
	
}

















