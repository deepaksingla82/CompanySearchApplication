# Getting Started
Spring Boot Version 3.4.2 was not compatible with Spring Cloud Feign version 4.1.0, that is why,
I have downgraded Spring Boot Version from 3.4.2 to 3.2.2
_Spring Boot [3.4.2] is not compatible with this Spring Cloud release train', 
action = 'Change Spring Boot version to one of the following versions [3.2.x]_

### Reference Documentation
Link to Exercise: https://github.com/RiskNarrative/spring-exercise

### Development Assumptions:

 - Assumption about the TruProxy API:
   * API Key (x-api-key) was not provided so I couldn't check how TruProxy behaving with different inputs.
   * Just used the sample URLs and Responses given just to understand the behaviour of the Cloud Service.
   * When TruProxy Company Search API is called with company Number, then API will return only one record 
    containing that Company Number (which may be an active or inactive company record) or no record at all.
   * When TruProxy Company Search API is called with company name, then TruProxy API may return multiple records
     (containing similar name records, which may be active or inactive companies), for example If we are calling 
     service with name 'BBC', then it may return companies details for these sample companies - BBC Limited, BBC
     News Network, IBBC Limited etc, etc.
   
 - Implementation Assumptions:
    * When query parameter(?status=active) is passed in the API's URL, then the API will filter the companies record and
      return only active companies.
    * Query parameter is case-insensitive, means active/Active/ACTIVE will behave in same way.
    * if you pass query parameter's ('status') value other than 'active', there will no filtering of the company 
      records returned from Tru Proxy API. In other words, it will not work for status=inactive or status=stale etc.
    * Unit Testing and Integration Testing has been implemented to test some scenarios based on mock responses.
    * No TruProxy persistence API is exposed, so save company record by company number is not implemented.
    * when an invalid (non-existance) company number is passed in the request, then API will response with message
      Not found with a status code of 404 OK. (However: There was no such requirement mentioned in the description). 

### Sample Endpoints case 1 (called with company Number):
URL: http://localhost:8080/api/v1/companies/search?status=active
Request Header: x-api-key: <Value-Not-Known>
Request Body:     
{
"companyName": "test",
"companyNumber": "06500244"        
}

Response:
{
"items": [
{
"title": "BBC LIMITED",
"address": {
"premises": "Boswell Cottage Main Street",
"locality": "Retford",
"country": "England",
"address_line_1": "North Leverton",
"postal_code": "DN22 0AD"
},
"officers": [
{
"name": "James, Kent",
"address": {
"premises": "The Stamford Building",
"locality": "Manchester",
"country": "England",
"address_line_1": "17 Princess Lane",
"postal_code": "M23 4AF"
},
"officer_role": "Managing Director",
"appointed_on": "2017-04-01"
}
],
"company_number": "06500244",
"company_type": "ltd",
"company_status": "active",
"date_of_creation": "2008-02-11"
}
],
"total_results": 1
}

### Sample Endpoint case 2 (called with company Name):
URL: http://localhost:8080/api/v1/companies/search?status=active
Request Header: x-api-key: <Value-Not-Known>
Request Body:     
{
"companyName": "BBC",
"companyNumber": ""        
}

Response:
{
"items": [
{
"title": "BBC LIMITED",
"address": {
"premises": "Boswell Cottage Main Street",
"locality": "Retford",
"country": "England",
"address_line_1": "North Leverton",
"postal_code": "DN22 0AD"
},
"officers": [
{
"name": "James, Kent",
"address": {
"premises": "The Stamford Building",
"locality": "Manchester",
"country": "England",
"address_line_1": "17 Princess Lane",
"postal_code": "M23 4AF"
},
"officer_role": "Managing Director",
"appointed_on": "2017-04-01"
}
],
"company_number": "06500244",
"company_type": "ltd",
"company_status": "active",
"date_of_creation": "2008-02-11"
},
{
"title": "BBC News Limited",
"address": {
"premises": "5 Sky Building",
"locality": "Salford Quay",
"country": "England",
"address_line_1": "North Leverton",
"postal_code": "M50 3UB"
},
"officers": [
{
"name": "Anthony, Jackson",
"address": {
"premises": "The Stamford Building",
"locality": "Manchester",
"country": "England",
"address_line_1": "17 Princess Lane",
"postal_code": "M23 4AF"
},
"officer_role": "Secretary",
"appointed_on": "2017-04-01"
},
{
"name": "Kuldeep Singh",
"address": {
"premises": "The Babbage House",
"locality": "Knutsford",
"country": "England",
"address_line_1": "Radbroke Hall",
"postal_code": "WA16 9EU"
},
"officer_role": "Managing Director",
"appointed_on": "2015-04-09"
}
],
"company_number": "01799031",
"company_type": "ltd",
"company_status": "active",
"date_of_creation": "2001-05-12"
}
],
"total_results": 2
}


### Tru Proxy API Reference Documentation
Search for Company:
https://exercise.trunarrative.cloud/TruProxyAPI/rest/Companies/v1/Search?Query={search_term}

Sample Response:
{
"page_number": 1,
"kind": "search#companies",
"total_results": 20,
"items": [
{
"company_status": "active",
"address_snippet": "Boswell Cottage Main Street, North Leverton, Retford, England, DN22 0AD",
"date_of_creation": "2008-02-11",
"matches": {
"title": [
1,
3
]
},
"description": "06500244 - Incorporated on 11 February 2008",
"links": {
"self": "/company/06500244"
},
"company_number": "06500244",
"title": "BBC LIMITED",
"company_type": "ltd",
"address": {
"premises": "Boswell Cottage Main Street",
"postal_code": "DN22 0AD",
"country": "England",
"locality": "Retford",
"address_line_1": "North Leverton"
},
"kind": "searchresults#company",
"description_identifier": [
"incorporated-on"
]
}]
}

Get Company Officers:
https://exercise.trunarrative.cloud/TruProxyAPI/rest/Companies/v1/Officers?CompanyNumber={number}

{
"etag": "6dd2261e61776d79c2c50685145fac364e75e24e",
"links": {
"self": "/company/10241297/officers"
},
"kind": "officer-list",
"items_per_page": 35,
"items": [
{
"address": {
"premises": "The Leeming Building",
"postal_code": "LS2 7JF",
"country": "England",
"locality": "Leeds",
"address_line_1": "Vicar Lane"
},
"name": "ANTLES, Kerri",
"appointed_on": "2017-04-01",
"resigned_on": "2018-02-12",
"officer_role": "director",
"links": {
"officer": {
"appointments": "/officers/4R8_9bZ44w0_cRlrxoC-wRwaMiE/appointments"
}
},
"date_of_birth": {
"month": 6,
"year": 1969
},
"occupation": "Finance And Accounting",
"country_of_residence": "United States",
"nationality": "American"
}]
}

Authentication:
Use the API key provided in your request header when calling the endpoints.
Example: curl -s -H 'x-api-key: xxxxxxxxxxxxx' "https://exercise.trunarrative.cloud/TruProxyAPI/rest/Companies/v1/Officers?CompanyNumber=10241297"

API credentials will be provided seperately.





