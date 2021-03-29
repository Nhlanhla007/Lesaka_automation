package JDGroupPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import Logger.Log;
import utils.Action;
import utils.DataTable2;

public class IC_ProductsSortBy {

	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	Ic_Products productsTypeSearch;
	public IC_ProductsSortBy(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2 = dataTable2;
		productsTypeSearch = new Ic_Products(driver, dataTable2);
	}

	
	@FindBy(id = "sorter")
	WebElement sortByFilter;
	
	@FindBy(css = "a.product-item-link")
	public List<WebElement> ic_products;
	
	@FindBy(xpath = "//span[contains(text(),'Next')]")
	public WebElement ic_ClickNext;

	static Logger logger = Log.getLogData(Action.class.getSimpleName());
	List<String> sortedProducts = new ArrayList<>();
	List<String> unSortedProducts = new ArrayList<>();
	
	

	public void sortByName(ExtentTest test) throws Exception {
		Collections.sort(sortedProducts);
		
		boolean isListSorted = sortedProducts.equals(unSortedProducts);
		action.CompareResult("Sort By Name comparison", "true", String.valueOf(isListSorted), test);
	}
	
	public void sortBy(ExtentTest test) throws Exception {
		String categorySearch = dataTable2.getValueOnCurrentModule("Search Type");		
		productsTypeSearch.loadProductListingPage(categorySearch, " ", test);
		String typeOfValidation = dataTable2.getValueOnCurrentModule("Validation Type");
		if(typeOfValidation.equalsIgnoreCase("Name")) {
			action.selectFromDropDownUsingVisibleText(sortByFilter, "Name", "Select filter by name");
			ic_FindProduct(test);
			sortByName(test);
		}else if(typeOfValidation.equalsIgnoreCase("Price-Low To High")) {
			action.selectFromDropDownUsingVisibleText(sortByFilter, "Name", "Select filter by name");
		}else if(typeOfValidation.equalsIgnoreCase("Price-High To Low")) {
			action.selectFromDropDownUsingVisibleText(sortByFilter, "Name", "Select filter by name");
		}

	}


	public WebElement ic_FindProduct(ExtentTest test) throws IOException {
		boolean status= true;
		while(status) {
			List<WebElement> allProducts = ic_products;
			for(WebElement el: allProducts) {
				sortedProducts.add(el.getText());
				unSortedProducts.add(el.getText());
			}
			WebElement nextButton = returnNext();
			if(nextButton != null) {
				clickNext(test);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					logger.info(e.getMessage());
				}
			}else {
				status = false;
			}
		}
		return null;
	}

	
	public void clickNext(ExtentTest test) {
		action.mouseover(ic_ClickNext, "scroll to element");
		try {
			action.explicitWait(5000);
			action.click(ic_ClickNext, "Clicked Next", test);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
		}
	}
	
	public WebElement returnNext() {
		boolean clickN = action.attributeEnabled(ic_ClickNext);
		if(clickN) {
			WebElement web = ic_ClickNext.findElement(By.xpath(".//parent::*"));
			boolean status = action.attributeValidation(web,"aria-disabled","false",5);
			if(status) {
				return ic_ClickNext.findElement(By.xpath(".//parent::*"));
			}
		}
		return null;
	}

}
