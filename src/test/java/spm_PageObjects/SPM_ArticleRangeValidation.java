package spm_PageObjects;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import ic_MagentoPageObjects.MagentoOrderStatusPage;
import ic_MagentoPageObjects.ic_Magento_Login;
import ic_PageObjects.Ic_Products;
import spm_MagentoPageObjects.SPM_Magento_RangeValidation;
import utils.Action;
import utils.DataTable2;

public class SPM_ArticleRangeValidation {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	MagentoOrderStatusPage validateRows;
	Ic_Products filterItems;
	SPM_LaunchPortal launchPortal;
	SPM_ProductSearch productSearch;
	ic_Magento_Login loginMagento;
	SPM_Magento_RangeValidation magentoRangeValidation;
	
	public SPM_ArticleRangeValidation(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);		
		this.dataTable2 = dataTable2;
		validateRows = new MagentoOrderStatusPage(driver, dataTable2);
		filterItems = new Ic_Products(driver, dataTable2);
		launchPortal = new SPM_LaunchPortal(driver, dataTable2);
		productSearch = new SPM_ProductSearch(driver, dataTable2);
		loginMagento = new ic_Magento_Login(driver, dataTable2);
		magentoRangeValidation = new SPM_Magento_RangeValidation(driver, dataTable2);
	}
	
	@FindBy(xpath = "//input[@id='username']")
	WebElement Magento_Username;
	
	@FindBy(xpath = "//input[@id='login']")
	WebElement Magento_Password;
	@FindBy(xpath = "//span[contains(text(),'Sign in')]")
	WebElement Magento_SigninBtn;

	public void launchPortal(ExtentTest test) {
		String url = dataTable2.getRowUsingReferenceAndKey("URL", "SUTURLS",dataTable2.getValueOnCurrentModule("urlKey"), "url");
		action.navigateToURL(url);
		action.waitForJStoLoad(120);
	}
	
	public void rangeValidation(ExtentTest test) throws Exception {
		String sku = dataTable2.getValueOnOtherModule("SPM_ProductSearch", "specificProduct", 0);
		String prodName = dataTable2.getValueOnOtherModule("SPM_ArticleRanges", "ProductName", 0);
		List<String> allSKU =  filterItems.filterProducts(sku);
		List<String> allProds = filterItems.filterProducts(prodName);
		String magentourlKey = dataTable2.getValueOnCurrentModule("magentoUrlKey");
		String magentoUsername = dataTable2.getRowUsingReferenceAndKey("URL","SUTURLS",dataTable2.getValueOnCurrentModule("magentoUrlKey"),"username");
		String magentoPassword = dataTable2.getRowUsingReferenceAndKey("URL","SUTURLS",dataTable2.getValueOnCurrentModule("magentoUrlKey"),"password");
		ExtentTest ab = null;
		for(int i = 0; i<allSKU.size() ; i++) {
		try {
     			//Call Launch portal
				//launchPortal(test);
				ab = test.createNode("Article " + allProds.get(i)+"("+allSKU.get(i)+")");
				launchPortal.launchPortal(ab);
				//action.navigateToURL("https://staging-sleepmasters-m23.vaimo.net/");
			//Call search for product
				productSearch.rangeSearchNew(ab, allProds.get(i),allSKU.get(i));
			//Validate range
				productSearch.rangeValidation(ab,allProds.get(i));
			//Login Magento
				//loginMagento.LoginToMagento(test, "", "");
				action.navigateToURL(dataTable2.getRowUsingReferenceAndKey("URL","SUTURLS",magentourlKey,"url"));
				if(i<1) {
				action.writeText(Magento_Username, magentoUsername, "Username field", ab);
				action.writeText(Magento_Password,magentoPassword , "Password field", ab);
				action.clickEle(Magento_SigninBtn, "click Magento_SigninBtn", ab);
				action.waitForJStoLoad(10);
				action.ajaxWait(20,ab);
				action.explicitWait(10000);
				}
				action.ajaxWait(20,ab);				
			//Validate details in magento
				magentoRangeValidation.validateRange(ab,allSKU.get(i),allProds.get(i));
		} catch (Exception e) {
			//Print error in report and continue;
			ab.createNode("Exception").fail(e.getMessage(),MediaEntityBuilder.createScreenCaptureFromBase64String(action.takeScreenShotAsBase64()).build());
			continue;
		}
		}
	}	
}
