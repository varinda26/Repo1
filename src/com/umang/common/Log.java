package com.umang.common;



import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;

public class Log{

	private static synchronized void writeLog(String strSubDir, String logString) {

		String strLogFilePath = "F:/UNSUB_LOGS/";
		String strFileName = "";
		String strDateDir = "";

		Calendar objCalendarRightNow = Calendar.getInstance();

		int intMonth = objCalendarRightNow.get(Calendar.MONTH) + 1;
		int intDate = objCalendarRightNow.get(Calendar.DATE);
		int intHour = objCalendarRightNow.get(Calendar.HOUR_OF_DAY);
		int intMinute = objCalendarRightNow.get(Calendar.MINUTE);
		int intSecond = objCalendarRightNow.get(Calendar.SECOND);
		int intMilliSecond = objCalendarRightNow.get(Calendar.MILLISECOND);

		String strYear = "" + objCalendarRightNow.get(Calendar.YEAR);

		strDateDir = strLogFilePath + "/" + intDate + "-" + intMonth + "-" + strYear;
		createDateDir(strDateDir);
		strFileName = strDateDir + "/" + strSubDir + "_" + intHour + ".log";

		try {

			FileWriter out = new FileWriter(strFileName, true);

			String strLogString = intDate + "-" + intMonth + "-" + strYear + " " + intHour + ":" + intMinute + ":"
					+ intSecond + ":" + intMilliSecond + Constants.LOG_SEPARATOR + logString + "\n";

			out.write(strLogString);
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(0);
		}
	}

	private synchronized static void createDateDir(String dateDir) {
		try {
			String folderName = dateDir;
			new File(folderName).mkdirs();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	 
	 public static synchronized void getDatabaseLog(String databaseErr){
		 writeLog("DBErrorLog", databaseErr);
	 }
	 
	 public static synchronized void getErrorLogs(String logString) {
			writeLog("ErrorLog", logString);
	 }
	 
	 public static synchronized void getInputOutputDetailsLog(String inputOutputParams){
		 writeLog("InputOutputParametersLog", inputOutputParams);
	 }
}
