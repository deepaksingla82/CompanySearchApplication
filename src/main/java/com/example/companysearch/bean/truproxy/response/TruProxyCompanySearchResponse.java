package com.example.companysearch.bean.truproxy.response;

import java.util.List;

public class TruProxyCompanySearchResponse {

    private int page_number;
    private String kind;
    private int total_results;
    private List<TruProxyCompanyDetails> items;



    public int getPage_number() {
        return page_number;
    }

    public void setPage_number(int page_number) {
        this.page_number = page_number;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public List<TruProxyCompanyDetails>  getItems() {
        return items;
    }

    public void setItems(List<TruProxyCompanyDetails> items) {
        this.items = items;
    }
}
