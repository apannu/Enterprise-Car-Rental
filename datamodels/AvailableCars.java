package datamodels;

import java.sql.Date;
import java.sql.Timestamp;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Name: Anirudh Pannu CWID: A20376101 Date: 2nd May, 2017 
 * File Name: AvailableCars.java Assignment: Final Project
 */
//class to generate getters and setters related to the available cars
public class AvailableCars {
	
	private String carName;
	private String carType;
	private Date availableDate;
	private Timestamp availableTime;
	private String rate;
	private int availableId;
	
	private SimpleStringProperty carNameProperty;
	private SimpleStringProperty carTypeProperty;
	private SimpleStringProperty availableDateProperty;
	private SimpleStringProperty availableTimeProperty;
	private SimpleStringProperty rateProperty;
	private SimpleIntegerProperty availableIdProperty;
	
	public int getAvailableId() {
		return availableId;
	}
	public void setAvailableId(int availableId) {
		this.availableId = availableId;
	}
	public int getAvailableIdProperty() {
		return availableIdProperty.get();
	}
	public void setAvailableIdProperty(SimpleIntegerProperty availableIdProperty) {
		this.availableIdProperty = availableIdProperty;
	}
	public String getCarName() {
		return carName;
	}
	public void setCarName(String carName) {
		this.carName = carName;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public Date getAvailableDate() {
		return availableDate;
	}
	public void setAvailableDate(Date availableDate) {
		this.availableDate = availableDate;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getRateProperty() {
		return rateProperty.get();
	}
	public void setRateProperty(SimpleStringProperty rateProperty) {
		this.rateProperty = rateProperty;
	}
	public Timestamp getAvailabeTime() {
		return availableTime;
	}
	public void setAvailabeTime(Timestamp availabeTime) {
		this.availableTime = availabeTime;
	}
	public Timestamp getAvailableTime() {
		return availableTime;
	}
	public void setAvailableTime(Timestamp availableTime) {
		this.availableTime = availableTime;
	}
	public String getCarNameProperty() {
		return carNameProperty.get();
	}
	public void setCarNameProperty(SimpleStringProperty carNameProperty) {
		this.carNameProperty = carNameProperty;
	}
	public String getCarTypeProperty() {
		return carTypeProperty.get();
	}
	public void setCarTypeProperty(SimpleStringProperty carTypeProperty) {
		this.carTypeProperty = carTypeProperty;
	}
	public String getAvailableDateProperty() {
		return availableDateProperty.get();
	}
	public void setAvailableDateProperty(SimpleStringProperty availableDateProperty) {
		this.availableDateProperty = availableDateProperty;
	}
	public String getAvailableTimeProperty() {
		return availableTimeProperty.get();
	}
	public void setAvailableTimeProperty(SimpleStringProperty availableTimeProperty) {
		this.availableTimeProperty = availableTimeProperty;
	}
	public AvailableCars(String carName, String carType, String availableDate, String availableTime, String rate, Integer availableId){
		this.carNameProperty = new SimpleStringProperty(carName);
		this.carTypeProperty = new SimpleStringProperty(carType);
		this.availableDateProperty = new SimpleStringProperty(availableDate);
		this.availableTimeProperty = new SimpleStringProperty(availableTime);
		this.rateProperty = new SimpleStringProperty(rate);
		this.availableIdProperty = new SimpleIntegerProperty(availableId);
	}
}