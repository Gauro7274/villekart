package com.gaurav.myapps.villekart.Exception;

public class InvalidPinCodeException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidPinCodeException(String message) {
        super(message);
    }
    
    public InvalidPinCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
