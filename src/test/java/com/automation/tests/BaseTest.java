package com.automation.tests;

import com.automation.core.DriverManager;
import com.automation.utils.ConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeMethod
    @Parameters({"platform", "browser", "deviceName"})
    public void setup(@Optional("web") String platform, 
                     @Optional("chrome") String browser,
                     @Optional String deviceName) {
        logger.info("Setting up test for platform: {}", platform);

        switch (platform.toLowerCase()) {
            case "web":
                setupWebTest(browser);
                break;
            case "android":
                setupAndroidTest(deviceName);
                break;
            case "ios":
                setupIOSTest(deviceName);
                break;
            case "api":
                logger.info("API test - no driver setup required");
                break;
            default:
                throw new IllegalArgumentException("Invalid platform: " + platform);
        }
    }

    private void setupWebTest(String browser) {
        logger.info("Initializing Web test with browser: {}", browser);
        DriverManager.initializeWebDriver(browser);
        DriverManager.getWebDriver().get(ConfigManager.getWebUrl());
    }

    private void setupAndroidTest(String deviceName) {
        logger.info("Initializing Android test");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", deviceName != null ? deviceName : ConfigManager.getAndroidDeviceName());
        capabilities.setCapability("app", ConfigManager.getAndroidAppPath());
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("autoGrantPermissions", true);
        
        DriverManager.initializeAndroidDriver(ConfigManager.getAppiumServerUrl(), capabilities);
    }

    private void setupIOSTest(String deviceName) {
        logger.info("Initializing iOS test");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", deviceName != null ? deviceName : ConfigManager.getIOSDeviceName());
        capabilities.setCapability("app", ConfigManager.getIOSAppPath());
        capabilities.setCapability("automationName", "XCUITest");
        capabilities.setCapability("autoAcceptAlerts", true);
        
        DriverManager.initializeIOSDriver(ConfigManager.getAppiumServerUrl(), capabilities);
    }

    @AfterMethod
    public void tearDown() {
        logger.info("Tearing down test");
        DriverManager.quitAllDrivers();
    }
}
