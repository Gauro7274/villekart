package com.gaurav.myapps.villekart.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_line", nullable = false)
    private String firstLine;

    @Column(name = "second_line", nullable = false)
    private String secondLine;
    
    @Column(name = "primary_mob_no", nullable = false)
    private String primaryMobileNo;
    
    @Column(name = "secondary_mob_no")
    private String secondaryMobileNo;

    @Column(nullable = false)
    private String state;
    
    @Column(nullable = false)	
    private String district;
    
    @Column(nullable = false)
    private String city;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Column(name = "delivery_route", nullable = false)
    private String deliveryRoute;

    @Column(name = "default_address")
    private boolean defaultAddress = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference  // Manages serialization for the child (Address)
    private User user;
}