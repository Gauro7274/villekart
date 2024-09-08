package com.gaurav.myapps.villekart.service;

import com.gaurav.myapps.villekart.entity.User;
import com.gaurav.myapps.villekart.model.AuthRequest;


public interface AuthService {

	public String addNewUser(User userDetails);
	
	
	public String authenticateUser(AuthRequest authRequest);

}
