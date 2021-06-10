package evs_PageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EVS_RemoveFromcart {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
		public EVS_RemoveFromcart(WebDriver driver, DataTable2 dataTable2) {
			this.driver = driver;
			PageFactory.initElements(driver, this);
			action = new Action(driver);
			this.dataTable2=dataTable2;
		}
		@FindBy(xpath = "//div[@data-block='minicart']")
	    private WebElement CartButton;
		
		@FindBy(xpath = "//span[@class='counter-number']")
	    private WebElement CartQuantity;
		
		@FindBy(xpath = "//span[contains(text(),'View / Edit Cart')]")
	    private WebElement ViewandEditcart;
		
		@FindBy(xpath = "//div[@class='page-title-wrapper']/h1/span")
	    private WebElement ShoppingCart_Hdr;
		
		@FindBy(xpath = "//div[@class='custom-clear']")
	    private WebElement Remove_all;
		
		@FindBy(xpath = "//body//aside[@role='dialog']//footer[@class='modal-footer']/button[@type='button']//span[text()='OK']")
	    private WebElement ConfirmDelete_Ok;
		
		@FindBy(xpath = "//p[contains(text(),'You have no items in your shopping cart.')]")
	    private WebElement ConfirmAllDelete_Message;
		
				
		public void Clear_miniCart(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException{
			int waitTime = 5;
			int checkQty;
			checkQty = retriveCartQuantity(waitTime,test);
			if(checkQty>0){
				NavigateToviewEditcart(waitTime, test);
				removeAllproductfromcart(waitTime, test);
				checkQty = retriveCartQuantity(waitTime,test);
			}
			if(checkQty==0){
				action.scrollToElement(CartButton, "ic_CartButton");
				action.CompareResult("All the products are removed from minicart", 0+" Quantity in cart", checkQty+" Quantity in cart", test);
			}else{
				action.scrollToElement(CartButton, "ic_CartButton");
				action.CompareResult("All the products are removed from minicart", 0+" Quantity in cart", checkQty+" Quantity in cart", test);
			}
		}
		public int retriveCartQuantity(int Timeout, ExtentTest test) throws IOException {
			action.explicitWait(Timeout);
			String quantInCart=CartQuantity.getText();
			
			if(quantInCart.equalsIgnoreCase("")) {
				quantInCart = "0";
				
			}
			int Quantity = Integer.parseInt(quantInCart);
			
			
			System.out.println("Cart Quantity is: "+Quantity);
			return Quantity;
		}
		public void NavigateToviewEditcart(int TimeOut, ExtentTest test) throws IOException{
			String ExpPageHdr = "Shopping Cart";
			action.click(CartButton, "Click mini cart icon", test);
			action.explicitWait(2000);
			
			if(action.isElementPresent(ViewandEditcart)){
				action.click(ViewandEditcart, "View and Edit cart", test);
				action.explicitWait(5000);
				if(action.isElementPresent(ShoppingCart_Hdr)){
					String shoppingcartPg = action.getText(ShoppingCart_Hdr, "ShoppingCart Header",test);
					if(shoppingcartPg.equalsIgnoreCase("ExpPageHdr")){
						action.CompareResult("Navigate to shopping cart page from mini cart", ExpPageHdr, shoppingcartPg, test);
					}else{
						action.CompareResult("Navigate to shopping cart page from mini cart", ExpPageHdr, shoppingcartPg, test);
					}
				}
			}
		}
		public void removeAllproductfromcart(int TimeOut,ExtentTest test) throws IOException{
			action.clickEle(Remove_all, "Remove all from cart", test);
			action.explicitWait(2000);
			action.clickEle(ConfirmDelete_Ok, "ConfirmDelete Ok btn", test);
			action.waitForPageLoaded(TimeOut);
			if(action.isElementPresent(ConfirmAllDelete_Message)){
				action.CompareResult("Products in the wishlist has been removed", "true", "true", test);
			}else{
				action.CompareResult("Products in the wishlist has not been removed", "true", "true", test);
			}
		}
}
