package com.gaurav.myapps.villekart.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Entity
@Data
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Order ID with VKart-0000001 format
    private String orderId;

    private Date orderDate;
    private String productName;
    private String productDescription;
    private String orderRoute;
    private Date chooseDeliveryDate;
    private String timeSlot;
    private User deliveryPartner;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference  // Manages serialization for the child (Address)
    private User user;

    // Method to generate the order ID with prefix VKart-
    @PrePersist
    public void generateOrderId() {
        // Assuming that sequence is stored and incremented in the database or service layer
        this.orderId = "VKart-" + String.format("%07d", id); 
    }

    // Getters and Setters
}