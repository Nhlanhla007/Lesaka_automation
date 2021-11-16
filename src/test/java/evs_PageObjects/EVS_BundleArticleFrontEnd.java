package evs_PageObjects;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.aventstack.extentreports.ExtentTest;
import evs_MagentoPageObjects.EVS_BundleArticleCreation;
import utils.Action;
import utils.DataTable2;

public class EVS_BundleArticleFrontEnd {
	WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    EVS_ProductSearch search;
    
    WebElement productName;
    String productPrice;
    
    public EVS_BundleArticleFrontEnd(WebDriver driver, DataTable2 dataTable2){
    	this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2=dataTable2;
        search = new EVS_ProductSearch(driver, dataTable2);
    }
	
    @FindBy(xpath = "//span[@class='base']")
    WebElement fro_BundleName;

    @FindBy(xpath = "//span[@class='sku-code']")
    WebElement fro_BundleSKU;
    
    @FindBy(xpath = "//span[@class='price-container price-final_price tax weee']//span[@class='price']")
    WebElement fro_BundlePrice;
    
    @FindBy(xpath = "//link[@rel='canonical']")
    WebElement fro_BundleURLkey;
    
    @FindBy(xpath = "//span[normalize-space()='Out of stock']")
    WebElement productOfStockErrorMessage;
    
    @FindBy(xpath = "//span[normalize-space()='In stock']")
    WebElement productOfStockMessage;

    @FindBy(xpath = "//*[@class=\"product-item-save-badge\"]//*[@class=\"price\"]")
    WebElement savePrice;
    
    @FindBy(xpath = "//*[@data-price-type = \"finalPrice\"]/span")
    WebElement productFinalPrice;
    
	
    public void navToProdDetailPage(ExtentTest test) throws Exception {
    	String proKeyURL = dataTable2.getValueOnOtherModule("evs_BundleCreation", "Product_URL", 0);
    	String frontURL = "https://staging-everyshop.vaimo.net/";
    	
    	action.navigateToURL(frontURL+proKeyURL);
    	action.waitForPageLoaded(10);
		action.ajaxWait(10, test);
    	
		if(action.isDisplayed(fro_BundleName)) {
			String bundleNameExp = dataTable2.getValueOnOtherModule("evs_BundleCreation", "Bundle_Article_Name", 0);
			action.CompareResult("The Bundle Name Appears Correctly", bundleNameExp, fro_BundleName.getText(), test);
		}else {
			throw new Exception("The Bundle Name Is Incorrect");
		}
		
		if(action.isDisplayed(fro_BundleSKU)) {
			String bundleSKU = dataTable2.getValueOnOtherModule("evs_BundleCreation", "Akeneo_ID", 0);
			action.CompareResult("The Bundle SKU Appears Correctly", bundleSKU, fro_BundleSKU.getText(), test);
		}else {
			throw new Exception("The Bundle SKU Is Incorrect");
		}
		
		if(action.isDisplayed(fro_BundlePrice)) {
			String bundlePrice = String.valueOf(EVS_BundleArticleCreation.totalBundlePrice);
			action.CompareResult("The Bundle Price Appears Correctly", bundlePrice, fro_BundlePrice.getText().replace("R", "").replace(",", "")+"00", test);
		}else {
			throw new Exception("The Bundle Price Is Incorrect");
		}
		
		String prodURL = action.getCurrentURL();
		String actualProURLKey=prodURL.replace("https://staging-everyshop.vaimo.net/", "");
		action.CompareResult("The URL KEY Appears ", proKeyURL, actualProURLKey, test);
		
    }
    
    public void validateBundleItems(ExtentTest test) throws Exception{
    	if (action.elementExistWelcome(productOfStockMessage, 20, "Product In Stock Message Appears ", test)) {
    		//action.CompareResult("Product Out Of Stock", productPrice, productPrice, test);
            action.CompareResult("The Product Bundle", "In stock", driver.findElement(By.xpath("//span[normalize-space()='In stock']")).getText(), test);
        } else {
            throw new Exception("Product In Stock message Is Not Displayed");
        }
    	
    	//click the dropdown
    	//validate the two items are present
    	
    }
   
