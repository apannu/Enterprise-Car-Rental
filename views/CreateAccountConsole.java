package views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Name: Anirudh Pannu CWID: A20376101 Date: 2nd May, 2017 
 * File Name: CreateAccountConsole.java Assignment: Final Project
 */
public class CreateAccountConsole extends Application {
	
	//start method to load the create account screen
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateAccountController.fxml"));
			Parent parentRoot = loader.load();
			Scene scene = new Scene(parentRoot);
			scene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			System.out.println("An exception occured while loading the Create Acocunt form."+e.getMessage());
		}
		
	}
	
	//main method
	public static void main(String[] args) {
		launch(args);
	}
}