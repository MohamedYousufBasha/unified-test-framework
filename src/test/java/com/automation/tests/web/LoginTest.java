package com.automation.tests.web;

import com.automation.core.DriverManager;
import com.automation.pages.web.LoginPage;
import com.automation.reports.ExtentManager;
import com.automation.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(description = "Verify successful login with valid credentials")
    public void testSuccessfulLogin() {
        ExtentManager.info("Starting successful login test");
        
        LoginPage loginPage = new LoginPage(DriverManager.getWebDriver());
        
        ExtentManager.info("Entering credentials");
        loginPage.login("testuser@example.com", "Password123!");
        
        // Add your assertion here based on successful login indication
        // Example: Assert.assertTrue(homePage.isWelcomeMessageDisplayed());
        
        ExtentManager.pass("Login test completed successfully");
    }

    @Test(description = "Verify error message with invalid credentials")
    public void testInvalidLogin() {
        ExtentManager.info("Starting invalid login test");
        
        LoginPage loginPage = new LoginPage(DriverManager.getWebDriver());
        
        ExtentManager.info("Entering invalid credentials");
        loginPage.login("invalid@example.com", "wrongpassword");
        
        String errorMessage = loginPage.getErrorMessage();
        ExtentManager.info("Error message: " + errorMessage);
        
        Assert.assertTrue(errorMessage.contains("Invalid"), 
            "Error message should contain 'Invalid'");
        
        ExtentManager.pass("Invalid login test completed successfully");
    }

    @Test(description = "Verify login button is displayed")
    public void testLoginButtonDisplayed() {
        ExtentManager.info("Verifying login button is displayed");
        
        LoginPage loginPage = new LoginPage(DriverManager.getWebDriver());
        
        boolean isDisplayed = loginPage.isLoginButtonDisplayed();
        Assert.assertTrue(isDisplayed, "Login button should be displayed");
        
        ExtentManager.pass("Login button display test passed");
    }
}
