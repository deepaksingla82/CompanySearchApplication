package com.example.companysearch.bean.truproxy.response;

import java.util.List;

public class TruProxyCompanyDetails {

    private String company_status;
    private String address_snippet;
    private String date_of_creation;
    private TruProxyMatches matches;
    private String description;
    private TruProxyLink links;
    private String company_number;
    private String title;
    private String company_type;
    private TruProxyAddress address;
    private String kind;
    private List<String> description_identifier;


    public String getCompany_status() {
        return company_status;
    }

    public void setCompany_status(String company_status) {
        this.company_status = company_status;
    }

    public String getAddress_snippet() {
        return address_snippet;
    }

    public void setAddress_snippet(String address_snippet) {
        this.address_snippet = address_snippet;
    }

    public String getDate_of_creation() {
        return date_of_creation;
    }

    public void setDate_of_creation(String date_of_creation) {
        this.date_of_creation = date_of_creation;
    }

    public TruProxyMatches getMatches() {
        return matches;
    }

    public void setMatches(TruProxyMatches matches) {
        this.matches = matches;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TruProxyLink getLinks() {
        return links;
    }

    public void setLinks(TruProxyLink links) {
        this.links = links;
    }

    public String getCompany_number() {
        return company_number;
    }

    public void setCompany_number(String company_number) {
        this.company_number = company_number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany_type() {
        return company_type;
    }

    public void setCompany_type(String company_type) {
        this.company_type = company_type;
    }

    public TruProxyAddress getAddress() {
        return address;
    }

    public void setAddress(TruProxyAddress address) {
        this.address = address;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public List<String> getDescription_identifier() {
        return description_identifier;
    }

    public void setDescription_identifier(List<String> description_identifier) {
        this.description_identifier = description_identifier;
    }
}
