package JDGroupPageObjects;

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

public class ic_SearchTextReturningNoResult {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	
	public ic_SearchTextReturningNoResult (WebDriver driver,DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
	
	}
	static Logger logger = Log.getLogData(Action.class.getSimpleName());
	
	@FindBy(xpath = "//input[@id='search']")
	WebElement icSearchBar;
	
	@FindBy(xpath = "//header/div[2]/div[1]/div[2]/div[1]/form[1]/div[3]/button[1]")
	WebElement icSearchIcon;
	
	@FindBy(xpath = "//*[@id=\"klevu-no-records\"]/div/div/div[1]")
	WebElement icInvalidMessage;
	
	@FindBy(xpath = "//div[contains(text(),\"We can't find products matching the selection.\")]")
	WebElement icNoProducts;
	
	public void ic_DoesNotExtistSearch(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) {
		String searchWord =dataTable2.getValueOnCurrentModule("searchWord");

		try {
			
			action.clear(icSearchBar,"SearchBar");
			action.writeText(icSearchBar, searchWord,"SearchBar",test);
			action.CompareResult("Search items ", "We are sorry we can't seem to find results for \""+searchWord+"\"", icInvalidMessage.getText(), test);
			action.click(icSearchIcon, "Click on search", test);
			action.CompareResult("No products were found", "We can't find products matching the selection.", icNoProducts.getText(), test);

			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}

}
