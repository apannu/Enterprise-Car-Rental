package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import datamodels.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Alert.AlertType;
import models.IActionMethods;
import models.DatabaseManager;
/**
 * Name: Anirudh Pannu CWID: A20376101 Date: 2nd May, 2017 
 * File Name: OTPController.java Assignment: Final Project
 */
public class OTPController implements Initializable, IActionMethods{

	@FXML
	private PasswordField otp;

	@FXML
	private Button verifyButton;
	@FXML 
	private Alert alert;

	private DatabaseManager databaseManager;
	private ResultSet resultSet = null;

	private String userName;
	
	//first method to be called at the load time of the controller
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		databaseManager = new DatabaseManager();
	}

	//method to verify the generated otp
	public void verifyOTPField(){
		Integer OTP = null;
		if(checkForBlank()){
		try{
			resultSet = databaseManager.fecthOTP(User.getUsername());
			while(resultSet.next()){
				OTP = resultSet.getInt(1);
			}

			if(OTP == Integer.parseInt(otp.getText()))
			{
				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("OTP has been verified");
				alert.setContentText("Trying to log you in into the system");
				Optional<ButtonType> result = alert.showAndWait();

				if(result.get() == ButtonType.OK){
					if(User.getUserType().equals("admin")){
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AdminConsole.fxml"));
						Parent parentRoot = (Parent) fxmlLoader.load();
						Stage currentStage = new Stage();
						currentStage.initModality(Modality.APPLICATION_MODAL);
						currentStage.initStyle(StageStyle.UNDECORATED);
						currentStage.setTitle("Admin Profile");
						currentStage.setScene(new Scene(parentRoot));

						AdminController controller = fxmlLoader.<AdminController>getController();
						currentStage.show();
					}
					else{
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AccountHolderConsole.fxml"));
						Parent parentRoot = (Parent) fxmlLoader.load();
						Stage currentStage = new Stage();
						currentStage.initModality(Modality.APPLICATION_MODAL);
						currentStage.initStyle(StageStyle.UNDECORATED);
						currentStage.setTitle("User Profile");
						currentStage.setScene(new Scene(parentRoot));
						currentStage.show();

					}
					ExitForm();

				}	
			}
			else
			{
				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Failed to verify your OTP.");
				alert.setContentText("Please enter valid a OTP.");
				Optional<ButtonType> result1 = alert.showAndWait();
				ExitForm();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		}else{
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("OTP Verification");
			alert.setContentText("Fields cannot be blank.");
			Optional<ButtonType> result = alert.showAndWait();
		}
	}
	
	//method to exit the page
	public void ExitForm(){
		try
		{
			Stage currentStage = (Stage) verifyButton.getScene().getWindow();
			currentStage.close();
		}
		catch(Exception e)
		{
			System.out.println("An exception occured while trying to exit the form"+e.getMessage());
		}
	}
	
	//method that checks for blank fields
	public Boolean checkForBlank(){
		if(otp.getText().toString().equals(""))
			return false;
		else
			return true;
	}
	
	//buttons events to navigate to different views
	@Override
	public void handleButtonAction(ActionEvent event) throws IOException, SQLException {
	}
	@Override
	public void ExitForm(ActionEvent event) throws IOException {
	}
}