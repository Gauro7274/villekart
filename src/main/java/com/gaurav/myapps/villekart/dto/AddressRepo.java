package com.gaurav.myapps.villekart.dto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gaurav.myapps.villekart.entity.Address;
import com.gaurav.myapps.villekart.entity.User;

public interface AddressRepo extends JpaRepository<Address, Integer> {
    List<Address> findByUser(User user);
}
