package com.example.companysearch.controller;

import com.example.companysearch.bean.request.CompanySearchRequest;
import com.example.companysearch.bean.response.CompanySearchResponse;
import com.example.companysearch.service.CompanySearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.companysearch.controller.CompanySearchController.API_KEY_HEADER;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompanySearchController.class)
public class CompanySearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CompanySearchService companySearchService;

    @BeforeEach
    public void setup(){
    }

    @Test
    public void testGetCompanyAndOfficersDetails() throws Exception {
        CompanySearchRequest companySearchRequest = new CompanySearchRequest("TestName", "101");
        CompanySearchResponse mockResponse = new CompanySearchResponse();
        given(companySearchService.retrieveCompanyAndOfficerDetails("api-key", "status", companySearchRequest))
                .willReturn(mockResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/companies/search")
                .header(API_KEY_HEADER, "a-value")
                .queryParam("status", "active")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companySearchRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

}