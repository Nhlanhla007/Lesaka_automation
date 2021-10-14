package evs_MagentoPageObjects;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.aventstack.extentreports.ExtentTest;
import evs_PageObjects.EVS_ProductSearch;
import utils.Action;
import utils.DataTable2;

public class EVS_BundleArticleCreation {
	
	WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    int ajaxTimeOutInSeconds = EVS_Magento_Login.ajaxTimeOutInSeconds;
    EVS_ProductSearch filterItems;
    
    public EVS_BundleArticleCreation(WebDriver driver, DataTable2 dataTable2){
    	this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2=dataTable2;
        filterItems = new EVS_ProductSearch(driver, dataTable2);
    }
	
	//Navigate To Bundle and Add basic details locators
    @FindBy(xpath="//span[normalize-space()='Catalog']")
    private WebElement bun_catalog;
    
    @FindBy(xpath="(//span[contains(text(),'Products')])[1]")
    private WebElement bun_product;
    
    @FindBy(xpath="//button[@class='action-toggle primary add']")
    private WebElement bun_dropDown;
    
    @FindBy(xpath="//span[@title='Bundle Product']")
    private WebElement bun_bundleProduct;
    
    @FindBy(xpath="//div[contains(text(),'Default')]")
    private WebElement bun_valueDefault;
    
    @FindBy(xpath="//*[@name=\"product[name]\"]")
    private WebElement bun_prodname;
    
    @FindBy(xpath="//*[@name=\"product[sku]\"]")
    private WebElement bun_prodSKU;
	
    @FindBy(xpath="//div[@class='admin__actions-switch']/input[@name='product[status]']")
    private WebElement bun_EnableProduct;
    
    @FindBy(xpath="(//span[@class='admin__actions-switch-text'])[2]")
    private WebElement bun_DynamicSKU;
    
    @FindBy(xpath="//*[@id=\"save-button\"]/span")
    private WebElement bun_Save;
    
    @FindBy(xpath="//*[@data-ui-id=\"messages-message-success\"]")
    private WebElement bun_successMessage;
    
    @FindBy(xpath="//button[@id='store-change-button']")
    private WebElement bun_buttonScope;
    
    @FindBy(xpath="//a[normalize-space()='Default Store View']")
    private WebElement bun_defaultStore;
    
    @FindBy(xpath="//span[normalize-space()='OK']")
    private WebElement bun_scopeOKmodal;
    
    @FindBy(xpath="//*[@name='product[tax_class_id]']")
    private WebElement bun_taxclass1;
    
    @FindBy(xpath="//*[@name='product[tax_class_id]']/option[2]")
    private WebElement bun_taxclass;
    
    @FindBy(xpath="//*[@name='product[weight]']")
    private WebElement bun_weight;
    
    @FindBy(xpath="//span[normalize-space()='Search Engine Optimization']")
    private WebElement bun_searchEngine;
    
    @FindBy(xpath="//*[@name='product[meta_title]']")
    private WebElement bun_productURL1;
    
    @FindBy(xpath="//*[@name='product[url_key_create_redirect]']")
    private WebElement bun_productURL;
    
    @FindBy(xpath="//div[@class='admin__actions-switch'])[5]")
    private WebElement bun_DynamicWeight;
    
    @FindBy(xpath="//div[@class='admin__actions-switch']/input[@name='product[is_new]']")
    private WebElement bun_setProdNew;
    
    @FindBy(xpath="//div[contains(@class,'admin__field admin__field-small _disabled')]//label/span[text()=\"Quantity\"]")
    private WebElement bun_QtyLabel;
    
    @FindBy(xpath="//*[@name='product[quantity_and_stock_status][qty]']")
    private WebElement bun_Qty;
    
    @FindBy(xpath="//*[@name='use_default[status]']")
    private WebElement bun_enableProd_defaultV;
    
    @FindBy(xpath="//*[@name='product[quantity_and_stock_status][is_in_stock]']")
    private WebElement bun_stockStatus;
    
