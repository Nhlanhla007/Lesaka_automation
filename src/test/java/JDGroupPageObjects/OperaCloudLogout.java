package JDGroupPageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.qameta.allure.Step;
import utils.Action;

public class OperaCloudLogout {
	WebDriver driver;
	Action action;

	public OperaCloudLogout(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
	}

	/****************
	 * Page Objects *
	 ****************/

	@FindBy(xpath = "//a[@title='Show User Options']")
	WebElement UserOptions;
	
	@FindBy(xpath = "//div[contains(@id,'oc_pg_lgout')]//a")
	WebElement logOut;
	
	@FindBy(id = "username")
	WebElement UName;

	/****************
	 * Page Methods *
	 ****************/

	@Step("Do Logout")
	public void logOut() {
//		action.javaScriptClick(UserOptions, "UserOptions");
//		action.javaScriptClick(logOut, "Log Out");
		action.waitFluent(UName);
	}
}
