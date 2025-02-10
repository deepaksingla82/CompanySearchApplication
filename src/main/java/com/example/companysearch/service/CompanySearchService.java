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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CompanySearchService {

    private static final Logger log = LoggerFactory.getLogger(CompanySearchService.class);
    private static final String ACTIVE = "Active";

    @Autowired
    private TruProxyAPIClient truProxyAPIClient;

    @Autowired
    private CompanySearchResponseMapper responseMapper;

    public CompanySearchResponse retrieveCompanyAndOfficerDetails(
            final String apiKey, final String companyStatus, final CompanySearchRequest companySearchRequest){

        ResponseEntity<TruProxyCompanySearchResponse> truCompaniesResponse = null;
        ResponseEntity<TruProxyOfficerSearchResponse> truOfficersResponse = null;
        List<TruProxyCompanyDetails> filteredTruCompaniesList = null;
        List<TruProxyOfficerDetails> activeTruOfficersList = null;

        if(companySearchRequest.getCompanyNumber() != null) {
            truCompaniesResponse = truProxyAPIClient.searchCompanies(apiKey, companySearchRequest.getCompanyNumber());
            truOfficersResponse = truProxyAPIClient.searchOfficers(apiKey, companySearchRequest.getCompanyNumber());
            List<TruProxyCompanyDetails> truCompaniesList = truCompaniesResponse.getBody().getItems();
            filteredTruCompaniesList = filterCompaniesOnStatus(truCompaniesList, companyStatus);
            if(CollectionUtils.isEmpty(filteredTruCompaniesList)){
                log.info("No active company returned from Tru proxy Service");
                throw new CompanyNotFoundException("Company Not Found");
            }
            activeTruOfficersList = filterActiveOfficers(truOfficersResponse.getBody());

            CompanySearchResponse companySearchResponse = responseMapper.mapToCompanySearchResponse(filteredTruCompaniesList.get(0), activeTruOfficersList);
            return companySearchResponse;

        }
        else {
            truCompaniesResponse = truProxyAPIClient.searchCompanies(apiKey, companySearchRequest.getCompanyName());
            List<TruProxyCompanyDetails> truCompaniesList = truCompaniesResponse.getBody().getItems();
            filteredTruCompaniesList = filterCompaniesOnStatus(truCompaniesList, companyStatus);

            if(filteredTruCompaniesList.size() == 0) {
                log.info("No active company returned from Tru proxy Service");
                throw new CompanyNotFoundException("Company Not Found");
            }
            else {
                int totalResult = filteredTruCompaniesList.size();
                List<CompanyDetails> convertedCompaniesList = new ArrayList<>();

                for(TruProxyCompanyDetails truProxyCompanyDetails : filteredTruCompaniesList){
                    String companyNumber = truProxyCompanyDetails.getCompany_number();
                    truOfficersResponse = truProxyAPIClient.searchOfficers(apiKey, companyNumber);
                    activeTruOfficersList = filterActiveOfficers(truOfficersResponse.getBody());
                    CompanyDetails convertedCompanyDetails = responseMapper.convertCompanyDetails(truProxyCompanyDetails, activeTruOfficersList);

                    convertedCompaniesList.add(convertedCompanyDetails);
                }
                CompanySearchResponse companySearchResponse = new CompanySearchResponse();
                companySearchResponse.setTotalResults(totalResult);
                companySearchResponse.setItems(convertedCompaniesList);

                return companySearchResponse;
            }
        }
    }

    private List<TruProxyCompanyDetails> filterCompaniesOnStatus(List<TruProxyCompanyDetails> originalCompanyDetails, String companyStatus){
        if(companyStatus != null && companyStatus.equalsIgnoreCase(ACTIVE) && !CollectionUtils.isEmpty(originalCompanyDetails)){
            List<TruProxyCompanyDetails> filteredList =
                    originalCompanyDetails.stream().filter(company ->
                                    company.getCompany_status() != null && company.getCompany_status().equalsIgnoreCase(ACTIVE))
                            .collect(Collectors.toList());
            return filteredList;
        } else {
            return originalCompanyDetails;
        }
    }

    private List<TruProxyOfficerDetails> filterActiveOfficers(TruProxyOfficerSearchResponse truProxyOfficerSearchResponse){
        List<TruProxyOfficerDetails> originalOfficersList = truProxyOfficerSearchResponse.getItems();

        if(CollectionUtils.isEmpty(originalOfficersList)){
            return originalOfficersList;
        }

        List<TruProxyOfficerDetails> activeOfficers = originalOfficersList.stream().filter( officer ->
                officer.getResigned_on() == null).collect(Collectors.toList());

        return activeOfficers;
    }

}