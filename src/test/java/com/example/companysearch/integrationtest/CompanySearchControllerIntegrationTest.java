package com.example.companysearch.integrationtest;


import com.example.companysearch.CompanySearchApplication;
import com.example.companysearch.bean.request.CompanySearchRequest;
import com.example.companysearch.bean.response.CompanySearchResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.CollectionUtils;

import static com.example.companysearch.controller.CompanySearchController.API_KEY_HEADER;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.OK;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CompanySearchApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CompanySearchControllerIntegrationTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders httpHeaders = new HttpHeaders();

    private static WireMockServer wireMockServer;

    @BeforeAll
    static void init() {
        wireMockServer = new WireMockServer(
                new WireMockConfiguration().port(7070)
        );
        wireMockServer.start();
        WireMock.configureFor("localhost", 7070);
    }

    @Test
    public void testGetCompanyAndOfficersDetails_WithCompanyNumber_ShouldReturnSuccessResponse(){
        String apiKeyValue = "a-value";
        String companyName = "test";
        String companyNumber = "06500244";

        CompanySearchRequest companySearchRequest = new CompanySearchRequest(companyName, companyNumber);
        httpHeaders.add(API_KEY_HEADER, apiKeyValue);
        HttpEntity<CompanySearchRequest> entity = new HttpEntity<>(companySearchRequest, httpHeaders);

        stubFor(WireMock.get(urlPathEqualTo("/Search"))
                .withQueryParam("Query", WireMock.equalTo(companyNumber))
                .withHeader(API_KEY_HEADER, equalTo(apiKeyValue))
                .willReturn(aResponse()
                        .withStatus(OK.value())
                        .withHeader("content-type", "application/json")
                        .withBodyFile("mock-responses/companies-by-number.json")
                ));

        stubFor(WireMock.get(urlPathEqualTo("/Officers"))
                .withQueryParam("CompanyNumber", WireMock.equalTo(companyNumber))
                .withHeader(API_KEY_HEADER, equalTo(apiKeyValue))
                .willReturn(aResponse()
                        .withStatus(OK.value())
                        .withHeader("content-type", "application/json")
                        .withBodyFile("mock-responses/officers-by-company-number-06500244.json")
                ));

        ResponseEntity<CompanySearchResponse> response = restTemplate.exchange(createURLWithPort("/api/v1/companies/search"),
                HttpMethod.POST, entity, CompanySearchResponse.class);

        assertEquals(OK.value(), response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalResults());
        assertTrue(!CollectionUtils.isEmpty(response.getBody().getItems()));
        assertEquals(1, response.getBody().getItems().size());
    }

    @Test
    public void testGetCompanyAndOfficersDetails_WithCompanyNumber_ShouldReturnActiveCompaniesSuccessResponse(){
        String apiKeyValue = "a-value";
        String companyName = "test";
        String companyNumber = "06500244";
        String companyStatus = "active";

        CompanySearchRequest companySearchRequest = new CompanySearchRequest(companyName, companyNumber);
        httpHeaders.add(API_KEY_HEADER, apiKeyValue);
        HttpEntity<CompanySearchRequest> entity = new HttpEntity<>(companySearchRequest, httpHeaders);

        stubFor(WireMock.get(urlPathEqualTo("/Search"))
                .withQueryParam("Query", WireMock.equalTo(companyNumber))
                .withHeader(API_KEY_HEADER, equalTo(apiKeyValue))
                .willReturn(aResponse()
                        .withStatus(OK.value())
                        .withHeader("content-type", "application/json")
                        .withBodyFile("mock-responses/companies-by-number.json")
                ));

        stubFor(WireMock.get(urlPathEqualTo("/Officers"))
                .withQueryParam("CompanyNumber", WireMock.equalTo(companyNumber))
                .withHeader(API_KEY_HEADER, equalTo(apiKeyValue))
                .willReturn(aResponse()
                        .withStatus(OK.value())
                        .withHeader("content-type", "application/json")
                        .withBodyFile("mock-responses/officers-by-company-number-06500244.json")
                ));

        ResponseEntity<CompanySearchResponse> response = restTemplate.exchange(createURLWithPort("/api/v1/companies/search?status={status}"),
                HttpMethod.POST, entity, CompanySearchResponse.class, companyStatus);

        assertEquals(OK.value(), response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalResults());
        assertTrue(!CollectionUtils.isEmpty(response.getBody().getItems()));
        assertEquals(1, response.getBody().getItems().size());
    }

    @Test
    public void testGetCompanyAndOfficersDetails_WithCompanyName_ShouldReturnSuccessResponse(){
        String apiKeyValue = "a-value";
        String companyName = "BBC";

        CompanySearchRequest companySearchRequest = new CompanySearchRequest(companyName, null);
        httpHeaders.add(API_KEY_HEADER, apiKeyValue);
        HttpEntity<CompanySearchRequest> entity = new HttpEntity<>(companySearchRequest, httpHeaders);

        stubFor(WireMock.get(urlPathEqualTo("/Search"))
                .withQueryParam("Query", WireMock.equalTo(companyName))
                .withHeader(API_KEY_HEADER, equalTo(apiKeyValue))
                .willReturn(aResponse()
                        .withStatus(OK.value())
                        .withHeader("content-type", "application/json")
                        .withBodyFile("mock-responses/companies-by-name.json")
                ));

        stubFor(WireMock.get(urlPathEqualTo("/Officers"))
                .withQueryParam("CompanyNumber", WireMock.equalTo("06500244"))
                .withHeader(API_KEY_HEADER, equalTo(apiKeyValue))
                .willReturn(aResponse()
                        .withStatus(OK.value())
                        .withHeader("content-type", "application/json")
                        .withBodyFile("mock-responses/officers-by-company-number-06500244.json")
                ));

        stubFor(WireMock.get(urlPathEqualTo("/Officers"))
                .withQueryParam("CompanyNumber", WireMock.equalTo("01500299"))
                .withHeader(API_KEY_HEADER, equalTo(apiKeyValue))
                .willReturn(aResponse()
                        .withStatus(OK.value())
                        .withHeader("content-type", "application/json")
                        .withBodyFile("mock-responses/officers-by-company-number-01500299.json")
                ));

        stubFor(WireMock.get(urlPathEqualTo("/Officers"))
                .withQueryParam("CompanyNumber", WireMock.equalTo("01799031"))
                .withHeader(API_KEY_HEADER, equalTo(apiKeyValue))
                .willReturn(aResponse()
                        .withStatus(OK.value())
                        .withHeader("content-type", "application/json")
                        .withBodyFile("mock-responses/officers-by-company-number-01799031.json")
                ));

        ResponseEntity<CompanySearchResponse> response = restTemplate.exchange(createURLWithPort("/api/v1/companies/search"),
                HttpMethod.POST, entity, CompanySearchResponse.class);

        assertEquals(OK.value(), response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().getTotalResults());
        assertTrue(!CollectionUtils.isEmpty(response.getBody().getItems()));
        assertEquals(3, response.getBody().getItems().size());
    }


    @Test
    public void testGetCompanyAndOfficersDetails_WithCompanyName_ShouldReturnActiveCompaniesSuccessResponse(){
        String apiKeyValue = "a-value";
        String companyName = "BBC";
        String companyStatus = "active";

        CompanySearchRequest companySearchRequest = new CompanySearchRequest(companyName, null);
        httpHeaders.add(API_KEY_HEADER, apiKeyValue);
        HttpEntity<CompanySearchRequest> entity = new HttpEntity<>(companySearchRequest, httpHeaders);

        stubFor(WireMock.get(urlPathEqualTo("/Search"))
                .withQueryParam("Query", WireMock.equalTo(companyName))
                .withHeader(API_KEY_HEADER, equalTo(apiKeyValue))
                .willReturn(aResponse()
                        .withStatus(OK.value())
                        .withHeader("content-type", "application/json")
                        .withBodyFile("mock-responses/companies-by-name.json")
                ));

        stubFor(WireMock.get(urlPathEqualTo("/Officers"))
                .withQueryParam("CompanyNumber", WireMock.equalTo("06500244"))
                .withHeader(API_KEY_HEADER, equalTo(apiKeyValue))
                .willReturn(aResponse()
                        .withStatus(OK.value())
                        .withHeader("content-type", "application/json")
                        .withBodyFile("mock-responses/officers-by-company-number-06500244.json")
                ));

        stubFor(WireMock.get(urlPathEqualTo("/Officers"))
                .withQueryParam("CompanyNumber", WireMock.equalTo("01500299"))
                .withHeader(API_KEY_HEADER, equalTo(apiKeyValue))
                .willReturn(aResponse()
                        .withStatus(OK.value())
                        .withHeader("content-type", "application/json")
                        .withBodyFile("mock-responses/officers-by-company-number-01500299.json")
                ));

        stubFor(WireMock.get(urlPathEqualTo("/Officers"))
                .withQueryParam("CompanyNumber", WireMock.equalTo("01799031"))
                .withHeader(API_KEY_HEADER, equalTo(apiKeyValue))
                .willReturn(aResponse()
                        .withStatus(OK.value())
                        .withHeader("content-type", "application/json")
                        .withBodyFile("mock-responses/officers-by-company-number-01799031.json")
                ));

        ResponseEntity<CompanySearchResponse> response =
                restTemplate.exchange(createURLWithPort("/api/v1/companies/search?status={status}"),
                HttpMethod.POST, entity, CompanySearchResponse.class, companyStatus);

        assertEquals(OK.value(), response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getTotalResults());
        assertTrue(!CollectionUtils.isEmpty(response.getBody().getItems()));
        assertEquals(2, response.getBody().getItems().size());
    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }


}