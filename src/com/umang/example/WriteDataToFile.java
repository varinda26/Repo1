package com.umang.example;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import com.umang.common.DBConnection;



public class WriteDataToFile {
	private final static Logger logger = Logger.getLogger(DBConnection.class
			.getName());
	
	
	public static void main(String[] args) {
		//String sessionInfo = System.currentTimeMillis()+"";
		ArrayList<String> deptList = getUnsubList("SELECT_DEPTT","0");
		System.out.println("list:"+deptList);
		
       ExecutorService executor = Executors.newFixedThreadPool(deptList.size());
       
       for (int i = 0; i < deptList.size(); i++) {  
    	   if(deptList.get(i)!=null){
    		   Runnable worker = new WorkerThread(deptList.get(i)); 
               executor.execute(worker);
    	   }
    	    
         }  
       executor.shutdown();  
       while (!executor.isTerminated()) {   }  
       System.out.println("Finished all threads");  
	}

	
	public static synchronized ArrayList<String> getUnsubList(String type, String dept){
		Connection dbConnection = null;
		CallableStatement callableStatement = null;
		ArrayList<String> deptList = new ArrayList<>();
		System.out.println("type"+type+"dept"+dept);
		String updatePassDetailsSql = "{call proc_get_unsub(?,?,?,?,?)}";
		try {
			dbConnection = DBConnection.getConnection();
			callableStatement = dbConnection.prepareCall(updatePassDetailsSql);

			callableStatement.setString(1, type);
			callableStatement.setString(2, dept);
			callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(4, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(5, java.sql.Types.VARCHAR);
			
			System.out.println("proc call"+callableStatement.toString());
			callableStatement.execute();
			String resStatus=callableStatement.getString(3);
			String resCode=callableStatement.getString(4);
			String resDesc=callableStatement.getString(5);
			System.out.println("response:"+callableStatement.getString(3));
			if(type!=null && ! type.equalsIgnoreCase("UPDATE_DEPTT")){
			ResultSet rs = callableStatement.getResultSet();
			
			while(rs.next()){
				deptList.add(rs.getString(1).trim());
			}
			
			}
			if(type!=null &&  type.equalsIgnoreCase("UPDATE_DEPTT")){
		
				//deptList.add(rs.getString(1).trim());
				deptList.add(resStatus);
				deptList.add(resCode);
				deptList.add(resDesc);
			//}
			
			}
			
			
			/*Log.getInputOutputDetailsLog( sessionInfo + LOG_SEPARATOR + "GET_UNSUB_DETAILS_RESPONSE_DETAIL" 
					+ LOG_SEPARATOR + sessionInfo + LOG_SEPARATOR+"outStatus:"
					+ outStatus+LOG_SEPARATOR+"responseCode:"+responseCode+LOG_SEPARATOR+"desc:"+desc);
*/
		
		} catch (Exception e) {
			e.printStackTrace();
			/*Log.getErrorLogs( sessionInfo + LOG_SEPARATOR 
					+"ERROR_WHILE_SELECTING_UNSUB_DEPT" 
					+ LOG_SEPARATOR + LogStackTrace.getStackTrace(e));
*/

		} 
		finally {
			try{
				if(dbConnection != null){
					DBConnection.close(callableStatement);
					DBConnection.close(dbConnection);
				}
			}
			catch(Exception e){
				e.printStackTrace();
				/*Log.getErrorLogs(sessionInfo + LOG_SEPARATOR 
						+"ERROR_WHILE_CLOSING_CONNECTION" 
				+ LOG_SEPARATOR + LogStackTrace.getStackTrace(e));
*/
			}
			
		}
		
		return deptList;

	}
	
	public static synchronized void writingDataToFile(ArrayList<String> arrayList, String strFileName) {
		String strFilePath = "F:/Unsub/Dept";
		String fileName="";
		System.out.println("data:"+arrayList);
		createDir(strFilePath);
		fileName = strFilePath +"/" +strFileName+".txt";
		System.out.println("file name:"+fileName);
		BufferedWriter bufferedWriter = null;
		try {

			bufferedWriter = new BufferedWriter(new FileWriter(fileName,true));
			
			Iterator<String> iterator = arrayList.iterator();
			while (iterator.hasNext()) {
				String line = (String) iterator.next();
				bufferedWriter.write(line);
				System.out.println("data written to file:"+line);
				bufferedWriter.newLine();
			}
			

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			
			try{
				if(bufferedWriter != null){
					bufferedWriter.close();

				}
			}
			catch(Exception e){e.printStackTrace();}
			
		}

	}
	private synchronized static void createDir(String dir) {
		try {
			String folderName = dir;
			new File(folderName).mkdirs();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


}
