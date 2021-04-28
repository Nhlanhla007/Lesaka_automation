package JDGroupPageObjects;

import java.io.IOException;
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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;

import Logger.Log;
import base.TestCaseBase;
import tests.JDTests;
import utils.Action;
import utils.ConfigFileReader;
import utils.DataTable2;
import utils.Values;

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

	    @FindBy(css = "a.go-back")
	    private WebElement backButton;
	    
	    public void navigateToCart(ExtentTest test) {
	    	try {
				action.clickEle(iCCartButton, "IC cart button", test);
			} catch (Exception e) {
				// TODO: handle exception
			}
	    }
	    
	    public static int sum;
		  public void iCcartVerification2(Map<String, List<String>> products,ExtentTest test) {
			  //Verifies if all the products have been added in the cart
			  String itemsCount = itemsInCartCounter(test);
			  //need to compare that the quantity in the list matches the itemsCount
			  int allProductsInCartQuantity = 0;
			  //Find all elements from the list
			  navigateToCart(test);
			  try {
				for(WebElement productsInCart : icAllCartProducts) {
					  String nameOfProduct = productsInCart.findElement(By.xpath(".//strong/a")).getText();
					  String price = productsInCart.findElement(By.xpath(".//span/span/span/span")).getText();					  
					  WebElement quantityTag = productsInCart.findElement(By.xpath(".//div[2]/input"));
					  String quantity = action.getAttribute(quantityTag, "data-item-qty");
					 
					  for(Map.Entry selectedProducts : products.entrySet()) {
						  //@SuppressWarnings("unchecked")
						List<String> data = (List<String>)selectedProducts.getValue();
						if(selectedProducts.getKey().equals(nameOfProduct)) {
						  action.CompareResult("Name : " + nameOfProduct , (String)selectedProducts.getKey(), nameOfProduct, test);
						  sum = sum + (Integer.parseInt(quantity) * Integer.parseInt(price.replace("R", "").replace(",", "") ) );
						  action.CompareResult("Price : " +price +" for " +nameOfProduct, data.get(0), price, test);
						  allProductsInCartQuantity += Integer.parseInt(data.get(1));
						  action.CompareResult("Quantity : " + quantity +" for " + nameOfProduct, data.get(1), quantity, test);						  
						}
					  }
				  }
				action.CompareResult("Products Total", String.valueOf(sum), icSubtotal.getText().replace("R", "").replace(",", "").replace(".", "") , test);
				action.CompareResult("Cart Counter Verfication", String.valueOf(allProductsInCartQuantity), itemsCount, test);
				action.clickEle(icCCheckout, "Secure Checkout", test);
				dataTable2.setValueOnOtherModule("ProductSearch", "CartTotal", String.valueOf(sum), 0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info(e.getMessage());
				e.printStackTrace();
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
	    	if(counterValue == ""|counterValue==null|counterValue.equals("0")) {
	    		counterValue = "0";
	    	}
	    	return counterValue;
	    }
	    
	    public void navigateToViewAndEditCart(ExtentTest test) throws Exception {
	    	action.clickEle(viewAndEditCart, "View And Edit Cart", test);
	    }
	    
	    public void removeAllItemsInCart(ExtentTest test) throws Exception {
	    	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	    	//WebDriverWait wait=new WebDriverWait(driver,3);
	    	//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/header/div/div/a/span[2]")));
	    	//(new WebDriverWait(driver, 5)).until(ExpectedConditions.visibilityOf(backButton));
	    	boolean isPresent = driver.findElements(By.cssSelector("a.go-back")).size() > 0;
	    	action.explicitWait(5000);
	    	if(isPresent) {
	    		backButton.click();
	    	}
	    	String cartCounter = itemsInCartCounter(test);
	    	if(Integer.parseInt(cartCounter)>0) {
	    	navigateToCart(test);
	    	navigateToViewAndEditCart(test);
	    	//action.mouseover(removeAllCartItems, "However over cart element");
	    	//action.explicitWait(7000);
	    	//action.click(removeAllCartItems, "Remove All items From Cart", test);
	    	//action.javaScriptClick(removeAllCartItems, "Remove All items From Cart", test);
	    	JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", removeAllCartItems);		
	    	boolean isRemovePopUpDisplayed = action.elementExistWelcome(removeConfirmationPopUp, 4000, "Clear Shopping Cart Pop Up", test);
	    	if(isRemovePopUpDisplayed) {	    		
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
	    
	    public void logonAgainAndVerifyCart(HashMap<String, ArrayList<String>> input,ExtentTest test) throws Exception {	    	
	    	//check cart quantity
	    	String itemsInCart = itemsInCartCounter(test);
	    	action.CompareResult("items in cart", "1", itemsInCart, test);
	    	//startBrowserSession
	    	//close browser
	    	//driver.quit();
	    	
	    	//driver = null;
	    	//WebDriver driver1 = new ChromeDriver();
	    	//navigate to login again
	    	//startBrowserSession(driver1);
	    	
			/*
			 * ic_Login login = new ic_Login(driver1, dataTable2); WebElement myAccount =
			 * driver1.findElement(By.xpath(
			 * "/html/body/div[1]/header/div[2]/div/div[3]/div[2]/span")); WebElement
			 * selectLogin =
			 * driver1.findElement(By.xpath("//*[@id=\"header-slideout--0\"]/li[3]/a"));
			 * WebElement userName = driver1.findElement(By.xpath("//*[@id='email']"));
			 * WebElement password = driver1.findElement(By.xpath("\"//*[@id='pass']\""));
			 * WebElement signIn = driver1.findElement(By.xpath("//*[@id=\"send2\"]/span"));
			 * 
			 * myAccount.click();
			 */
	    	//int rowNumber = Integer.parseInt(dataTable2.getValueOnOtherModule("ic_login", "TCID", 0));
	    	//login.Login_ic(input, test, rowNumber);
	    	
	    	//String itemsInCartAfterLogin = itemsInCartCounter(test);
	    	//action.CompareResult("Items in cart", "1", itemsInCartAfterLogin, test);
	    	//check the product
	    	//login.Login_ic(, test, rowNumber);
	    	//driver.navigate().to("https://JDGroup:JDGr0up2021@staging-incredibleconnection-m23.vaimo.net/");
			//driver.manage().window().maximize();
			//driver.navigate().refresh();
	    	//verify cart
	    	
	    }	    	  
	    
	    public void verifyCart(ExtentTest test) throws Exception {
	    	//check cart
	    	action.explicitWait(5000);
	    	String itemsInCart = itemsInCartCounter(test);
	    	String productQuantity = dataTable2.getValueOnOtherModule("ProductSearch", "Quantity", 0);
	    	String productFromExcel = dataTable2.getValueOnOtherModule("ProductSearch","specificProduct", 0);
	    	action.CompareResult("Items in cart", productQuantity, itemsInCart, test);
	    	
	    	action.click(iCCartButton, "Cart button", test);
	    	WebElement product =icAllCartProducts.get(0);
	    	String prodInCart = product.findElement(By.xpath(".//strong/a")).getText();
	    	action.CompareResult("Product in cart", productFromExcel, prodInCart, test);
	    	//check product in cart
	    }
	    
}