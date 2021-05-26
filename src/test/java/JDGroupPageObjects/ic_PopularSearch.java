package JDGroupPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class ic_PopularSearch{
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	public ic_PopularSearch(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
	}
	@FindBy(xpath = "//input[@id='search']")
	private WebElement icSearchBar;
	
	
	@FindBy(xpath = "/html/body/div[5]/div[1]/div[1]/div[1]/strong[contains(text(),'Popular Searches:')]")
	private WebElement PopularSearch;
	
	@FindBy(xpath = "/html/body/div[5]/div[1]/div[1]/div[1]/strong[contains(text(),'Popular Searches:')]/../div/a")
	List<WebElement> PopularSearchlist;
	public void VerifyPopularSearch(ExtentTest test,int rowNumber) throws IOException{
		action.click(icSearchBar, "ic SearchBar", test);
		boolean chkPopsearch = checkForPopularSearch(test,"Popular Searches:");
		action.CompareResult(" popular search is successfully displayed", "true", String.valueOf(chkPopsearch), test);
	}
	public boolean checkForPopularSearch(ExtentTest test, String Expdata) throws IOException{
		boolean checkFlag = false;
		String Alldata=null;
		if(action.elementExists(PopularSearch, 11)){
			String actPuopularSearch = action.getText(PopularSearch, "PopularSearch",test);
			action.CompareResult(" popular search is populated ", Expdata.toUpperCase().trim(), actPuopularSearch.toUpperCase().trim(), test);
			List<WebElement> allData  = PopularSearchlist;
			for(WebElement el: allData){
				String eachitem = el.getText().trim();
				Alldata = Alldata+eachitem+", ";
			}
			System.out.println("Alldata :"+Alldata);
			checkFlag = true;
		}
		if(checkFlag){
			action.CompareResult("popular search List off catagory :"+Alldata.trim(), "true", String.valueOf(checkFlag), test);
		}
		return checkFlag;
		
	}
}
