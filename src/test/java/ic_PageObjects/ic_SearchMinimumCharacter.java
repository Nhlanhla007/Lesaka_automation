package ic_PageObjects;

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

public class ic_SearchMinimumCharacter {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	
	public ic_SearchMinimumCharacter (WebDriver driver,DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
	
	}
	
   static Logger logger = Log.getLogData(Action.class.getSimpleName());
	
	@FindBy(xpath = "//*[@name=\"q\"]")
	WebElement icSearchBar;
	
	@FindBy(xpath = "//header/div[2]/div[1]/div[2]/div[1]/form[1]/div[3]/button[1]")
	WebElement icSearchIcon;
	
	@FindBy(xpath = "/html/body/div[4]")
	WebElement icContinueTyping;
	
	
	public void icValidMinimumSearch(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber){
		 String searchCharacter = "a";
		
		try {
			
			action.clear(icSearchBar,"SearchBar");
			action.writeText(icSearchBar, searchCharacter,"SearchBar",test);
			action.CompareResult("Search items ", "please type in at least 3 letters...", icContinueTyping.getText(), test);
			action.explicitWait(5000);
			action.clear(icSearchBar, "clear");
			action.explicitWait(5000);
			action.writeText(icSearchBar, searchCharacter+"b","SearchBar",test);
			action.CompareResult("Search items ", "please type in at least 3 letters...", icContinueTyping.getText(), test);
			//action.click(icSearchIcon, "Click on search", test);
			

			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}

}
