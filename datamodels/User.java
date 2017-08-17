package datamodels;
/**
 * Name: Anirudh Pannu CWID: A20376101 Date: 2nd May, 2017 
 * File Name: User.java Assignment: Final Project
 */
//class to generate getters and setters related to the user, to capture username and email id
public class User {
	
	private static String username;
	private static String emailId;
	private static String userType;
	
	public static String getUsername() {
		return username;
	}
	public static void setUsername(String username) {
		User.username = username;
	}
	public static String getEmailId() {
		return emailId;
	}
	public static void setEmailId(String emailId) {
		User.emailId = emailId;
	}
	public static String getUserType() {
		return userType;
	}
	public static void setUserType(String userType) {
		User.userType = userType;
	}
}
