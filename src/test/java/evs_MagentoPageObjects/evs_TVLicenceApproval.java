package evs_MagentoPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import evs_PageObjects.EVS_ProductSearch;
import utils.Action;
import utils.DataTable2;

public class evs_TVLicenceApproval {

	WebDriver driver;
	Action action;
	DataTable2 dataTable2;

	public evs_TVLicenceApproval(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2 = dataTable2;
	}
	
}