    @FindBy(xpath="//button[@class='action-additional']//span[@data-bind='text: title'][normalize-space()='Advanced Inventory']")
    private WebElement bun_advanceInventory;
    
    @FindBy(xpath="//input[@name='product[stock_data][use_config_manage_stock]']")
    private WebElement bun_useConfg;
    
    @FindBy(xpath="//select[@name='product[stock_data][manage_stock]']")
    private WebElement bun_manageStock;
    
    @FindBy(xpath="//select[@name='product[stock_data][manage_stock]']/option[@data-title=\"No\"]")
    private WebElement bun_selectNo;
    
    @FindBy(xpath="(//span[contains(text(),'Done')])[2]")
    private WebElement bun_Done;
    
    @FindBy(xpath="//div[contains(text(),'Select...')]")
    private WebElement bun_Category;
    
    @FindBy(xpath="(//*[contains(text(),'Everyshop')]")
    private WebElement bun_cateEveryshop;
    
    
    //Add product To Bundle and Add SAP data detail locators
    @FindBy(xpath = "//*[@class=\"admin__collapsible-title\"]//span[contains(text(),'Bundle Items')]")
    WebElement bundleItem;
    
    @FindBy(name = "product[shipment_type]")
    WebElement shipBundleType;
	
    @FindBy(xpath = "//button[@data-index='add_button']")
    WebElement addOption;
    
    @FindBy(xpath = "//*[@class=\"data-grid-filters-action-wrap\"]/button")
    WebElement filter;
    
    @FindBy(xpath = "//*[contains(text(),'Clear all')]")
    WebElement clearAll;
    
    @FindBy(name = "sku")
    WebElement skuField;
    
    @FindBy(xpath = "//*[contains(text(),'Apply Filters')]/parent::button")
    WebElement applyFilter;
    
    @FindBy(xpath = "//td[@class=\"data-grid-checkbox-cell\"]/label/input")
    WebElement addProductCheckbox;
    
    @FindBy(xpath = "//*[contains(text(),'Add Selected Products')]/parent::button")
    WebElement addSelectedProducts;
    
    @FindBy(xpath = "//*[contains(text(),'SAP Data')]")
    WebElement sapDataMenuItem;
    
    @FindBy(name = "product[site_specific_article_status]")
    WebElement siteSpecificAtricleStatus;
    
    @FindBy(name = "use_default[site_specific_article_status]")
    WebElement siteSpecifcCheckBox;
    
    @FindBy(name = "product[rough_stock_indicator]")
    WebElement roughStockIndicator;
    
    @FindBy(name ="use_default[rough_stock_indicator]")
    WebElement roughStockIndicatorCheckBox;
    
	public void test() {}
	
	public static int totalBundlePrice; 
	
	public void NavigateToBundle(ExtentTest test) throws Exception {
		action.click(bun_catalog, "Click To Catalog", test);
		action.click(bun_product, "Click To Products", test);
		action.click(bun_dropDown, "Click The Dropdown", test);
		action.click(bun_bundleProduct, "Select Bundle Product", test);
		action.waitForPageLoaded(ajaxTimeOutInSeconds);
		action.ajaxWait(ajaxTimeOutInSeconds, test);
    }
	
