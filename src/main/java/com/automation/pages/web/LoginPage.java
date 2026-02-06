package com.automation.pages.web;

import com.automation.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    // Page Factory Elements
    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(xpath = "//div[@class='error-message']")
    private WebElement errorMessage;

    @FindBy(linkText = "Forgot Password?")
    private WebElement forgotPasswordLink;

    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Page Methods
    public void enterUsername(String username) {
        logger.info("Entering username: {}", username);
        type(usernameField, username);
    }

    public void enterPassword(String password) {
        logger.info("Entering password");
        type(passwordField, password);
    }

    public void clickLoginButton() {
        logger.info("Clicking login button");
        click(loginButton);
    }

    public String getErrorMessage() {
        logger.info("Getting error message");
        return getText(errorMessage);
    }

    public void clickForgotPassword() {
        logger.info("Clicking forgot password link");
        click(forgotPasswordLink);
    }

    // High-level action
    public void login(String username, String password) {
        logger.info("Performing login with username: {}", username);
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isLoginButtonDisplayed() {
        return isElementDisplayed(loginButton);
    }
}
