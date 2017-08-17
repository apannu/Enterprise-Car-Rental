package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import datamodels.User;
import datamodels.AvailableCars;
import datamodels.BookingDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import models.DatabaseManager;
/**
 * Name: Anirudh Pannu CWID: A20376101 Date: 2nd May, 2017 
 * File Name: BookCarController.java Assignment: Final Project
 */
public class BookCarController implements Initializable {

	@FXML
	private Label welcomeMessage,labelTableView;
	@FXML
	private Label pickupTimeLabel,dropoffTimeLabel;
	@FXML
	private DatePicker pickupDate,dropoffDate;
	@FXML
	private ComboBox<String> pickupTime = new ComboBox();
	@FXML
	private ComboBox<String> dropoffTime = new ComboBox();
	@FXML 
	private TableView<AvailableCars> tableView;
	@FXML
	private ObservableList<AvailableCars> availableCarsObservableList;
	@FXML
	private ObservableList<String> pickupTimeOptions;
	@FXML
	private ObservableList<String> dropoffTimeOptions;
	@FXML
	private TableColumn<AvailableCars, Hyperlink> carName;
	@FXML
	private TableColumn<AvailableCars, String> carType;
	@FXML
	private TableColumn<AvailableCars, String> availableDate;
	@FXML
	private TableColumn<AvailableCars, String> availableTime;
	@FXML
	private TableColumn<AvailableCars, String> rate;
	@FXML
	private TableColumn<AvailableCars, Integer> availableId;
	@FXML
	private Stage currentStage;
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private Parent parentRoot;
	@FXML 
	private Alert alert;
	private ResultSet resultSet = null;
	private DatabaseManager databaseManager;
	private List<AvailableCars> availableCarsList;
	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-LLLL-dd");
	@FXML
	private Button exitButton;
	@FXML
	private Button searchButton;
	
	//first method to be called at the load time of the controller
	@SuppressWarnings("rawtypes")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		databaseManager = new DatabaseManager();
		tableView.setRowFactory(new Callback<TableView<AvailableCars>, TableRow<AvailableCars>>() {

			@Override
			public TableRow<AvailableCars> call(TableView<AvailableCars> tv) {
				TableRow<AvailableCars> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
						try
						{
							Stage presentStage = (Stage) exitButton.getScene().getWindow();		
							presentStage.close();
							
							Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to go ahead with this booking?").showAndWait();	
							if(result.get() == ButtonType.OK)
							{
								AvailableCars availableCars = row.getItem();
								String carName = availableCars.getCarNameProperty();
								
								int availableId = availableCars.getAvailableIdProperty();
								
								BookingDetails bookingDetails = new BookingDetails();
								String carType = availableCars.getCarTypeProperty();
								String rate = availableCars.getRateProperty();
								
								bookingDetails.setCarName(carName);
								bookingDetails.setCarType(carType);
								bookingDetails.setPickupDate(pickupDate.getValue().format(dateTimeFormatter));
								bookingDetails.setDropoffDate(dropoffDate.getValue().format(dateTimeFormatter));
								bookingDetails.setPickupTime(pickupTime.getValue());
								bookingDetails.setDropoffTime(dropoffTime.getValue());
								bookingDetails.setCarType(carType);
								bookingDetails.setNumberOfHours(databaseManager.getNumberOfHours(pickupDate.getValue().format(dateTimeFormatter), 
										pickupTime.getValue(),
										dropoffDate.getValue().format(dateTimeFormatter), dropoffTime.getValue()));
								bookingDetails.setEmailId(User.getEmailId());
								bookingDetails.setRate(Double.parseDouble(rate));
								bookingDetails.setBookingAmount(bookingDetails.getNumberOfHours()*Double.parseDouble(rate));
								double tax = 0.10*bookingDetails.getBookingAmount();
								bookingDetails.setTax(tax);
								bookingDetails.setTotalAmount(bookingDetails.getBookingAmount()+bookingDetails.getTax());
								bookingDetails.setAvailableId(availableId);
								
								FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Payment.fxml"));
								Parent parentRoot = (Parent) fxmlLoader.load();
								Stage currentStage = new Stage();
								currentStage.initModality(Modality.APPLICATION_MODAL);
								currentStage.initStyle(StageStyle.UNDECORATED);
								currentStage.setScene(new Scene(parentRoot));
								currentStage.show();
								
								PaymentController controller = fxmlLoader.<PaymentController>getController();
								controller.getBookingDetails(bookingDetails);
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
				});
				return row;
			}
		});
		
		//on change event of the pickup date
		pickupDate.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent e) {
	    	pickupTimeLabel.setVisible(true);
	        pickupTime.setVisible(true);
	        pickupTimeOptions = FXCollections.observableArrayList();
	        for(int i = 0; i<25; i++)
	        {
	        	if(i==0)
	        		pickupTimeOptions.add("00:00");
	        	else
	        		pickupTimeOptions.add(i+":00");
	        }
	        pickupTime.setItems(pickupTimeOptions);
	    }
	});
		
		//on change event of the drop off date
		dropoffDate.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	dropoffTimeLabel.setVisible(true);
		        dropoffTime.setVisible(true);
		        dropoffTimeOptions = FXCollections.observableArrayList();
		        for(int i = 0; i<25; i++)
		        {
		        	if(i==0)
		        		dropoffTimeOptions.add("00:00");
		        	else
		        		dropoffTimeOptions.add(i+":00");
		        }
		        dropoffTime.setItems(dropoffTimeOptions);
		    }
		});

	}
	
	//method to call the search call availability
	@FXML
	public void initDateSearch(){
			searchCarAvailability();
	}
	
	//method to search the car availability
	private void searchCarAvailability() {
		try {
			if(checkForBlank())
			{
				resultSet = databaseManager.searchCarAvailaibility(pickupDate.getValue().format(dateTimeFormatter), 
						dropoffDate.getValue().format(dateTimeFormatter), 
						pickupTime.getValue());
				availableCarsList = new ArrayList<>();
				availableCarsObservableList = FXCollections.observableArrayList();
				AvailableCars availableCars;
				while(resultSet.next())
				{
					availableCars =  new AvailableCars(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), 
							resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6));				
					availableCarsObservableList.add(availableCars);
				}	
				
				carName.setCellValueFactory(new PropertyValueFactory<AvailableCars, Hyperlink>("carNameProperty"));
				carType.setCellValueFactory(new PropertyValueFactory<AvailableCars, String>("carTypeProperty"));
				availableDate.setCellValueFactory(new PropertyValueFactory<AvailableCars, String>("availableDateProperty"));
				availableTime.setCellValueFactory(new PropertyValueFactory<AvailableCars, String>("availableTimeProperty"));
				rate.setCellValueFactory(new PropertyValueFactory<AvailableCars, String>("rateProperty"));
				availableId.setCellValueFactory(new PropertyValueFactory<AvailableCars, Integer>("availableIdProperty"));
				tableView.setItems(availableCarsObservableList);
			}
			else
			{
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Car Search");
				alert.setContentText("Please select the dates.");
				Optional<ButtonType> result = alert.showAndWait();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//buttons events to navigate to different views
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException, SQLException{
	}
	
	//method that checks for blank fields
	public Boolean checkForBlank(){
		try
		{
			if(pickupDate.getValue()==null)
				return false;
			else if(dropoffDate.getValue()==null)
				return false;
			else
				return true;
		}
		catch(Exception e)
		{
			System.out.println("An exceeption occured while trying to check for blank fields"+e.getMessage());
		}
		return null;
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
}
