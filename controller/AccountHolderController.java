package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import datamodels.User;
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
import models.IActionMethods;
import models.DatabaseManager;

/**
 * Name: Anirudh Pannu CWID: A20376101 Date: 2nd May, 2017 File Name:
 * AccountHolderController.java Assignment: Final Project
 */
public class AccountHolderController implements Initializable, IActionMethods {

	@FXML
	private Label welcomeMessage;
	@FXML
	private MenuItem editDetails, bookCar;
	@FXML
	private Stage stage;
	@FXML
	private Parent root;
	@FXML
	private Button exitButton;

	private ResultSet resultSet = null;
	private DatabaseManager databaseManager;

	// first method to be called at the load time of the controller
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		databaseManager = new DatabaseManager();
		String firstName = "", lastName = "", emailId = "";
		try {
			resultSet = databaseManager.getUserDetails(User.getUsername());

			while (resultSet.next()) {
				firstName = resultSet.getString(1);
				lastName = resultSet.getString(2);
				emailId = resultSet.getString(3);
			}
			welcomeMessage.setText("Welcome, " + firstName + " " + lastName);

		} catch (Exception e) {
			System.out.println("An error occured while opening the displaying the customer details." + e.getMessage());
		}

	}

	// buttons events to navigate to different views
	@Override
	public void handleButtonAction(ActionEvent event) throws IOException, SQLException {
		try {
			// TODO Auto-generated method stub
			if (event.getSource() == editDetails) {
				stage = (Stage) welcomeMessage.getScene().getWindow();
				root = FXMLLoader.load(getClass().getResource("/fxml/ViewProfile.fxml"));
			} else if (event.getSource() == bookCar) {
				// get reference to the button's stage
				stage = (Stage) welcomeMessage.getScene().getWindow();
				// load up OTHER FXML document
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/BookCar.fxml"));

				root = fxmlLoader.load();

				BookCarController controller = fxmlLoader.<BookCarController> getController();
			}
			// create a new scene with root and set the stage
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			System.out.println("An error occured while trying to chose a sub menu" + e.getMessage());
		}

	}

	// method that checks for blank fields
	@Override
	public Boolean checkForBlank() {
		return null;
	}

	// method to exit the page
	@Override
	public void ExitForm(ActionEvent event) throws IOException {
		try {
			Stage presentStage = (Stage) exitButton.getScene().getWindow();
			presentStage.close();
		} catch (Exception e) {
			System.out.println("An exception occured while trying to exit the form." + e.getMessage());
		}
	}
}
