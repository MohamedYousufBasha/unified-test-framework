# Quick Start Guide

## 🚀 Get Started in 5 Minutes

### Step 1: Prerequisites Check
```bash
# Check Java version (should be 11+)
java -version

# Check Maven version (should be 3.6+)
mvn -version

# For mobile testing, check Appium
appium --version
```

### Step 2: Clone and Build
```bash
# Clone the repository
git clone <your-repo-url>
cd unified-test-framework

# Install dependencies
mvn clean install -DskipTests
```

### Step 3: Configure Your Application
Edit `src/test/resources/config.properties`:

**For Web Testing:**
```properties
web.url=https://your-app.com
browser=chrome
```

**For API Testing:**
```properties
api.base.url=https://api.your-app.com
```

**For Mobile Testing:**
```properties
appium.server.url=http://127.0.0.1:4723
android.device.name=emulator-5554
android.app.path=/path/to/your/app.apk
```

### Step 4: Run Your First Test

**Web Test:**
```bash
mvn test -Dtest=LoginTest
```

**API Test:**
```bash
mvn test -Dtest=UserApiTest
```

**All Tests:**
```bash
mvn clean test
```

### Step 5: View Reports
Open the generated report:
```bash
# Navigate to test-output folder
# Open ExtentReport_<timestamp>.html in browser
```

## 📝 Creating Your First Test

### 1. Create a Page Object (Web)

Create file: `src/main/java/com/automation/pages/web/HomePage.java`

```java
package com.automation.pages.web;

import com.automation.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    
    @FindBy(id = "welcome-message")
    private WebElement welcomeMessage;
    
    public HomePage(WebDriver driver) {
        super(driver);
    }
    
    public String getWelcomeMessage() {
        return getText(welcomeMessage);
    }
}
```

### 2. Create a Test

Create file: `src/test/java/com/automation/tests/web/HomeTest.java`

```java
package com.automation.tests.web;

import com.automation.core.DriverManager;
import com.automation.pages.web.HomePage;
import com.automation.reports.ExtentManager;
import com.automation.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomeTest extends BaseTest {
    
    @Test(description = "Verify home page loads successfully")
    public void testHomePage() {
        ExtentManager.info("Starting home page test");
        
        HomePage homePage = new HomePage(DriverManager.getWebDriver());
        String message = homePage.getWelcomeMessage();
        
        ExtentManager.info("Welcome message: " + message);
        Assert.assertNotNull(message, "Welcome message should be displayed");
        
        ExtentManager.pass("Home page test passed");
    }
}
```

### 3. Add to TestNG Suite

Edit `testng.xml`:

```xml
<test name="Home Page Tests">
    <parameter name="platform" value="web"/>
    <parameter name="browser" value="chrome"/>
    <classes>
        <class name="com.automation.tests.web.HomeTest"/>
    </classes>
</test>
```

### 4. Run Your Test
```bash
mvn test -Dtest=HomeTest
```

## 🎯 Common Commands

```bash
# Clean and run all tests
mvn clean test

# Run with specific browser
mvn test -Dbrowser=chrome
mvn test -Dbrowser=firefox
mvn test -Dbrowser=headless-chrome

# Run specific test class
mvn test -Dtest=LoginTest

# Run specific test method
mvn test -Dtest=LoginTest#testSuccessfulLogin

# Run with custom TestNG XML
mvn test -Dsurefire.suiteXmlFiles=custom-suite.xml

# Skip tests during build
mvn clean install -DskipTests

# Run in parallel
mvn test -DparallelMode=methods -DthreadCount=3
```

## 🐛 Troubleshooting Quick Fixes

**Problem: WebDriver not found**
```bash
# WebDriverManager handles this automatically, but if issues persist:
mvn clean install -U
```

**Problem: Appium connection refused**
```bash
# Start Appium server
appium

# Check if server is running on correct port
appium --port 4723
```

**Problem: Tests not found**
```bash
# Recompile
mvn clean compile test-compile
```

**Problem: Reports not generated**
```bash
# Check test-output folder exists
# Verify TestListener is in testng.xml
# Check logs in test-output/logs/
```

## 📚 Next Steps

1. ✅ Customize page objects for your application
2. ✅ Add more test cases
3. ✅ Configure CI/CD pipeline
4. ✅ Set up test data management
5. ✅ Integrate with defect tracking

## 💡 Tips

- **Use meaningful test names** - Makes reports easier to read
- **Add ExtentManager logs** - Better debugging and reporting
- **Keep page objects clean** - One responsibility per method
- **Run locally before CI/CD** - Catch issues early
- **Review reports regularly** - Identify flaky tests

## 📞 Need Help?

- Check `README.md` for detailed documentation
- Review example tests in the framework
- Check logs in `test-output/logs/`
- Review Extent Report for test execution details

Happy Testing! 🎉
