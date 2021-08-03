package evs_PageObjects;

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

public class EVS_RemoveItemsFromCart {

	WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    EVS_Cart cart;
	EVS_ProductSearch product;
    
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
    
    public EVS_RemoveItemsFromCart(WebDriver driver,DataTable2 dataTable2) {
    	this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2 = dataTable2;
        cart = new EVS_Cart(driver, dataTable2);
		product = new EVS_ProductSearch(driver, dataTable2);
	}



    public void removeItemFromCart(ExtentTest test) throws Exception {
		String productsToSearch = dataTable2.getValueOnOtherModule("evs_ProductSearch", "specificProduct", 0);
		List<String> theProducts = product.filterProducts(productsToSearch);
		int totalProducts =theProducts.size();
    	boolean buttonAvail = action.waitUntilElementIsDisplayed(backButton, 15000);
		action.explicitWait(4000);
		if(buttonAvail) {
			backButton.click();
		}
		if(action.waitUntilElementIsDisplayed(decreaseCartButton, 15000)) {
			action.click(decreaseCartButton, "Remove Item in Cart", test);
			}
		boolean isRemovePopUpDisplayed = action.elementExistWelcome(removeConfirmationPopUp, 5,"Clear Shopping Cart Pop Up", test);
		if (isRemovePopUpDisplayed) {
			action.click(okButtonRemoveAllItems, "Remove selected Article", test);
			action.explicitWait(10000);
//			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			boolean cartCountVisablility = driver.findElements(By.xpath("//*[@class = \"counter-number\"]")).size()> 0;
			if(cartCountVisablility) {
				String cartCount = cart.itemsInCartCounter(test);
				boolean countFlag=false;
				if(totalProducts>Integer.parseInt(cartCount)){
					countFlag=true;
					action.CompareResult("Cart count is updated! "+"Original Count of Items: "+totalProducts+" Items in Cart after removing some item: "+cartCount,"true", String.valueOf(countFlag), test);
				}
				else{
					action.CompareResult("Cart count is updated! "+"Original Count of Items: "+totalProducts+" Items in Cart after removing some item: "+cartCount,"true", String.valueOf(countFlag), test);
					throw new Exception("Cart count is updated! "+"Original Count of Items: "+totalProducts+" Items in Cart after removing some item: "+cartCount);
				}
			}

		}
	}

}
