package spm_PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class SPM_LaunchPortal {

	WebDriver driver;
	Action action;
	DataTable2 dataTable2;

	public SPM_LaunchPortal(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2 = dataTable2;

	}

	public void launchPortal(ExtentTest test) {
		String url = dataTable2.getRowUsingReferenceAndKey("URL", "SUTURLS",dataTable2.getValueOnCurrentModule("urlKey"), "url");
		action.navigateToURL(url);
		action.waitForJStoLoad(120);
	}
	
}
