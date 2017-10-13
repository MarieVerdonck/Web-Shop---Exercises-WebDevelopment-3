package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Person;

public class PersonDbJDBC implements PersonDb {
	
	@Override
	public Person get(String personId) {
		if(personId == null){
			throw new DbException("No id given");
		}
		try {
			Connection connection = JDBCConnection.getConnectionObject().getConnection();;
			String sql = "SELECT * FROM person WHERE userid='" + personId + "'";
	        Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery( sql );
			System.out.println(result);
			if (!result.next()){
				return null;
			}
			Person person = this.putRowInPerson(result);
			statement.close();
			connection.close();
			return person;
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	@Override
	public List<Person> getAll() {
		Connection connection = JDBCConnection.getConnectionObject().getConnection();
		List<Person> persons = new ArrayList<Person>();
        String sql = "SELECT * FROM person";
		try {
	        Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery( sql );
			while(result.next()) {
				persons.add(this.putRowInPerson(result));
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		} 
		return persons;
	}

	@Override
	public void add(Person person) {
		if(person == null){
			throw new DbException("No person given");
		}
		if (this.get(person.getUserid())!=null) {
			throw new DbException("User already exists");
		}
		String userid = person.getUserid();
        String password = person.getPassword();
        String email = person.getEmail();
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
		String sql = "INSERT INTO person (userid, email, password, \"firstName\", \"lastName\") "
	            + "VALUES ('" + userid + "','" + email + "','" + password +"','" + firstName + "','" + lastName + "');";
		Connection connection = JDBCConnection.getConnectionObject().getConnection();
		Statement statement;
		try {
			statement = connection.createStatement();
			statement.executeUpdate( sql );
			statement.close();
			connection.close();
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	@Override
	public void update(Person person) {
		String userid = person.getUserid();
        String password = person.getPassword();
        String email = person.getEmail();
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        String sql = "UPDATE person SET password = '" + password + "', \"email\"='" + email + "', \"firstName\"='" + firstName +", \"lastName\"='" + lastName + "' WHERE \"userid\" = '" + userid + "';";
        Connection connection = JDBCConnection.getConnectionObject().getConnection();
        Statement statement;
		try {
			statement = connection.createStatement();
			statement.executeUpdate( sql );
			statement.close();
			connection.close();
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(String personId) {
		if(personId == null){
			throw new DbException("No id given");
		}
		if (this.get(personId)!=null) {
			throw new DbException("No user exists with this Id.");
		}
		String sql = "DELETE FROM person WHERE userid='" + personId + "'";
		Connection connection = JDBCConnection.getConnectionObject().getConnection();
		Statement statement;
		try {
			statement = connection.createStatement();
			statement.executeUpdate( sql );
			statement.close();
			connection.close();
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}
	
	private Person putRowInPerson(ResultSet result) {
		String userid;
		try {
			userid = result.getString("userid");
			String email = result.getString("email");
			String password = result.getString("password");
			String firstName = result.getString("firstName");
			String lastName = result.getString("lastName");
			
			Person person = new Person(userid, email, password, firstName, lastName);
			return person;
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

}
