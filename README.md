# Unified Test Automation Framework

A comprehensive test automation framework supporting **Web**, **Mobile (iOS & Android)**, and **API** testing with Java, Selenium, Appium, TestNG, and Extent Reports.

## 🚀 Features

- **Multi-Platform Support**: Web, Android, iOS, and REST API testing
- **Page Object Model (POM)**: Using Page Factory pattern
- **Parallel Execution**: TestNG parallel test execution
- **Rich Reporting**: Extent Reports with screenshots
- **CI/CD Integration**: Jenkins pipeline ready
- **Logging**: Log4j2 for comprehensive logging
- **Configuration Management**: Properties-based configuration
- **Screenshot on Failure**: Automatic screenshot capture
- **WebDriver Management**: Automatic driver management with WebDriverManager

## 📁 Project Structure

```
unified-test-framework/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/automation/
│   │           ├── api/           # API test base classes
│   │           ├── core/          # Core framework (DriverManager, BasePage)
│   │           ├── listeners/     # TestNG listeners
│   │           ├── pages/         # Page Object classes
│   │           │   ├── web/       # Web pages
│   │           │   └── mobile/    # Mobile pages
│   │           ├── reports/       # Extent Report manager
│   │           └── utils/         # Utility classes
│   └── test/
│       ├── java/
│       │   └── com/automation/tests/
│       │       ├── api/          # API tests
│       │       ├── mobile/       # Mobile tests
│       │       └── web/          # Web tests
│       └── resources/
│           ├── config.properties # Configuration file
│           └── log4j2.xml        # Logging configuration
├── testng.xml                    # TestNG suite configuration
├── Jenkinsfile                   # Jenkins CI/CD pipeline
├── pom.xml                       # Maven dependencies
└── README.md
```

## 🛠️ Tech Stack

| Component | Technology |
|-----------|-----------|
| Language | Java 11 |
| Build Tool | Maven |
| Web Automation | Selenium 4 |
| Mobile Automation | Appium 9 |
| API Testing | REST Assured |
| Test Framework | TestNG |
| Reporting | Extent Reports 5 |
| Logging | Log4j2 |
| CI/CD | Jenkins |
| Driver Management | WebDriverManager |

## 📋 Prerequisites

### For Web Testing
- Java JDK 11 or higher
- Maven 3.6+
- Chrome/Firefox/Edge browser

### For Mobile Testing
- Node.js and npm
- Appium Server 2.x
- Android SDK (for Android testing)
- Xcode (for iOS testing on macOS)

### For API Testing
- No additional prerequisites

## ⚙️ Setup Instructions

### 1. Clone the Repository
```bash
git clone <repository-url>
cd unified-test-framework
```

### 2. Install Dependencies
```bash
mvn clean install -DskipTests
```

### 3. Configure Properties
Edit `src/test/resources/config.properties`:

```properties
# Web Configuration
web.url=https://your-application-url.com
browser=chrome

# Mobile Configuration
appium.server.url=http://127.0.0.1:4723
android.device.name=your-device-name
android.app.path=/path/to/your/app.apk
ios.device.name=iPhone 14
ios.app.path=/path/to/your/app.app

# API Configuration
api.base.url=https://api.example.com
api.key=your-api-key
```

### 4. Setup Appium (For Mobile Testing)

#### Install Appium
```bash
npm install -g appium@next
appium driver install uiautomator2  # For Android
appium driver install xcuitest      # For iOS
```

#### Start Appium Server
```bash
appium
```

## 🏃 Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Suite
```bash
mvn test -Dsurefire.suiteXmlFiles=testng.xml
```

### Run with Specific Browser
```bash
mvn test -Dbrowser=chrome
mvn test -Dbrowser=firefox
mvn test -Dbrowser=headless-chrome
```

### Run Specific Test Class
```bash
mvn test -Dtest=LoginTest
```

### Run API Tests Only
```bash
mvn test -Dtest=com.automation.tests.api.*
```

## 📊 Reports

