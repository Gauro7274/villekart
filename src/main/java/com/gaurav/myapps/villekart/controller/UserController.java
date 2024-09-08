package com.gaurav.myapps.villekart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gaurav.myapps.villekart.entity.User;
import com.gaurav.myapps.villekart.model.AuthRequest;
import com.gaurav.myapps.villekart.service.AuthService;
import com.gaurav.myapps.villekart.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/User")
@Validated
public class UserController {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserService userService;

	// Endpoint for logging in, generates a JWT token if authentication is
	@PostMapping("/login")
	public String login(@RequestBody AuthRequest authRequest){
		// Authenticate using username and password
		final String jwt = authService.authenticateUser(authRequest);
		return jwt; // Return the JWT token
	}

	// Endpoint for signing up new users
	@PostMapping("/signUp")
	public ResponseEntity<String> signup(@RequestBody User user) {
		String response = null;
		response = authService.addNewUser(user);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/test")
	public ResponseEntity<String> test() {
		return null;
	}
	@GetMapping("/validatePincode/{pincode}")
	public ResponseEntity<String> validatePincode(@PathVariable String pincode) {
		String pincodeDetails = userService.validatePincode(pincode);
		return new ResponseEntity<>(pincodeDetails, HttpStatus.OK);
	}

}