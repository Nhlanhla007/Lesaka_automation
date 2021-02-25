package JDGroupPageObjects;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;

public class IC_Cart {
		
	  WebDriver driver;
	    Action action;

	    public IC_Cart(WebDriver driver) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	        action = new Action(driver);

	    }
	    
	    @FindBy(xpath="/html/body/div[1]/header/div[2]/div/div[3]/div[3]/a")
	    private WebElement iCCartButton;
	    
	    //loop throUGH THIS HERE TO DETERMINE IF THE PRODUCT IS FOUND, IF FOUND VALIDATE QUANTITY AND PRICE
	    @FindBy(xpath="//*[@id=\"mini-cart\"]/li")
	    private List<WebElement> icAllCartProducts;
	    
	    @FindBy(xpath="//*[@id=\"minicart-content-wrapper\"]/div[2]/div[2]/div[1]/div/span/span")
	    private WebElement icSubtotal;
	    
	    @FindBy(xpath="//*[@id=\"top-cart-btn-checkout\"]/span")
	    private WebElement icCCheckout;
	    
	    
	    public void iCcartVerification(ExtentTest test){
	
	    	try {
	    	    action.explicitWait(5000);
				action.click(iCCartButton, "iCCartButton",test);
			} catch (IOException e1) {

			}
	    	//int numberOfProducts = action.getlistSize(icAllCartProducts);
	    	
			/*
			 * int sum = 0; for(int i= 1; i <= elementName.size(); i++){
			 * 
			 * WebElement priceElement =
			 * driver.findElement(By.xpath("//*[@id=\"mini-cart\"]/li["+i+
			 * "]/div/div/div[1]/div[1]/span/span/span/span")); WebElement quantityElement =
			 * driver.findElement(By.xpath("//*[@id=\"mini-cart\"]/li["+i+
			 * "]/div/div/div[1]/div[2]/input")); String priceElementVariable =
			 * action.getText(priceElement, "PriceElement"); String quantityElementVariable
			 * = quantityElement.getAttribute("value"); sum = sum +
			 * (Integer.parseInt(quantityElementVariable) *
			 * Integer.parseInt(priceElementVariable.replace("R", "").replace(",", "")));
			 * 
			 * }
			 */
	    	/*ExtentTest node = test.createNode("Cart Subtotal Check");
	    	try{
	    		String icSubtotalElementVariable =  action.getText(icSubtotal, "icSubtotalElement").replace("R", "").replace(",", "").replace(".00", "");
		    	Assert.assertEquals( Integer.parseInt(icSubtotalElementVariable) , sum);
		    	if(  Integer.parseInt(icSubtotalElementVariable) == sum) {
		    		node.pass("Cart items matches the Cart Subtotal");
		    	} else{
		    		node.fail("Cart Items not matching the Subtotal");
		    	}
		    		
		    	} catch (Exception e){
		    		node.fail("Cart Items not matching the Subtotal");
		    
		    	}	
	    	try {
				action.click(icCCheckout, "ClickingOnCheckutButton", test);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }*/
	       	
	    }  
}
