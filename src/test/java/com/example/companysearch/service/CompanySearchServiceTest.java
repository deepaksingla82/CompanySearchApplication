package com.example.companysearch.service;

import com.example.companysearch.bean.request.CompanySearchRequest;
import com.example.companysearch.bean.response.CompanyDetails;
import com.example.companysearch.bean.response.CompanySearchResponse;
import com.example.companysearch.bean.truproxy.response.TruProxyCompanyDetails;
import com.example.companysearch.bean.truproxy.response.TruProxyCompanySearchResponse;
import com.example.companysearch.bean.truproxy.response.TruProxyOfficerDetails;
import com.example.companysearch.bean.truproxy.response.TruProxyOfficerSearchResponse;
import com.example.companysearch.client.TruProxyAPIClient;
import com.example.companysearch.exception.CompanyNotFoundException;
import com.example.companysearch.mapper.CompanySearchResponseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompanySearchServiceTest {

    @Mock
    private TruProxyAPIClient truProxyAPIClient;

    @Mock
    CompanySearchResponseMapper responseMapper;

    @InjectMocks
    private CompanySearchService service;

    @BeforeEach
    public void setup(){
    }

    @Test
    public void testRetrieveCompanyAndOfficerDetailsWithCompanyNumber(){
        String apiKey = "aKey";
        String status = "active";
        String companyName = "ACompany";
        String companyNumber = "1010";

        CompanySearchRequest companySearchRequest = new CompanySearchRequest(companyName, companyNumber);

        TruProxyCompanySearchResponse truProxyCompanySearchResponse = new TruProxyCompanySearchResponse();
        TruProxyCompanyDetails companyDetails = new TruProxyCompanyDetails();
        companyDetails.setCompany_number(companyNumber);
        companyDetails.setCompany_status("active");
        List<TruProxyCompanyDetails> companyDetailsList = new ArrayList<>();
        companyDetailsList.add(companyDetails);
        truProxyCompanySearchResponse.setItems(companyDetailsList);
        truProxyCompanySearchResponse.setTotal_results(1);

        TruProxyOfficerSearchResponse truProxyOfficerSearchResponse = new TruProxyOfficerSearchResponse();
        TruProxyOfficerDetails truProxyOfficerDetails = new TruProxyOfficerDetails();
        truProxyOfficerDetails.setName("Test Officer");
        List<TruProxyOfficerDetails> officerDetailsList = new ArrayList<>();
        officerDetailsList.add(truProxyOfficerDetails);
        truProxyOfficerSearchResponse.setItems(officerDetailsList);

        CompanySearchResponse expectedResponse = new CompanySearchResponse();
        CompanyDetails companyDetails1 = new CompanyDetails();
        companyDetails1.setCompanyNumber(companyNumber);
        companyDetails1.setTitle(companyName);
        List<CompanyDetails> companies = new ArrayList<>();
        companies.add(companyDetails1);
        expectedResponse.setItems(companies);
        expectedResponse.setTotalResults(1);

        ResponseEntity<TruProxyCompanySearchResponse> compResponseEntity = new ResponseEntity<>(truProxyCompanySearchResponse, HttpStatus.OK);

        ResponseEntity<TruProxyOfficerSearchResponse> offResponseEntity = new ResponseEntity<>(truProxyOfficerSearchResponse, HttpStatus.OK);

        when(truProxyAPIClient.searchCompanies(apiKey, companySearchRequest.getCompanyNumber())).thenReturn(compResponseEntity);
        when(truProxyAPIClient.searchOfficers(apiKey, companySearchRequest.getCompanyNumber())).thenReturn(offResponseEntity);
        when(responseMapper.mapToCompanySearchResponse(companyDetails, officerDetailsList)).thenReturn(expectedResponse);

        CompanySearchResponse searchResponse = service.retrieveCompanyAndOfficerDetails(apiKey, status, companySearchRequest);

        assertEquals(1, searchResponse.getTotalResults());
        assertNotNull(searchResponse.getItems());
        assertEquals(companyNumber, searchResponse.getItems().get(0).getCompanyNumber());
    }


    @Test
    public void testRetrieveCompanyAndOfficerDetailsWithCompanyNumber_WhenNoActiveCompaniesReturnedFromTruProxy_ShouldThrowException(){
        String apiKey = "aKey";
        String status = "active";
        String companyName = "ACompany";
        String companyNumber = "1010";

        CompanySearchRequest companySearchRequest = new CompanySearchRequest(companyName, companyNumber);

        TruProxyCompanySearchResponse truProxyCompanySearchResponse = new TruProxyCompanySearchResponse();
        TruProxyCompanyDetails companyDetails = new TruProxyCompanyDetails();
        companyDetails.setCompany_number(companyNumber);
        companyDetails.setCompany_status("in-active");
        List<TruProxyCompanyDetails> companyDetailsList = new ArrayList<>();
        companyDetailsList.add(companyDetails);
        truProxyCompanySearchResponse.setItems(companyDetailsList);
        truProxyCompanySearchResponse.setTotal_results(1);

        ResponseEntity<TruProxyCompanySearchResponse> compResponseEntity = new ResponseEntity<>(truProxyCompanySearchResponse, HttpStatus.OK);

        when(truProxyAPIClient.searchCompanies(apiKey, companySearchRequest.getCompanyNumber())).thenReturn(compResponseEntity);

        //Optional<CompanySearchResponse> searchResponse = service.retrieveCompanyAndOfficerDetails(apiKey, status, companySearchRequest);
        //then(caughtException()).isInstanceOf(CompanyNotFoundException.class);

        assertThrows(CompanyNotFoundException.class,
                () -> service.retrieveCompanyAndOfficerDetails(apiKey, status, companySearchRequest), "Company Not Found");


        //assertThrows()


        //CompanyNotFoundException companyNotFoundException = new CompanyNotFoundException("Company Not Found");
        //assertEquals("Invalid parametercount: expected=3, passed=2", exception.getMessage());


        //assertEquals(true, searchResponse.isEmpty());
    }



    @Test
    public void testRetrieveCompanyAndOfficerDetailsWithCompanyName(){
        String apiKey = "aKey";
        String status = "active";
        String companyName = "ACompany";
        String companyNumber1 = "1010";
        String companyNumber2 = "1011";

        CompanySearchRequest companySearchRequest = new CompanySearchRequest(companyName, null);

        TruProxyCompanySearchResponse truProxyCompanySearchResponse = new TruProxyCompanySearchResponse();
        TruProxyCompanyDetails truCompanyDetails = new TruProxyCompanyDetails();
        truCompanyDetails.setCompany_number(companyNumber1);
        truCompanyDetails.setCompany_status("active");

        TruProxyCompanyDetails truCompanyDetails2 = new TruProxyCompanyDetails();
        truCompanyDetails2.setCompany_number(companyNumber2);
        truCompanyDetails2.setCompany_status("active");

        List<TruProxyCompanyDetails> truCompanyDetailsList = new ArrayList<>();
        truCompanyDetailsList.add(truCompanyDetails);
        truCompanyDetailsList.add(truCompanyDetails2);

        truProxyCompanySearchResponse.setItems(truCompanyDetailsList);
        truProxyCompanySearchResponse.setTotal_results(2);

        TruProxyOfficerSearchResponse truProxyOfficerSearchResponse = new TruProxyOfficerSearchResponse();
        TruProxyOfficerDetails truProxyOfficerDetails = new TruProxyOfficerDetails();
        truProxyOfficerDetails.setName("Test Officer");
        List<TruProxyOfficerDetails> officerDetailsList = new ArrayList<>();
        officerDetailsList.add(truProxyOfficerDetails);
        truProxyOfficerSearchResponse.setItems(officerDetailsList);

        CompanyDetails companyDetails = new CompanyDetails();
        companyDetails.setCompanyNumber(companyNumber1);
        companyDetails.setTitle(companyName);

        CompanyDetails companyDetails2 = new CompanyDetails();
        companyDetails2.setCompanyNumber(companyNumber2);
        companyDetails2.setTitle(companyName);


        ResponseEntity<TruProxyCompanySearchResponse> compResponseEntity = new ResponseEntity<>(truProxyCompanySearchResponse, HttpStatus.OK);
        ResponseEntity<TruProxyOfficerSearchResponse> offResponseEntity = new ResponseEntity<>(truProxyOfficerSearchResponse, HttpStatus.OK);

        when(truProxyAPIClient.searchCompanies(apiKey, companySearchRequest.getCompanyName())).thenReturn(compResponseEntity);
        when(truProxyAPIClient.searchOfficers(anyString(), anyString())).thenReturn(offResponseEntity);
        when(responseMapper.convertCompanyDetails(truCompanyDetails, officerDetailsList)).thenReturn(companyDetails);
        when(responseMapper.convertCompanyDetails(truCompanyDetails2, officerDetailsList)).thenReturn(companyDetails2);

        CompanySearchResponse searchResponse = service.retrieveCompanyAndOfficerDetails(apiKey, status, companySearchRequest);

        assertEquals(2, searchResponse.getTotalResults());
        assertNotNull(searchResponse.getItems());
        assertEquals(2, searchResponse.getItems().size());
        assertEquals(companyNumber1, searchResponse.getItems().get(0).getCompanyNumber());
        assertEquals(companyNumber2, searchResponse.getItems().get(1).getCompanyNumber());
    }

    @Test
    public void testRetrieveCompanyAndOfficerDetailsWithCompanyName_WhenNoActiveCompaniesReturnedFromTruProxy_ShouldThrowException(){
        String apiKey = "aKey";
        String status = "active";
        String companyName = "ACompany";
        String companyNumber1 = "1010";
        String companyNumber2 = "1011";

        CompanySearchRequest companySearchRequest = new CompanySearchRequest(companyName, null);

        TruProxyCompanySearchResponse truProxyCompanySearchResponse = new TruProxyCompanySearchResponse();
        TruProxyCompanyDetails truCompanyDetails = new TruProxyCompanyDetails();
        truCompanyDetails.setCompany_number(companyNumber1);
        truCompanyDetails.setCompany_status("in-active");

        TruProxyCompanyDetails truCompanyDetails2 = new TruProxyCompanyDetails();
        truCompanyDetails2.setCompany_number(companyNumber2);
        truCompanyDetails2.setCompany_status("in-active");

        List<TruProxyCompanyDetails> truCompanyDetailsList = new ArrayList<>();
        truCompanyDetailsList.add(truCompanyDetails);
        truCompanyDetailsList.add(truCompanyDetails2);

        truProxyCompanySearchResponse.setItems(truCompanyDetailsList);
        truProxyCompanySearchResponse.setTotal_results(2);
        ResponseEntity<TruProxyCompanySearchResponse> compResponseEntity = new ResponseEntity<>(truProxyCompanySearchResponse, HttpStatus.OK);

        when(truProxyAPIClient.searchCompanies(apiKey, companySearchRequest.getCompanyName())).thenReturn(compResponseEntity);

        assertThrows(CompanyNotFoundException.class,
                () -> service.retrieveCompanyAndOfficerDetails(apiKey, status, companySearchRequest), "Company Not Found");
    }

}