    public void OutOfStockBundleProduct(ExtentTest test) throws Exception {
    	if (action.elementExistWelcome(productOfStockErrorMessage, 20, "Out Of Stock Pop Up", test)) {
    		//action.CompareResult("Product Out Of Stock", productPrice, productPrice, test);
            action.CompareResult("Product Out Of Stock", "Out of stock", driver.findElement(By.xpath("//span[normalize-space()='Out of stock']")).getText(), test);
        } else {
            throw new Exception("Out Of Stock Pop Up Is Not Displayed");
        }
    }
    
   
    public void searchBundleProduct(ExtentTest test) throws Exception {
    	String bundleName = dataTable2.getValueOnOtherModule("evs_BundleCreation", "Bundle_Article_Name", 0).trim();
    	String akeneoID = dataTable2.getValueOnOtherModule("evs_BundleCreation", "Akeneo_ID", 0);
    	String category = dataTable2.getValueOnOtherModule("evs_BundleCreation", "Bundle_Category_Type", 0);
    	search.loadProductListingPage(category, akeneoID, test);
    	//search.ic_EnterTextToSearchBar(akeneoID, test);
    	boolean productExist = driver.findElements(By.xpath("//*[@class=\"message info empty\"]")).size() >0;
    	if(!(productExist)) {
    		productName = search.ic_FindProduct(test,bundleName);
    		//Compare product name
    		action.CompareResult("Bundle Article Name", bundleName, productName.getText(), test);
    		//Product pricing needs to be done
    		productPrice = productName.findElement(By.xpath("./parent::*/parent::*//*[@class=\"price\"]")).getText().replace("R", "").replace(".", "").replace(",", "");
    		
    		action.CompareResult("Price", String.valueOf(EVS_BundleArticleCreation.totalBundlePrice), productPrice, test);
    		
    	}else {
    		throw new Exception("Product could not be found");
    	}
    
    }
    
    public void searchForBundleArticleUsingSKU(ExtentTest test) throws Exception {
    	String searchType = dataTable2.getValueOnOtherModule("evs_BundleIncreaseQty", "SearchType", 0);
    	String productSKU = dataTable2.getValueOnOtherModule("evs_BundleIncreaseQty", "ProductSKU", 0);
    	search.searchForProductUsingSKU(searchType, productSKU, test);
    	if(action.waitUntilElementIsDisplayed(savePrice, 20)) {
    	dataTable2.setValueOnOtherModule("evs_BundleIncreaseQty", "Initial_Save_Price", savePrice.getText().replace("R", "").replace(",", ""), 0);
    	}else {
    		dataTable2.setValueOnOtherModule("evs_BundleIncreaseQty", "Initial_Save_Price", "0.00", 0);
    	}
    	dataTable2.setValueOnOtherModule("evs_BundleIncreaseQty", "Initial_Product_Price", productFinalPrice.getText().replace("R", "").replace(",", ""), 0);
    	action.explicitWait(5000);
    }        
    
    public void searchForBundleArticleSKUValidateUpdates(ExtentTest test) throws Exception {
    	String searchType = dataTable2.getValueOnOtherModule("evs_BundleIncreaseQty", "SearchType", 0);
    	String productSKU = dataTable2.getValueOnOtherModule("evs_BundleIncreaseQty", "ProductSKU", 0);
    	search.searchForProductUsingSKU(searchType, productSKU, test);
    	
    	//Compare the necessary
    	//Compare the SAVE
    	if(action.waitUntilElementIsDisplayed(savePrice, 20)) {
    	String newSavePrice=savePrice.getText().replace("R", "").replace(",", "");    	
    	String calculatedSavePrice = dataTable2.getValueOnOtherModule("evs_BundleIncreaseQty", "Change_Save_Price", 0);
    	action.CompareResult("Updated SAVE price", calculatedSavePrice, newSavePrice, test);
    	}
    	//Compare Orginal Pricing
    	String newTotalPrice = productFinalPrice.getText().replace("R", "").replace(",", "");
    	String calculatedTotal = dataTable2.getValueOnOtherModule("evs_BundleIncreaseQty", "Change_Price", 0);
    	action.CompareResult("Updated New TOTAL", calculatedTotal, newTotalPrice, test);
    }
    
}
