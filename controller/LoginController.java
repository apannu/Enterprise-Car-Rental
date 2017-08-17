package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import datamodels.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.EmailSending;
import models.DatabaseManager;
/**
 * Name: Anirudh Pannu CWID: A20376101 Date: 2nd May, 2017 
 * File Name: LoginController.java Assignment: Final Project
 */
public class LoginController extends EmailSending implements Initializable {

	@FXML
	private TextField userName,passwordField;
	@FXML 
	private Alert alert;

	private ResultSet resultSet = null;
	private DatabaseManager databaseManager;
	private static int numberOfTimes = 4;

	//method to check the login credentials and generate an OTP
	public void Login(ActionEvent event) {
		String password = "", name = "", email = "", userType = "";
		char activeFlag = 'N';
		int returnCode;
		if(validateBlankFields()){
			try {
				resultSet = databaseManager.getLoginDetails(userName.getText().toString().trim());

				while(resultSet.next()){
					name = resultSet.getString(1);
					password = resultSet.getString(2);
					userType = resultSet.getString(3);
					activeFlag = resultSet.getString(4).charAt(0);
					email = resultSet.getString(5);
				}
				if(name!="")
				{
					if(activeFlag == 'Y' && !(userType.equals("admin"))){
						if(password.equals(passwordField.getText())){
							
							Stage stage = (Stage) userName.getScene().getWindow();		
							stage.close();
							Integer OTP = createOTP();
							sendEmailUsingSSL(email, name, OTP);
							databaseManager.updateLoginWithOTP(OTP, userName.getText());
	
							User.setUsername(userName.getText());
							User.setEmailId(email);
							User.setUserType(userType);
							
							new Alert(Alert.AlertType.CONFIRMATION, "Login Successful. Enter OTP sent to registered email address.").showAndWait();			 
							FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/OTP.fxml"));
							Parent parentRoot = (Parent) fxmlLoader.load();
							Stage nextStage = new Stage();
							nextStage.initModality(Modality.APPLICATION_MODAL);
							nextStage.initStyle(StageStyle.UNDECORATED);
							nextStage.setScene(new Scene(parentRoot));
							nextStage.show();
						}
						else{
							--numberOfTimes;
							if(numberOfTimes > 0){
								new Alert(Alert.AlertType.ERROR, "Login Unsuccessful. You have " +numberOfTimes +" login attempts left.").showAndWait();
								passwordField.setText("");
								passwordField.requestFocus();
							}
							else
							{
								new Alert(Alert.AlertType.ERROR, "Your account seems to be blocked. Please contact the executive.").showAndWait();
								returnCode = databaseManager.updateLoginWithMaxAttemps(userName.getText().toString().trim());
							}
						}
					}
					else if(activeFlag == 'Y' && (userType.equals("admin"))){
						if(password.equals(passwordField.getText())){
							
							Stage stage = (Stage) userName.getScene().getWindow();		
							stage.close();
							
							new Alert(Alert.AlertType.CONFIRMATION, "You have been logged in.").showAndWait();			 
							FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AdminConsole.fxml"));
							Parent parentRoot = (Parent) fxmlLoader.load();
							Stage currentStage = new Stage();
							currentStage.initModality(Modality.APPLICATION_MODAL);
							currentStage.initStyle(StageStyle.UNDECORATED);
							currentStage.setScene(new Scene(parentRoot));
	
							AdminController adminController = fxmlLoader.<AdminController>getController();
							User.setUsername(userName.getText());
//							adminController.firstMethod();
							currentStage.show();
						}
						else{
							--numberOfTimes;
							if(numberOfTimes > 0){
								new Alert(Alert.AlertType.ERROR, "Login Unsuccessful. You have " +numberOfTimes +" login attempts left.").showAndWait();
								passwordField.setText("");
								passwordField.requestFocus();
							}
							else
							{
								new Alert(Alert.AlertType.ERROR, "Your account is blocked. Please contact the bank representative.").showAndWait();
								returnCode = databaseManager.updateLoginWithMaxAttemps(userName.getText().toString().trim());
							}
						}
	
					}
					else{
						new Alert(Alert.AlertType.ERROR, "Your account is not activated or may have been blocked. Please contact the representative.").showAndWait();
						System.exit(0);
					}
			} 
				else
				{
					new Alert(Alert.AlertType.CONFIRMATION, "User "+userName.getText()+" does not exists. Please create an account to log in.").showAndWait();			 
				}
		}
			catch (Exception e) {
				System.out.println("An error occured while logging in. Please try again"+e.getMessage());
			}

			finally {
				databaseManager.closeConnection();
			}
		}
		else{
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Login");
			alert.setContentText("Field is Blank. Cannot Login.");
			Optional<ButtonType> result = alert.showAndWait();				
		}
	}
	
	//method to create the account
	public void createAccount(ActionEvent event) {
		try 
		{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CreateAccount.fxml"));
			Parent parentRoot;
			parentRoot = (Parent) fxmlLoader.load();
			Stage currentStage = new Stage();
			currentStage.initModality(Modality.APPLICATION_MODAL);
			currentStage.initStyle(StageStyle.UNDECORATED);
			currentStage.setTitle("ABC");
			currentStage.setScene(new Scene(parentRoot));  
			currentStage.show();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
//			System.out.println("An error occured while redirecting to the Create Account page."+e.getMessage());
		}
	}
	
	//first method to be called at the load time of the controller
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		databaseManager = new DatabaseManager();
	}

	//method that checks for blank fields
	public Boolean validateBlankFields(){
		if(userName.getText().toString().equals(""))
			return false;
		else if(passwordField.getText().toString().equals(""))
			return false;
		else
			return true;
	}

}