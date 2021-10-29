package spm_MagentoPageObjects;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import ic_MagentoPageObjects.MagentoOrderStatusPage;
import ic_MagentoPageObjects.ic_Magento_Login;
import utils.Action;
import utils.DataTable2;

public class SPM_Magento_RangeValidation {

	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	MagentoOrderStatusPage validateRows;
	int ajaxTimeOutInSeconds = ic_Magento_Login.ajaxTimeOutInSeconds;
	
	@FindBy(name = "product[sku]")
	WebElement productSKU;
	
	@FindBy(name = "product[sjm_bed_finder_type]")
	WebElement beddingType;
	
	@FindBy(name = "product[sjm_bed_size]")
	WebElement bedWidth;
	
	@FindBy(name = "product[sjm_length]")
	WebElement bedLength;
	
	@FindBy(name = "product[name]")
	WebElement productName;
	
	@FindBy(name = "product[price]")
	WebElement productPrice;
		
    @FindBy(xpath = "//span[contains(text(),'Apply Filters')]")
    public WebElement magentoApplyFilterTab;
    
    @FindBy(xpath = "//button[contains(text(),'Filters')]")
    public WebElement magentoFilterTab;
	
    @FindBy(xpath = "//button[contains(text(),'Clear all')]")
    public WebElement clearFilters;
	
    @FindBy(xpath="//span[normalize-space()='Catalog']")
    private WebElement bun_catalog;
    
    @FindBy(xpath="(//span[contains(text(),'Products')])[1]")
    private WebElement bun_product;
    
    @FindBy(name = "sku")
    public WebElement magentoIdSearchField;
    
	public SPM_Magento_RangeValidation(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);		
		this.dataTable2 = dataTable2;
		validateRows = new MagentoOrderStatusPage(driver, dataTable2);
	}

	public void validateRange(ExtentTest test) throws Exception {					
		String frontEndWidth = dataTable2.getValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Width", 0);
		String frontEndLength = dataTable2.getValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Length", 0);
		String exp_articleName = dataTable2.getValueOnOtherModule("SPM_ArticleRanges", "ProductName", 0);
		String exp_articleSKU = dataTable2.getValueOnOtherModule("SPM_ProductSearch", "specificProduct", 0);
		String bedType = dataTable2.getValueOnOtherModule("SPM_ArticleRanges", "Bedding_Type", 0);
		//String price = dataTable2.getValueOnOtherModule("SPM_ArticleRanges", "Price", 0);
		navigateToProductSearch(test);
		searchForOrder(exp_articleSKU, test);
		validateRows.viewOrderDetails(test);
    	//validate the name
		action.CompareResult("Article Name", exp_articleName.trim(), action.getAttribute(productName, "value").trim(), test);
    	//validate the length		
		action.CompareResult("Article Length", frontEndLength, action.getSelectedOptionFromDropDown(bedLength), test);
    	//validate the width
		action.CompareResult("Article Width", frontEndWidth, action.getSelectedOptionFromDropDown(bedWidth), test);
    	//Validate SKU
		action.CompareResult("Article SKU", exp_articleSKU, action.getAttribute(productSKU,"value"), test);
		//Validate BED finder type
		action.CompareResult("Article Bedding Type", bedType, action.getSelectedOptionFromDropDown(beddingType), test);
		//Validate Price
		//action.CompareResult("Article Price", price, action.getAttribute(productPrice, "value").replace("R", "").replace(".", "").replace(",", ""), test);
		
    }
	
	
	public void navigateToProductSearch(ExtentTest test) throws Exception {		
		action.click(bun_catalog, "Click To Catalog", test);
		action.waitUntilElementIsDisplayed(bun_product, 0);
		action.click(bun_product, "Click To Products", test);		
	}
	
	 public void searchForOrder(String idToSearch, ExtentTest test) throws Exception {
	        action.waitForPageLoaded(ajaxTimeOutInSeconds);
	        action.ajaxWait(ajaxTimeOutInSeconds, test);
	        if (action.waitUntilElementIsDisplayed(clearFilters, 10)) {
	            action.javaScriptClick(clearFilters, "Cleared Filters", test);
	            action.ajaxWait(ajaxTimeOutInSeconds, test);
	        }
	        action.javaScriptClick(magentoFilterTab, "Filter tab", test);
	        action.writeText(magentoIdSearchField, idToSearch, "searchId", test);
	        action.explicitWait(3000);
	        action.click(magentoApplyFilterTab, "Apply to filters", test);
	        action.ajaxWait(ajaxTimeOutInSeconds, test);
	        action.explicitWait(5000);	        
	    }
	
	
}