package testCases;

import java.time.Duration;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;

public class TC001_AccountRegistrationTest extends BaseClass {

	@Test(groups="Regression")
	void verify_account_registration() throws InterruptedException {
		try {
			logger.info("Starting TC001_AccountRegistrationTest ****");
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickRegister();
			logger.info("Clicked on Register link");
			AccountRegistrationPage arp = new AccountRegistrationPage(driver);
			arp.setFirstName(randomString().toUpperCase());
			arp.setLastName(randomString().toUpperCase());
			arp.setEmail(randomString() + "@gmail.com");
			arp.setPasswored("Pass@1234");
			arp.clickPrivacyPolicy();
			arp.clickContinue();
			logger.info("Validate expected message .... ");
			String msg = arp.getConfirmMsg();
			Assert.assertEquals(msg, "Your Account Has Been Created!");
		} catch (Exception e) {
			logger.error("Test Failed");
			logger.debug("Debug logs");
			e.printStackTrace(); // Prints the error to the console
			logger.error("Test failed due to exception: ", e);
			Assert.fail();
		}

		logger.info("Test case finished");
	}

}