	public void SetBasicBundleProductDetails_1(ExtentTest test) throws Exception {
		String attriSet = dataTable2.getValueOnOtherModule("evs_BundleCreation","Attribut_Set" ,0);
		String bundleName = dataTable2.getValueOnOtherModule("evs_BundleCreation", "Bundle_Article_Name", 0);
		String akeneoID = dataTable2.getValueOnOtherModule("evs_BundleCreation", "Akeneo_ID", 0);
		String dynamicSKU = dataTable2.getValueOnOtherModule("evs_BundleCreation", "Dynamic_SKU", 0);
		Random rand = new Random();
		String Bundle_id = String.format("%03d", rand.nextInt(10000));
		
			action.scrollElemetnToCenterOfView(bun_valueDefault, "Scroll to Akeneo ID", test);
		if(attriSet.equalsIgnoreCase("Default")) {
			action.CompareResult("Akeneo ID Is Set To Default", attriSet, bun_valueDefault.getText(), test);
		}else {
			throw new Exception("The attribute is not set to default");
		}
			action.scrollElemetnToCenterOfView(bun_prodname, "Scroll To Bundle Name", test);
			action.writeText(bun_prodname, bundleName +Bundle_id , "Bundle Name", test);
			dataTable2.setValueOnOtherModule("evs_BundleCreation", "Bundle_Article_Name", bundleName +Bundle_id, 0);
			action.clear(bun_prodSKU, akeneoID);
			action.writeText(bun_prodSKU, akeneoID +Bundle_id, "Bundle SKU", test);
			dataTable2.setValueOnOtherModule("evs_BundleCreation", "Akeneo_ID", akeneoID +Bundle_id, 0);
			
			if(dynamicSKU.equalsIgnoreCase("Yes")) {
				action.scrollElemetnToCenterOfView(bun_DynamicSKU, "Scroll To Dynamic SKU", test);
				action.CompareResult("The Dynamic SKU Is Set To Yes", "true", "true", test);
			}else {
				action.click(bun_DynamicSKU, "set the SKU", test);
			}
				action.javaScriptClick(bun_EnableProduct, "set Enable product", test);

			action.click(bun_Save, "Click Save", test);
			action.waitForPageLoaded(ajaxTimeOutInSeconds);
			action.ajaxWait(ajaxTimeOutInSeconds, test);
			String successMessa = action.getText(bun_successMessage, "Product Has Been Saved", test);
			if(successMessa.equalsIgnoreCase("You saved the product.")) {
				action.CompareResult("Product Saved successfully", successMessa, bun_successMessage.getText(), test);
				
			}
			
	}
	
