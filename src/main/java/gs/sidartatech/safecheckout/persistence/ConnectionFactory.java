package gs.sidartatech.safecheckout.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {
	private static Logger logger = Logger.getLogger(ConnectionFactory.class.getName());
	private static Connection con;
	private static String url;
	private static String port;
	private static String sid;
	private static String user;
	private static String pwd;

	private ConnectionFactory() {

	}

	public static synchronized Connection getConnection() {
		try {
			if (con == null) {
				logger.log(Level.INFO, "Get connection with Database...");
				readDataBaseProperties();
				Class.forName("org.postgresql.Driver");
				String connextionURL = "jdbc:postgresql://" + url + ":" + port + "/" + sid;
				con = DriverManager.getConnection(connextionURL, user, pwd);
				logger.log(Level.INFO, "Get connection with Database... (OK)");
			}
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, "Error getting database connection", e);
			System.exit(-1);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error getting database connection", e);
			System.exit(-1);
		}
		return con;
	}
	
	private static void readDataBaseProperties() {
		logger.log(Level.INFO, "Reading Database Parameters...");
		Properties p = new Properties();
		InputStream in = ConnectionFactory.class.getResourceAsStream("/database.properties");
		try {
			p.load(in);
			url = p.getProperty("url");
			port = p.getProperty("port");
			sid = p.getProperty("sid");
			user = p.getProperty("user");
			pwd = p.getProperty("pwd");
			in.close();
			logger.log(Level.INFO, "Reading Database Parameters...(OK)");
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error getting database parameters");
			logger.log(Level.SEVERE, e.getMessage(), e);
			System.exit(-1);
		}	
	}
}
