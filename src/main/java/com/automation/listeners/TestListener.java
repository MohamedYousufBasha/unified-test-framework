package com.automation.listeners;

import com.automation.core.DriverManager;
import com.automation.reports.ExtentManager;
import com.automation.utils.ScreenshotUtil;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {
        logger.info("Test Suite Started: {}", context.getName());
        ExtentManager.createInstance();
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Test Suite Finished: {}", context.getName());
        ExtentManager.flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Test Started: {}", result.getMethod().getMethodName());
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription() != null 
            ? result.getMethod().getDescription() 
            : "";
        ExtentManager.createTest(testName, description);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test Passed: {}", result.getMethod().getMethodName());
        ExtentManager.getTest().log(Status.PASS, "Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test Failed: {}", result.getMethod().getMethodName());
        ExtentManager.getTest().log(Status.FAIL, "Test Failed: " + result.getMethod().getMethodName());
        ExtentManager.getTest().log(Status.FAIL, result.getThrowable());
        
        // Capture screenshot on failure
        try {
            String screenshotPath = ScreenshotUtil.captureScreenshot(result.getMethod().getMethodName());
            if (screenshotPath != null) {
                ExtentManager.addScreenshot(screenshotPath);
                logger.info("Screenshot captured: {}", screenshotPath);
            }
        } catch (Exception e) {
            logger.error("Failed to capture screenshot", e);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Test Skipped: {}", result.getMethod().getMethodName());
        ExtentManager.getTest().log(Status.SKIP, "Test Skipped: " + result.getMethod().getMethodName());
        if (result.getThrowable() != null) {
            ExtentManager.getTest().log(Status.SKIP, result.getThrowable());
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.warn("Test Failed but within success percentage: {}", result.getMethod().getMethodName());
    }
}
