package datamodels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 * Name: Anirudh Pannu CWID: A20376101 Date: 2nd May, 2017 
 * File Name: AvailableBookings.java Assignment: Final Project
 */
//class to generate getters and setters related to the existing bookings
public class AvailableBookings {
	
	private int carBookingId;
	private int carAvailabilityId;
	private String dropoffDate;
	private String dropoffTime;
	private String pickupDate;
	private String pickupTime;
	private int carId;
	
	private SimpleIntegerProperty carBookingIdProperty;
	private SimpleIntegerProperty carAvailabilityIdProperty;
	private SimpleStringProperty dropoffDateProperty;
	private SimpleStringProperty dropoffTimeProperty;
	private SimpleStringProperty pickupDateProperty;
	private SimpleStringProperty pickupTimeProperty;
	private SimpleIntegerProperty carIdProperty;
	
	public int getCarId() {
		return carId;
	}
	public void setCarId(int carId) {
		this.carId = carId;
	}
	public int getCarIdProperty() {
		return carIdProperty.get();
	}
	public void setCarIdProperty(SimpleIntegerProperty carIdProperty) {
		this.carIdProperty = carIdProperty;
	}
	public int getCarBookingId() {
		return carBookingId;
	}
	public void setCarBookingId(int carBookingId) {
		this.carBookingId = carBookingId;
	}
	public int getCarAvailabilityId() {
		return carAvailabilityId;
	}
	public void setCarAvilabilityId(int carAvailabilityId) {
		this.carAvailabilityId = carAvailabilityId;
	}
	public String getDropoffDate() {
		return dropoffDate;
	}
	public void setDropoffDate(String dropoffDate) {
		this.dropoffDate = dropoffDate;
	}
	public String getDropoffTime() {
		return dropoffTime;
	}
	public void setDropoffTime(String dropoffTime) {
		this.dropoffTime = dropoffTime;
	}
	public String getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}
	public String getPickupTime() {
		return pickupTime;
	}
	public void setPickupTime(String pickupTime) {
		this.pickupTime = pickupTime;
	}
	public int getCarBookingIdProperty() {
		return carBookingIdProperty.get();
	}
	public void setCarBookingIdProperty(SimpleIntegerProperty carBookingIdProperty) {
		this.carBookingIdProperty = carBookingIdProperty;
	}
	public int getCarAvailabilityIdProperty() {
		return carAvailabilityIdProperty.get();
	}
	public void setCarAvilabilityIdProperty(SimpleIntegerProperty carAvailabilityIdProperty) {
		this.carAvailabilityIdProperty = carAvailabilityIdProperty;
	}
	public String getDropoffDateProperty() {
		return dropoffDateProperty.get();
	}
	public void setDropoffDateProperty(SimpleStringProperty dropoffDateProperty) {
		this.dropoffDateProperty = dropoffDateProperty;
	}
	public String getDropoffTimeProperty() {
		return dropoffTimeProperty.get();
	}
	public void setDropoffTimeProperty(SimpleStringProperty dropoffTimeProperty) {
		this.dropoffTimeProperty = dropoffTimeProperty;
	}
	public String getPickupDateProperty() {
		return pickupDateProperty.get();
	}
	public void setPickupDateProperty(SimpleStringProperty pickupDateProperty) {
		this.pickupDateProperty = pickupDateProperty;
	}
	public String getPickupTimeProperty() {
		return pickupTimeProperty.get();
	}
	public void setPickupTimeProperty(SimpleStringProperty pickupTimeProperty) {
		this.pickupTimeProperty = pickupTimeProperty;
	}
	
	public AvailableBookings(int carBookingId, int carAvailabilityId, String dropoffDate, 
			String dropoffTime, String pickupDate, String pickupTime, int carId){
		this.carBookingIdProperty = new SimpleIntegerProperty(carBookingId);
		this.carAvailabilityIdProperty = new SimpleIntegerProperty(carAvailabilityId);
		this.dropoffDateProperty = new SimpleStringProperty(dropoffDate);
		this.dropoffTimeProperty = new SimpleStringProperty(dropoffTime);
		this.pickupDateProperty = new SimpleStringProperty(pickupDate);
		this.pickupTimeProperty = new SimpleStringProperty(pickupTime);
		this.carIdProperty = new SimpleIntegerProperty(carId);
	}
	
	
}