package com.example.companysearch.bean.truproxy.response;

import java.util.List;

public class TruProxyOfficerSearchResponse {

    private String etag;
    private String kind;
    private TruProxyLink links;
    private int items_per_page;
    private List<TruProxyOfficerDetails> items;


    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public TruProxyLink getLinks() {
        return links;
    }

    public void setLinks(TruProxyLink links) {
        this.links = links;
    }

    public int getItems_per_page() {
        return items_per_page;
    }

    public void setItems_per_page(int items_per_page) {
        this.items_per_page = items_per_page;
    }

    public List<TruProxyOfficerDetails> getItems() {
        return items;
    }

    public void setItems(List<TruProxyOfficerDetails> items) {
        this.items = items;
    }
}