	public void SetTaxBundleProductDetails_1(ExtentTest test) throws Exception {
		String storeScope = dataTable2.getValueOnOtherModule("evs_BundleCreation","Default_Store_View" ,0);
		String dynamicWeight = dataTable2.getValueOnOtherModule("evs_BundleCreation","Dynamic_Weight" ,0);
		String SetProdNew = dataTable2.getValueOnOtherModule("evs_BundleCreation","Set_Product" ,0);
		String stockStatus = dataTable2.getValueOnOtherModule("evs_BundleCreation","Stock" ,0);
		
		if(storeScope.equalsIgnoreCase("Default Store View")){
			action.click(bun_buttonScope, "Set Store Scope View", test);
			action.click(bun_defaultStore, "Click Default for Default Store View", test);
			action.explicitWait(ajaxTimeOutInSeconds);
			action.click(bun_scopeOKmodal, "Click Yes to Change the Scope View", test);
			action.waitForPageLoaded(ajaxTimeOutInSeconds);
			action.ajaxWait(ajaxTimeOutInSeconds, test);
		}else {
			throw new Exception ("The Store scope view could be set");
		}
		
		action.scrollElemetnToCenterOfView(bun_taxclass1, "Scroll To Tax Class", test);
		if(!(action.ic_isEnabled(bun_taxclass1))) {
			action.CompareResult("Tax Class Is Set to Taxable Goods", "Taxable Goods", bun_taxclass.getText(), test);
		}else {
			throw new Exception("Tax class is not disabled");
		}
		
		action.scrollElemetnToCenterOfView(bun_weight, "Scroll To Dyanmic Weight", test);
		if(dynamicWeight.equalsIgnoreCase("Yes")) {
			if(!(action.ic_isEnabled(bun_weight))) {
				action.CompareResult("The Dynamic Weight Is Set To Yes", "true", "true", test);
			}else {
				action.click(bun_DynamicWeight, "Set dynamic weight", test);
			}
		}
		action.scrollElemetnToCenterOfView(bun_setProdNew, "Scroll to Set Product New", test);
		if(SetProdNew.equalsIgnoreCase("yes")) {
			action.javaScriptClick(bun_setProdNew, "Set Product New", test);
		}
		
		action.scrollElemetnToCenterOfView(bun_Qty, "Scroll To Quantity", test);
		if(!(action.ic_isEnabled(bun_Qty))) {
			action.CompareResult("Quantity units is 0 ", "0", "0", test);
		}else {
			throw new Exception("Quantity is not zero");
		}
		
		if(action.ic_isEnabled(bun_stockStatus)) {
			action.CompareResult("Stock Status Is Set To In Stock ", stockStatus, bun_stockStatus.getText(), test);
		}else {
			throw new Exception("Stock Status has be updated to Out of stock ");
		}
		
		searchEngineURL(test);
		AdvanceInventoryBundle(test);
		categoryName(test);
		action.click(bun_Save, "Click Save", test);
		String successMessa = "You saved the product.";
		action.CompareResult("Saved successfully", successMessa, bun_successMessage.getText(), test);
		action.waitForPageLoaded(ajaxTimeOutInSeconds);
		action.ajaxWait(ajaxTimeOutInSeconds, test);
	}
	public void AdvanceInventoryBundle(ExtentTest test) throws Exception {
		String advanceInventory = dataTable2.getValueOnOtherModule("evs_BundleCreation","Advance_inventory" ,0);
		
		if(advanceInventory.equalsIgnoreCase("yes")) {
		action.scrollElemetnToCenterOfView(bun_advanceInventory, "Scroll to Advance inventory", test);
		action.javaScriptClick(bun_advanceInventory, "Click On Advance Inventory", test);
		action.waitForPageLoaded(ajaxTimeOutInSeconds);
		action.ajaxWait(ajaxTimeOutInSeconds, test);
		action.javaScriptClick(bun_useConfg, "Uncheck the Use Config Settings", test);
		action.javaScriptClick(bun_manageStock, "Click On Manage Stock", test);
		action.click(bun_selectNo, "Select No for Manage Stock", test);
		action.javaScriptClick(bun_Done, "Click Done To Save", test);
		action.waitForPageLoaded(ajaxTimeOutInSeconds);
		action.ajaxWait(ajaxTimeOutInSeconds, test);
		}else{
			throw new Exception("Couldn't edit the inventory");
		}
		
	}
	//Need to be edited late to accomodate different 
	public void categoryName(ExtentTest test) throws Exception{
		String categoryName = dataTable2.getValueOnOtherModule("evs_BundleCreation","Category_name(s)" ,0);
		List<String> allOptionCategory = filterItems.filterProducts(categoryName);
		
		action.javaScriptClick(bun_Category, "click category", test);
		WebElement searchfield =  bun_Category.findElement(By.xpath(".//parent::div/following-sibling::*/div/input"));
		WebElement checkBoxCate;
		
		for(int i = 0;i<allOptionCategory.size();i++) {	
			action.writeText(searchfield, allOptionCategory.get(i), "To Enter The Category", test);
			action.ajaxWait(ajaxTimeOutInSeconds, test);
			checkBoxCate = driver.findElement(By.xpath("//*[contains(text(),'"+allOptionCategory.get(i)+"')]"));
			action.click(checkBoxCate, "Select The Checkbox", test);
			action.clear(searchfield, "Clear Search Field");
		}
		
	}
	public void searchEngineURL(ExtentTest test) throws Exception{
		action.scrollElemetnToCenterOfView(bun_searchEngine, "Scroll to Search engine", test);
		action.click(bun_searchEngine, "Scroll to Search engine", test);
		action.scrollElemetnToCenterOfView(bun_productURL, "Scroll to Product Key URL", test);
		String urlKeyName = dataTable2.getValueOnOtherModule("evs_BundleCreation", "Bundle_Article_Name", 0);
		String URL_Key =urlKeyName.toLowerCase().replace(" ", "-").replace("+-", "");
		dataTable2.setValueOnOtherModule("evs_BundleCreation", "Product_URL", URL_Key, 0);
	}

