package com.automation.core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

public class BasePage {
    protected static final Logger logger = LogManager.getLogger(BasePage.class);
    protected WebDriver webDriver;
    protected AppiumDriver mobileDriver;
    protected WebDriverWait wait;

    // Constructor for Web
    public BasePage(WebDriver driver) {
        this.webDriver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
        logger.debug("Initialized web page: {}", this.getClass().getSimpleName());
    }

    // Constructor for Mobile
    public BasePage(AppiumDriver driver) {
        this.mobileDriver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
        logger.debug("Initialized mobile page: {}", this.getClass().getSimpleName());
    }

    // Wait Methods
    protected void waitForElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    // Click Methods
    protected void click(WebElement element) {
        waitForElementToBeClickable(element);
        element.click();
        logger.debug("Clicked on element: {}", element);
    }

    // Type Methods
    protected void type(WebElement element, String text) {
        waitForElementToBeVisible(element);
        element.clear();
        element.sendKeys(text);
        logger.debug("Typed '{}' into element: {}", text, element);
    }

    // Get Text
    protected String getText(WebElement element) {
        waitForElementToBeVisible(element);
        String text = element.getText();
        logger.debug("Retrieved text '{}' from element: {}", text, element);
        return text;
    }

    // JavaScript Executor Methods
    protected void scrollToElement(WebElement element) {
        if (webDriver != null) {
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            logger.debug("Scrolled to element: {}", element);
        }
    }

    protected void clickUsingJS(WebElement element) {
        if (webDriver != null) {
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript("arguments[0].click();", element);
            logger.debug("Clicked element using JavaScript: {}", element);
        }
    }

    // Utility Methods
    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.error("Sleep interrupted", e);
            Thread.currentThread().interrupt();
        }
    }
}
