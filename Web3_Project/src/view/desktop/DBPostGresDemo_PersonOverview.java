package view.desktop;

import java.sql.*;
import java.util.Properties;

import db.DbException;
import domain.Person;

public class DBPostGresDemo_PersonOverview {

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
			Connection connection = DriverManager.getConnection(url, properties);
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery( "SELECT * FROM person" );
			
			while(result.next()) {
				String userid = result.getString("userid");
				String email = result.getString("email");
				String password = result.getString("password");
				String firstName = result.getString("firstName");
				String lastName = result.getString("lastName");
				
				Person person = new Person(userid, email, password, firstName, lastName);
				System.out.println(person);
			}
			
			statement.close();
			connection.close();
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}
	
}
