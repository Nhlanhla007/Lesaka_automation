package ic_PageObjects;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class IC_Pagination {

	
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	 
	Ic_Products productFunc;
	IC_Cart nextButtonNav;
	
	public IC_Pagination(WebDriver driver,DataTable2 dataTable2){
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;		
		productFunc = new Ic_Products(driver, dataTable2);
		nextButtonNav = new IC_Cart(driver, dataTable2);
	}
	

	@FindBy(xpath = "//span[contains(text(),'Next')]")
	public WebElement ic_ClickNext;
	
	@FindBy(xpath = "//span[contains(text(),'Back')]")
	public WebElement ic_BackButton;
	
	@FindBy(css = "a.product-item-link")
	public List<WebElement> ic_products;
	
	@FindBy(xpath = "//*[@class=\"items pages-items\"]/li")
	public List<WebElement> pageItems;
	
	@FindBy(xpath = "//*[@class=\"item current\"]/strong/span[2]")
	public WebElement currentPage;

	public void paginate(ExtentTest test) throws Exception {
		productFunc.loadProductListingPage("All Products", "", test);
		action.scrollElemetnToCenterOfView(ic_ClickNext,"ic_ClickNext",test);
		
		String allProductsInPage = String.valueOf(ic_products.size());
		if(allProductsInPage != null | allProductsInPage != "") {
//			action.CompareResult("Total Product Count On Page One : " + allProductsInPage, "True", "True", test);
		}
		
		
		
		String currentPageNumber = currentPage.getText();//findCurrentPage(pageItems).getText();		
		if(currentPageNumber != null | currentPageNumber != "") {
			action.CompareResult("Current Page Value On Page One : " + currentPageNumber, "True", "True", test);
		}
		
		WebElement nextButton = returnNext();
		if(nextButton != null) {
		action.click(nextButton, "Next Page Button", test);
		}else {
			throw new Exception("Next Button is not enabled or visible");
		}
		
		action.scrollElemetnToCenterOfView(ic_ClickNext,"ic_ClickNext",test);
		
		String allProductsInPage2 = String.valueOf(ic_products.size());
		if( allProductsInPage2 != null | allProductsInPage2 != "") {
//		action.CompareResult("Total Products In Page Two Match Products In Page One?", allProductsInPage, allProductsInPage2, test);
		}
				
		int expChangedCurrentPage = Integer.parseInt(currentPageNumber) + 1;		
		String newCurrentPage2 =  currentPage.getText();				
		action.CompareResult("Page Two Number Increased by 1 ", String.valueOf(expChangedCurrentPage), newCurrentPage2, test);
		
		WebElement backButton = returnBackButton();
		if(backButton != null) {
		action.click(backButton, "Back Page Button", test);
		}else {
			throw new Exception("Back Button is not enabled or visible");
		}
		
		action.scrollElemetnToCenterOfView(ic_ClickNext,"ic_ClickNext",test);
		
		String allProductsInPage1 = String.valueOf(ic_products.size());
		if(allProductsInPage1 != null | allProductsInPage1 != "") {
			action.CompareResult("Product Total After Back Navigation", allProductsInPage, allProductsInPage1, test);
		}
	
		
		String currentPageAfterBackNav = currentPage.getText();
		if(currentPageAfterBackNav != null | currentPageAfterBackNav != "") {
		int expPageNumberAfterBackNav =  Integer.parseInt(newCurrentPage2) - 1;
		action.CompareResult("Page Number Decreased By 1 After Back Navigation", String.valueOf(expPageNumberAfterBackNav), currentPageAfterBackNav, test);
		}
	}		
	
	//Verifies if the next button is clickable
	public WebElement returnNext() {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		boolean isPresent = driver.findElements(By.xpath( "//span[contains(text(),'Next')]" ) ).size() > 0;
		//boolean clickN = action.attributeEnabled(ic_ClickNext);
		if(isPresent) {
			WebElement web = ic_ClickNext.findElement(By.xpath(".//parent::*"));
			boolean status = action.attributeValidation(web,"aria-disabled","false",5);
			if(status) {
				return ic_ClickNext.findElement(By.xpath(".//parent::*"));
			}
		}
		return null;
	}


	//Verifies if the back button is visible
	public WebElement returnBackButton() {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		boolean isPresent = driver.findElements(By.xpath( "//span[contains(text(),'Back')]" ) ).size() > 0;
		//boolean clickN = action.attributeEnabled(ic_ClickNext);
		if(isPresent) {
			WebElement web = ic_BackButton.findElement(By.xpath(".//parent::*"));
			boolean status = action.attributeValidation(web,"aria-disabled","false",5);
			if(status) {
				return ic_BackButton.findElement(By.xpath(".//parent::*"));
			}
		}
		return null;
	}


	
	
}
