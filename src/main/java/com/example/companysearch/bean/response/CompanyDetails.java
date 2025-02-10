package com.example.companysearch.bean.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CompanyDetails {


    @JsonProperty("company_number")
    private String companyNumber;

    @JsonProperty("company_type")
    private String companyType;
    private String title;
    @JsonProperty("company_status")
    private String companyStatus;
    @JsonProperty("date_of_creation")
    private String creationDate;
    private Address address;
    private List<OfficerDetails> officerDetails;



    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(String companyStatus) {
        this.companyStatus = companyStatus;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<OfficerDetails> getOfficers() {
        return officerDetails;
    }

    public void setOfficers(List<OfficerDetails> officerDetails) {
        this.officerDetails = officerDetails;
    }
}
