package models;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
/**
 * Name: Anirudh Pannu CWID: A20376101 Date: 2nd May, 2017 
 * File Name: IActionMethods.java Assignment: Final Project
 */
public interface IActionMethods {
	//method to navigate to the menu using controllers
	@FXML
	public void handleButtonAction(ActionEvent event) throws IOException, SQLException;
	
	//method to check for the blank fields
	public Boolean checkForBlank();
	
	//method to exit the form
	@FXML
	public void ExitForm(ActionEvent event) throws IOException;

}
