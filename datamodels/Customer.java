package datamodels;
/**
 * Name: Anirudh Pannu CWID: A20376101 Date: 2nd May, 2017 
 * File Name: Customer.java Assignment: Final Project
 */
//class to generate getters and setters related to the customers
public class Customer {
	
    private String firstName;
	private String lastName;
	private String emailId;
	private String addressLine1;
	private String addressLine2;
	private Long age;
	private Long zipCode;
	private Long LicenceNumber;
	private String phoneNumber;
	private String city;
	private String username;
	private String gender;
	private String state;
	private String password;
	private String dateofbirth;
	
	public String getDateofbirth() {
		return dateofbirth;
	}
	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getEmail() {
		return emailId;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public String getCity() {
		return city;
	}
	public String getGender() {
		return gender;
	}
	public String getState() {
		return state;
	}
	public void setFirstName(String fName) {
		firstName = fName;
	}
	public void setLastName(String lName) {
		lastName = lName;
	}
	public void setAddressLine1(String aLine1) {
		addressLine1 = aLine1;
	}
	public void setAddressLine2(String aLine2) {
		addressLine2 = aLine2;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Long getAge() {
		return age;
	}
	public void setAge(Long age) {
		this.age = age;
	}
	public Long getZipCode() {
		return zipCode;
	}
	public void setZipCode(Long zipCode) {
		this.zipCode = zipCode;
	}
	public Long getLicenceNumber() {
		return LicenceNumber;
	}
	public void setLicenceNumber(Long licenceNumber) {
		LicenceNumber = licenceNumber;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
