package models;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import datamodels.Payment;
import datamodels.AvailableBookings;
import datamodels.Billing;
import datamodels.BookingDetails;
import datamodels.Customer;
/**
 * Name: Anirudh Pannu CWID: A20376101 Date: 2nd May, 2017 
 * File Name: DatabaseManager.java Assignment: Final Project
 */
public class DatabaseManager {

	private int returnCode = -1;
	private int returnCode1 = -1;
	private int returnCode2 = -1;
	private int returnCode3 = -1;
	private int returnCode4 = -1;
	private PreparedStatement preparedStatement;
	private	ResultSet resultSet = null;
	private StringBuilder stringBuilder = new StringBuilder();
	private Connection connection;
	private Connector connector = new Connector();
	private int maxCustomerId =0;
	private int maxCardId =0;
	private int maxAvailableId =0;
	private int maxBillingId =0;
	private int maxBookingId =0;
	private int maxLoginId =0;

	//default constructor
	public DatabaseManager() {
	}
	
	//method to get the login details
	public ResultSet getLoginDetails(String username){
		try{
			connection = connector.getConn();
			if(connection!= null){
				stringBuilder = new StringBuilder();
				stringBuilder.append("select login.username, login.password, ");
				stringBuilder.append("login.type, login.active_flag, cust.email_id, login.type ");
				stringBuilder.append("from login_details login, customer_details cust where login.username = ? ");
				stringBuilder.append("and cust.customer_id = login.customer_id and login.active_flag = 'Y';");
				preparedStatement = (PreparedStatement) connection.prepareStatement(stringBuilder.toString());
				preparedStatement.setString(1, username);
				resultSet = preparedStatement.executeQuery();
			}
		}catch(Exception e){
			System.out.println("An error occured while checking the logging details."+e.getMessage());
		}
		
		return resultSet;
	}
	
	//method to get the user details
	public ResultSet getUserDetails(String username){
		try{
			connection = connector.getConn();
			if(connection!= null){
				stringBuilder = new StringBuilder();
				stringBuilder.append(" select customer_fname, customer_lname, email_id, ");
				stringBuilder.append(" phone_number, age, address1, address2, city, state, zip_code, license_number from customer_details cust, login_details login");
				stringBuilder.append(" where cust.customer_id = login.customer_id and login.username = ? ");
				preparedStatement = (PreparedStatement) connection.prepareStatement(stringBuilder.toString());
				preparedStatement.setString(1, username);
				resultSet = preparedStatement.executeQuery();
			}
		}catch(Exception e){
			System.out.println("An error occured while displaying the user details, after loggin in."+e.getMessage());
		}
		return resultSet;
	}
	
	//method to get the number of hours
	public Double getNumberOfHours(String pickUpDate, String pickUpTime, String dropOffDate, String dropOffTime){
		Double numberOfHours = 0.0;
		try{
			connection = connector.getConn();
			if(connection!= null){
				stringBuilder = new StringBuilder();
				stringBuilder.append(" select timestampdiff(hour, ?, ?) ");
				preparedStatement = (PreparedStatement) connection.prepareStatement(stringBuilder.toString());
				preparedStatement.setString(1, pickUpDate+" "+pickUpTime+":00");
				preparedStatement.setString(2, dropOffDate+" "+dropOffTime+":00");
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next())
				numberOfHours = (double) resultSet.getInt(1);
			}
		}catch(Exception e){
			System.out.println("An error occured while trying to get the number of hours."+e.getMessage());
		}
		
