package com.example.companysearch.bean.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CompanySearchResponse {

    @JsonProperty("total_results")
    private int totalResults;

    private List<CompanyDetails> items;

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<CompanyDetails> getItems() {
        return items;
    }

    public void setItems(List<CompanyDetails> items) {
        this.items = items;
    }
}
