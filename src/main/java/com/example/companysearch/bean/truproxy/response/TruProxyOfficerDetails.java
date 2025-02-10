package com.example.companysearch.bean.truproxy.response;

public class TruProxyOfficerDetails {

    private String name;
    private String appointed_on;
    private String resigned_on;
    private String officer_role;
    private String occupation;
    private String country_of_residence;
    private String nationality;
    private TruProxyOfficerLinks links;
    private TruProxyOfficerDob date_of_birth;
    private TruProxyAddress address;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppointed_on() {
        return appointed_on;
    }

    public void setAppointed_on(String appointed_on) {
        this.appointed_on = appointed_on;
    }

    public String getResigned_on() {
        return resigned_on;
    }

    public void setResigned_on(String resigned_on) {
        this.resigned_on = resigned_on;
    }

    public String getOfficer_role() {
        return officer_role;
    }

    public void setOfficer_role(String officer_role) {
        this.officer_role = officer_role;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getCountry_of_residence() {
        return country_of_residence;
    }

    public void setCountry_of_residence(String country_of_residence) {
        this.country_of_residence = country_of_residence;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public TruProxyOfficerLinks getLinks() {
        return links;
    }

    public void setLinks(TruProxyOfficerLinks links) {
        this.links = links;
    }

    public TruProxyOfficerDob getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(TruProxyOfficerDob date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public TruProxyAddress getAddress() {
        return address;
    }

    public void setAddress(TruProxyAddress address) {
        this.address = address;
    }
}
