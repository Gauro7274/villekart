package com.gaurav.myapps.villekart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gaurav.myapps.villekart.dto.UserRepo;
import com.gaurav.myapps.villekart.entity.User;

@Service
public class CustomUserDetailsConfig implements UserDetailsService {

	@Autowired
	UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if(null == user) {
			throw new UsernameNotFoundException("Bad Credentials");
		}
		return user;
	}

}
