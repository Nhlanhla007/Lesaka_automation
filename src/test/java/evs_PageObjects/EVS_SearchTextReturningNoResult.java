package evs_PageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import Logger.Log;
import utils.Action;
import utils.DataTable2;

public class EVS_SearchTextReturningNoResult {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	
	public EVS_SearchTextReturningNoResult (WebDriver driver,DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
	
	}
	static Logger logger = Log.getLogData(Action.class.getSimpleName());
	
	@FindBy(xpath = "//input[@id='search']")
	private WebElement evs_SearchBar;
	
	@FindBy(xpath = "//button[@class='action search primary']")
	WebElement evs_SearchIcon;
	
	@FindBy(xpath = "//div[contains(text(),'Please try another search term...')]")
	WebElement evs_InvalidMessage;
	
	@FindBy(xpath = "//div[contains(text(),\"We can't find products matching the selection.\")]")
	WebElement noProductsMsg;

	
	public void evs_DoesNotExtistSearch(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException {
		String searchWord =dataTable2.getValueOnCurrentModule("searchWord");
		action.clear(evs_SearchBar,"SearchBar");
		action.writeText(evs_SearchBar, searchWord,"SearchBar",test);
		action.explicitWait(2000);
		action.CompareResult("Search items ", "Please try another search term...", evs_InvalidMessage.getText(), test);
		action.click(evs_SearchIcon, "Click on search", test);
		action.explicitWait(5000);
		action.CompareResult("Search Result", "We can't find products matching the selection.",noProductsMsg.getText(), test);

	}

}
