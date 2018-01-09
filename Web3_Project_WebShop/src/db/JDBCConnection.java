package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class JDBCConnection {

	private Properties properties;
	private String url;

	public JDBCConnection(Properties properties) {
		try {
			Class.forName("org.postgresql.Driver");
			this.properties = properties;
			this.url = properties.getProperty("url");
		} catch (Exception e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	public Connection getConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			return DriverManager.getConnection(url, properties);
		} catch (SQLException | ClassNotFoundException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

}
