package pageObjects;

import org.apache.poi.ss.formula.ThreeDEval;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage {

	public AccountRegistrationPage(WebDriver driver) {
		super(driver);

	}

	// input[@id='input-firstname']
	// input[@id='input-lastname']
	// input[@id='input-email']
	// input[@id='input-password']
	// input[@id='input-newsletter']
	// input[@name='agree']
	// button[normalize-space()='Continue']

	@FindBy(xpath = "//input[@id='input-firstname']")
	WebElement txtFirstName;
	@FindBy(xpath = "//input[@id='input-lastname']")
	WebElement txtLastName;
	@FindBy(xpath = "//input[@id='input-email']")
	WebElement txtemail;
	@FindBy(xpath = "//input[@id='input-password']")
	WebElement txtpasswored;
	@FindBy(xpath = "//input[@name='agree']")
	WebElement privacyPolicy;
	@FindBy(xpath = "//button[@type='submit']")
	WebElement btnContinue;
	
	@FindBy(xpath = "//div[@id=\"content\"]/h1")
	WebElement msgConfirm;

	public void setFirstName(String firstName) {
		txtFirstName.sendKeys(firstName);
	}

	public void setLastName(String lastName) {
		txtLastName.sendKeys(lastName);
	}

	public void setEmail(String email) {
		txtemail.sendKeys(email);
	}

	public void setPasswored(String pass) {
		txtpasswored.sendKeys(pass);
	}

	public void clickContinue() {
		btnContinue.click();
	}

	public void MoveToElement() {
		Actions act = new Actions(driver);
		act.moveToElement(privacyPolicy).build().perform();
	}

	public void clickPrivacyPolicy() {
		MoveToElement();
		privacyPolicy.click();
	}
	
	public String getConfirmMsg() throws InterruptedException {
		Thread.sleep(1000);
		return msgConfirm.getText();
	}

}
