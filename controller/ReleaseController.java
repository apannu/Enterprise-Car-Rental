package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import datamodels.AvailableBookings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
 * File Name: ReleaseController.java Assignment: Final Project
 */
public class ReleaseController implements Initializable {

	@FXML 
	private TableView<AvailableBookings> tableView;
	@FXML
	private TableColumn<AvailableBookings, Integer> carBookingId;
	@FXML
	private TableColumn<AvailableBookings, Integer> carAvailabilityId;
	@FXML
	private TableColumn<AvailableBookings, String> pickupDate;
	@FXML
	private TableColumn<AvailableBookings, String> dropoffDate;
	@FXML
	private TableColumn<AvailableBookings, String> pickupTime;
	@FXML
	private TableColumn<AvailableBookings, String> dropoffTime;
	@FXML
	private TableColumn<AvailableBookings, Integer> carId;
	@FXML
	private Stage stage;
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private Parent root;
	@FXML 
	private Alert alert;
	@FXML
	private ObservableList<AvailableBookings> availableBookingsObservableList;
	private List<AvailableBookings> availableBookingsList;

	private ResultSet resultSet = null;
	private DatabaseManager databaseManager;
	
	@FXML
	private Button exitButton;
	
	@FXML
	private Button searchButton;
	
	//first method to be called at the load time of the controller
	@SuppressWarnings("rawtypes")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		databaseManager = new DatabaseManager();
		tableView.setRowFactory(new Callback<TableView<AvailableBookings>, TableRow<AvailableBookings>>() {

			@Override
			public TableRow<AvailableBookings> call(TableView<AvailableBookings> tv) {
				TableRow<AvailableBookings> tableRow = new TableRow<>();
				tableRow.setOnMouseClicked(event -> {
						try
						{
							Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to release this car?").showAndWait();	
							if(result.get() == ButtonType.OK)
							{
								int updateCode = databaseManager.releaseCarBooking(tableRow.getItem());
								int insertCode = databaseManager.insertNewCarAvailability(tableRow.getItem());
								System.out.println(updateCode);
								System.out.println(insertCode);
								if(updateCode>1)
								{
									if(insertCode==1)
									{
										Stage presentStage = (Stage) exitButton.getScene().getWindow();		
										presentStage.close();
										
										alert = new Alert(AlertType.CONFIRMATION);
										alert.setTitle("Release Car");
										alert.setContentText("The booking has been released. This car is now available to book from the present time." );
										Optional<ButtonType> result2 = alert.showAndWait();
										
										FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AdminConsole.fxml"));
										Parent parentRoot = (Parent) fxmlLoader.load();
										Stage nextStage = new Stage();
										nextStage.initModality(Modality.APPLICATION_MODAL);
										nextStage.initStyle(StageStyle.UNDECORATED);
										nextStage.setScene(new Scene(parentRoot));
										nextStage.show();
									}
								}
								else
								{
									Optional<ButtonType> result1 = new Alert(Alert.AlertType.ERROR, 
											"There was an error in releasing this car. Please try again").showAndWait();
								}
							}
						}
						catch(Exception e)
						{
//							System.out.println("An error occured while trying to release the car."+e.getMessage());
							e.printStackTrace();
						}
				});
				return tableRow;
			}
		});
	}
	
	//method to call the search method for the existing bookings
	@FXML
	public void searchCall(){
			searchCarAvailability();
	}
	
	//method to search the car availability
	private void searchCarAvailability() {
		try {
				resultSet = databaseManager.searchCurrentBookings();
				availableBookingsList = new ArrayList<AvailableBookings>();
				availableBookingsObservableList = FXCollections.observableArrayList();
				AvailableBookings availableBookings;
				while(resultSet.next())
				{
					availableBookings =  new AvailableBookings(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), 
							resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7));				
					availableBookingsObservableList.add(availableBookings);
				}	
				
				carBookingId.setCellValueFactory(new PropertyValueFactory<AvailableBookings, Integer>("carBookingIdProperty"));
				carAvailabilityId.setCellValueFactory(new PropertyValueFactory<AvailableBookings, Integer>("carAvailabilityIdProperty"));
				dropoffDate.setCellValueFactory(new PropertyValueFactory<AvailableBookings, String>("dropoffDateProperty"));
				dropoffTime.setCellValueFactory(new PropertyValueFactory<AvailableBookings, String>("dropoffTimeProperty"));
				pickupDate.setCellValueFactory(new PropertyValueFactory<AvailableBookings, String>("pickupDateProperty"));
				pickupTime.setCellValueFactory(new PropertyValueFactory<AvailableBookings, String>("pickupTimeProperty"));
				carId.setCellValueFactory(new PropertyValueFactory<AvailableBookings, Integer>("carIdProperty"));
				tableView.setItems(availableBookingsObservableList);
				
		} catch (Exception e) {
			System.out.println("An exception occured while trying to search for the car availability"+e.getMessage());
		}
	}
	
	//buttons events to navigate to different views
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException, SQLException{
	}
	
	//method to exit the page
	public void ExitForm(){
		try
		{
			Stage presentStage = (Stage) exitButton.getScene().getWindow();		
			presentStage.close();
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AdminConsole.fxml"));
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
