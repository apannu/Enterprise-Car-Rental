package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import datamodels.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.DatabaseManager;
import models.EmailSending;
/**
 * Name: Anirudh Pannu CWID: A20376101 Date: 2nd May, 2017 
 * File Name: CreateAccountController.java Assignment: Final Project
 */
public class CreateAccountController extends EmailSending implements Initializable {

	@FXML
	private TextField 
	firstName
	,lastName
	,emailId
	,phoneNumber
	,age
	,addressLine1
	,addressLine2
	,city
	,state
	,zipCode
	,licenceNumber
	,username
	,password;
	
	@FXML
	private Label welcomeMessage;

	@FXML
	private ComboBox<String> genderComboBox = new ComboBox();

	@FXML
	private DatePicker dateOfBirth;

	@FXML
	private ComboBox<String> stateComboBox = new ComboBox();

	@FXML
	private Button resetButton;
	@FXML
	private Button saveButton;
	@FXML
	private Button exitButton;
	@FXML 
	private Alert alert;

	private ResultSet resultSet = null;
	private DatabaseManager databaseManager;
	private Customer customer;

	//method to reset the fields
	public void resetPageFields(){
		firstName.setText("");
		lastName.setText("");
		emailId.setText("");
		phoneNumber.setText("");
		genderComboBox.setValue("");
		age.setText("");
		addressLine1.setText("");
		addressLine2.setText("");
		city.setText("");
		stateComboBox.setValue("");
		zipCode.setText("");
		licenceNumber.setText("");
		username.setText("");
		password.setText("");
	}
	
	//first method to be called at the load time of the controller
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		databaseManager = new DatabaseManager();		
		customer = new Customer();
		List<String> list = new ArrayList<String>();
		//add the names of the states to the list
		list.add("Alabama");
		list.add("Alaska");
		list.add("Arizona");
		list.add("Arkansas");
		list.add("California");
		list.add("Colorado");
		list.add("Connecticut");
		list.add("Delaware");
		list.add("Florida");
		list.add("Georgia");
		list.add("Hawaii");
		list.add("Idaho");
		list.add("Illinois");
		list.add("Indiana");
		list.add("Iowa");
		list.add("Kansas");
		list.add("Kentucky");
		list.add("Louisiana");
		list.add("Maine");
		list.add("Maryland");
		list.add("Massachusetts");
		list.add("Michigan");
		list.add("Minnesota");
		list.add("Mississippi");
		list.add("Missouri");

		ObservableList<String> obList = FXCollections.observableList(list);
		stateComboBox.getItems().clear();
		stateComboBox.setItems(obList);

		List<String> list2 = new ArrayList<String>();
		list2.add("Male");
		list2.add("Female");

