package datamodels;
/**
 * Name: Anirudh Pannu CWID: A20376101 Date: 2nd May, 2017 
 * File Name: Payment.java Assignment: Final Project
 */
//class to generate getters and setters related to the payment
public class Payment {
	
    private String cardNumber;
	private String cardDate;
	private String cardCVV;
	private String cardType;
	private String zipcode;
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardDate() {
		return cardDate;
	}
	public void setCardDate(String cardDate) {
		this.cardDate = cardDate;
	}
	public String getCardCVV() {
		return cardCVV;
	}
	public void setCardCVV(String cardCVV) {
		this.cardCVV = cardCVV;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
}
