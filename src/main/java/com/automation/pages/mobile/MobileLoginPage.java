package com.automation.pages.mobile;

import com.automation.core.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class MobileLoginPage extends BasePage {

    // Android Elements
    @AndroidFindBy(id = "com.example.app:id/username")
    private WebElement usernameField;

    @AndroidFindBy(id = "com.example.app:id/password")
    private WebElement passwordField;

    @AndroidFindBy(id = "com.example.app:id/loginButton")
    private WebElement loginButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Error']")
    private WebElement errorMessage;

    // Constructor
    public MobileLoginPage(AppiumDriver driver) {
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
