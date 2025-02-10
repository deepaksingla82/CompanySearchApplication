package com.example.companysearch.client;

import com.example.companysearch.bean.truproxy.response.TruProxyCompanySearchResponse;
import com.example.companysearch.bean.truproxy.response.TruProxyOfficerSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.companysearch.controller.CompanySearchController.API_KEY_HEADER;

@FeignClient(name="tru-proxy-api-service", url="${truProxyService.base.url}")
public interface TruProxyAPIClient {

    //given URL for company Search:
    // https://exercise.trunarrative.cloud/TruProxyAPI/rest/Companies/v1/Search?Query={search_term}
    //given URL for Officers search:
    // https://exercise.trunarrative.cloud/TruProxyAPI/rest/Companies/v1/Officers?CompanyNumber={number}

    @GetMapping(value = "/Search", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<TruProxyCompanySearchResponse> searchCompanies(@RequestHeader(API_KEY_HEADER) String apiKey,
                                                                  @RequestParam(value = "Query") String search_term);


    @GetMapping(value = "/Officers", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<TruProxyOfficerSearchResponse> searchOfficers(@RequestHeader(API_KEY_HEADER) String apiKey,
                                                                 @RequestParam(value = "CompanyNumber") String number);

}