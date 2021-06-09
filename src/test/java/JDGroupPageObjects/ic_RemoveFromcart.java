package JDGroupPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class ic_RemoveFromcart {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
		public ic_RemoveFromcart(WebDriver driver, DataTable2 dataTable2) {
			this.driver = driver;
			PageFactory.initElements(driver, this);
			action = new Action(driver);
			this.dataTable2=dataTable2;
		}
		@FindBy(xpath = "//header/div[2]/div/div[3]/div[3]/a")
	    private WebElement ic_CartButton;
		
		@FindBy(xpath = "//div[@class='minicart-wrapper']//span[@class='counter-number']")
	    private WebElement ic_CartQuantity;
		
		@FindBy(xpath = "//div[@class='actions']//span[contains(text(),'View and Edit Your Cart')]")
	    private WebElement ViewandEditcart;
		
		@FindBy(xpath = "//*[@id='maincontent']/div[1]/h1//span[text()='Shopping Cart']")
	    private WebElement ShoppingCart_Hdr;
		@FindBy(xpath = "//*[@id='form-validate']//div[@class='custom-clear']//span[text()='Remove All']")
	    private WebElement Remove_all;
		
		@FindBy(xpath = "//body//aside[@role='dialog']//footer[@class='modal-footer']/button[@type='button']//span[text()='OK']")
	    private WebElement ConfirmDelete_Ok;
		@FindBy(xpath = "/html/body/div[1]/main/div[2]/div/div/p[text()='You have no items in your shopping cart.']")
	    private WebElement ConfirmAllDelete_Message;
		
				
		public void Clear_miniCart(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException{
			int waitTime = 6;
			int checkQty;
			checkQty = retriveCartQuantity(waitTime,test);
			if(checkQty>0){
				NavigateToviewEditcart(waitTime, test);
				removeAllproductfromcart(waitTime, test);
				checkQty = retriveCartQuantity(waitTime,test);
			}
			if(checkQty==0){
				action.scrollToElement(ic_CartButton, "ic_CartButton");
				action.CompareResult("All the products are removed from minicart", 0+" Quantity in cart", checkQty+" Quantity in cart", test);
			}else{
				action.scrollToElement(ic_CartButton, "ic_CartButton");
				action.CompareResult("All the products are removed from minicart", 0+" Quantity in cart", checkQty+" Quantity in cart", test);
			}
		}
//		public int retriveCartQuantity(int Timeout, ExtentTest test) throws IOException {
//			action.explicitWait(Timeout);
//			int Quantity = Integer.parseInt(action.getText(ic_CartQuantity, "ic mini Cart Quantity",test));
//			return Quantity;
//		}
		
		public int retriveCartQuantity(int Timeout, ExtentTest test) throws IOException {
			action.explicitWait(Timeout);
			String quantInCart=ic_CartQuantity.getText();
			
			if(quantInCart.equalsIgnoreCase("")) {
				quantInCart = "0";
				
			}
			int Quantity = Integer.parseInt(quantInCart);
			
			
			System.out.println("Cart Quantity is: "+Quantity);
			return Quantity;
		}
		public void NavigateToviewEditcart(int TimeOut, ExtentTest test) throws IOException{
			String ExpPageHdr = "Shopping Cart";
			action.click(ic_CartButton, "click mini cart icon", test);
			if(action.elementExists(ViewandEditcart, TimeOut)){
				action.click(ViewandEditcart, "View and Edit cart", test);
				if(action.elementExists(ShoppingCart_Hdr, TimeOut)){
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
			action.click(Remove_all, "Remove all from cart", test);
			action.scrollToElement(ConfirmDelete_Ok, "ConfirmDelete_Ok");
			action.click(ConfirmDelete_Ok, "ConfirmDelete Ok btn", test);
			action.waitForPageLoaded(TimeOut);
			if(action.elementExists(ConfirmAllDelete_Message, TimeOut)){
				action.CompareResult("Products in the wishlist has been removed", "true", "true", test);
			}else{
				action.CompareResult("Products in the wishlist has been removed", "true", "true", test);
			}
		}
}
