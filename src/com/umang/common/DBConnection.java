package com.umang.common;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.log4j.Logger;

import com.umang.common.LogStackTrace;

public class DBConnection {

	private static Connection connection;
	private final static Logger logger = Logger.getLogger(DBConnection.class
			.getName());
	
	
	private DBConnection(){}
	public static Connection getConnection() {

		 
		synchronized (DBConnection.class) {
			 try {
					if (connection == null || connection.isClosed()) {

						Class.forName("com.mysql.jdbc.Driver");
						String[] connectionDetails = getConnectionParameters();

						if (connectionDetails != null && connectionDetails.length == 3) {
							connection = DriverManager.getConnection(
									connectionDetails[0], connectionDetails[1],
									connectionDetails[2]);
						} else {
							logger.fatal("Invalid Connection Parameters");
							throw new Exception();
						}
					}
				} catch (Exception e) {
					logger.error("DBConnection Error : "
							+ LogStackTrace.getStackTrace(e));
					
					e.printStackTrace();
					System.exit(0);
				}
 
		 }
				return connection;
	}

	public static String[] getConnectionParameters() {
		String[] connectionDetails = null;
		String databaseIp = Utilities.getConfKeyValue("database_ip");
		String databasePort = Utilities.getConfKeyValue("database_port");
		String databaseName = Utilities.getConfKeyValue("database_name");
		String username = Utilities.getConfKeyValue("database_username");
		String password = Utilities.getConfKeyValue("database_password");
		if (databaseIp != null && databasePort != null && databaseName != null
				&& username != null && password != null) {
			connectionDetails = new String[3];
			connectionDetails[0] = "jdbc:mysql://" + databaseIp + ":"
					+ databasePort + "/" + databaseName;
			connectionDetails[1] = username;
			connectionDetails[2] = password;
		}
		return connectionDetails;
	}

	public static void close(Connection connection) {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close(CallableStatement callableStatement) {
		try {
			if (callableStatement != null && !callableStatement.isClosed()) {
				callableStatement.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}