	public void addBundleProducts(ExtentTest test) throws Exception {
		String shipBundleTypeValue = dataTable2.getValueOnOtherModule("evs_BundleCreation", "Ship_Bundle_Method", 0);
		String optionTitles = dataTable2.getValueOnOtherModule("evs_BundleCreation", "Option_Title(s)", 0);
		String storeViewTitles= dataTable2.getValueOnOtherModule("evs_BundleCreation", "Store_View(s)", 0);
		String inputTypes = dataTable2.getValueOnOtherModule("evs_BundleCreation", "Input_Type", 0);
		String productSKUValue = dataTable2.getValueOnOtherModule("evs_BundleCreation", "Product_SKU(S)", 0);
		String productQuantity = dataTable2.getValueOnOtherModule("evs_BundleCreation", "Item_Quantity", 0);		
		List<String> allOptionTitles = filterItems.filterProducts(optionTitles);
		List<String> allInputTypes = filterItems.filterProducts(inputTypes);
		List<String> allStoreViewTitles = filterItems.filterProducts(storeViewTitles);
		List<String> allproductSKUValues = filterItems.filterProducts(productSKUValue);
		List<String> allItemQuantity = filterItems.filterProducts(productQuantity);
		action.scrollElemetnToCenterOfView(bundleItem, "Bundle Items In List", test);
//		action.click(bundleItem, "Bundle Item", test);
		action.dropDownselectbyvisibletext(shipBundleType, shipBundleTypeValue, "Ship Bundle Items", test);		

		//Need a huge loop that goes over all the products that will be added 
		for(int i = 0;i<allOptionTitles.size();i++) {			
			//System.out.println("bundle_options[bundle_options]["+i+"][title]");
			//System.out.println("bundle_options[bundle_options]["+i+"][type]");
			//System.out.println("bundle_options[bundle_options]["+i+"][required]");
			action.scrollElemetnToCenterOfView(addOption, "Add New Bundle Item", test);
			action.click(addOption, "Add New Bundle Item", test);
			//Confirm that the title shows up
			boolean o = driver.findElements(By.name("bundle_options[bundle_options]["+i+"][default_title]")).size() >0;
			
			if(o) {
				//Add option title
				WebElement optionTitle = driver.findElement(By.name("bundle_options[bundle_options]["+i+"][default_title]"));
				action.writeText(optionTitle, allOptionTitles.get(i), "Option Title "+i+1, test);
				
				//Add Store View Title
				WebElement storeViewTitle = driver.findElement(By.name("bundle_options[bundle_options]["+i+"][title]"));
				action.writeText(storeViewTitle,allStoreViewTitles.get(i) , "Store View Title "+i, test);
				
				//Select input type from drop down
				WebElement inputType = driver.findElement(By.name("bundle_options[bundle_options]["+i+"][type]"));
				action.dropDownselectbyvisibletext(inputType, allInputTypes.get(i), allInputTypes.get(i), test);
				
				//Confirm check box is selected
				WebElement requiredStatus = driver.findElement(By.name("bundle_options[bundle_options]["+i+"][required]"));
				if(!(action.checkboxStatus(requiredStatus, "Required Checkbox", test))) {
					action.click(requiredStatus, "Required Checkbox", test);
				}
				
				//Adds products from listing page
				addProductsToOption(i, allproductSKUValues.get(i),allItemQuantity.get(i),test);
											
			}
			action.explicitWait(3000);
		}
		action.ajaxWait(ajaxTimeOutInSeconds, test);
		action.javaScriptClick(bun_Save, "Save", test);
		action.elementExistWelcome(bun_successMessage, ajaxTimeOutInSeconds, "Save pop up success", test);
		
	}
	
	
	public void addProductsToOption(int optionIteration,String productSKU, String quantity, ExtentTest test) throws Exception {
		////Add option below
		WebElement addOptionButton = driver.findElement(By.xpath("//*[@name=\"bundle_options[bundle_options]["+optionIteration+"][type]\"]/parent::*/parent::*/parent::*/parent::*/following-sibling::button"));
		action.click(addOptionButton, "Add Products To Option", test);
		action.ajaxWait(ajaxTimeOutInSeconds, test);
		action.explicitWait(5000);
		
		if(action.waitUntilElementIsDisplayed(clearAll, 8000)) {
			action.click(clearAll, "Clear All Filter", test);
			action.ajaxWait(ajaxTimeOutInSeconds, test);
			action.explicitWait(5000);
		}		 
		action.click(filter, "Filter", test);
		action.writeText(skuField, productSKU , "SKU value", test);
		action.click(applyFilter, "Apply Filters", test);
		
		action.ajaxWait(ajaxTimeOutInSeconds, test);
		action.explicitWait(5000);
		
		if(action.waitUntilElementIsDisplayed(addProductCheckbox, 10000)) {
			action.selectCheckBox(addProductCheckbox, "Add Product To Bundle Checkbox", test);
			totalBundlePrice += Integer.parseInt(driver.findElement(By.xpath("//div[3]/table/tbody/tr/td[8]/div")).getText().replace("R", "").replace(".", "").replace(",", ""));
		}else {
			throw new Exception("No Records Returned");
		}
		
		action.click(addSelectedProducts, "Add Selected Products", test);
		
		action.ajaxWait(ajaxTimeOutInSeconds, test);
		action.explicitWait(3000);
		//Validate quantity shows in the front
		boolean isProductPresent = driver.findElements(By.xpath("//*[contains(text(),'"+productSKU+"')]")).size() >0;
		if(isProductPresent) {
			action.CompareResult("Product has been added to the options list", "true", "true", test);
			WebElement quantityField = driver.findElement(By.name("bundle_options[bundle_options]["+optionIteration+"][bundle_selections][0][selection_qty]"));
			action.writeText(quantityField, quantity, "Quantity", test);
		}else {
			throw new Exception("Product was not added to Bundle Product List");
		}
		
	}
	
