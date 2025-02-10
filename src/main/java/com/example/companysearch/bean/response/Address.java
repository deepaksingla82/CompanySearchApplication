package com.example.companysearch.bean.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Address {

    private String premises;

    @JsonProperty("address_line_1")
    private String addressLine1;
    private String locality;
    @JsonProperty("postal_code")
    private String postalCode;
    private String country;



    public String getPremises() {
        return premises;
    }

    public void setPremises(String premises) {
        this.premises = premises;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
