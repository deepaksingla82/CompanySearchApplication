package com.example.companysearch.mapper;

import com.example.companysearch.bean.response.Address;
import com.example.companysearch.bean.response.CompanyDetails;
import com.example.companysearch.bean.response.CompanySearchResponse;
import com.example.companysearch.bean.response.OfficerDetails;
import com.example.companysearch.bean.truproxy.response.TruProxyAddress;
import com.example.companysearch.bean.truproxy.response.TruProxyCompanyDetails;
import com.example.companysearch.bean.truproxy.response.TruProxyOfficerDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompanySearchResponseMapper {

    public CompanySearchResponse mapToCompanySearchResponse(final TruProxyCompanyDetails truProxyCompanyDetails,
                                                            final List<TruProxyOfficerDetails> truProxyOfficerDetailsList){

        CompanySearchResponse companySearchResponse = new CompanySearchResponse();
        CompanyDetails companyDetails = convertCompanyDetails(truProxyCompanyDetails, truProxyOfficerDetailsList);
        List<CompanyDetails> companyDetailsList = new ArrayList<>();
        companyDetailsList.add(companyDetails);

        companySearchResponse.setItems(companyDetailsList);
        companySearchResponse.setTotalResults(companyDetailsList.size());
        return companySearchResponse;
    }

    public CompanyDetails convertCompanyDetails(final TruProxyCompanyDetails truProxyCompanyDetails,
                                                 final List<TruProxyOfficerDetails> truProxyOfficerDetailsList){

        CompanyDetails companyDetails = new CompanyDetails();
        List<OfficerDetails> officerDetails = convertOfficerDetails(truProxyOfficerDetailsList);

        companyDetails.setOfficers(officerDetails);
        companyDetails.setCompanyNumber(truProxyCompanyDetails.getCompany_number());
        companyDetails.setCompanyStatus(truProxyCompanyDetails.getCompany_status());
        companyDetails.setCompanyType(truProxyCompanyDetails.getCompany_type());
        companyDetails.setAddress(convertAddress(truProxyCompanyDetails.getAddress()));
        companyDetails.setTitle(truProxyCompanyDetails.getTitle());
        companyDetails.setCreationDate(truProxyCompanyDetails.getDate_of_creation());
        return companyDetails;
    }

    private Address convertAddress(final TruProxyAddress truProxyAddress){
        Address address = new Address();
        address.setPremises(truProxyAddress.getPremises());
        address.setAddressLine1(truProxyAddress.getAddress_line_1());
        address.setLocality(truProxyAddress.getLocality());
        address.setPostalCode(truProxyAddress.getPostal_code());
        address.setCountry(truProxyAddress.getCountry());
        return address;
    }

    private List<OfficerDetails> convertOfficerDetails(final List<TruProxyOfficerDetails> truProxyOfficerDetailsList){
        List<OfficerDetails> officerDetailsList = new ArrayList<>();

        if(CollectionUtils.isEmpty(truProxyOfficerDetailsList)){
            return officerDetailsList;
        }

        OfficerDetails officerDetails = null;
        for(TruProxyOfficerDetails proxyOfficerDetails : truProxyOfficerDetailsList){
            officerDetails = new OfficerDetails();
            officerDetails.setOfficerRole(proxyOfficerDetails.getOfficer_role());
            officerDetails.setAddress(convertAddress(proxyOfficerDetails.getAddress()));
            officerDetails.setAppointedDate(proxyOfficerDetails.getAppointed_on());
            officerDetails.setName(proxyOfficerDetails.getName());
            officerDetailsList.add(officerDetails);
        }
        return officerDetailsList;
    }

}