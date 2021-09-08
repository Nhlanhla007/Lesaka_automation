package ic_PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class IC_RemoveItemsFromCart {

	WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    IC_Cart cart;
    Ic_Products product;
    
    @FindBy(css = "a.go-back")
    private WebElement backButton;
    
    @FindBy(xpath = "//*[@id=\"shopping-cart-table\"]/tbody/tr[1]/td[3]//label/div/button[1]")
    private WebElement decreaseCartButton;
    
    @FindBy(xpath = "(//*[contains(@class, 'qty-action update update-cart-item')])")
    private WebElement updateQuantity;
    
    @FindBy(xpath = "//*[@class=\"modal-inner-wrap\"]")
    public WebElement removeConfirmationPopUp;
    
    @FindBy(xpath = "//*[@class=\"action-primary action-accept\"]")
    public WebElement okButtonRemoveAllItems;
    
    @FindBy(xpath = "//*[@class=\"cart-empty\"]/p[1]")
    public WebElement emptyCartConfrimation;
    
    @FindBy(xpath = "//*[@class=\"item-info\"]/td[1]/div//*[contains(text(),\"TV License Application\")]")
    private WebElement tvLicenseCartAddition;
    
    public IC_RemoveItemsFromCart(WebDriver driver,DataTable2 dataTable2) {
    	this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2 = dataTable2;
        cart = new IC_Cart(driver, dataTable2);
        product = new Ic_Products(driver, dataTable2);
	}
	
    //DECREASE QUANTITY FROM CART ON SPECIFIC PRODUCT FUNCTIONALITY
	/*
	 * public void removeItemFromCart(ExtentTest test) throws Exception {
	 * backButton.click(); action.click(decreaseCartButton, "Decrease Item In Cart",
	 * test); action.click(updateQuantity, "Update Quantity", test);
	 * action.explicitWait(8000); String cartCounter =
	 * cart.itemsInCartCounter(test);
	 * action.CompareResult("Verify Items in cart counter", "2",cartCounter, test);
	 * }
	 */
    
    public void removeItemFromCart(ExtentTest test) throws Exception {
    	String productsToSearch = dataTable2.getValueOnOtherModule("ProductSearch", "specificProduct", 0);
		List<String> theProducts = product.filterProducts(productsToSearch);
		int totalProducts =theProducts.size();
    	navigateBack(test);
    	if(action.waitUntilElementIsDisplayed(decreaseCartButton, 15000)) {
    	action.click(decreaseCartButton, "Decrease Item In Cart",test);
    	}
    	boolean isRemovePopUpDisplayed = action.elementExistWelcome(removeConfirmationPopUp, 4000, "Clear Shopping Cart Pop Up", test);
    	if(isRemovePopUpDisplayed) {	    		
    		action.click(okButtonRemoveAllItems, "Remove selected Article", test);
    		action.explicitWait(4000);
    		boolean cartCountVisablility = driver.findElements(By.xpath("//*[@class = \"counter-number\"]")).size()> 0;
			if(cartCountVisablility) {
				String cartCount = cart.itemsInCartCounter(test);
				if(cartCount.equals("")) {
					cartCount="0";
				}
				boolean countFlag=false;
				if(totalProducts>Integer.parseInt(cartCount)){
					countFlag=true;
					action.CompareResult("Cart count is updated! "+"Original Count of Items: "+totalProducts+" Items in Cart after removing some item: "+cartCount,"true", String.valueOf(countFlag), test);
				}
				else{
					action.CompareResult("Cart count is updated! "+"Original Count of Items: "+totalProducts+" Items in Cart after removing some item: "+cartCount,"true", String.valueOf(countFlag), test);
					throw new Exception("Cart count is updated! "+"Original Count of Items: "+totalProducts+" Items in Cart after removing some item: "+cartCount);
				}
    		//String cartCounter =cart.itemsInCartCounter(test);
    		//action.CompareResult("Verify Items in cart counter", "2", cartCounter, test);
    	}
    	}
    }
    
    public void removeTVLicense(ExtentTest test) throws Exception {
		String removalOfWhichProd = dataTable2.getValueOnCurrentModule("Product_To_Remove");
		if(removalOfWhichProd.equals("TV Product")) {			
			removeItemFromCart(test);			
			//Add empty cart validation
			if(action.waitUntilElementIsDisplayed(emptyCartConfrimation, 15000)) {
	    		String emptyCartVerification = emptyCartConfrimation.getText();
	    		action.CompareResult("Empty Cart Message Verification", "You have no items in your shopping cart.", emptyCartVerification.trim(), test);
	    		}	    		
		}else if(removalOfWhichProd.equals("TV License")){
			navigateBack(test);
			action.waitUntilElementIsDisplayed(tvLicenseCartAddition, 15);
			action.scrollElemetnToCenterOfView(tvLicenseCartAddition, "TV License Cart Item", test);
			WebElement removeItemButton = tvLicenseCartAddition.findElement(By.xpath(".//parent::*/parent::td/parent::tr//*[@class=\"qty-action remove\"]"));
			action.javaScriptClick(removeItemButton, "TV license Remove button", test);
			action.elementExistWelcome(removeConfirmationPopUp, 5,"Remove TV License Addition", test);
			action.click(okButtonRemoveAllItems, "Remove selected Article", test);
			//Add empty cart validation
			if(action.waitUntilElementIsDisplayed(emptyCartConfrimation, 15000)) {
	    		String emptyCartVerification = emptyCartConfrimation.getText();
	    		action.CompareResult("Empty Cart Message Verification", "You have no items in your shopping cart.", emptyCartVerification.trim(), test);
			}
		}
	}
    
	public void navigateBack(ExtentTest test) throws Exception {
		boolean buttonAvail = action.waitUntilElementIsDisplayed(backButton, 15000);
		action.explicitWait(4000);
		if(buttonAvail) {
			action.click(backButton, "Back Navigation To Cart Page", test);
		}
	}
    
}
