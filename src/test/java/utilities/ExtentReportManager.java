package utilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import testCases.BaseClass;

public class ExtentReportManager implements ITestListener {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static String browserName = "Unknown";
    private static String browserVersion = "Unknown";
    private static String osName = System.getProperty("os.name");
    private static String osVersion = System.getProperty("os.version");

    // Initialize Extent Reports
    public static ExtentReports getReportInstance() {
        if (extent == null) {
            String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport.html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

            sparkReporter.config().setDocumentTitle("Automation Test Report");
            sparkReporter.config().setReportName("Functional Testing Results");
            
         // Fetch browser details if running in Selenium Grid
            fetchBrowserAndOSDetails();

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Tester", "Chaitanya Bhusari");
            extent.setSystemInfo("Application", "opencart");
            extent.setSystemInfo("OS", osName + " (" + osVersion + ")");
            extent.setSystemInfo("Browser", browserName + " v" + browserVersion);
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        }
        return extent;
    }

    // Listener Methods
    @Override
    public void onStart(ITestContext context) {
        getReportInstance();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
        test.get().log(Status.INFO, "Test Started: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "Test Failed: " + result.getMethod().getMethodName());
        test.get().log(Status.FAIL, result.getThrowable());

        // Capture screenshot
        Object testClass = result.getInstance();
        WebDriver driver = ((BaseClass) testClass).driver;  // Assuming BaseClass has WebDriver instance

        String screenshotPath = captureScreenshot(driver, result.getMethod().getMethodName());
        test.get().addScreenCaptureFromPath(screenshotPath);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test Skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    // Capture Screenshot
    public static String captureScreenshot(WebDriver driver, String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testName + "_" + timestamp + ".png";
        
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File(screenshotPath);

        try {
            FileHandler.copy(srcFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return screenshotPath;
    }
    
    private static void fetchBrowserAndOSDetails() {
        try {
            // Using ChromeOptions to fetch capabilities (you can change this to other browser options)
            ChromeOptions options = new ChromeOptions();
            options.setCapability("browserName", "chrome"); // Or other browser depending on the test

            // Initialize the remote driver to connect to the Selenium Grid
            RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
            
            // Fetch the capabilities once connected to the Grid
            Capabilities caps = remoteDriver.getCapabilities();
            browserName = caps.getBrowserName();
            browserVersion = caps.getBrowserVersion();
            osName = caps.getPlatformName().name(); // Platform Name (OS)

            remoteDriver.quit();  // Always close the remote driver to free up resources
        } catch (MalformedURLException e) {
            System.out.println("Error: Invalid URL for Selenium Grid.");
        } catch (Exception e) {
            System.out.println("Error fetching browser details: " + e.getMessage());
        }
    }
}
