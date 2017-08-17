package datamodels;

/**
 * Name: Anirudh Pannu CWID: A20376101 Date: 2nd May, 2017 
 * File Name: Billing.java Assignment: Final Project
 */
//class to generate getters and setters related to the billing
public class Billing {
	
    private int customerId;
	private int cardId;
	private int carBookingId;
	private int totalAmount;
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getCardId() {
		return cardId;
	}
	public void setCardId(int cardId) {
		this.cardId = cardId;
	}
	public int getCarBookingId() {
		return carBookingId;
	}
	public void setCarBookingId(int carBookingId) {
		this.carBookingId = carBookingId;
	}
	public int getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
}
