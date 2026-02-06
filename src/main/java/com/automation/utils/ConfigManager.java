package com.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static final Logger logger = LogManager.getLogger(ConfigManager.class);
    private static Properties properties;
    private static final String CONFIG_FILE = "src/test/resources/config.properties";

    static {
        loadProperties();
    }

    private static void loadProperties() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
            logger.info("Configuration loaded successfully from {}", CONFIG_FILE);
        } catch (IOException e) {
            logger.error("Failed to load configuration file: {}", CONFIG_FILE, e);
            throw new RuntimeException("Configuration file not found", e);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property '{}' not found in configuration", key);
        }
        return value;
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    // Web Configuration
    public static String getWebUrl() {
        return getProperty("web.url");
    }

    public static String getBrowser() {
        return getProperty("browser", "chrome");
    }

    // Mobile Configuration
    public static String getAppiumServerUrl() {
        return getProperty("appium.server.url", "http://127.0.0.1:4723");
    }

    public static String getAndroidAppPath() {
        return getProperty("android.app.path");
    }

    public static String getIOSAppPath() {
        return getProperty("ios.app.path");
    }

    public static String getAndroidDeviceName() {
        return getProperty("android.device.name");
    }

    public static String getIOSDeviceName() {
        return getProperty("ios.device.name");
    }

    // API Configuration
    public static String getApiBaseUrl() {
        return getProperty("api.base.url");
    }

    public static String getApiKey() {
        return getProperty("api.key");
    }

    // Test Configuration
    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait", "10"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait", "15"));
    }

    public static boolean takeScreenshotOnFailure() {
        return Boolean.parseBoolean(getProperty("screenshot.on.failure", "true"));
    }
}
