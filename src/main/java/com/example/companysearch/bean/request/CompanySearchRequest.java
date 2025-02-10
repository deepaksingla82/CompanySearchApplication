package com.example.companysearch.bean.request;

public class CompanySearchRequest {

    private String companyName;

    private String companyNumber;



    public CompanySearchRequest(){}

    public CompanySearchRequest(String companyName, String companyNumber) {
        this.companyName = companyName;
        this.companyNumber = companyNumber;

    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
