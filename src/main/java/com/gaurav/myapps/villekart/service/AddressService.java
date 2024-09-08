package com.gaurav.myapps.villekart.service;

import java.util.List;

import com.gaurav.myapps.villekart.entity.Address;

public interface AddressService {

	public String addOrUpdateAddressForUser(String username, Address address);
	
	public List<Address> getAddressesByUsername(String username);

	public String deleteAddressById(String username, int id);

}