### Extent Reports
After test execution, open the report:
```
test-output/ExtentReport_<timestamp>.html
```

### TestNG Reports
```
test-output/index.html
```

### Screenshots
Failed test screenshots are stored in:
```
test-output/screenshots/
```

### Logs
Application logs are stored in:
```
test-output/logs/automation.log
```

## 🔄 Jenkins Integration

### 1. Create Jenkins Job
- Create a new Pipeline job
- Configure SCM to point to your repository
- Set Pipeline script from SCM

### 2. Configure Parameters
The Jenkinsfile includes these parameters:
- `BROWSER`: chrome, firefox, edge, headless-chrome
- `PLATFORM`: web, android, ios, api, all
- `TEST_SUITE`: testng.xml (or your custom suite)

### 3. Run Pipeline
The pipeline will:
1. Checkout code
2. Compile project
3. Run tests
4. Generate reports
5. Archive artifacts
6. Send email notifications

## 📝 Writing Tests

### Web Test Example
```java
public class LoginTest extends BaseTest {
    @Test
    public void testLogin() {
        LoginPage loginPage = new LoginPage(DriverManager.getWebDriver());
        loginPage.login("user@example.com", "password");
        // Add assertions
    }
}
```

### Mobile Test Example
```java
public class MobileLoginTest extends BaseTest {
    @Test
    public void testMobileLogin() {
        MobileLoginPage loginPage = new MobileLoginPage(DriverManager.getMobileDriver());
        loginPage.login("user@example.com", "password");
        // Add assertions
    }
}
```

### API Test Example
```java
public class UserApiTest extends BaseTest {
    private ApiBase apiBase = new ApiBase();
    
    @Test
    public void testGetUser() {
        Response response = apiBase.get("/users/1");
        apiBase.verifyStatusCode(response, 200);
        // Add assertions
    }
}
```

## 📚 Page Object Model

### Web Page Object
```java
public class LoginPage extends BasePage {
    @FindBy(id = "username")
    private WebElement usernameField;
    
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    public void enterUsername(String username) {
        type(usernameField, username);
    }
}
```

### Mobile Page Object
```java
public class MobileLoginPage extends BasePage {
    @AndroidFindBy(id = "com.app:id/username")
    private WebElement usernameField;
    
    public MobileLoginPage(AppiumDriver driver) {
        super(driver);
    }
}
```

## 🎯 Best Practices

1. **Maintain Page Objects**: Keep page objects clean and focused
2. **Use Meaningful Names**: Name tests and methods descriptively
3. **Add Assertions**: Always verify expected outcomes
4. **Use Extent Reports**: Log test steps for better reporting
5. **Handle Waits**: Use explicit waits over Thread.sleep()
6. **Keep Tests Independent**: Each test should run independently
7. **Use TestNG Groups**: Organize tests using TestNG groups
8. **Parameterize Tests**: Use TestNG parameters for data-driven testing

## 🔧 Customization

### Add New Page Object
1. Create class in `src/main/java/com/automation/pages/`
2. Extend `BasePage`
3. Add elements using `@FindBy` annotations
4. Implement page methods

### Add New Test
1. Create test class in `src/test/java/com/automation/tests/`
2. Extend `BaseTest`
3. Add `@Test` methods
4. Use Extent Reports for logging

### Add to TestNG Suite
Edit `testng.xml` and add your test class:
```xml
<test name="Your Test">
    <classes>
        <class name="com.automation.tests.YourTest"/>
    </classes>
</test>
```

## 🐛 Troubleshooting

### WebDriver Issues
- Ensure WebDriverManager is downloading drivers correctly
- Check browser version compatibility
- Try headless mode if display issues occur

### Appium Issues
- Verify Appium server is running
- Check device/emulator is connected: `adb devices`
- Verify app path in config.properties
- Check capabilities in BaseTest

### Build Issues
- Run `mvn clean install`
- Check Java version: `java -version`
- Verify Maven installation: `mvn -version`

## 📧 Contact

For questions or issues, please contact the QA team.

## 📄 License

This project is licensed under the MIT License.
