package view.desktop;

import java.sql.*;

import javax.swing.JOptionPane;

import db.DbException;
import db.JDBCConnection;

public class DBPostGresDemo_AddPerson {

	public static void main(String[] args) {
		try {
	        String userid = JOptionPane.showInputDialog("userid");
	        String password = JOptionPane.showInputDialog("password");
	        String email = JOptionPane.showInputDialog("email");
	        String firstName = JOptionPane.showInputDialog("first name");
	        String lastName = JOptionPane.showInputDialog("last name");
	        
	        Connection connection = JDBCConnection.getConnectionObject().getConnection();
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

