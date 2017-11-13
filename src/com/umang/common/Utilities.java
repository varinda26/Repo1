package com.umang.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Properties;

import com.umang.common.OSValidator;


public class Utilities {

	private static Properties objProperties=null;

	public static String getConfKeyValue(String key) {
		FileInputStream fis = null;
		String filePath = "";
		try {
			if (objProperties == null) {
				objProperties = new Properties();
				
				if (OSValidator.isWindows()) {
					filePath = "F:/database/config.properties";
				} 
				else {
					filePath = "/home/tomcat/config.properties";
					
				}
				
				fis = new FileInputStream(filePath);
				objProperties.load(fis);
				
			}
		} 
		catch (Exception e) {
			System.out.println("Error while loading Property file:" + e.getMessage());
			
		} 
		finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return objProperties.getProperty(key);
	}

	public static void closeCallableStatement(CallableStatement callableStatement) {
		try {
			if (callableStatement != null && !callableStatement.isClosed()) {
				callableStatement.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void closeConnection(Connection connection) {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 public static int getIntegerValue(String strValue) {
	        if (strValue != null && !strValue.equals("")) {
	            return Integer.parseInt(strValue);
	        } else {
	            return 0;
	        }
	    }
	 
	}
