package ic_PageObjects;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class ic_existingAddress {
	
	WebDriver driver;
    Action action;
    Ic_Products ic_products;
    
    public ic_existingAddress(WebDriver driver, DataTable2 dataTable2) {
		 this.driver = driver;
	        PageFactory.initElements(driver, this);
	        action = new Action(driver);
	        ic_products = new Ic_Products(driver, dataTable2);
	 }
	 
    @FindBy(xpath="//*[@id=\"checkout-shipping-method-load\"]/table/tbody/tr[1]/td[4]/button")
    private WebElement ic_Deliver;
 
    @FindBy(xpath="//*[@id=\"opc-sidebar\"]/div[1]/div[1]/button/span")
    private WebElement ic_continuePayment;
    
    public void AddressThere(ExtentTest test) throws IOException, InterruptedException{

    	action.explicitWait(14000);

    	action.click(ic_Deliver, "The address is already", test);
    	action.explicitWait(8000);

    	action.click(ic_continuePayment, "continue Payment", test);
    	action.explicitWait(5000);
    }

}
