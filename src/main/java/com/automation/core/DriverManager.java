package com.automation.core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.time.Duration;

public class DriverManager {
    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
    private static ThreadLocal<AppiumDriver> mobileDriver = new ThreadLocal<>();

    // Web Driver Methods
    public static WebDriver getWebDriver() {
        return webDriver.get();
    }

    public static void setWebDriver(WebDriver driver) {
        webDriver.set(driver);
    }

    public static void initializeWebDriver(String browser) {
        logger.info("Initializing Web Driver for browser: {}", browser);
        WebDriver driver;

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-notifications");
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                driver.manage().window().maximize();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                driver.manage().window().maximize();
                break;

            case "headless-chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions headlessOptions = new ChromeOptions();
                headlessOptions.addArguments("--headless");
                headlessOptions.addArguments("--window-size=1920,1080");
                headlessOptions.addArguments("--disable-gpu");
                driver = new ChromeDriver(headlessOptions);
                break;

            default:
                logger.error("Invalid browser specified: {}", browser);
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        setWebDriver(driver);
        logger.info("Web Driver initialized successfully");
    }

    // Mobile Driver Methods
    public static AppiumDriver getMobileDriver() {
        return mobileDriver.get();
    }

    public static void setMobileDriver(AppiumDriver driver) {
        mobileDriver.set(driver);
    }

    public static void initializeAndroidDriver(String appiumServerUrl, DesiredCapabilities capabilities) {
        try {
            logger.info("Initializing Android Driver");
            AndroidDriver driver = new AndroidDriver(new URL(appiumServerUrl), capabilities);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            setMobileDriver(driver);
            logger.info("Android Driver initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize Android Driver", e);
            throw new RuntimeException("Android Driver initialization failed", e);
        }
    }

    public static void initializeIOSDriver(String appiumServerUrl, DesiredCapabilities capabilities) {
        try {
            logger.info("Initializing iOS Driver");
            IOSDriver driver = new IOSDriver(new URL(appiumServerUrl), capabilities);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            setMobileDriver(driver);
            logger.info("iOS Driver initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize iOS Driver", e);
            throw new RuntimeException("iOS Driver initialization failed", e);
        }
    }

    // Quit Methods
    public static void quitWebDriver() {
        if (webDriver.get() != null) {
            logger.info("Quitting Web Driver");
            webDriver.get().quit();
            webDriver.remove();
        }
    }

    public static void quitMobileDriver() {
        if (mobileDriver.get() != null) {
            logger.info("Quitting Mobile Driver");
            mobileDriver.get().quit();
            mobileDriver.remove();
        }
    }

    public static void quitAllDrivers() {
        quitWebDriver();
        quitMobileDriver();
    }
}
