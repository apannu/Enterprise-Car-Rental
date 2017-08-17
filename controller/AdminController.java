package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
/**
 * Name: Anirudh Pannu CWID: A20376101 Date: 2nd May, 2017 
 * File Name: AdminController.java Assignment: Final Project
 */
public class AdminController implements Initializable {
	@FXML
	private Label welcomeMessage;
	@FXML
	private MenuItem activateAccount, releaseCar;
	@FXML
	private Stage presentStage;
	@FXML
	private Parent parentRoot;
	@FXML
	private Button exitButton;
	
	//first method to be called at the load time of the controller
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		welcomeMessage.setText("Welcome, Admin");
	}
	
	//buttons events to navigate to different views
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException, SQLException{
		try
		{
			if(event.getSource()==activateAccount){
				//get reference to the button's stage         
				presentStage=(Stage) welcomeMessage.getScene().getWindow();
				//load up OTHER FXML document
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ActivateAccount.fxml"));
				parentRoot = fxmlLoader.load();
			}
			else if(event.getSource()==releaseCar){
				presentStage=(Stage) welcomeMessage.getScene().getWindow();
				//load up OTHER FXML document
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ReleaseCar.fxml"));
				parentRoot = fxmlLoader.load();
			}
			//create a new scene with root and set the stage
			Scene scene = new Scene(parentRoot);
			presentStage.setScene(scene);
			presentStage.show();
		}
		catch(Exception e)
		{
			System.out.println("An error occured while trying to chose a sub menu"+e.getMessage());
		}
	}
	
	//method to exit the page
	public void ExitForm(ActionEvent event) throws IOException {
		try
		{
			Stage presentStage = (Stage) exitButton.getScene().getWindow();
			presentStage.close();
		}
		catch(Exception e)
		{
			System.out.println("An exception occured while trying to exit the form."+e.getMessage());
		}
	}
}