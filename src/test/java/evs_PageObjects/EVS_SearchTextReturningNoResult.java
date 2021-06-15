package evs_PageObjects;

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
	
	@FindBy(xpath = "//div[contains(text(),\"We're sorry, no results found for\")]")
	WebElement icNoProducts;
	
	public void evs_DoesNotExtistSearch(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) {
		String searchWord =dataTable2.getValueOnCurrentModule("searchWord");

		try {
			
			action.clear(evs_SearchBar,"SearchBar");
			action.writeText(evs_SearchBar, searchWord,"SearchBar",test);
			action.CompareResult("Search items ", "Please try another search term...", evs_InvalidMessage.getText(), test);
			action.click(evs_SearchIcon, "Click on search", test);
			action.CompareResult("No products were found", "We're sorry, no results found for \""+searchWord+"\"", icNoProducts.getText(), test);

			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}

}
