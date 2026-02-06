package com.automation.tests.mobile;

import com.automation.core.DriverManager;
import com.automation.pages.mobile.MobileLoginPage;
import com.automation.reports.ExtentManager;
import com.automation.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MobileLoginTest extends BaseTest {

    @Test(description = "Verify successful login on mobile app")
    public void testMobileSuccessfulLogin() {
        ExtentManager.info("Starting mobile login test");
        
        MobileLoginPage loginPage = new MobileLoginPage(DriverManager.getMobileDriver());
        
        ExtentManager.info("Entering mobile credentials");
        loginPage.login("testuser@example.com", "Password123!");
        
        // Add your assertion here based on successful login indication
        // Example: Assert.assertTrue(homePage.isWelcomeMessageDisplayed());
        
        ExtentManager.pass("Mobile login test completed successfully");
    }

    @Test(description = "Verify error message with invalid credentials on mobile")
    public void testMobileInvalidLogin() {
        ExtentManager.info("Starting mobile invalid login test");
        
        MobileLoginPage loginPage = new MobileLoginPage(DriverManager.getMobileDriver());
        
        ExtentManager.info("Entering invalid mobile credentials");
        loginPage.login("invalid@example.com", "wrongpassword");
        
        String errorMessage = loginPage.getErrorMessage();
        ExtentManager.info("Error message: " + errorMessage);
        
        Assert.assertTrue(errorMessage.contains("Invalid") || errorMessage.contains("Error"), 
            "Error message should be displayed");
        
        ExtentManager.pass("Mobile invalid login test completed successfully");
    }

    @Test(description = "Verify login button is displayed on mobile")
    public void testMobileLoginButtonDisplayed() {
        ExtentManager.info("Verifying mobile login button is displayed");
        
        MobileLoginPage loginPage = new MobileLoginPage(DriverManager.getMobileDriver());
        
        boolean isDisplayed = loginPage.isLoginButtonDisplayed();
        Assert.assertTrue(isDisplayed, "Login button should be displayed");
        
        ExtentManager.pass("Mobile login button display test passed");
    }
}
