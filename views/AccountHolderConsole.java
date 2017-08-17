package views;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Name: Anirudh Pannu CWID: A20376101 Date: 2nd May, 2017 
 * File Name: AccountHolderConsole.java Assignment: Final Project
 */
public class AccountHolderConsole extends Application {
	@FXML
	private Stage stage;
	
	//start method to load the Account Holder's screen
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent parentRoot = FXMLLoader.load(getClass().getResource("/fxml/AccountHolderConsole.fxml"));
			Scene scene = new Scene(parentRoot,400,400);
			scene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			System.out.println("An exception occured while loading the Customer page."+e.getMessage());
		}
	}
	
	//main method
	public static void main(String[] args) {
		launch(args);
	}
}