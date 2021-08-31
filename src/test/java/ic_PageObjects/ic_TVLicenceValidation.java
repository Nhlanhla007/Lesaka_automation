package ic_PageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class ic_TVLicenceValidation {

    WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    public ic_TVLicenceValidation(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2=dataTable2;
}
    
	@FindBy(xpath = "//*[contains(text(),'Items in Cart')]")
	WebElement viewAllItemsInCart;
	
	@FindBy(xpath = "//*[@class=\"totals sub\"]/td/span")
	WebElement subtotalAfterLicense;
	
	@FindBy(xpath = "//*[@class=\"product-item-details\"]//strong")
	List<WebElement> productsAddedToCart;
	
    
    
    public void cartValidationWithLicense(ExtentTest test) throws Exception {
		boolean flag = true;
		//NEED TO CREATE A SHEET
		String licenseAddition = dataTable2.getValueOnCurrentModule("License Addition");
		String tvLicenseAmount = dataTable2.getValueOnCurrentModule("TV License Amount");
		// Map<String,List<String>> allProductsInCart = EVS_ProductSearch.productData;
		String allProductsInCart = dataTable2.getValueOnOtherModule("ProductSearch", "CartTotal", 0);
		String subtotal = action.getText(subtotalAfterLicense, "Cart Subtotal", test).replace("R", "").replace(",", "");
		if (licenseAddition.equalsIgnoreCase("yes")) {
			// for (Map.Entry selectedProducts : allProductsInCart.entrySet()) {
			double subTotalAfterTVLicense = Double.parseDouble(allProductsInCart) + Double.parseDouble(tvLicenseAmount);
			action.CompareResult("TV License R265 has been added to cart", subtotal,String.valueOf(subTotalAfterTVLicense), test);

			action.waitUntilElementIsDisplayed(viewAllItemsInCart, 10000);
			action.explicitWait(5000);
			action.click(viewAllItemsInCart, "View All Items In Cart", test);

			for (WebElement tvLicenseProd : productsAddedToCart) {
				String productInCart = tvLicenseProd.getText();
				if (productInCart.equalsIgnoreCase("TV License Application")) {
					action.scrollElemetnToCenterOfView(tvLicenseProd, "TV License Product", test);
					flag = false;
					action.CompareResult("Is TV License Application added to cart?", "True", "True", test);
					
					//Add SKU tv license value to the cart either new SKU or existing SKU
					List<String> tvLicenseProdSKU = new ArrayList<>();
					tvLicenseProdSKU.add("1");
					tvLicenseProdSKU.add("000000000010011406");
					tvLicenseProdSKU.add("R" + tvLicenseAmount );
					Ic_Products.productData.put("TV License", tvLicenseProdSKU);					
					break;
				}
			}
			if (flag) {
				action.CompareResult("Is TV License Application added to cart?", "True", "False", test);
			}
		} else {
			action.CompareResult("Subtotal amount is the same as the cart total", allProductsInCart, subtotal, test);
		}

	}
	




}
