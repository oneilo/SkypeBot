package skypebot.util;

import java.sql.*;

/**
 * Connects to and uses a MySQL database
 * 
 * Original credits to:
 * 
 * @author -_Husky_-
 * @author tips48
 * 
 *         Modified by:
 * @author Kyle
 */
public class MySQL {
	private final String user;
	private final String database;
	private final String password;
	private final String port;
	private final String hostname;

	private Connection connection;

	public MySQL(String hostname, String port, String database, String username, String password) {
		this.hostname = hostname;
		this.port = port;
		this.database = database;
		this.user = username;
		this.password = password;
		this.connection = null;
	}

	public Connection openConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database
					+ "?autoReconnect=true&", this.user, this.password);
		} catch (SQLException e) {
			System.out.println("Could not connect to MySQL server! because: " + e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC Driver not found!");
		}
		return connection;
	}

	public boolean checkConnection() {
		return connection != null;
	}

	public Connection getConnection() {
		return connection;
	}

	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Error closing the MySQL Connection!");
				e.printStackTrace();
			}
		}
	}

	public ResultSet querySQL(String query) {
		Connection c;

		if (checkConnection()) {
			c = getConnection();
		} else {
			c = openConnection();
		}

        try {
            Statement s = c.createStatement();
            ResultSet ret = s.executeQuery(query);
            closeConnection();
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}

	public void updateSQL(String update) {
		Connection c;

		if (checkConnection()) {
			c = getConnection();
		} else {
			c = openConnection();
		}

		Statement s;

		try {
			s = c.createStatement();
			s.executeUpdate(update);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		closeConnection();
	}
}
