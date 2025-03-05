package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class NyAccountPage extends BasePage{

	public NyAccountPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(xpath="//h2[normalize-space()='My Account']") WebElement myAccount;
	@FindBy(xpath="//a[@class='list-group-item'][normalize-space()='Logout']") WebElement lnklogout;
	
	public boolean verifyMyAccount() {
		
		try {
		return myAccount.isDisplayed();
		}catch (Exception e) {
			return false;
		}
	}
	
	public void clicklogout() {
		
		Actions act = new Actions(driver);
		act.moveToElement(lnklogout).build().perform();		
		lnklogout.click();
	}

}
