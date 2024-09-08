package com.gaurav.myapps.villekart.serviceImpl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gaurav.myapps.villekart.dto.AddressRepo;
import com.gaurav.myapps.villekart.dto.UserRepo;
import com.gaurav.myapps.villekart.entity.Address;
import com.gaurav.myapps.villekart.entity.User;
import com.gaurav.myapps.villekart.service.AddressService;
import com.gaurav.myapps.villekart.utils.StringConstants;

import jakarta.transaction.Transactional;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	AddressRepo addrRepo;

	@Autowired
	UserRepo userRepo;

	@Override
	@CacheEvict(value = "userAddresses", key = "#username")
	@Transactional
	public String addOrUpdateAddressForUser(String username, Address address) {
		User user = userRepo.findByUsername(username);
		try {
			List<Address> userAddresses = user.getAddresses();

			if (userAddresses.isEmpty()) {
				// First address for the user, set as default
				address.setDefaultAddress(true);
			} else {
				if (address.isDefaultAddress()) {
					// If a new address is being added as default, set all other addresses' default
					// flag to false
					for (Address addr : userAddresses) {
						addr.setDefaultAddress(false);
					}
				}

				// Check if the address is already present in the database (based on ID)
				if (address.getId() != 0) {
					// Look for the address with the same ID in the user's addresses
					Optional<Address> existingAddressOpt = userAddresses.stream()
							.filter(addr -> addr.getId() == address.getId()).findFirst();

					if (existingAddressOpt.isPresent()) {
						// Update the existing address
						Address existingAddress = existingAddressOpt.get();
						existingAddress.setFirstLine(address.getFirstLine());
						existingAddress.setSecondLine(address.getSecondLine());
						existingAddress.setState(address.getState());
						existingAddress.setPostalCode(address.getPostalCode());
						existingAddress.setDeliveryRoute(address.getDeliveryRoute());
						existingAddress.setDefaultAddress(address.isDefaultAddress());
					} else {
						// Address ID is present but not found in the user's addresses (possible error)
						return "Address not found for this user";
					}
				} else {
					// New address, just add it to the user's list
					address.setUser(user);
					userAddresses.add(address);
				}
			}

			// Save the updated user object, which will also update the addresses
			userRepo.save(user);
			return StringConstants.ADDRESS_SAVE_SUCCESS.getValue();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return StringConstants.ADDRESS_SAVE_FAILED.getValue();
		}

	}

	@Cacheable(value = "userAddresses", key = "#username")
	public List<Address> getAddressesByUsername(String username) {
		User user = userRepo.findByUsername(username);
		if (user != null) {
			return user.getAddresses();
		} else {
			throw new RuntimeException("User not found with username: " + username);
		}
	}

	@Override
	@CacheEvict(value = "userAddresses", key = "#username")
	@Transactional
	public String deleteAddressById(String username, int addressId) {
		try {
			// Find the address by ID
			Address address = addrRepo.findById(addressId).orElseThrow(() -> new RuntimeException("Address not found"));

			// Remove the address from the user
			User user = address.getUser();
			List<Address> addresses = user.getAddresses();

			if (address.isDefaultAddress() && !addresses.isEmpty()) {

				// Sort addresses by ID in descending order
				//this is done for getting recently added address as default
                Collections.sort(addresses, Comparator.comparingInt(Address::getId).reversed()); 
                
				Optional<Address> existingAddressOpt = addresses.stream()
						.filter(addr -> addr.getId() != address.getId()).findFirst();

				if (existingAddressOpt.isPresent()) {
					// set default address field
					Address existingAddress = existingAddressOpt.get();
					existingAddress.setDefaultAddress(true);
				}

			}

			user.getAddresses().remove(address);

			// Delete the address
			addrRepo.delete(address);

			// Save the updated user object
			userRepo.save(user);

			return StringConstants.ADDRESS_DELETE_SUCCESS.getValue();
		} catch (Exception e) {
			e.printStackTrace();
			return StringConstants.ADDRESS_DELETE_FAILED.getValue();
		}
	}

}
