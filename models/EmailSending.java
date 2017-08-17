package models;

import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import datamodels.BookingDetails;
/**
 * Name: Anirudh Pannu CWID: A20376101 Date: 2nd May, 2017 
 * File Name: EmailSending.java Assignment: Final Project
 */
public class EmailSending {
	
	//method to send an email for otp
	public void sendEmailUsingSSL(String emailAddress, String Name, Integer OTP){
		Properties smtpServerProperties = new Properties();
		
		smtpServerProperties.put("mail.smtp.host", "smtp.gmail.com");
		smtpServerProperties.put("mail.smtp.socketFactory.port", "465");
		smtpServerProperties.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		smtpServerProperties.put("mail.smtp.auth", "true");
		smtpServerProperties.put("mail.smtp.port", "465");
		
		Session session = Session.getDefaultInstance(smtpServerProperties,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("carrentalsystems1@gmail.com","SQL36oracle$$$");
					}
				});
		
		try {

			Message mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress("carrentalsystems1@gmail.com"));
			mimeMessage.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(emailAddress));
			mimeMessage.setSubject("Welcome Message");
			mimeMessage.setText("Dear " + Name +
					"\n\n Car Rental Team welcomes you. For security reasons, please keep your username and password secret."
					+ " \n\n You will be required to enter OTP as " + OTP +". "
							+ " \n\n For other queries please call our support desk at "
							+ " +1 (312)866-7564. ");

			Transport.send(mimeMessage);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//method to send an email for the confirmation of the booking
	public void sendConfirmationMail(BookingDetails bookingDetails){
		Properties smtpServerProperties = new Properties();
		
		smtpServerProperties.put("mail.smtp.host", "smtp.gmail.com");
		smtpServerProperties.put("mail.smtp.socketFactory.port", "465");
		smtpServerProperties.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		smtpServerProperties.put("mail.smtp.auth", "true");
		smtpServerProperties.put("mail.smtp.port", "465");
		
		Session session = Session.getDefaultInstance(smtpServerProperties,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("carrentalsystems1@gmail.com","SQL36oracle$$$");
					}
				});
		
		try 
		{
			Message mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress("carrentalsystems1@gmail.com"));
			mimeMessage.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(bookingDetails.getEmailId()));
			mimeMessage.setSubject("Booking Confirmation");
			mimeMessage.setText("Car Rental Team welcomes you. Your booking has been done. Here are your details:"
					+ " \n\n Car Name: "+bookingDetails.getCarName()
					+ " \n Car Type: "+bookingDetails.getCarType()
					+ " \n Dropoff Date: "+bookingDetails.getDropoffDate()
					+ " \n Dropoff Time: "+bookingDetails.getDropoffTime()
					+ " \n Pickup Date: "+bookingDetails.getPickupDate()
					+ " \n Pickup Time: "+bookingDetails.getPickupTime()
					+ " \n Number of Hours: "+bookingDetails.getNumberOfHours()
					+ " \n Total Amount: "+bookingDetails.getStringTotalAmount()
							+ " \n\n In case of any queries, please reach out to the help desk: "
							+ "312-866-7564");

			Transport.send(mimeMessage);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	//method to create the otp
	public Integer createOTP() {
		Random rand = new Random();
		String id = String.format("%04d", rand.nextInt(10000));
		return Integer.parseInt(id);
	}

	//method to validate an email id format
	public boolean validateEmailId(String email){
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		 
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		
		if(matcher.matches())
			return true;
		else
			return false;
	}

}
