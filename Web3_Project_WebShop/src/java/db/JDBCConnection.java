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
	private static final String user = "r0298778";
	private static final String password = "*****";
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
    
    //!!SQL Commands!!
    /*
     * Auto increment id:
     * CREATE SEQUENCE user_id_seq;
     * ALTER TABLE user ALTER user_id SET DEFAULT NEXTVAL('user_id_seq');
     * 
     * Add column:
     * ALTER TABLE table_name
     * ADD COLUMN new_column_name data_type constraint;
     * Ex: alter table r0298778_test.person add column address character varying(500) not null;
     * Problem: not null constraint > previous entries null for that column > add first without constraint > update values > set not null > alter table person alter column address set not null;
     * 																		> 2nd solution; with default: alter table person add column address not null default 'foo' > update data > remove default > alter table person alter column address drop default;
     * Drop column: alter table "name" drop "column_name"
     * Drop table: drop "table_name" cascade|restrict (drop objects dependent|refuse to drop if dependents)
     * **/

}
