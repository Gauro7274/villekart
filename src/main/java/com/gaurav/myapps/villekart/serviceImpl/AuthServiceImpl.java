package com.gaurav.myapps.villekart.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gaurav.myapps.villekart.dto.UserRepo;
import com.gaurav.myapps.villekart.entity.User;
import com.gaurav.myapps.villekart.model.AuthRequest;
import com.gaurav.myapps.villekart.service.AuthService;
import com.gaurav.myapps.villekart.utils.JwtUtils;
import com.gaurav.myapps.villekart.utils.StringConstants;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private AuthenticationManager authenticationManager; // Authentication manager to authenticate login requests

	@Autowired
	private JwtUtils jwtUtil; // Utility class for JWT generation and validation

	@Autowired
	private UserDetailsService userDetailsService; // Service to load user details

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public String addNewUser(@Valid User userDetails) {
		userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
		userRepo.save(userDetails);
		return StringConstants.NEW_USER_SUCCESS.getValue();
	}

	@Override
	public String authenticateUser(AuthRequest authRequest) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

		// Load user details and generate JWT token
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);

		return jwt; // Return the JWT token
	}
	
	

	

	

}
