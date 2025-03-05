package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.NyAccountPage;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {

	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class,groups={"datadriven","Master"}) // DataProvider from diffrent class
	public void verify_loginDDT(String userName, String passwored, String exp) {
		logger.info("******Starting TC003_LoginDDT *****");
		try {
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();

		LoginPage lp = new LoginPage(driver);
		lp.enterEmailAdd(userName);
		lp.enterPasswored(passwored);
		lp.clickLogin();

		NyAccountPage nap = new NyAccountPage(driver);
		boolean targetPage = nap.verifyMyAccount();

		if (exp.equalsIgnoreCase("Valid")) {
			if (targetPage == true) {
				nap.clicklogout();
				Assert.assertTrue(true);
			}
			else {
				Assert.assertTrue(false);
			}
		}
		
		if (exp.equalsIgnoreCase("Invalid")) {
			if (targetPage == true) {
				nap.clicklogout();
				Assert.assertTrue(false);
			}
			else {
				Assert.assertTrue(true);
			}
		}		
	
		}catch(Exception e) {
			Assert.assertTrue(false);
		}
		logger.info("******Finished TC003_LoginDDT *****");
}
	
	}
