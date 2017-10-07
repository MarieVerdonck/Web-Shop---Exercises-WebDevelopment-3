package view.desktop;

import java.sql.*;
import java.util.Properties;

import javax.swing.JOptionPane;

import db.DbException;

public class DBPostGresDemo_AddPerson {

	public static void main(String[] args) {
		Properties properties = new Properties();
		String url = "jdbc:postgresql://gegevensbanken.khleuven.be:51617/2TX34"; 
		properties.setProperty("currentSchema", "r0298778");
		//TODO Make generic account and grant access
		properties.setProperty("user", "TODO");
		properties.setProperty("password", "TODO");
		properties.setProperty("ssl", "true");
		properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		
		try {
	        Class.forName("org.postgresql.Driver");
	    } catch (ClassNotFoundException e) {
	        throw new DbException(e.getMessage(),e);
	    }
		try {
	        String userid = JOptionPane.showInputDialog("userid");
	        String password = JOptionPane.showInputDialog("password");
	        String email = JOptionPane.showInputDialog("email");
	        String firstName = JOptionPane.showInputDialog("first name");
	        String lastName = JOptionPane.showInputDialog("last name");
	        
			Connection connection = DriverManager.getConnection(url, properties);
			Statement statement = connection.createStatement();
			
			String sql = "INSERT INTO person (userid, email, password, \"firstName\", \"lastName\") "
		            + "VALUES ('" + userid + "','" + email + "','" + password +"','" + firstName + "','" + lastName + "');";
			
			statement.executeUpdate(sql);
			
			statement.close();
			connection.close();
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}
	
}

