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

public class ic_ResetPasswordEmailLink {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	
	public ic_ResetPasswordEmailLink(WebDriver driver, DataTable2 dataTable2){
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
	}
	@FindBy(xpath="//*[@id=\":31\"]/div[1]/div[1]/table/tbody/tr/td/table/tbody/tr[2]/td/p/a")
	private WebElement resetLink;

	@FindBy(xpath="//input[@ name=\"password\"]")
	private WebElement ic_newPassword;

	@FindBy(xpath="//input[@ name=\"password_confirmation\"]")
	private WebElement ic_newConfirmPassword;

	@FindBy(xpath="//*[@id=\"form-validate\"]/div/div/button/span")
	private WebElement ic_SetNewPassword;

	@FindBy(xpath="/html/body/div[1]/header/div[3]/div[2]/div/div/div/text()")
	private WebElement ic_PasswordMessage;

	@FindBy(xpath="/html/body/div[1]/header/div[3]/div[2]/div/div/div/text()")
	private WebElement ic_LinkExpired;


	public void clickLinkOnGmail( HashMap<String, ArrayList<String>> input,ExtentTest test, int rowNumber)throws IOException {
		action.mouseover(resetLink,"Move to the link");
		action.click(resetLink,"Click the link on emails", test);
		String passLink = action.getText(resetLink,"get the link");
		dataTable2.setValueOnCurrentModule("PasswordResetlink",passLink);
		action.explicitWait(10000);
	}

	public void resetNewPassword(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber) throws IOException{
		String Password = dataTable2.getValueOnCurrentModule("newPassword");
		String ConfirmPassWord = dataTable2.getValueOnCurrentModule("confirmNewPass");
		String linkToReset = dataTable2.getValueOnCurrentModule("PasswordResetlink");
		//navigate
		action.navigateToURL(linkToReset);
		action.explicitWait(5000);
		action.writeText(ic_newPassword,Password,"Write new password",test);
		action.explicitWait(2000);
		action.writeText(ic_newConfirmPassword, ConfirmPassWord, "Confirm password", test );
		action.explicitWait(5000);
		action.click(ic_SetNewPassword,"click Set New Password", test);
		action.explicitWait(5000);
		action.CompareResult("validate password was changed","You updated your password.", ic_PasswordMessage.getText(),test);

	}

		public void clickUsedResetLink(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber) throws IOException{
			String usedLinkReset = dataTable2.getValueOnCurrentModule("PasswordResetlink");

			action.navigateToURL(usedLinkReset);
			action.explicitWait(5000);
			action.CompareResult("The link is has expired","Your password reset link has expired.",ic_LinkExpired.getText(),test);


		}

}
