package testCases;

import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseClass {
    public static WebDriver driver;
    public Logger logger;
    public Properties prop;

    @BeforeClass(groups = { "Sanity", "Regression", "Master" })
    @Parameters({ "browser" })
    public void setup(@Optional("chrome") String browser) throws IOException, InterruptedException {
        System.out.println("Browser = " + browser);

        // Load config.properties file
        String filePath = System.getProperty("user.dir") + "/src/test/resources/config.properties";
        FileReader fileReader = new FileReader(filePath);
        prop = new Properties();
        prop.load(fileReader);

        String appUrl = prop.getProperty("appUrl").toLowerCase();
        String executionEnv = prop.getProperty("execution_env").toLowerCase();
        String osName = prop.getProperty("os").toLowerCase(); // OS from config

        // Initialize logger
        logger = LogManager.getLogger(this.getClass());
        logger.info("Starting the test suite on OS: " + osName);

        // Browser initialization based on execution environment
        if (executionEnv.equals("remote")) {
            try {
                driver = initializeRemoteDriver(browser, osName);
            } catch (MalformedURLException e) {
                logger.error("Error initializing remote WebDriver", e);
                throw new RuntimeException("Failed to start remote driver", e);
            }
        } else if (executionEnv.equals("local")) {
            driver = initializeLocalDriver(browser);
        } else {
            throw new IllegalArgumentException("Invalid execution environment specified in config: " + executionEnv);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        logger.info("Navigating to the application: " + appUrl);
        Thread.sleep(5000);
        driver.get(appUrl);
    }

    private WebDriver initializeRemoteDriver(String browser, String osName) throws MalformedURLException {
        String remoteUrl = "http://localhost:4444/wd/hub";
        logger.info("Running tests on remote Selenium Grid at: " + remoteUrl);

        Platform platform = getPlatform(osName);

        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setPlatformName(platform.name());
                return new RemoteWebDriver(new URL(remoteUrl), chromeOptions);
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setPlatformName(platform.name());
                return new RemoteWebDriver(new URL(remoteUrl), edgeOptions);
            default:
                throw new IllegalArgumentException("Invalid browser for remote execution: " + browser);
        }
    }

    private WebDriver initializeLocalDriver(String browser) {
        logger.info("Running tests locally on: " + browser);
        switch (browser.toLowerCase()) {
            case "chrome":
                return new ChromeDriver();
            case "edge":
                return new EdgeDriver();
            default:
                throw new IllegalArgumentException("Invalid browser for local execution: " + browser);
        }
    }

    private Platform getPlatform(String osName) {
        switch (osName.toLowerCase()) {
            case "windows":
                return Platform.WINDOWS;
            case "linux":
                return Platform.LINUX;
            case "mac":
            case "macos":
                return Platform.MAC;
            default:
                logger.warn("Unknown OS specified, defaulting to ANY");
                return Platform.ANY;
        }
    }

    @AfterClass(groups = { "Sanity", "Regression", "Master" })
    void tearDown() {
        if (driver != null) {
            logger.info("Test completed, quitting the driver.");
            driver.quit();
        }
    }

    // Random string generation for test data
    public String randomString() {
        return RandomStringUtils.randomAlphabetic(5);
    }
}
