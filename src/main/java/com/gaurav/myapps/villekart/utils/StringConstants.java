package com.gaurav.myapps.villekart.utils;

public enum StringConstants {
	NEW_USER_SUCCESS("User registered successfully"),
	INVALID_USERNAME("Invalid Username"),
	PASSWORD_NOT_MATCH("Invalid Username"),
	NEW_USER_FAILED("Failed to register user please contact support"),
	USERNAME_ALREADY_PRESENT("Uername is not available please try with different username"), 
	ROLE_NOT_NULL("Please Define the Role"),
	PASSWORD_NOT_NULL("Please Insert the Valid Password"),
	ADDRESS_SAVE_SUCCESS("address added"),
	ADDRESS_SAVE_FAILED("unable to add address please try again later"),
	USERNAME_NOT_NULL("Please Insert the Valid Username"),
	ADDRESS_DELETE_SUCCESS("Address Deleted"),
	ADDRESS_DELETE_FAILED("failed to delete address"), 
	SUCCESS("Success"),
	INVALID_PINCODE("Invalid Pincode");
	
	private final String constantStr;
	
	private StringConstants(String constantStr) {
		this.constantStr=constantStr;
	}
	
	public String getValue() {
		return this.constantStr;
	}
}
