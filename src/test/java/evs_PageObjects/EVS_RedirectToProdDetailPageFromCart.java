package evs_PageObjects;

import Logger.Log;
import com.aventstack.extentreports.ExtentTest;
import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.aventstack.extentreports.ExtentTest;
import Logger.Log;
import utils.Action;
import utils.DataTable2;

public class EVS_RedirectToProdDetailPageFromCart {
	 WebDriver driver;
	    Action action;
	    DataTable2 dataTable2;
	    EVS_Cart cart;
	    
		/*
		 * @FindBy(xpath = "//*[@class=\"product-item-name\"]/a") private
		 * List<WebElement> allProductsInCart;
		 */
	    
	    @FindBy(xpath = "//*[@id=\"mini-cart\"]/li[1]//strong/a")
	    private WebElement productInCart;
	    
	    @FindBy(xpath = "//*[@class=\"base\"]")
	    private WebElement productNavigatedTo;
	    
	    @FindBy(css = "a.go-back")
	    private WebElement backButton;
	    
	    public EVS_RedirectToProdDetailPageFromCart(WebDriver driver, DataTable2 dataTable2) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	        action = new Action(driver);
	        this.dataTable2 = dataTable2;
	        cart = new EVS_Cart(driver, dataTable2);
	    }
	    static Logger logger = Log.getLogData(Action.class.getSimpleName());
	    
	    public void verifyNavigationToProductDetailPageFromCart(ExtentTest test) throws Exception {
	    	boolean buttonAvail = action.waitUntilElementIsDisplayed(backButton, 15000);
			action.explicitWait(4000);
			if(buttonAvail) {
				backButton.click();
			}
			action.explicitWait(5000);
	    	cart.navigateToCart(test);
	    	String firstElementInCArt = productInCart.getText();
	    	action.click(productInCart, "Product Link In Cart", test);
	    	String currentUrl =driver.getTitle();
	    	currentUrl = currentUrl.replace("-", " ");
	    	if(currentUrl.toLowerCase().contains(firstElementInCArt.toLowerCase())) {
	    		action.CompareResult("Redirected URL contains selected product", "true", "true", test);
	    	}
	    	action.CompareResult("Product navigated to", firstElementInCArt, productNavigatedTo.getText(), test);
	    	
	    }
}
