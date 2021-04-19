package JDGroupPageObjects;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class ic_CompareProducts {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	Ic_Products icproducts;
	
	public ic_CompareProducts(WebDriver driver, DataTable2 dataTable2){
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
	}
	
	//span[contains(text(),'Add to Compare')]
	@FindBy(xpath = "//span[contains(text(),'Add to Compare')]")
	public WebElement ic_compareBtn;
	
	
	@FindBy(xpath = "//a[contains(text(),'comparison list')]")
	public WebElement ic_compareLink;
	
	@FindBy(xpath = "//*[@id=\"product-comparison\"]/tbody[1]/tr/td[1]/a/span/span/img")
	public WebElement ic_firstCompareProduct;
	
	@FindBy(xpath = "//*[@id=\"product-comparison\"]/tbody[1]/tr/td[2]/a/span/span/img")
	public WebElement ic_SecondCompareProduct;
	
	@FindBy(xpath = "//body[1]/div[1]/header[1]/div[2]/div[1]/div[2]/div[3]/nav[1]/ul[1]/li[1]/a[1]/span[1]")
	public WebElement ic_ProductsCompared;
	
	@FindBy(xpath = "//span[contains(text(),'Clear All')]")
	public WebElement ic_ComparedClearAll;
	
	@FindBy(xpath = "//span[contains(text(),'OK')]")
	public WebElement ic_ComparedClearOK;
	
	@FindBy(xpath = "//div[contains(text(),'You cleared the comparison list.')]")
	public WebElement ic_ComparisonList;
	
	@FindBy(xpath = "//*[@id=\"maincontent\"]/div/div[1]/div[3]/div[2]/div[2]/a")
	public WebElement ic_CompareDisabled;
	
	
	public void compareProductsFunctionality(ExtentTest test, WebElement el) throws IOException{
		
		action.click(el, "Click on Product", test);
		action.explicitWait(5000);
		action.click(ic_compareBtn, "click Add to Compare", test);
		
	}
	
	public void validateAddedProductsCompare(ExtentTest test, WebElement el) throws IOException{
		 //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		// boolean isPresent = driver.findElements(By.cssSelector("a.go-back")).size() > 0;
		action.click(ic_compareLink, "Go to Compare page", test);
		//verify element exist
		action.elementExists(ic_firstCompareProduct, 100);
		action.explicitWait(1000);
		action.elementExists(ic_SecondCompareProduct, 100);
		action.explicitWait(1000);
	}
	
	public void clearAllProduct (ExtentTest test, WebElement el) throws IOException{
		
		action.click(ic_ProductsCompared, "Click on All Products", test);
		action.explicitWait(1000);
		action.click(ic_ComparedClearAll, "Clear All", test);
		action.explicitWait(1000);
		action.click(ic_ComparedClearOK, "Click OK", test);
		action.explicitWait(1000);
		action.CompareResult("Compare list cleared", "You cleared the comparison list.", ic_ComparisonList.getText(), test);
		action.explicitWait(1000);
		action.getScreenShot("ic_CompareDisabled");
		
	}
	
	

}
