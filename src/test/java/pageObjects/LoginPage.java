package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

	public LoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	

	//input[@id='input-email']
	//input[@id='input-password']
	//button[normalize-space()='Login']
	@FindBy(xpath="//input[@id='input-email']") WebElement txtemailaddress;
	@FindBy(xpath="//input[@id='input-password']") WebElement txtpasswored;
	@FindBy(xpath="//input[@value='Login']") WebElement btnLogin;
	
	public void enterEmailAdd(String emailadd) {
		txtemailaddress.sendKeys(emailadd);
	}
	
	public void enterPasswored(String passwored) {
		txtpasswored.sendKeys(passwored);
	}
	
	public void clickLogin() {
		btnLogin.click();
	}

}
