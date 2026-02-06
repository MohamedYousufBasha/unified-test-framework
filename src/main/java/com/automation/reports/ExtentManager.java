package com.automation.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {
    private static final Logger logger = LogManager.getLogger(ExtentManager.class);
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static String reportPath;

    public static ExtentReports createInstance() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            reportPath = "test-output/ExtentReport_" + timestamp + ".html";
            
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            
            // Configuration
            sparkReporter.config().setDocumentTitle("Automation Test Report");
            sparkReporter.config().setReportName("Test Execution Report");
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
            
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            
            // System Information
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
            extent.setSystemInfo("Environment", "QA");
            
            logger.info("Extent Report initialized at: {}", reportPath);
        }
        return extent;
    }

    public static ExtentTest createTest(String testName, String description) {
        ExtentTest extentTest = extent.createTest(testName, description);
        test.set(extentTest);
        logger.debug("Created test: {}", testName);
        return extentTest;
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void log(Status status, String message) {
        if (test.get() != null) {
            test.get().log(status, message);
        }
    }

    public static void pass(String message) {
        log(Status.PASS, message);
    }

    public static void fail(String message) {
        log(Status.FAIL, message);
    }

    public static void info(String message) {
        log(Status.INFO, message);
    }

    public static void warning(String message) {
        log(Status.WARNING, message);
    }

    public static void skip(String message) {
        log(Status.SKIP, message);
    }

    public static void addScreenshot(String screenshotPath) {
        if (test.get() != null) {
            try {
                test.get().addScreenCaptureFromPath(screenshotPath);
            } catch (Exception e) {
                logger.error("Failed to attach screenshot", e);
            }
        }
    }

    public static void flush() {
        if (extent != null) {
            extent.flush();
            logger.info("Extent Report flushed");
        }
    }

    public static String getReportPath() {
        return reportPath;
    }
}
