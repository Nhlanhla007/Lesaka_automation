package ic_MagentoPageObjects;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class Magento_FetchOrderpayload {
	WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    public Magento_FetchOrderpayload(WebDriver driver,DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2=dataTable2;
    }
    
    //@FindBy(xpath = "/strong[contains(text(),'Sales Order')]")
    @FindBy(xpath = "//body/div[2]/main[1]/div[1]/div[2]/div[1]/div[1]/div[1]/button[@class='action-toggle primary add']")
	 private WebElement IntegrationDropdown;
    
    @FindBy(xpath = "//span[@title = \"Fetch Order Payload\"]")
	 private WebElement FetchOrderPayload;
    
    
    public void FetchOrderPayload(ExtentTest test) throws IOException{
    	try {
			if(action.elementExists(IntegrationDropdown, 11)){
				action.click(IntegrationDropdown, "Integration Dropdown", test);
				if(action.waitUntilElementIsDisplayed(FetchOrderPayload, 5000)){
					action.click(FetchOrderPayload, "Download Payload", test);
					action.explicitWait(10000);
					action.CompareResult("Fetch order Payload sucessfully", "True", "True", test);
				}else{
					action.CompareResult("Fetch order Payload sucessfully", "True", "False", test);
				}
			}else{
				action.CompareResult("Fetch order Payload sucessfully", "True", "False", test);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			action.CompareResult("Fetch order Payload sucessfully", "True", "False", test);
		}
    }
    
}
