package evs_PageObjects;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class EVS_IncreaseQuanityInCart {

	WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    EVS_Cart cart;
    
    @FindBy(css = "a.go-back")
    private WebElement backButton;
    
    @FindBy(xpath = "//*[@id=\"shopping-cart-table\"]/tbody/tr[1]/td[3]//label/div/button[2]")
    private WebElement quantityAdd;
    
    @FindBy(xpath = "(//*[contains(@class, 'qty-action update update-cart-item')])")
    private WebElement updateQuantity;        
    
	public EVS_IncreaseQuanityInCart(WebDriver driver, DataTable2 dataTable2){
		
		this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2 = dataTable2;
        cart = new EVS_Cart(driver, dataTable2);
	}

	public void increaseQuantity(ExtentTest test) throws Exception {
		//navigate back
		boolean buttonAvail = action.waitUntilElementIsDisplayed(backButton, 15000);
		action.explicitWait(4000);
		if(buttonAvail) {
			backButton.click();
		}
		if(action.waitUntilElementIsDisplayed(quantityAdd, 15000)) {
		action.click(quantityAdd, "Increase Quantity", test);
		}
		//click update
		action.explicitWait(2000);
		action.click(updateQuantity, "Update Quantity", test);
		action.explicitWait(3000);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		boolean cartCountVisablility = driver.findElements(By.xpath("//*[@class = \"counter-number\"]")).size()> 0;
		if(cartCountVisablility) {
			action.explicitWait(3000);		
			String cartCount = cart.itemsInCartCounter(test);
			action.CompareResult("Cart Count After update", "2", cartCount, test);
	}
	}
	
}
