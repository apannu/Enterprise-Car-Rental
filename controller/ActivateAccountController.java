package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.IActionMethods;
import models.DatabaseManager;

/**
 * Name: Anirudh Pannu CWID: A20376101 Date: 2nd May, 2017 File Name:
 * ActivateAccountController.java Assignment: Final Project
 */
public class ActivateAccountController implements Initializable, IActionMethods {

	@FXML
	private TextField emailId;
	@FXML
	private Label welcomeMessage;
	@FXML
	private MenuItem activateAccount;
	@FXML
	private Stage stage;
	@FXML
	private Parent root;
	@FXML
	private Button activateButton, exitButton;
	@FXML
	private Alert alert;

	private DatabaseManager databaseManager;

	// method to reset the fields
	public void resetFields() {
		emailId.setText("");
	}

	// first method to be called at the load time of the controller
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		databaseManager = new DatabaseManager();

		emailId.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if (!newValue.matches("\\D*")) {
					emailId.setText(newValue.replaceAll("[^\\D]", ""));
				}
			}

		});
	}

	// buttons events to navigate to different views
	@FXML
	public void handleButtonAction(ActionEvent event) throws IOException, SQLException {
		try {
			if (event.getSource() == activateAccount) {
				// get reference to the button's stage
				stage = (Stage) welcomeMessage.getScene().getWindow();
				// load up OTHER FXML document
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ActivateAccount.fxml"));
				root = fxmlLoader.load();
				ActivateAccountController activatecontroller = fxmlLoader.<ActivateAccountController> getController();
			}
			// create a new scene with root and set the stage
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			System.out.println("An exception occured while trying to navigate to the sub menu" + e.getMessage());
		}
	}

	// method to activate the account
	public void activeAccount() {
		try {
			if (checkForBlank()) {
				if (databaseManager.activateAccount(emailId.getText()) == 1)
					new Alert(Alert.AlertType.ERROR, "User with email id " + emailId.getText() + " has been activated.")
							.showAndWait();
				else
					new Alert(Alert.AlertType.CONFIRMATION,
							"Some error occured while trying to activate the user with email id: " + emailId.getText())
									.showAndWait();
			}
		} catch (Exception e) {
			System.out.println("An exception occured while trying to activate the user." + e.getMessage());
		}
	}

	// method to check blank fields
	@Override
	public Boolean checkForBlank() {
		if (emailId.getText().toString().equals("")) {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Activate Account");
			alert.setContentText("EmailId cannot be blank.");
			Optional<ButtonType> result = alert.showAndWait();
			emailId.requestFocus();
			return false;
		}

		else
			return true;
	}

	// method to exit the page
	@Override
	public void ExitForm(ActionEvent event) throws IOException {
	}

	// method to cancel the form
	public void CancelForm() {
		try {
			Stage stage = (Stage) exitButton.getScene().getWindow();
			stage.close();

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AdminConsole.fxml"));
			Parent parentRoot = (Parent) fxmlLoader.load();
			Stage nextStage = new Stage();
			nextStage.initModality(Modality.APPLICATION_MODAL);
			nextStage.initStyle(StageStyle.UNDECORATED);
			nextStage.setScene(new Scene(parentRoot));
			nextStage.show();
		} catch (Exception e) {
			System.out.println("An exception occured while trying to exit the form." + e.getMessage());
		}
	}
}