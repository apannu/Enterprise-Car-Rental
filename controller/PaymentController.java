package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import datamodels.Billing;
import datamodels.BookingDetails;
import datamodels.Payment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.DatabaseManager;
import models.EmailSending;
/**
 * Name: Anirudh Pannu CWID: A20376101 Date: 2nd May, 2017 
 * File Name: PaymentController.java Assignment: Final Project
 */
public class PaymentController extends EmailSending implements Initializable {
	
	@FXML
	private TextField 
	cardNumber
	,cardCVV
	,cardType
	,zipcode;
	
	@FXML
	private Label welcomeMessage;
	
	@FXML
	private Label 
	carName
	,pickupDate
	,dropOffDate
	,pickupTime
	,dropoffTime
	,carType
	,numberOfHours
	,emailId
	,bookingAmount
	,tax
	,totalAmount
	,bookingAvailableId
	;
	@FXML
	private DatePicker cardExpiryDate;

	@FXML
	private Button resetButton;
	@FXML
	private Button saveButton;
	@FXML
	private Button exitButton;
	@FXML 
	private Alert alert;

	private ResultSet resultSet = null;
	private DatabaseManager loginDatabaseOperations;
	private Payment payment;
	private Billing billing;
	private BookingDetails bookingDetails2;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LLLL-dd");
	
	int saveCard = 0;
	int saveBill = 0;
	int customerId = 0;

	public void resetAllFields(){
		cardNumber.setText("");
		cardCVV.setText("");
		zipcode.setText("");
	}
	
	//first method to be called at the load time of the controller
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}	
	
	//method to get the booking details
public void getBookingDetails(BookingDetails bookingDetails){
		
		try{
			loginDatabaseOperations = new DatabaseManager();
			
			carName.setText(bookingDetails.getCarName());
			pickupDate.setText(bookingDetails.getPickupDate());
			dropOffDate.setText(bookingDetails.getDropoffDate());
			pickupTime.setText(bookingDetails.getPickupTime());
			dropoffTime.setText(bookingDetails.getDropoffTime());
			carType.setText(bookingDetails.getCarType());
			numberOfHours.setText(Double.toString(bookingDetails.getNumberOfHours()));
			emailId.setText(bookingDetails.getEmailId());
			bookingAmount.setText("$"+Double.toString(bookingDetails.getBookingAmount()));
			tax.setText("$"+Double.toString(bookingDetails.getTax()));
			totalAmount.setText("$"+Double.toString(bookingDetails.getTotalAmount()));
			bookingAvailableId.setText(Integer.toString(bookingDetails.getAvailableId()));
			resultSet = loginDatabaseOperations.selectCustomerId(bookingDetails.getEmailId());
			
			while(resultSet.next())
				customerId = resultSet.getInt(1);
			
			billing = new Billing();
			billing.setCustomerId(customerId);
			billing.setTotalAmount(bookingDetails.getTotalAmount().intValue());
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

//method to get the card type that customer entered at the time of payment
private String getCardType(String cardNumber){

	String visaRegex        = "^4[0-9]{12}(?:[0-9]{3})?$";
	String masterRegex      = "^5[1-5][0-9]{14}$";
	String discoverRegex    = "^6(?:011|5[0-9]{2})[0-9]{12}$";
	String cardType 			= "";

	String regex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" +
			"(?<mastercard>5[1-5][0-9]{14})|" +
			"(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|" +
			"(?<amex>3[47][0-9]{13})|" +
			"(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|" +
			"(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$";

	try {
		Pattern pattern = Pattern.compile(regex);
		cardNumber = cardNumber.replaceAll("\\D", "");
		Matcher matcher = pattern.matcher(cardNumber);
		System.out.println(matcher.matches());
		if(matcher.matches()) {
			//If card is valid then verify which group it belong 
			if(matcher.group("mastercard")!= null){
				System.out.println("mastercard");
				cardType = "MASTER CARD";
			}
			else if(matcher.group("visa") != null){
				System.out.println("visa");
				cardType = "VISA";
			}
			else if(matcher.group("discover") != null){
				System.out.println("discover");
				cardType = "DISCOVER";
			}
			else if(matcher.group("amex") != null){
				System.out.println("amex");
				cardType = "AMEX";
			}
			else if(matcher.group("diners") != null){
				System.out.println("diners");
				cardType = "DINERS";
			}
		}
		else{
			cardType = "NA";
		}
	} catch (Exception e) {
		System.out.println("An exception occured while trying to get the card type of the entered card number."+e.getMessage());
	}
	return cardType;
}
	
	//method to save the payment details
	public void savePaymentDetails(ActionEvent event){
		try{
			if(validateBlankFields())
			{
				payment = new Payment();
				
				payment.setCardNumber(cardNumber.getText());
				payment.setCardDate(cardExpiryDate.getValue().format(formatter));
				payment.setCardCVV(cardCVV.getText());
				payment.setZipcode(zipcode.getText());
				payment.setCardType(getCardType(cardNumber.getText()));
				
				bookingDetails2 = new BookingDetails();
				bookingDetails2.setDropoffDate(dropOffDate.getText());
				bookingDetails2.setDropoffTime(dropoffTime.getText());
				bookingDetails2.setPickupDate(pickupDate.getText());
				bookingDetails2.setPickupTime(pickupTime.getText());
				bookingDetails2.setAvailableId(Integer.parseInt(bookingAvailableId.getText()));
				bookingDetails2.setEmailId(emailId.getText());
				bookingDetails2.setCarName(carName.getText());
				bookingDetails2.setCarType(carType.getText());
				bookingDetails2.setNumberOfHours(Double.valueOf(numberOfHours.getText()));
				bookingDetails2.setStringTotalAmount(totalAmount.getText());
				
				saveCard = loginDatabaseOperations.saveBillingDetails(payment, billing, bookingDetails2);
				if(saveCard==1)
				{
					sendConfirmationMail(bookingDetails2);
					alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Payment");
					alert.setContentText("Booking has been done. Please check your email for further details." );
					Optional<ButtonType> result = alert.showAndWait();
					
					ExitForm();
				}
				else
				{
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Payment");
					alert.setContentText("Some error occured while booking the car. Please try again." );
					Optional<ButtonType> result = alert.showAndWait();
				}
			}
			else
			{
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Payment");
				alert.setContentText("Please fill the mandatory fields.");
				Optional<ButtonType> result = alert.showAndWait();
			}
		}
		catch(Exception e)
		{
			System.out.println("An exception occured while trying to save the payment details"+e.getMessage());
		}
	}
	
	//method to exit the page
	public void ExitForm(){
		try
		{
			Stage presentStage = (Stage) exitButton.getScene().getWindow();		
			presentStage.close();
			
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
			System.out.println("An exception occured while trying to exit the form."+e.getMessage());
		}
	}

	//method that checks for blank fields
	public Boolean validateBlankFields(){
		if(cardNumber.getText().toString().equals(""))
		{
			cardNumber.requestFocus();
			return false;
		}
		else if(cardCVV.getText().toString().equals(""))
		{
			cardCVV.requestFocus();
			return false;
		}
		else if(zipcode.getText().toString().equals(""))
		{
			zipcode.requestFocus();
			return false;
		}
		else
			return true;
	}
}
