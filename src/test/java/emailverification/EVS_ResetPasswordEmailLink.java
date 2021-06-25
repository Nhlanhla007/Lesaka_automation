package emailverification;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Action;
import utils.DataTable2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EVS_ResetPasswordEmailLink {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;

	public EVS_ResetPasswordEmailLink(WebDriver driver, DataTable2 dataTable2){
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
	}
	@FindBy(linkText="Set a New Password")
	private WebElement resetLink;

	@FindBy(xpath="//input[@ name=\"password\"]")
	private WebElement evs_newPassword;

	@FindBy(xpath="//input[@ name=\"password_confirmation\"]")
	private WebElement evs_newConfirmPassword;

	@FindBy(xpath="//*[@id=\"form-validate\"]/div/div/button/span")
	private WebElement evs_SetNewPassword;

	@FindBy(xpath="/html/body/div[1]/header/div[3]/div[2]/div/div/div/text()")
	private WebElement evs_PasswordMessage;

	@FindBy(xpath="/html/body/div[1]/header/div[3]/div[2]/div/div/div/text()")
	private WebElement evs_LinkExpired;


	public void clickLinkOnGmail(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber)throws IOException {
//		ele
		action.mouseover(resetLink,"Move to the link");
//		action.click(resetLink,"Click the link on emails", test);
//		action.getAttribute(resetLink,"data-saferedirecturl");
		String passLink = action.getAttribute(resetLink,"data-saferedirecturl");
		dataTable2.setValueOnCurrentModule("PasswordResetlink",passLink);
		action.explicitWait(10000);
	}

	public void resetNewPassword(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber) throws IOException{
		String Password = dataTable2.getValueOnCurrentModule("newPassword");
		String linkToReset = dataTable2.getValueOnCurrentModule("PasswordResetlink");
		//navigate
		action.navigateToURL(linkToReset);
		action.explicitWait(5000);
		action.writeText(evs_newPassword,Password,"Write new password",test);
		action.explicitWait(2000);
		action.writeText(evs_newConfirmPassword, Password, "Confirm password", test );
		action.explicitWait(5000);
		action.click(evs_SetNewPassword,"click Set New Password", test);
		action.explicitWait(5000);
		action.CompareResult("validate password was changed","You updated your password.", evs_PasswordMessage.getText(),test);

	}

	public void clickUsedResetLink(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber) throws IOException{
		String usedLinkReset = dataTable2.getValueOnCurrentModule("PasswordResetlink");
		action.navigateToURL(usedLinkReset);
		action.explicitWait(5000);
		action.CompareResult("The link is has expired","Your password reset link has expired.",evs_LinkExpired.getText(),test);
	}

}
