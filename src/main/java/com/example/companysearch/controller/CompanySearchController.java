package com.example.companysearch.controller;

import com.example.companysearch.bean.request.CompanySearchRequest;
import com.example.companysearch.bean.response.CompanySearchResponse;
import com.example.companysearch.service.CompanySearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/companies")
public class CompanySearchController {

    public static final String API_KEY_HEADER = "x-api-key";

    @Autowired
    private CompanySearchService companySearchService;

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity searchCompanyDetails(@RequestHeader(API_KEY_HEADER) String apiKey,
                                               @RequestBody CompanySearchRequest companySearchRequest,
                                               @RequestParam(required = false, value = "status") String companyStatus){

        CompanySearchResponse response = companySearchService.retrieveCompanyAndOfficerDetails(apiKey, companyStatus, companySearchRequest);
        return new ResponseEntity(response, HttpStatus.OK);
    }


}