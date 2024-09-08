package com.gaurav.myapps.villekart.dto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gaurav.myapps.villekart.entity.Orders;
import com.gaurav.myapps.villekart.entity.User;

@Repository
public interface OrderRepo extends JpaRepository<Orders, Long>{
	List<Orders> findByUser(User user);
}
