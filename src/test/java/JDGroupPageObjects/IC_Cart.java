package JDGroupPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import Logger.Log;
import utils.Action;
import utils.DataTable2;

public class IC_Cart {
		
	  WebDriver driver;
	    Action action;
	    DataTable2 dataTable2;
	    public IC_Cart(WebDriver driver, DataTable2 dataTable2) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	        action = new Action(driver);
	        this.dataTable2 = dataTable2;
	    }
	    static Logger logger = Log.getLogData(Action.class.getSimpleName());
	    
	    //@FindBy(xpath="/html/body/div[1]/header/div[2]/div/div[3]/div[3]/a")
	    @FindBy(xpath = "//header/div[2]/div/div[3]/div[3]/a")
	    private WebElement iCCartButton;
	    
	    //loop throUGH THIS HERE TO DETERMINE IF THE PRODUCT IS FOUND, IF FOUND VALIDATE QUANTITY AND PRICE
	    @FindBy(xpath="//*[@id=\"mini-cart\"]/li")
	    private List<WebElement> icAllCartProducts;
	    
	    @FindBy(xpath="//*[@id=\"minicart-content-wrapper\"]/div[2]/div[2]/div[1]/div/span/span")
	    private WebElement icSubtotal;
	    
	    @FindBy(xpath="//*[@id=\"top-cart-btn-checkout\"]/span")
	    private WebElement icCCheckout;
	    
	    @FindBy(xpath = "//*[@class=\"counter-number\"]")
	    private WebElement cartCounterIcon;
	    
	    @FindBy(xpath = "//*[@class=\"action viewcart\"]")
	    private WebElement viewAndEditCart;
	    
	    @FindBy(xpath = "//*[@class=\"custom-clear\"]")
	    private WebElement removeAllCartItems;
		
	    @FindBy(xpath = "//*[@class=\"modal-inner-wrap\"]")
	    public WebElement removeConfirmationPopUp;
	    
	    @FindBy(xpath = "//*[@class=\"action-primary action-accept\"]")
	    public WebElement okButtonRemoveAllItems;
	    
	    @FindBy(xpath = "//*[@id=\"maincontent\"]/div[2]//p[1]")
	    public WebElement emptyCartConfrimation;
	    
	    public void navigateToCart(ExtentTest test) {
	    	try {
				action.clickEle(iCCartButton, "IC cart button", test);
			} catch (Exception e) {
				// TODO: handle exception
			}
	    }
	    
	    public static int sum;
		  public void iCcartVerification2(Map<String, List<String>> products,ExtentTest test) {
			  //Find all elements from the list
			  navigateToCart(test);
			  try {
				for(WebElement productsInCart : icAllCartProducts) {
					  String nameOfProduct = productsInCart.findElement(By.xpath(".//strong/a")).getText();
					  String price = productsInCart.findElement(By.xpath(".//span/span/span/span")).getText();					  
					  WebElement quantityTag = productsInCart.findElement(By.xpath(".//div[2]/input"));
					  String quantity = action.getAttribute(quantityTag, "data-item-qty");
					  sum += (Integer.parseInt(quantity)*Integer.parseInt(price.replace("R", "").replace(",", "")));
					  for(Map.Entry selectedProducts : products.entrySet()) {
						  //@SuppressWarnings("unchecked")
						List<String> data = (List<String>)selectedProducts.getValue();
						if(selectedProducts.getKey().equals(nameOfProduct)) {
						  action.CompareResult("Name : " + nameOfProduct , (String)selectedProducts.getKey(), nameOfProduct, test);
						  action.CompareResult("Price : " +price +" for " +nameOfProduct, data.get(0), price, test);
						  action.CompareResult("Quantity : " + quantity +" for " + nameOfProduct, data.get(1), quantity, test);						  
						}
					  }
				  }
				action.CompareResult("Products Total", String.valueOf(sum), icSubtotal.getText().replace("R", "").replace(",", "").replace(".", "") , test);
				action.clickEle(icCCheckout, "Secure Checkout", test);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info(e.getMessage());
			}
			  //Compare with data from the map
			  
		  }
		  

	    public void cartButtonValidation(WebElement addToCartButton,int waitTimeInSeconds,ExtentTest test) {
			try {
				String cartAdditionMethod = "ProductDetailPage";
				WebElement cartButton;
				if(cartAdditionMethod.equalsIgnoreCase("ProductListingPage")) {
				 cartButton = addToCartButton.findElement(By.xpath(".//button/span"));
				}else {
				 cartButton = addToCartButton.findElement(By.xpath(".//span"));
				}
				
				
				boolean addingFlag = false;
				boolean addedFlag = false;
				int addingCount = 0;
				int addedCount = 0;
				
				while (!addingFlag) {
					//System.out.println("adding flag ="+ addingFlag);
					if (cartButton.getText().equalsIgnoreCase("Adding...")) {
						addingFlag = true;
						//System.out.println("adding flag ="+ addingFlag);
						
						while(!addedFlag) {
							//System.out.println("added flag ="+ addedFlag);
							if(cartButton.getText().equalsIgnoreCase("Added")) {
								addedFlag = true;
								//System.out.println("added flag ="+ addedFlag);
								break;
							}else {
								Thread.sleep(10);
								addedCount+=10;
								//System.out.println("addedCount = "+addedCount);
								if(addedCount > waitTimeInSeconds * 1000) {
									break;
								}
							}
						}
						
					}else {
						Thread.sleep(10);
						addingCount+=10;
						//System.out.println("addingCount = "+addingCount);
						if(addingCount > waitTimeInSeconds * 1000) {
							break;
						}
					}
				} 
				
				action.CompareResult("Adding text appears on add to cart button" , "true",String.valueOf(addingFlag), test);
				action.CompareResult("Added text appears on add to cart button" , "true",String.valueOf(addedFlag), test);
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info(e.getMessage());
			}
			  
		 }
	    
	    public Map<String, List<String>> productQuantityAndPrice(Map<String, String> products,List<String> quantity,ExtentTest test) {
	    	Map<String, List<String>> productData = new HashMap<String, List<String>>();
	    	
	    	for(Iterator<String> iterator = quantity.iterator(); iterator.hasNext(); ) {
	    		
	    		//find quantity --Add it to the list
	    		for(Map.Entry map : products.entrySet()) {
	    		//find name of product add it as the key
	    			List<String> priceAndQuantity = new ArrayList<>();
	    			String key = (String)map.getKey();
	    			String value = (String)map.getValue();
	    			priceAndQuantity.add(value);
	    			String quantityNum = iterator.next();
	    			
	    			
	    			priceAndQuantity.add(quantityNum);	    			
	    			iterator.remove();	
	    			
	    		productData.put(key, priceAndQuantity);
	    		}
	    	}	    	
	    	return productData;
	    }
	    
	    
	    public String itemsInCartCounter(ExtentTest test) {
	    	String counterValue = cartCounterIcon.getText();
	    	return counterValue;
	    }
	    
	    public void removeAllItemsInCart(ExtentTest test) throws Exception {
	    	String cartCounter = itemsInCartCounter(test);
	    	if(Integer.parseInt(cartCounter)>0) {
	    	navigateToCart(test);
	    	action.click(viewAndEditCart, "View And Edit Cart", test);
	    	action.explicitWait(4000);
	    	//action.mouseover(removeAllCartItems, "However over cart element");
	    	//action.explicitWait(7000);
	    	//action.click(removeAllCartItems, "Remove All items From Cart", test);
	    	//action.javaScriptClick(removeAllCartItems, "Remove All items From Cart", test);
	    	JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", removeAllCartItems);
			action.explicitWait(5000);
	    	boolean isRemovePopUpDisplayed = action.elementExistWelcome(removeConfirmationPopUp, 4000, "Remove Pop Up Exists", test);
	    	if(isRemovePopUpDisplayed) {
	    		action.explicitWait(4000);
	    		action.click(okButtonRemoveAllItems, "Remove All Items Button", test);
	    		action.explicitWait(4000);
	    		String emptyCartVerification = emptyCartConfrimation.getText();
	    		action.CompareResult("Empty Cart Message Verification", "You have no items in your shopping cart.", emptyCartVerification.trim(), test);
	    		cartCounter = itemsInCartCounter(test);
	    		action.CompareResult("Cart Count:Mini Cart Is Empty", "0", cartCounter	, test);
	    	}
	    	}else {
	    		action.CompareResult("Cart Count:Mini Cart Is Empty", "0", cartCounter, test);
	    	}
	    }
	    
}