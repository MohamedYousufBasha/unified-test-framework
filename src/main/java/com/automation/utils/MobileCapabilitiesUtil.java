package com.automation.utils;

import org.openqa.selenium.remote.DesiredCapabilities;

public class MobileCapabilitiesUtil {

    public static DesiredCapabilities getAndroidCapabilities(String deviceName, String appPath) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("app", appPath);
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("autoGrantPermissions", true);
        capabilities.setCapability("noReset", false);
        capabilities.setCapability("fullReset", false);
        capabilities.setCapability("newCommandTimeout", 300);
        return capabilities;
    }

    public static DesiredCapabilities getAndroidCapabilitiesWithAppPackage(
            String deviceName, String appPackage, String appActivity) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("appPackage", appPackage);
        capabilities.setCapability("appActivity", appActivity);
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("autoGrantPermissions", true);
        capabilities.setCapability("noReset", false);
        return capabilities;
    }

    public static DesiredCapabilities getIOSCapabilities(String deviceName, String appPath) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("app", appPath);
        capabilities.setCapability("automationName", "XCUITest");
        capabilities.setCapability("autoAcceptAlerts", true);
        capabilities.setCapability("noReset", false);
        capabilities.setCapability("fullReset", false);
        capabilities.setCapability("newCommandTimeout", 300);
        return capabilities;
    }

    public static DesiredCapabilities getIOSCapabilitiesWithBundleId(
            String deviceName, String bundleId) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("bundleId", bundleId);
        capabilities.setCapability("automationName", "XCUITest");
        capabilities.setCapability("autoAcceptAlerts", true);
        capabilities.setCapability("noReset", false);
        return capabilities;
    }

    public static DesiredCapabilities addCapability(
            DesiredCapabilities capabilities, String key, Object value) {
        capabilities.setCapability(key, value);
        return capabilities;
    }
}
