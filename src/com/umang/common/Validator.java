package com.umang.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	
	public static boolean validateString(String string) {
		if((string != null && (string.trim().length()) > 0)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public static boolean validateInt(String id){
		if(id != null && isIntValue(id)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public static boolean validateEmail(String email){
		if(email != null && isEmail(email)){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	public static boolean validateMsisdn(String msisdn){
		if(msisdn != null && checkNumeric(msisdn)){
			
			return (msisdn.matches("^[789]\\d{9}$")&& msisdn.length()==10);
		}
		else{
			return false;
		}
	}
	
	public static boolean isIntValue(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	
		
	public static boolean isEmail(String s1) {
		return s1.matches(
				"^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{1,5}$");
	
	}
	
	public static boolean checkNumeric(String msisdn) {
		boolean bool = false;
		try {
			String pattern = "^[0-9]+$";
			Pattern r = Pattern.compile(pattern);
			if (msisdn != null) {
				Matcher m = r.matcher(msisdn);
				if (m.find()) {
					bool = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}
	
}
