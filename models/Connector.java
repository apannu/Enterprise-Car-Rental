package models;

import java.sql.DriverManager;

import com.mysql.jdbc.Connection;
/**
 * Name: Anirudh Pannu CWID: A20376101 Date: 2nd May, 2017 
 * File Name: Connector.java Assignment: Final Project
 */
public class Connector {

	public static Connection conn =  null;

	//method to get the connection for a database operation
	public Connection getConn() {
		try
		{
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://www.papademas.net:3306/fp","dbfp","510");   //DB_URL,Username,Password
			
		}
		catch(Exception e)
		{
			System.out.println("An exception has occured while intializing a connection"+e.getMessage());
		}
		return conn;
	}
}
