package com.example.utils;

//import org.springframework.stereotype.Component;



public class UserNotLoggedException extends Exception {	
	private static final long serialVersionUID = 1L;
	
	public UserNotLoggedException(String errorMessage) {
		super(errorMessage);
	}
	
	

}
