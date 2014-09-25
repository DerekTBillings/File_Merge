package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {           
			AnchorPane page = (AnchorPane) FXMLLoader.load(Main.class.getResource("GUI.fxml"));
	        Scene scene = new Scene(page);
	        primaryStage.setScene(scene);
	        primaryStage.setTitle("Excel Export");
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
