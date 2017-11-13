package com.umang.example;

import java.util.ArrayList;

public class WorkerThread implements Runnable {
	  
    private String dept;
    
    public WorkerThread(String s){
        this.dept=s;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" dept= "+dept);
        processCommand();
        System.out.println(Thread.currentThread().getName()+" End.");
    }

    private void processCommand() {
        try {
            System.out.println("============updating data=========");
            ArrayList<String> deptData = WriteDataToFile.getUnsubList("DEPTT_DATA",dept);
			System.out.println("dept data for "+dept+" ===="+deptData);
          if(deptData !=null && deptData.size() > 0){
        	  WriteDataToFile.writingDataToFile(deptData,dept);
          }
          
          WriteDataToFile.getUnsubList("UPDATE_DEPTT",dept);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
}