		return numberOfHours;
	}
	
	//method to update the login table with maximum attempts
	public int updateLoginWithMaxAttemps(String username){
		try{
			returnCode = -1;
			connection = connector.getConn();
			if(connection!= null){
				stringBuilder = new StringBuilder();
				stringBuilder.append(" update login_details ");
				stringBuilder.append(" set active_flag = 'N' ");
				stringBuilder.append(" where username = ? ");
				preparedStatement = (PreparedStatement) connection.prepareStatement(stringBuilder.toString());
				preparedStatement.setString(1, username);

				returnCode = preparedStatement.executeUpdate();
			}
		}catch(Exception e){
			System.out.println("An error occured while trying to update the login table after having maximum attempts to login."+e.getMessage());
		}
		return returnCode;
	}
	
	//update the login table to activate the account
	public int updateLoginTableToActivate(String username){
		try{
			returnCode = -1;
			connection = connector.getConn();
			if(connection!= null){
				stringBuilder = new StringBuilder();
				stringBuilder.append(" update payup_LOGIN ");
				stringBuilder.append(" set active_yn = 'Y', updated_by = 'admin', updated_at = (SELECT CURRENT_TIMESTAMP)");
				stringBuilder.append(" where username = ?");
				preparedStatement = (PreparedStatement) connection.prepareStatement(stringBuilder.toString());
				preparedStatement.setString(1, username);
				returnCode = preparedStatement.executeUpdate(stringBuilder.toString());
			}
		}catch(Exception e){
			System.out.println("An error occured while trying to activate the user's account."+e.getMessage());
		}
		return returnCode;
	}
	
	//method to save the customer details
	public int saveCustomerDetails(Customer user){
		try{
			returnCode = -1;
			connection = connector.getConn();
			resultSet = null;
			if(connection != null){
				stringBuilder = new StringBuilder();
				stringBuilder.append("select max(customer_id) from customer_details");
				preparedStatement = (PreparedStatement)connection.prepareStatement(stringBuilder.toString());
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next())
					maxCustomerId = resultSet.getInt(1);
				
				stringBuilder = new StringBuilder();
				stringBuilder.append("insert into customer_details (customer_id,customer_fname,customer_lname,"
						+ "email_id,phone_number,age,Address1,Address2,"
						+ "city,State,zip_code,license_number,gender)"
						+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?) ");
				preparedStatement = (PreparedStatement)connection.prepareStatement(stringBuilder.toString());
				preparedStatement.setInt(1, maxCustomerId+1);
				preparedStatement.setString(2, user.getFirstName());
				preparedStatement.setString(3, user.getLastName());
				preparedStatement.setString(4, user.getEmail());
				preparedStatement.setString(5, user.getPhoneNumber());
				preparedStatement.setLong(6, user.getAge());
				preparedStatement.setString(7, user.getAddressLine1());
				preparedStatement.setString(8, user.getAddressLine2());
				preparedStatement.setString(9, user.getCity());
				preparedStatement.setString(10, user.getState());
				preparedStatement.setLong(11, user.getZipCode());
				preparedStatement.setLong(12, user.getLicenceNumber());
				preparedStatement.setString(13, user.getGender());

				preparedStatement.execute();
				stringBuilder = new StringBuilder();

			    stringBuilder.append("select max(login_id) from login_details");
				preparedStatement = (PreparedStatement)connection.prepareStatement(stringBuilder.toString());
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next())
					maxLoginId = resultSet.getInt(1);
				stringBuilder = new StringBuilder();
				
				stringBuilder.append("insert into login_details (login_id, customer_id, username, password, type, active_flag) values (?,?,?,?,?,?)");
				preparedStatement = (PreparedStatement)connection.prepareStatement(stringBuilder.toString());
				preparedStatement.setInt(1, maxLoginId+1);
				preparedStatement.setInt(2, maxCustomerId+1);
				preparedStatement.setString(3, user.getUsername());
				preparedStatement.setString(4, user.getPassword());
				preparedStatement.setString(5, "customer");
				preparedStatement.setString(6, "Y");
				preparedStatement.execute();
			}
		}catch(Exception e){
			System.out.println("An error occured while trying to save the customer details."+e.getMessage());
		}
		
		return returnCode;
		
	}
	
	//method to save the billing details
	public int saveBillingDetails(Payment payment, Billing billing, BookingDetails bookingDetails){
		try{
			connection = connector.getConn();
			resultSet = null;
			if(connection != null){
				
				//to insert in card_Details
				stringBuilder = new StringBuilder();
				stringBuilder.append("select max(card_id) from card_details");
				preparedStatement = (PreparedStatement)connection.prepareStatement(stringBuilder.toString());
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next())
					maxCardId = resultSet.getInt(1);
				
				stringBuilder = new StringBuilder();
				stringBuilder.append("insert into card_details (card_id, card_number, card_date, card_cvv, card_type, zipcode) "
						+ " values (?,?,?,?,?,?) ");
				preparedStatement = (PreparedStatement)connection.prepareStatement(stringBuilder.toString());
				preparedStatement.setInt(1, maxCardId+1);
				preparedStatement.setString(2, payment.getCardNumber());
				preparedStatement.setString(3, payment.getCardDate());
				preparedStatement.setString(4, payment.getCardCVV());
				preparedStatement.setString(5, "Master Card");
				preparedStatement.setString(6, payment.getZipcode());
				returnCode1 = preparedStatement.executeUpdate();
				
				
				//to insert in car_booking_details
				stringBuilder = new StringBuilder();
				stringBuilder.append("select max(car_booking_id) from car_booking_details");
				preparedStatement = (PreparedStatement)connection.prepareStatement(stringBuilder.toString());
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next())
					maxBookingId = resultSet.getInt(1);
				
				stringBuilder = new StringBuilder();
				stringBuilder.append("insert into car_booking_details (car_booking_id, car_availability_id, pickup_time, "
						+ "dropoff_time, dropoff_date, pickup_date, received) "
						+ " values (?,?,?,?,?,?,?) ");
				preparedStatement = (PreparedStatement)connection.prepareStatement(stringBuilder.toString());
				preparedStatement.setInt(1, maxBookingId+1);
				preparedStatement.setInt(2, bookingDetails.getAvailableId());
				preparedStatement.setString(3, bookingDetails.getPickupTime());
				preparedStatement.setString(4, bookingDetails.getDropoffTime());
				preparedStatement.setString(5, bookingDetails.getDropoffDate());
				preparedStatement.setString(6, bookingDetails.getPickupDate());
				preparedStatement.setString(7, "N");
				returnCode2 = preparedStatement.executeUpdate();
				
				
				//to insert in booking_details
				stringBuilder = new StringBuilder();
				stringBuilder.append("select max(billing_id) from billing_details");
				preparedStatement = (PreparedStatement)connection.prepareStatement(stringBuilder.toString());
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next())
					maxBillingId = resultSet.getInt(1);
				
				stringBuilder = new StringBuilder();
				stringBuilder.append("insert into billing_details (billing_id, customer_id, card_id, car_booking_id, total_amount) "
						+ " values (?,?,?,?,?) ");
				preparedStatement = (PreparedStatement)connection.prepareStatement(stringBuilder.toString());
				preparedStatement.setInt(1, maxBillingId+1);
				preparedStatement.setInt(2, billing.getCustomerId());
				preparedStatement.setInt(3, maxCardId+1);
				preparedStatement.setInt(4, maxBookingId+1);
				preparedStatement.setInt(5, billing.getTotalAmount());
				returnCode3 = preparedStatement.executeUpdate();
				
				if(returnCode1==1 && returnCode2==1 && returnCode3==1)
					returnCode4=1;
			}
		}catch(Exception e){
			System.out.println("An error occured while trying to save the billing details."+e.getMessage());
		}
		
		return returnCode4;
	}
	
	//method to select the customer id
	public ResultSet selectCustomerId(String emailId){
		try{
			connection = connector.getConn();
			if(connection!= null){
				stringBuilder = new StringBuilder();
				stringBuilder.append(" select cust.customer_id from customer_details cust, login_details login");
				stringBuilder.append(" where cust.email_id = ? and cust.customer_id = login.customer_id and login.active_flag = 'Y'");
				preparedStatement = (PreparedStatement) connection.prepareStatement(stringBuilder.toString());
				preparedStatement.setString(1, emailId);
				resultSet = preparedStatement.executeQuery();
			}
		}catch(Exception e){
			System.out.println("An error occured while trying to select the customer Id"+e.getMessage());
		}
		
		return resultSet;
	}
	
	//method to get the encrypted password
	public static String getEncryptedPassword(String input) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] message = messageDigest.digest(input.getBytes());
			BigInteger number = new BigInteger(1, message);
			String hashtext = number.toString(16);
			// Now we need to zero pad it if you actually want the full 32 chars.
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	//method to activate the account
	public int activateAccount(String emailId){
		returnCode = 0;
		try{
			connection = connector.getConn();
			if(connection!= null){
				stringBuilder = new StringBuilder();
				stringBuilder.append("update login_details login, customer_details cust set login.active_flag = 'Y' "
						+ "where login.customer_id = cust.customer_id and cust.email_id = ?");
				preparedStatement = (PreparedStatement) connection.prepareStatement(stringBuilder.toString());
				preparedStatement.setString(1, emailId);
				returnCode = preparedStatement.executeUpdate();
			}
		}catch(Exception e){
			System.out.println("An error occured while trying to activate the account."+e.getMessage());
		}
		return returnCode;
	}
	
	//method to deactivate the account
	public int deactivateAccount(String emailId){
		returnCode = 0;
		try{
			connection = connector.getConn();
			if(connection!= null){
				stringBuilder = new StringBuilder();
				stringBuilder.append("update login_details login, customer_details cust set login.active_flag = 'N' "
						+ "where login.customer_id = cust.customer_id and cust.email_id = ?");
				preparedStatement = (PreparedStatement) connection.prepareStatement(stringBuilder.toString());
				preparedStatement.setString(1, emailId);
				returnCode = preparedStatement.executeUpdate();
			}
		}catch(Exception e){
			System.out.println("An error occured while trying to deactivate the account"+e.getMessage());
		}
		return returnCode;
	}
	
	//method to update the login table with the otp
	public int updateLoginWithOTP(Integer otp, String username) {
		returnCode = 0;
		try{
			connection = connector.getConn();
			if(connection!= null){
				stringBuilder = new StringBuilder();
				stringBuilder.append("UPDATE login_details ");
				stringBuilder.append(" SET OTP = ? ");
				stringBuilder.append(" where username = ? ");
				preparedStatement = (PreparedStatement) connection.prepareStatement(stringBuilder.toString());
				preparedStatement.setDouble(1, otp);
				preparedStatement.setString(2, username);
				returnCode = preparedStatement.executeUpdate();
			}
		}catch(Exception e){
			System.out.println("An error occured while trying to update the llogin table with the latest OTP."+e.getMessage());
		}
		return returnCode;		
	}
	
	//method to fetch the otp
	public ResultSet fecthOTP(String username) {
		try{
			connection = connector.getConn();
			if(connection!= null){
				stringBuilder = new StringBuilder();
				stringBuilder.append("select OTP ");
				stringBuilder.append(" from login_details ");
				stringBuilder.append(" where username = ? ");
				preparedStatement = (PreparedStatement) connection.prepareStatement(stringBuilder.toString());
				preparedStatement.setString(1, username);
				resultSet = preparedStatement.executeQuery();
			}
		}catch(Exception e){
			System.out.println("An error occured while trying to fetch the OTP."+e.getMessage());
		}
		return resultSet;		
	}

	//method to get the profile details
	public ResultSet getProfileDetails(String username){
		try{
			connection = connector.getConn();
			if(connection!= null){
				stringBuilder = new StringBuilder();
				stringBuilder.append(" select cust.customer_fname, cust.customer_lname, cust.email_id, cust.gender, "
						+ "cust.phone_number, cust.age, cust.address1, cust.address2, "
						+ "cust.city, cust.state, cust.zip_code, cust.license_number from customer_details cust, login_details login");
				stringBuilder.append(" where username = ? and cust.customer_id = login.customer_id");
				preparedStatement = (PreparedStatement) connection.prepareStatement(stringBuilder.toString());
				preparedStatement.setString(1, username);
				resultSet = preparedStatement.executeQuery();
			}
		}catch(Exception e){
			System.out.println("An error occured while trying to fetch the profile details."+e.getMessage());
		}
		return resultSet;
	}

	//method to search the car availability
	public ResultSet searchCarAvailaibility(String pickupDate, String dropoffDate, String pickupTime){
		try{
			connection = connector.getConn();
			if(connection!= null){
				stringBuilder = new StringBuilder();
				stringBuilder.append(" select cd.car_name, cd.car_type, date_format(cad.available_date, '%d-%M-%Y %W'), "
						+ "date_format(cad.available_time,'%H:%i'), cd.car_hour, cad.car_availability_id "
						+ "from car_details cd, car_availability_details cad ");
				stringBuilder.append("where active_flag = 'Y' and cd.car_id = cad.car_id ");
				stringBuilder.append("and cad.available_date between ? and ? ");
				stringBuilder.append("and timestampdiff(hour,concat(date_format(cad.available_date, '%Y-%c-%d'),' ',date_format(cad.available_time,'%H:%i'),':00') ");
				stringBuilder.append(",?) >= 0");
				preparedStatement = (PreparedStatement) connection.prepareStatement(stringBuilder.toString());
				preparedStatement.setString(1, pickupDate);
				preparedStatement.setString(2, dropoffDate);
				preparedStatement.setString(3, pickupDate+" "+pickupTime+":00");
				resultSet = preparedStatement.executeQuery();
			}
		}catch(Exception e){
			System.out.println("An error occured while trying to search the car availability."+e.getMessage());
		}
		return resultSet;
	}
	
	//method to search the current bookings
	public ResultSet searchCurrentBookings(){
		try{
			connection = connector.getConn();
			if(connection!= null){
				stringBuilder = new StringBuilder();
				stringBuilder.append(" select cbd.car_booking_id, cbd.car_availability_id, cbd.dropoff_date, "
						+ "cbd.dropoff_time, cbd.pickup_Date, cbd.pickup_time, car.car_id "
						+ "from car_booking_details cbd, car_availability_details cad, car_Details car ");
				stringBuilder.append("where cad.active_flag = 'Y' and cbd.received = 'N' and cbd.car_availability_id = cad.car_availability_id ");
				stringBuilder.append("and car.car_id = cad.car_id");
				preparedStatement = (PreparedStatement) connection.prepareStatement(stringBuilder.toString());
				resultSet = preparedStatement.executeQuery();
			}
		}catch(Exception e){
			System.out.println("An error occured while trying to search the current bookings."+e.getMessage());
		}
		return resultSet;
	}
	
	//method to release the car bookings
	public int releaseCarBooking(AvailableBookings availableBookings){
		try{
			returnCode = -1;
			connection = connector.getConn();
			if(connection!= null){
				stringBuilder = new StringBuilder();
				stringBuilder.append(" update car_booking_details cbd, car_availability_details cad "
						+ " set cbd.received = 'Y', cad.active_flag = 'N' where cbd.car_availability_id = cad.car_availability_id "
						+ " and cbd.car_booking_id = ? and cad.car_availability_id = ?");
				preparedStatement = (PreparedStatement) connection.prepareStatement(stringBuilder.toString());
				preparedStatement.setInt(1, availableBookings.getCarBookingIdProperty());
				preparedStatement.setInt(2, availableBookings.getCarAvailabilityIdProperty());
				returnCode = preparedStatement.executeUpdate();
			}
		}catch(Exception e){
			System.out.println("An error occured while trying to release the car booking"+e.getMessage());
		}
		return returnCode;
	}
	
	//method to insert a new car availability
	public int insertNewCarAvailability(AvailableBookings availableBookings){
		try{
			int id = availableBookings.getCarIdProperty();
			returnCode = -1;
			connection = connector.getConn();
			if(connection!= null){
				//to insert a new car availability in card_availability_details
				stringBuilder = new StringBuilder();
				stringBuilder.append("select max(car_availability_id) from car_availability_details");
				preparedStatement = (PreparedStatement)connection.prepareStatement(stringBuilder.toString());
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next())
					maxAvailableId = resultSet.getInt(1);
				
				stringBuilder = new StringBuilder();
				stringBuilder.append("insert into car_availability_details (car_availability_id, car_id, available_date, "
						+ "available_time, active_flag) "
						+ " values (?,?,?,?,?) ");
				preparedStatement = (PreparedStatement)connection.prepareStatement(stringBuilder.toString());
				preparedStatement.setInt(1, maxAvailableId+1);
				preparedStatement.setInt(2, id);
				preparedStatement.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
				preparedStatement.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));
				preparedStatement.setString(5, "Y");
				returnCode1 = preparedStatement.executeUpdate();
			}
		}catch(Exception e){
//			System.out.println("An error occured while trying to release the car booking"+e.getMessage());
			e.printStackTrace();
		}
		return returnCode1;
	}

	//method to update the customer details
	public int updateCustomerDetails(String user, Customer customer) {
		returnCode = 0;
		try{
			connection = connector.getConn();
			if(connection!= null){
				stringBuilder = new StringBuilder();
				stringBuilder.append("update customer_details cust, login_details login ");
				stringBuilder.append(" set cust.customer_lname = ?, cust.Email_id = ?, cust.Phone_Number = ?, cust.Age = ?, ");
				stringBuilder.append(" cust.address1 = ?, cust.address2 = ?, cust.city = ?, cust.state = ?, cust.zip_code = ?, ");
				stringBuilder.append(" cust.license_number = ?, cust.gender = ? ");
				stringBuilder.append(" where cust.customer_id = login.customer_id and login.username = ?");
				preparedStatement = (PreparedStatement) connection.prepareStatement(stringBuilder.toString());
				preparedStatement.setString(1, customer.getLastName());
				preparedStatement.setString(2, customer.getEmail());
				preparedStatement.setString(3, customer.getPhoneNumber());
				preparedStatement.setLong(4, customer.getAge());
				preparedStatement.setString(5, customer.getAddressLine1());
				preparedStatement.setString(6, customer.getAddressLine2());
				preparedStatement.setString(7, customer.getCity());
				preparedStatement.setString(8, customer.getState());
				preparedStatement.setLong(9, customer.getZipCode());
				preparedStatement.setLong(10, customer.getLicenceNumber());
				preparedStatement.setString(11, customer.getGender());
				preparedStatement.setString(12, user);
				returnCode = preparedStatement.executeUpdate();
			}
		}catch(Exception e){
			System.out.println("An error occured while trying to update the customer details."+e.getMessage());
		}
		return returnCode;		
	}
	
	//method to select all the users
	public ResultSet selectAllUsers(String emailId){
		try{
			connection = connector.getConn();
			if(connection!= null){
				stringBuilder = new StringBuilder();
				stringBuilder.append("select email_id from customer_details where email_id = ?");
				preparedStatement = (PreparedStatement) connection.prepareStatement(stringBuilder.toString());
				preparedStatement.setString(1, emailId);
				resultSet = preparedStatement.executeQuery();
			}
		}catch(Exception e){
			System.out.println("An error occured while trying to get the email id of all the users."+e.getMessage());
		}
		
		return resultSet;
	}

	//method to close the connection
	public void closeConnection(){
		try{
			connection.close();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}