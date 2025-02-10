package com.example.companysearch.bean.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OfficerDetails {

    private String name;
    @JsonProperty("officer_role")
    private String officerRole;
    @JsonProperty("appointed_on")
    private String appointedDate;
    private Address address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfficerRole() {
        return officerRole;
    }

    public void setOfficerRole(String officerRole) {
        this.officerRole = officerRole;
    }

    public String getAppointedDate() {
        return appointedDate;
    }

    public void setAppointedDate(String appointedDate) {
        this.appointedDate = appointedDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
