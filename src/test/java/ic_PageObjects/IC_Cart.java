package ic_PageObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import Logger.Log;
import tests.JDTests;
import utils.Action;
import utils.DataTable2;

public class IC_Cart {
		
	  WebDriver driver;
	    Action action;
	    DataTable2 dataTable2;
	    
	    JDTests browser ;
	    ic_Login login ;
	    public IC_Cart(WebDriver driver, DataTable2 dataTable2) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	        action = new Action(driver);
	        this.dataTable2 = dataTable2;
	        browser =  new JDTests();
	        login =  new ic_Login(driver, dataTable2);
	    }
	    static Logger logger = Log.getLogData(Action.class.getSimpleName());

	    @FindBy(xpath="//*[@class=\"action showcart\"]")
	    private WebElement iCCartButton;

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
	    
	    @FindBy(xpath = "//*[@class=\"cart-empty\"]/p[1]")
	    public WebElement emptyCartConfrimation;

	    @FindBy(css = "a.go-back")
	    private WebElement backButton;
	    
	    public void navigateToCart(ExtentTest test) {
	    	try {
				action.clickEle(iCCartButton, "IC cart button", test);
			} catch (Exception e) {
				// TODO: handle exception
			}
	    }
	    
	    public int sum;
		  public void iCcartVerification2(Map<String, List<String>> products,ExtentTest test) {
		  	action.explicitWait(3000);
			  String itemsCount = itemsInCartCounter(test);
			  int allProductsInCartQuantity = 0;
			  navigateToCart(test);
			  try {
				for(WebElement productsInCart : icAllCartProducts) {
						action.explicitWait(2000);
					  String nameOfProduct = productsInCart.findElement(By.xpath(".//strong/a")).getText();
					  String price = productsInCart.findElement(By.xpath(".//span/span/span/span")).getText();					  
					  WebElement quantityTag = productsInCart.findElement(By.xpath(".//div[2]/input"));
					  String quantity = action.getAttribute(quantityTag, "data-item-qty");
					 
					  for(Map.Entry selectedProducts : products.entrySet()) {
						List<String> data = (List<String>)selectedProducts.getValue();
						if(selectedProducts.getKey().toString().contains(nameOfProduct)) {
						  action.CompareResult("Name : " + nameOfProduct , (String)selectedProducts.getKey(), nameOfProduct, test);
						  sum = sum + (Integer.parseInt(quantity) * Integer.parseInt(price.replace("R", "").replace(",", "") ) );
						  action.CompareResult("Price : " +price +" for " +nameOfProduct, data.get(2), price, test);
						  allProductsInCartQuantity += Integer.parseInt(data.get(0));
						  action.CompareResult("Quantity : " + quantity +" for " + nameOfProduct, data.get(0), quantity, test);						  
						}
					  }
				  }
				action.CompareResult("Products Total", String.valueOf(sum), icSubtotal.getText().replace("R", "").replace(",", "").replace(".", "") , test);
				action.CompareResult("Cart Counter Verification", String.valueOf(allProductsInCartQuantity), itemsCount, test);
				action.clickEle(icCCheckout, "Secure Checkout", test);
				action.waitForPageLoaded(60);
 	            action.ajaxWait(10, test);
 	            action.explicitWait(20000);
				dataTable2.setValueOnOtherModule("ProductSearch", "CartTotal", String.valueOf(sum), 0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info(e.getMessage());
				e.printStackTrace();
			}
			  
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
					if (cartButton.getText().equalsIgnoreCase("Adding...")) {
						addingFlag = true;
						
						while(!addedFlag) {
							if(cartButton.getText().equalsIgnoreCase("Added")) {
								addedFlag = true;
								break;
							}else {
								Thread.sleep(10);
								addedCount+=10;
								if(addedCount > waitTimeInSeconds * 1000) {
									break;
								}
							}
						}
						
					}else {
						Thread.sleep(10);
						addingCount+=10;
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

	    		for(Map.Entry map : products.entrySet()) {
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
	    	if(counterValue == ""|counterValue==null|counterValue.equals("0")) {
	    		counterValue = "0";
	    	}
	    	return counterValue;
	    }
	    
	    public void navigateToViewAndEditCart(ExtentTest test) throws Exception {
			action.scrollToElement(viewAndEditCart,"View and Edit Cart",test);
			action.explicitWait(2000);
			action.clickEle(viewAndEditCart, "View And Edit Cart", test);
	    }
	    
	    public void removeAllItemsInCart(ExtentTest test) throws Exception {
	    	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	    	boolean isPresent = driver.findElements(By.cssSelector("a.go-back")).size() > 0;
	    	action.explicitWait(5000);
	    	if(isPresent) {
	    		backButton.click();
	    	}
	    	action.explicitWait(5000);
	    	String cartCounter = itemsInCartCounter(test);

	    	if(cartCounter.equalsIgnoreCase("")) {
	    		cartCounter = "0";
	    	}
	    	if(Integer.parseInt(cartCounter)>0) {
	    	navigateToCart(test);
	    	navigateToViewAndEditCart(test);
	    	action.explicitWait(5000);
	    	JavascriptExecutor executor = (JavascriptExecutor) driver;
	    	if(action.waitUntilElementIsDisplayed(removeAllCartItems, 15000)) {
			executor.executeScript("arguments[0].click();", removeAllCartItems);		
	    	}
	    	boolean isRemovePopUpDisplayed = action.elementExistWelcome(removeConfirmationPopUp, 4, "Clear Shopping Cart Pop Up", test);
	    	if(isRemovePopUpDisplayed) {	    		
	    		action.click(okButtonRemoveAllItems, "Remove All Items Button", test);
	    		if(action.waitUntilElementIsDisplayed(emptyCartConfrimation, 15000)) {
	    		String emptyCartVerification = emptyCartConfrimation.getText();
	    		action.CompareResult("Empty Cart Message Verification", "You have no items in your shopping cart.", emptyCartVerification.trim(), test);
	    		}	    		
	    		action.explicitWait(5000);
	    		cartCounter = itemsInCartCounter(test);
	    		action.CompareResult("Cart Count:Mini Cart Is Empty", "0", cartCounter	, test);
	    	}
	    	}else {
	    		action.CompareResult("Cart Count:Mini Cart Is Empty", "0", cartCounter, test);
	    }
	    }
	    
	    public void logonAgainAndVerifyCart(HashMap<String, ArrayList<String>> input,ExtentTest test) throws Exception {
	    	String itemsInCart = itemsInCartCounter(test);
	    	action.CompareResult("items in cart", "1", itemsInCart, test);

	    }	    	  
	    
	    public void verifyCart(ExtentTest test) throws Exception {
	    	action.explicitWait(5000);
	    	String itemsInCart = itemsInCartCounter(test);
	    	String productQuantity = dataTable2.getValueOnOtherModule("ProductSearch", "Quantity", 0);
	    	String productFromExcel = dataTable2.getValueOnOtherModule("ProductSearch","specificProduct", 0);
	    	action.CompareResult("Items in cart", productQuantity, itemsInCart, test);
	    	
	    	action.click(iCCartButton, "Cart button", test);
	    	WebElement product =icAllCartProducts.get(0);
	    	String prodInCart = product.findElement(By.xpath(".//strong/a")).getText();
	    	action.CompareResult("Product in cart", productFromExcel, prodInCart, test);
	    }
	    
}