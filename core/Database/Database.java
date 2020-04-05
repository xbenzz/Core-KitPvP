package me.core.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
	
	private static Connection connection;
	private static PreparedStatement statement;
	private static String host;
	private static String database;
	private static String username;
	private static String password;
  
	public static void openConnection() {
	    host = "";
	    database = "";
	    username = "";
	    password = "";
	    try {
	    	if ((connection != null) && (!connection.isClosed())) {
	    		return;
	    	}
	    	Class.forName("com.mysql.jdbc.Driver");
	    	connection = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database + "?autoReconnect=true", username, password);
	    	System.out.println("[KITCORE] Database has been connected successfully.");
	    } catch (SQLException e) {
	    	e.printStackTrace(); 
	    } catch (ClassNotFoundException e) {
	    	e.printStackTrace();
	    }
	}
  
	public static void closeConnection() {
		try {
			if ((connection == null) && (connection.isClosed())) {
				return;
			}	
			connection.close();
			System.out.println("[KITCORE] Database has been disconnected successfully.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
  
  public static void createTables() {
	  try {
		  String sql = "CREATE TABLE IF NOT EXISTS playerData(ID INT(255) NOT NULL AUTO_INCREMENT PRIMARY KEY, UUID VARCHAR(64), Username VARCHAR(64), TogglePM BOOLEAN, Rank VARCHAR(64))";
		  String sql2 = "CREATE TABLE IF NOT EXISTS playerIP(ID INT(255) NOT NULL AUTO_INCREMENT PRIMARY KEY, UUID VARCHAR(64), Address VARCHAR(64), Date VARCHAR(64))";
		  String sql3 = "CREATE TABLE IF NOT EXISTS punishments (ID INT(255) NOT NULL AUTO_INCREMENT PRIMARY KEY, player VARCHAR(36), punisher VARCHAR(36), reason LONGTEXT, expire LONGTEXT, time LONGTEXT, type VARCHAR(16), category VARCHAR(16), date VARCHAR(36), ip VARCHAR(45), ipban INT(16), active INT(16), removed INT(16), removedby VARCHAR(36));";
		  String sql6 = "CREATE TABLE IF NOT EXISTS chat(ID INT(255) NOT NULL AUTO_INCREMENT PRIMARY KEY, server VARCHAR(100), player VARCHAR(100), message VARCHAR(400), date VARCHAR(36), time VARCHAR(50), type VARCHAR(50))";
		  String sql7 = "CREATE TABLE IF NOT EXISTS playerTime (ID INT(255) NOT NULL AUTO_INCREMENT PRIMARY KEY, UUID VARCHAR(36), time VARCHAR(36));";
		  String sql8 = "CREATE TABLE IF NOT EXISTS reports (ID INT(255) NOT NULL AUTO_INCREMENT PRIMARY KEY, player VARCHAR(36), reporter VARCHAR(36), reason LONGTEXT, date VARCHAR(36));";
		  String sql9 = "CREATE TABLE IF NOT EXISTS reportsLogs (ID INT(255) NOT NULL AUTO_INCREMENT PRIMARY KEY, player VARCHAR(36), reporter VARCHAR(36), reason LONGTEXT, date VARCHAR(36), log LONGTEXT);";
		  String sql10 = "CREATE TABLE IF NOT EXISTS kitData(ID INT(255) NOT NULL AUTO_INCREMENT PRIMARY KEY, UUID varchar(64), level INT(16), exp INT(16), kills INT(16), deaths INT(16), high INT(16), current INT(16), gaps INT(16), sword INT(16), arrows INT(16), coins INT(16))";
		  String sql11 = "CREATE TABLE IF NOT EXISTS notes (ID INT(255) NOT NULL AUTO_INCREMENT PRIMARY KEY, player VARCHAR(36), note LONGTEXT, date VARCHAR(36));";
		  String sql12 = "CREATE TABLE IF NOT EXISTS anticheat (ID INT(255) NOT NULL AUTO_INCREMENT PRIMARY KEY, player VARCHAR(36), reason LONGTEXT, date VARCHAR(36), active INT(16));";
		  statement = connection.prepareStatement(sql);
		  statement.executeUpdate();
		  statement = connection.prepareStatement(sql2);
		  statement.executeUpdate();
		  statement = connection.prepareStatement(sql3);
		  statement.executeUpdate();
		  statement = connection.prepareStatement(sql6);
		  statement.executeUpdate();
		  statement = connection.prepareStatement(sql7);
		  statement.executeUpdate();
		  statement = connection.prepareStatement(sql8);
		  statement.executeUpdate();
		  statement = connection.prepareStatement(sql9);
		  statement.executeUpdate();
		  statement = connection.prepareStatement(sql10);
		  statement.executeUpdate();
		  statement = connection.prepareStatement(sql11);
		  statement.executeUpdate();
		  statement = connection.prepareStatement(sql12);
		  statement.executeUpdate();
	  } catch (SQLException e) {
		  e.printStackTrace();
	  }
  }
  
  	public static Connection getConnection() {
  		return connection;
  	}
  
  	public static PreparedStatement getStatement() {
  		return statement;
  	}
  
}
