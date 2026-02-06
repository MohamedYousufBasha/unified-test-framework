package com.automation.tests.api;

import com.automation.api.ApiBase;
import com.automation.reports.ExtentManager;
import com.automation.tests.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class UserApiTest extends BaseTest {

    private ApiBase apiBase = new ApiBase();

    @Test(description = "Get user details by ID")
    public void testGetUser() {
        ExtentManager.info("Starting GET user test");
        
        Response response = apiBase.get("/users/1");
        
        ExtentManager.info("Verifying status code");
        apiBase.verifyStatusCode(response, 200);
        
        String userId = response.jsonPath().getString("id");
        ExtentManager.info("User ID: " + userId);
        
        Assert.assertEquals(userId, "1", "User ID should be 1");
        ExtentManager.pass("GET user test passed");
    }

    @Test(description = "Create a new user")
    public void testCreateUser() {
        ExtentManager.info("Starting POST create user test");
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "John Doe");
        requestBody.put("email", "john.doe@example.com");
        requestBody.put("age", 30);
        
        Response response = apiBase.post("/users", requestBody);
        
        ExtentManager.info("Verifying status code");
        apiBase.verifyStatusCode(response, 201);
        
        String createdName = response.jsonPath().getString("name");
        Assert.assertEquals(createdName, "John Doe", "Created user name should match");
        
        ExtentManager.pass("POST create user test passed");
    }

    @Test(description = "Update existing user")
    public void testUpdateUser() {
        ExtentManager.info("Starting PUT update user test");
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Jane Doe Updated");
        requestBody.put("email", "jane.updated@example.com");
        
        Response response = apiBase.put("/users/1", requestBody);
        
        ExtentManager.info("Verifying status code");
        apiBase.verifyStatusCode(response, 200);
        
        ExtentManager.pass("PUT update user test passed");
    }

    @Test(description = "Delete user by ID")
    public void testDeleteUser() {
        ExtentManager.info("Starting DELETE user test");
        
        Response response = apiBase.delete("/users/1");
        
        ExtentManager.info("Verifying status code");
        apiBase.verifyStatusCode(response, 204);
        
        ExtentManager.pass("DELETE user test passed");
    }

    @Test(description = "Get users with query parameters")
    public void testGetUsersWithParams() {
        ExtentManager.info("Starting GET users with query params test");
        
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("page", "1");
        queryParams.put("limit", "10");
        
        Response response = apiBase.get("/users", queryParams);
        
        apiBase.verifyStatusCode(response, 200);
        
        int userCount = response.jsonPath().getList("users").size();
        ExtentManager.info("Number of users returned: " + userCount);
        
        Assert.assertTrue(userCount > 0, "Should return at least one user");
        ExtentManager.pass("GET users with params test passed");
    }
}