		ObservableList<String> obList2 = FXCollections.observableList(list2);
		genderComboBox.getItems().clear();
		genderComboBox.setItems(obList2);
	}	
	
	//method to create the account
	public void createAccount(ActionEvent event){
		try{
			if(checkForBlank()){
				customer.setFirstName(firstName.getText());
				customer.setLastName(lastName.getText());
				customer.setEmailId(emailId.getText());
				customer.setGender(genderComboBox.getValue());
				customer.setPhoneNumber(phoneNumber.getText());
				customer.setAge(Long.parseLong(age.getText()));
				customer.setAddressLine1(addressLine1.getText());
				customer.setAddressLine2(addressLine2.getText());
				customer.setCity(city.getText());
				customer.setState(stateComboBox.getValue());
				customer.setZipCode(Long.parseLong(zipCode.getText()));
				customer.setLicenceNumber(Long.parseLong(licenceNumber.getText()));
				customer.setUsername(username.getText());
				customer.setPassword(password.getText());
				if(checkExistingRecord(customer)){
					if(validateEmailId(customer.getEmail()))
					{
						databaseManager.saveCustomerDetails(customer);

						System.out.println("Account Created for Email Id: "+emailId.getText());
						alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Account Created");
						if(customer.getGender().equalsIgnoreCase("Male"))
							alert.setContentText("Account Created for Mr. "+ lastName.getText()+" Use OTP sent via email to login." );
						else if(customer.getGender().equalsIgnoreCase("Female"))
							alert.setContentText("Account Created for Miss "+ lastName.getText()+" Use OTP sent via email to login." );

						Optional<ButtonType> result = alert.showAndWait();
						ExitForm();
					}
					else{
						alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Create Account");
						alert.setContentText("Email Address Invalid. Please enter valid email address.");
						Optional<ButtonType> result = alert.showAndWait();
					}
				}
				else{
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Create Account");
					alert.setContentText("Duplicate Record found. Please use another email id.");
					Optional<ButtonType> result = alert.showAndWait();
				}
			
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	//method to exit the page
	public void ExitForm(){
		try
		{
			Stage stage = (Stage) exitButton.getScene().getWindow();		
			stage.close();
		}
		catch(Exception e)
		{
			System.out.println("An exception occured while performing Exit operation."+e.getMessage());
		}
	}

	//check if there is any existing record of the customer
	public Boolean checkExistingRecord(Customer user){
		try{
			resultSet = databaseManager.selectAllUsers(user.getEmailId());
			while(resultSet.next())
			{
				if(resultSet.getString(1).equals(user.getEmailId()))
					return false;
			}
		}catch(Exception e){
			System.out.println("Ane xception occured while trying to check the existing email id"+e.getMessage());
		}
		return true;
	}

	//method that checks for blank fields
	public Boolean checkForBlank(){
		if(firstName.getText().toString().equals(""))
		{
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Create Account");
			alert.setContentText("First Name cannot be blank.");
			Optional<ButtonType> result = alert.showAndWait();
			firstName.requestFocus();
			return false;
		}
		else if(lastName.getText().toString().equals(""))
		{
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Create Account");
			alert.setContentText("Last Name cannot be blank.");
			Optional<ButtonType> result = alert.showAndWait();
			lastName.requestFocus();
			return false;
		}
		else if(emailId.getText().toString().equals(""))
		{
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Create Account");
			alert.setContentText("Email Id cannot be blank.");
			Optional<ButtonType> result = alert.showAndWait();
			emailId.requestFocus();
			return false;
		}
		else if(genderComboBox.getValue()==null)
		{
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Create Account");
			alert.setContentText("Gender cannot be blank.");
			Optional<ButtonType> result = alert.showAndWait();

			genderComboBox.requestFocus();
			return false;
		}
		else if(phoneNumber.getText().toString().equals(""))
		{
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Create Account");
			alert.setContentText("Phone Number cannot be blank.");
			Optional<ButtonType> result = alert.showAndWait();
			phoneNumber.requestFocus();
			return false;
		}
		else if(age.getText().toString().equals(""))
		{
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Create Account");
			alert.setContentText("Age cannot be blank.");
			Optional<ButtonType> result = alert.showAndWait();
			age.requestFocus();
			return false;
		}
		else if(addressLine1.getText().toString().equals(""))
		{
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Create Account");
			alert.setContentText("Address Line 1 cannot be blank.");
			Optional<ButtonType> result = alert.showAndWait();
			addressLine1.requestFocus();
			return false;
		}
		else if(addressLine2.getText().toString().equals(""))
		{
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Create Account");
			alert.setContentText("Address Line 2 cannot be blank.");
			Optional<ButtonType> result = alert.showAndWait();
			addressLine2.requestFocus();
			return false;
		}
		else if(city.getText().toString().equals(""))
		{
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Create Account");
			alert.setContentText("City cannot be blank.");
			Optional<ButtonType> result = alert.showAndWait();
			city.requestFocus();
			return false;
		}
		else if(stateComboBox.getValue()==null)
		{
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Create Account");
			alert.setContentText("State cannot be blank.");
			Optional<ButtonType> result = alert.showAndWait();
			stateComboBox.requestFocus();
			return false;
		}
		else if(zipCode.getText()==null)
		{
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Create Account");
			alert.setContentText("Zip Code cannot be blank.");
			Optional<ButtonType> result = alert.showAndWait();
			zipCode.requestFocus();
			return false;
		}
		else if(licenceNumber.getText()==null)
		{
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Create Account");
			alert.setContentText("License Number cannot be blank.");
			Optional<ButtonType> result = alert.showAndWait();
			licenceNumber.requestFocus();
			return false;
		}
		else if(username.getText().toString().equals(""))
		{
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Create Account");
			alert.setContentText("Username cannot be blank.");
			Optional<ButtonType> result = alert.showAndWait();
			username.requestFocus();
			return false;
		}
		else if(password.getText().toString().equals("")){
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Create Account");
			alert.setContentText("Password cannot be blank.");
			Optional<ButtonType> result = alert.showAndWait();
			password.requestFocus();
			return false;
		}
		else
			return true;
	}
}
