package kmC_tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;


public class kmcTest_Cases {
	WebDriver driver;
    Action action;
    
    public kmcTest_Cases(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        
    }
  //my element
    @FindBy(xpath = "//*[@name=\"search\"]")
    WebElement searchBarProduct;
    @FindBy(xpath = "//*[@class=\"nav-link active\"]")
    WebElement activeClass;
    @FindBy(xpath = "//*[@role=\"alert\"]")
    WebElement validationM;
    @FindBy(xpath = "//*[@popovertitle=\"Cart Items\"]")
    WebElement cartpage;
    @FindBy(xpath = "//*[contains(text(),\"No item in cart\")]")
    WebElement NoItemInCart;
    @FindBy(xpath = "/html/body/app-root/div/app-product/div[2]/div/div/div[3]/table/tbody/tr/td[1]/img")
    WebElement imagesClick;
    @FindBy(xpath = "//button[@class=\"close\"]//span")
    WebElement closeBtn;
    
    
    public void add_items(ExtentTest test) throws Exception {
    	String username= "CANDIDATE_003";
    	String password ="qa_assessment";
    	String url = "https://"+username+":"+password+"@"+ "master.d2thsy9okxnekb.amplifyapp.com/product";
    	String prodName = "Jungle Oats";
    	int prodQTY = 1;
    	
    	driver.get(url);
    	//action.navigateToURL(url);
    	if(action.isEnabled(activeClass)) {
    		
    		WebElement theProduct = driver.findElement(By.xpath("//*[contains(text(),'"+prodName+"')]"));
    		WebElement qtyElement = driver.findElement(By.xpath("(//*[@class=\"input-group-items\"]//input)[3]"));
    		if(action.isDisplayed(theProduct)) {
    			qtyElement.clear();
    			action.writeText(qtyElement, Integer.toString(prodQTY), "write the quantity", test);
    			driver.findElement(By.xpath("(//button[contains(text(),\"ADD TO CART\")])[3]")).click();
    			action.isElementPresent(validationM);
    			
    		}else {
    			throw new Exception("the product is not available");
    		
    		}
    	}
    	
    	
    }
    
    public void removeItemsinCart(ExtentTest test) throws Exception {
    	action.explicitWait(1000, test);
    	String prodName = "Jungle Oats";
    	action.click(cartpage, "Click cart icon", test);
    	WebElement theProduct = driver.findElement(By.xpath("//*[contains(text(),'"+prodName+"')]"));
    if(action.isDisplayed(theProduct)) {	
    	driver.findElement(By.xpath("(//button[contains(text(),\"REMOVE\")])[1]")).click();
    	action.isElementPresent(NoItemInCart);
    	
    	}else {
			throw new Exception("the product wasn't found on the cart");
    	}
    }
    
	public void searchBaritem(ExtentTest test) throws Exception {
		String username= "CANDIDATE_003";
    	String password ="qa_assessment";
    	String url = "https://"+username+":"+password+"@"+ "master.d2thsy9okxnekb.amplifyapp.com/product";
    	String prodName = "Jungle Oats";
    	
    	action.navigateToURL(url);
    	action.explicitWait(1000, test);
    	WebElement theProduct = driver.findElement(By.xpath("//*[contains(text(),'"+prodName+"')]"));
    	if(action.isEnabled(searchBarProduct)) {
    		searchBarProduct.clear();
    		action.writeText(searchBarProduct, prodName, "write the product name", test);
    		//searchBarProduct.sendKeys("");
    		//action.CompareResult("The product returned", prodName, theProduct.getText(), test);
    		
    	}else {
			throw new Exception("the search results are incorrect");
    	}
    	
    }
	
	public void productDetails(ExtentTest test) throws Exception {
		String username= "CANDIDATE_003";
    	String password ="qa_assessment";
    	String url = "https://"+username+":"+password+"@"+ "master.d2thsy9okxnekb.amplifyapp.com/product";
    	String prodName = "Jungle Oats";
    	
    	action.navigateToURL(url);
    	action.explicitWait(1000, test);
    	WebElement theProduct = driver.findElement(By.xpath("//*[contains(text(),'"+prodName+"')]"));
    	if(action.isDisplayed(theProduct)) {
    		action.click(imagesClick, "view the product details", test);
    		action.explicitWait(1000, test);
    		action.click(closeBtn, "close the details", test);
    	}
    }
	
	public void validatePages(ExtentTest test) throws Exception {
    	
    }
    
}
