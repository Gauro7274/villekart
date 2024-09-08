package com.gaurav.myapps.villekart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaurav.myapps.villekart.entity.Address;
import com.gaurav.myapps.villekart.service.AddressService;
import com.gaurav.myapps.villekart.utils.StringConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/UserData")
@Validated
public class DataController {

	@Autowired
	AddressService adrService;

	@PostMapping("/saveOrUpdateAddress")
	public ResponseEntity<String> saveUserAdress(@RequestBody Address address) {
		String username = null;
		ResponseEntity<String> responseEntity = null;
		String response = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			username = authentication.getName(); // This returns the username
			response = adrService.addOrUpdateAddressForUser(username, address);
			responseEntity = new ResponseEntity<String>(response, HttpStatus.OK);
		} else {
			response = StringConstants.ADDRESS_SAVE_FAILED.getValue();
			responseEntity = new ResponseEntity<String>(response, HttpStatus.OK);
		}
		return responseEntity;
	}
	
	@DeleteMapping("/deleteAddress/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable("id") int id) {
		String username = null;
		String responseMessage = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			username = authentication.getName(); // This returns the username
			responseMessage = adrService.deleteAddressById(username,id);
		}
        if (null != responseMessage  && responseMessage.equals(StringConstants.ADDRESS_DELETE_SUCCESS.getValue())) {
            return ResponseEntity.ok(responseMessage);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

	// GET endpoint to retrieve addresses for a user
	//for edit list of adresses
	@GetMapping("/retrieveAddressList")
	public ResponseEntity<List<Address>> getUserAddresses() {
		String username = null;
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.isAuthenticated()) {
				username = authentication.getName(); // This returns the username

				List<Address> addresses = adrService.getAddressesByUsername(username);
				return ResponseEntity.ok(addresses);
			}
			else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
			}
		} catch (UsernameNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	
}
