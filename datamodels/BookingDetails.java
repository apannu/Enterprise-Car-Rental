package datamodels;
/**
 * Name: Anirudh Pannu CWID: A20376101 Date: 2nd May, 2017 
 * File Name: BookingDetails.java Assignment: Final Project
 */
//class to generate getters and setters related to the booking details
public class BookingDetails {
	
	private String carName;
	private String pickupDate;
	private String dropoffDate;
	private String pickupTime;
	private String dropoffTime;
	private String carType;
	private double numberOfHours;
	private String emailId;
	private double rate;
	private double bookingAmount;
	private double tax;
	private double totalAmount;
	private String stringTotalAmount;
	private int availableId;
	
	public String getStringTotalAmount() {
		return stringTotalAmount;
	}
	public void setStringTotalAmount(String stringTotalAmount) {
		this.stringTotalAmount = stringTotalAmount;
	}
	public int getAvailableId() {
		return availableId;
	}
	public void setAvailableId(int availableId) {
		this.availableId = availableId;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Double getBookingAmount() {
		return bookingAmount;
	}
	public void setBookingAmount(Double bookingAmount) {
		this.bookingAmount = bookingAmount;
	}
	public Double getTax() {
		return tax;
	}
	public void setTax(Double tax) {
		this.tax = tax;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getCarName() {
		return carName;
	}
	public void setCarName(String carName) {
		this.carName = carName;
	}
	public String getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}
	public String getDropoffDate() {
		return dropoffDate;
	}
	public void setDropoffDate(String dropoffDate) {
		this.dropoffDate = dropoffDate;
	}
	public String getPickupTime() {
		return pickupTime;
	}
	public void setPickupTime(String pickupTime) {
		this.pickupTime = pickupTime;
	}
	public String getDropoffTime() {
		return dropoffTime;
	}
	public void setDropoffTime(String dropoffTime) {
		this.dropoffTime = dropoffTime;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public Double getNumberOfHours() {
		return numberOfHours;
	}
	public void setNumberOfHours(Double numberOfHours) {
		this.numberOfHours = numberOfHours;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
}