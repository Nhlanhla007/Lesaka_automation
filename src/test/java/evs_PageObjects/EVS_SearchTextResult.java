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

public class EVS_SearchTextResult {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	
	public EVS_SearchTextResult (WebDriver driver,DataTable2 dataTable2) {
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
	
	@FindBy(xpath = "//a[contains(text(),'View all')]")
	WebElement viewAll;
	
	@FindBy(xpath = "//span[contains(text(),\"Search results for\")]")
	WebElement productSearchMessage;
	
	@FindBy(xpath = "//p[@id='toolbar-amount']/span")
	WebElement itemCount;
	
	
	public void searchResultValidation(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber){
		String searchWord =dataTable2.getValueOnCurrentModule("searchWord");
		
		try {
			
			action.clear(evs_SearchBar,"SearchBar");
			action.writeText(evs_SearchBar, searchWord,"Search Bar",test);
			action.clickEle(viewAll, "View All", test);
			action.waitExplicit(4);
			String expMsg="Search results for: '"+searchWord+"'";
			action.CompareResult("Product Suggested", expMsg, productSearchMessage.getText(), test);			
			String productCount=action.getText(itemCount, "Item Count", test);
			int prodCount=Integer.parseInt(productCount);
			logger.info("Product could is: "+prodCount);
			boolean checkProd=prodCount>0;
			action.CompareResult("Suggested Product Count","true", String.valueOf(checkProd), test);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}

}
