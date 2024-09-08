package com.gaurav.myapps.villekart.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gaurav.myapps.villekart.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
	User findByUsername(String username);
}
