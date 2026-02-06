package com.automation.api;

import com.automation.utils.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class ApiBase {
    protected static final Logger logger = LogManager.getLogger(ApiBase.class);
    protected RequestSpecification request;

    public ApiBase() {
        RestAssured.baseURI = ConfigManager.getApiBaseUrl();
        request = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
        logger.debug("API Base initialized with base URI: {}", RestAssured.baseURI);
    }

    // GET Request
    public Response get(String endpoint) {
        logger.info("Sending GET request to: {}", endpoint);
        Response response = request.get(endpoint);
        logResponse(response);
        return response;
    }

    public Response get(String endpoint, Map<String, String> queryParams) {
        logger.info("Sending GET request to: {} with query params: {}", endpoint, queryParams);
        Response response = request.queryParams(queryParams).get(endpoint);
        logResponse(response);
        return response;
    }

    // POST Request
    public Response post(String endpoint, Object body) {
        logger.info("Sending POST request to: {}", endpoint);
        logger.debug("Request body: {}", body);
        Response response = request.body(body).post(endpoint);
        logResponse(response);
        return response;
    }

    // PUT Request
    public Response put(String endpoint, Object body) {
        logger.info("Sending PUT request to: {}", endpoint);
        logger.debug("Request body: {}", body);
        Response response = request.body(body).put(endpoint);
        logResponse(response);
        return response;
    }

    // PATCH Request
    public Response patch(String endpoint, Object body) {
        logger.info("Sending PATCH request to: {}", endpoint);
        logger.debug("Request body: {}", body);
        Response response = request.body(body).patch(endpoint);
        logResponse(response);
        return response;
    }

    // DELETE Request
    public Response delete(String endpoint) {
        logger.info("Sending DELETE request to: {}", endpoint);
        Response response = request.delete(endpoint);
        logResponse(response);
        return response;
    }

    // Set Headers
    public ApiBase setHeader(String key, String value) {
        request.header(key, value);
        return this;
    }

    public ApiBase setHeaders(Map<String, String> headers) {
        request.headers(headers);
        return this;
    }

    // Set Auth Token
    public ApiBase setAuthToken(String token) {
        request.header("Authorization", "Bearer " + token);
        return this;
    }

    // Verify Response
    public void verifyStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        if (actualStatusCode == expectedStatusCode) {
            logger.info("Status code verification passed: {}", actualStatusCode);
        } else {
            logger.error("Status code verification failed. Expected: {}, Actual: {}", 
                expectedStatusCode, actualStatusCode);
            throw new AssertionError("Expected status code: " + expectedStatusCode + 
                ", but got: " + actualStatusCode);
        }
    }

    // Log Response
    private void logResponse(Response response) {
        logger.info("Response Status Code: {}", response.getStatusCode());
        logger.debug("Response Time: {} ms", response.getTime());
        logger.debug("Response Body: {}", response.getBody().asString());
    }

    // Extract Response
    public <T> T extractResponse(Response response, Class<T> responseClass) {
        return response.as(responseClass);
    }
}
