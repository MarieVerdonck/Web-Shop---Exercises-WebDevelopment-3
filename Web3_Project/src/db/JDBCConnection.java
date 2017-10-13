package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//Singleton
public final class JDBCConnection {
	
	private static volatile JDBCConnection instance = null;
	
	private static final Properties properties = new Properties();
	private static final String url = "jdbc:postgresql://gegevensbanken.khleuven.be:51617/2TX34"; 
	private static final String currentSchema = "r0298778";
	//TODO Make generic account and grant access
	private static final String user = "***";
	private static final String password = "***";
	private static final String ssl = "true";
	private static final String sslfactory = "org.postgresql.ssl.NonValidatingFactory";

	private JDBCConnection() {
		setProperties();
	}
	
	public static JDBCConnection getConnectionObject() {
		if (instance == null) {
			synchronized(JDBCConnection.class) {
				if (instance == null) {
					instance = new JDBCConnection();
				}
			}
		}
		return instance;
	}
	
	public Connection getConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			return DriverManager.getConnection(url, properties);
		} catch (SQLException | ClassNotFoundException  e) {
			throw new DbException(e.getMessage(),e);
		}
	}
	
	private void setProperties() {
		properties.setProperty("currentSchema", currentSchema);
		properties.setProperty("user", user);
		properties.setProperty("password", password);
		properties.setProperty("ssl", ssl);
		properties.setProperty("sslfactory", sslfactory);
	}

}
