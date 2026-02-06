package com.automation.utils;

import com.automation.core.DriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {
    private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class);
    private static final String SCREENSHOT_DIR = "test-output/screenshots/";

    public static String captureScreenshot(String testName) {
        try {
            // Create screenshots directory if it doesn't exist
            File directory = new File(SCREENSHOT_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String fileName = testName + "_" + timestamp + ".png";
            String filePath = SCREENSHOT_DIR + fileName;

            // Try web driver first
            if (DriverManager.getWebDriver() != null) {
                TakesScreenshot screenshot = (TakesScreenshot) DriverManager.getWebDriver();
                File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
                File destFile = new File(filePath);
                FileUtils.copyFile(srcFile, destFile);
                logger.info("Screenshot captured: {}", filePath);
                return filePath;
            }
            // Try mobile driver
            else if (DriverManager.getMobileDriver() != null) {
                TakesScreenshot screenshot = (TakesScreenshot) DriverManager.getMobileDriver();
                File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
                File destFile = new File(filePath);
                FileUtils.copyFile(srcFile, destFile);
                logger.info("Screenshot captured: {}", filePath);
                return filePath;
            }
        } catch (Exception e) {
            logger.error("Failed to capture screenshot for test: {}", testName, e);
        }
        return null;
    }

    public static String captureScreenshotAsBase64(String testName) {
        try {
            if (DriverManager.getWebDriver() != null) {
                TakesScreenshot screenshot = (TakesScreenshot) DriverManager.getWebDriver();
                return screenshot.getScreenshotAs(OutputType.BASE64);
            } else if (DriverManager.getMobileDriver() != null) {
                TakesScreenshot screenshot = (TakesScreenshot) DriverManager.getMobileDriver();
                return screenshot.getScreenshotAs(OutputType.BASE64);
            }
        } catch (Exception e) {
            logger.error("Failed to capture screenshot as base64", e);
        }
        return null;
    }
}
