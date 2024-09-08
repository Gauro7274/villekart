package com.gaurav.myapps.villekart.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PincodeDetails {

    @JsonProperty("District")
    private String district;

    @JsonProperty("Block")
    private String block;

    @JsonProperty("State")
    private String state;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("Pincode")
    private String pincode;

}
