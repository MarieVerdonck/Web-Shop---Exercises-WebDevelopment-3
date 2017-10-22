package view.desktop;

import java.sql.*;

import db.DbException;
import db.JDBCConnection;
import domain.Person;

public class DBPostGresDemo_PersonOverview {

	public static void main(String[] args) {
		try {
			Connection connection = JDBCConnection.getConnectionObject().getConnection();
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
