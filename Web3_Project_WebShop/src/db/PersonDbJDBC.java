package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Person;

public class PersonDbJDBC implements PersonDb {

    @Override
    public Person get(String personId) {
        if (personId == null) {
            throw new DbException("No id given");
        }
        try {
            Connection connection = JDBCConnection.getConnectionObject().getConnection();;
            String sql = "SELECT * FROM person WHERE userid=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, personId);
            ResultSet result = statement.executeQuery();
            System.out.println(result);
            if (!result.next()) {
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
        List<Person> persons = new ArrayList<Person>();
        String sql = "SELECT * FROM person";
        try {
            Connection connection = JDBCConnection.getConnectionObject().getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
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
        if (person == null) {
            throw new DbException("No person given");
        }
        if (this.get(person.getUserid()) != null) {
            throw new DbException("User already exists");
        }
        String userid = person.getUserid();
        String password = person.getPassword();
        String email = person.getEmail();
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        String salt = person.getSalt();
        String sql = "INSERT INTO person (userid, email, password, \"firstName\", \"lastName\", salt) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = JDBCConnection.getConnectionObject().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userid);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, firstName);
            statement.setString(5, lastName);
            statement.setString(6, salt);
            statement.execute();
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
        String sql = "UPDATE person SET password = ?, \"email\"=?, \"firstName\"=?, \"lastName\"=? WHERE \"userid\" = ?;";
        try {
            Connection connection = JDBCConnection.getConnectionObject().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, password);
            statement.setString(2, email);
            statement.setString(3, firstName);
            statement.setString(4, lastName);
            statement.setString(5, userid);
            statement.execute();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(String personId) {
        if (personId == null) {
            throw new DbException("No id given");
        }
        if (this.get(personId) == null) {
            throw new DbException("No user exists with this Id.");
        }
        String sql = "DELETE FROM person WHERE userid=?";
        try {
            Connection connection = JDBCConnection.getConnectionObject().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, personId);
            statement.execute();
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
            String salt = result.getString("salt");

            Person person = new Person(userid, email, password, firstName, lastName, salt);
            System.out.println("personin persondbjdbc: " + person.getPassword());
            return person;
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e);
        }
    }

}