	public void sapData(ExtentTest test) throws Exception{
		String siteSpecifcStatus = dataTable2.getValueOnOtherModule("evs_BundleCreation", "Site_Article_Status", 0);
		String roughStockIndicatorValue = dataTable2.getValueOnOtherModule("evs_BundleCreation", "Site_Article_Status", 0);
		
		action.scrollElemetnToCenterOfView(sapDataMenuItem, "SAP Menu Item", test);		
		action.click(sapDataMenuItem, "SAP DATA", test);
		action.scrollElemetnToCenterOfView(siteSpecificAtricleStatus, "Site Specific Article Status", test);
		if (siteSpecifcCheckBox.isSelected()) {
			action.click(siteSpecifcCheckBox, "Uncheck Default Checkbox", test);
			action.selectFromDropDownUsingVisibleText(siteSpecificAtricleStatus, siteSpecifcStatus,"Site Specific Article Status");
		} else {
			action.selectFromDropDownUsingVisibleText(siteSpecificAtricleStatus, siteSpecifcStatus,"Site Specific Article Status");
		}
		
		action.scrollElemetnToCenterOfView(roughStockIndicator, "Rough Stock Indicator", test);
		if(roughStockIndicatorCheckBox.isSelected()) {
			action.click(roughStockIndicatorCheckBox, "Uncheck Default Checkbox", test);
			action.writeText(roughStockIndicator, roughStockIndicatorValue, "Rough Stock Indicator", test);
		}else {
			action.writeText(roughStockIndicator, roughStockIndicatorValue, "Rough Stock Indicator", test);
		}
		
		action.ajaxWait(ajaxTimeOutInSeconds, test);
		action.scrollElemetnToCenterOfView(bun_EnableProduct, "Enable The Bundle Product", test);
		if(action.isDisplayed(bun_EnableProduct)) {
			action.javaScriptClick(bun_enableProd_defaultV, "Uncheck Use Default Value", test);
			action.javaScriptClick(bun_EnableProduct, "Enable The Product", test);
		}
		action.javaScriptClick(bun_Save, "Save", test);
		action.ajaxWait(ajaxTimeOutInSeconds, test);
		action.waitForPageLoaded(ajaxTimeOutInSeconds);
		action.explicitWait(4000);
		action.elementExistWelcome(bun_successMessage, ajaxTimeOutInSeconds, "Save pop up success", test);
	}
	
}
