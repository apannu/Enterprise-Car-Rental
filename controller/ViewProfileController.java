package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import datamodels.Customer;
import datamodels.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
 * File Name: ViewProfileController.java Assignment: Final Project
 */
public class ViewProfileController extends EmailSending implements Initializable {
	
	@FXML
	private TextField firstName
						,lastName
						,emailId
						,gender	
						,phoneNumber
						,age
						,addressLine1
						,addressLine2
						,city
						,state
						,zipCode
						,licenceNumber;	
	@FXML
	private Button resetButton;
	@FXML
	private Button saveButton;
	@FXML
	private Button deactivateButton;
	@FXML 
	private Alert alert;
	@FXML
	private Stage stage;
	@FXML
	private Parent root;
	
	private DatabaseManager databaseManager;
	private ResultSet resultSet = null;
	private Customer customer;
	
	@FXML
	private Button exitButton;
	
	//first method to be called at the load time of the controller
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try
		{
			databaseManager = new DatabaseManager();
			getProfileDetails();
		}
		catch(Exception e)
		{
			System.out.println("An exception occured while trying to initialize the Database Manager class"+e.getMessage());
		}
	}
	
	//method to get the profile details
	public void getProfileDetails(){
		
		try{
			resultSet = databaseManager.getProfileDetails(User.getUsername());
			while(resultSet.next())
			{
				firstName.setText(resultSet.getString(1));
				lastName.setText(resultSet.getString(2));
				emailId.setText(resultSet.getString(3));
				gender.setText(resultSet.getString(4));
				phoneNumber.setText(resultSet.getString(5));
				age.setText(resultSet.getString(6));
				addressLine1.setText(resultSet.getString(7));
				addressLine2.setText(resultSet.getString(8));
				city.setText(resultSet.getString(9));
				state.setText(resultSet.getString(10));
				zipCode.setText(resultSet.getString(11));
				licenceNumber.setText(resultSet.getString(12));
			}
		}catch(Exception e){
			System.out.println("An exception occured while trying to fetch the profile details"+e.getMessage());
		}
	}
	
	//method to update the profile details
	public void updateProfileDetails(){
		try{
			if(validationForBlankFields()){
			customer = new Customer();
			
			customer.setLastName(lastName.getText());
			customer.setEmailId(emailId.getText());
			customer.setGender(gender.getText());
			customer.setPhoneNumber(phoneNumber.getText());
			customer.setAge(Long.parseLong(age.getText()));
			customer.setAddressLine1(addressLine1.getText());
			customer.setAddressLine2(addressLine2.getText());
			customer.setCity(city.getText());
			customer.setState(state.getText());
			customer.setZipCode(Long.parseLong(zipCode.getText()));
			customer.setLicenceNumber(Long.parseLong(licenceNumber.getText()));
			
			if(databaseManager.updateCustomerDetails(User.getUsername(), customer) == 1){
				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Profile");
				alert.setContentText("Profile Updated.");
				Optional<ButtonType> result = alert.showAndWait();
				
				ExitForm();
				
				
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AccountHolderConsole.fxml"));
				Parent parentRoot = (Parent) fxmlLoader.load();
				Stage nextStage = new Stage();
				nextStage.initModality(Modality.APPLICATION_MODAL);
				nextStage.initStyle(StageStyle.UNDECORATED);
				nextStage.setScene(new Scene(parentRoot));
				nextStage.show();
			}
			}else{
				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Profile");
				alert.setContentText("Fields cannot be blank.");
				Optional<ButtonType> result = alert.showAndWait();
			}
			
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	//method to exit the page
	public void ExitForm(){
		try
		{
			Stage stage = (Stage) exitButton.getScene().getWindow();		
			stage.close();
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AccountHolderConsole.fxml"));
			Parent parentRoot = (Parent) fxmlLoader.load();
			Stage nextStage = new Stage();
			nextStage.initModality(Modality.APPLICATION_MODAL);
			nextStage.initStyle(StageStyle.UNDECORATED);
			nextStage.setScene(new Scene(parentRoot));
			nextStage.show();
		}
		catch(Exception e)
		{
			System.out.println("An exception occured while performing the Exit operation."+e.getMessage());
		}
	}
	
	//method to dectivate the account
	public void deactivateAccount()
	{
		try
		{
			int deactivateCode = 0;
			deactivateCode = databaseManager.deactivateAccount(User.getUsername());
			if(deactivateCode==1)
			{
				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Account");
				alert.setContentText("Account has been deactivated.");
				Optional<ButtonType> result = alert.showAndWait();
			}
			else
			{
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Account");
				alert.setContentText("Account could not be deactivated. Please try again.");
				Optional<ButtonType> result = alert.showAndWait();
			}
		}
		catch(Exception e)
		{
			System.out.println("An exception occured while deactivating the account."+e.getMessage());
		}
	}
	
	//method to reset the fields
	public void resetAllFields(){
		try{
			lastName.setText("");
			emailId.setText("");
			gender.setText("");
			phoneNumber.setText("");
			age.setText("");
			addressLine1.setText("");
			addressLine2.setText("");
			city.setText("");
			state.setText("");
			zipCode.setText("");
			licenceNumber.setText("");
		}catch(Exception e){
			System.out.println("An exception occured while trying to reset all the fields"+e.getMessage());
		}
	}
	
	//buttons events to navigate to different views
	@FXML
	public void handleButtonAction(ActionEvent event) throws IOException, SQLException{
}
	
	//method that checks for blank fields
	public Boolean validationForBlankFields(){
		if(addressLine1.getText().equals(""))
			return false;
		else if(city.getText().equals(""))
			return false;
		else if(state.getText().equals(""))
			return false;
		else
			return true;
	}
	
	//method to check for blank email id
	public Boolean checkForBlankEmailId(){
		if(emailId.getText().equals(""))
			return false;
		else
			return true;
	}
}