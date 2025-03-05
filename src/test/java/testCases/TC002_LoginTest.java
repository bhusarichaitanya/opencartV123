package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.NyAccountPage;

public class TC002_LoginTest extends BaseClass{
	
	@Test(groups={"Sanity","Master"})
	public void verify_login() {
		logger.info("******Starting TC_002_LoginTest *****");
		
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		LoginPage lp = new LoginPage(driver);
		lp.enterEmailAdd("amolpatil@gmail.com");
		lp.enterPasswored("Pass@1234");
		lp.clickLogin();
		
		NyAccountPage nap = new NyAccountPage(driver);
		Assert.assertTrue(nap.verifyMyAccount());
	}
}
