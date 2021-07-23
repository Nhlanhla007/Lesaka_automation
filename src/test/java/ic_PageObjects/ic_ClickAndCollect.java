package ic_PageObjects;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.aventstack.extentreports.ExtentTest;
import utils.Action;
import utils.DataTable2;
public class ic_ClickAndCollect {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	public ic_ClickAndCollect(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2 = dataTable2;
	}

	@FindBy(xpath = "//button[contains(text(),'Collect')]")

	private WebElement CollectBtn;
    @FindBy(xpath="//span[contains(text(),'Find stores close to me')]")
    private WebElement FindStore;
    @FindBy(xpath="//*[@id=\"opc-sidebar\"]/div[1]/div[1]/button")
    private WebElement Continue_payment;
    @FindBy(xpath="//*[@id='checkout-shipping-method-load']/table/tbody/tr[3]/td/div/ol/li[2]/div/button")
    private WebElement SelectStore;
    
    @FindBy(xpath="//ol[@class='product-instorestock-store-list']//li//div//button")
    public List<WebElement> Storelist;

    @FindBy(xpath="//div[@class='message info']//span[contains(text(),'No stock available for Click')]")
    private WebElement MessageNostore;

    boolean check =false;
     public void ClickandCollectDeliveryoption(ExtentTest test) throws Exception {

    	 action.explicitWait(15000);
    	 action.waitUntilElementIsDisplayed(CollectBtn,30);
         action.click(CollectBtn, "Click and Collect delivery option", test);
        action.ajaxWait(10,test);
        if(action.waitUntilElementIsDisplayed(FindStore, 10)){
            action.click(FindStore, "Find Store close to me option", test);
            action.ajaxWait(10,test);
//            action.explicitWait(5000);

            if(!(Storelist.size()>1)){
//            action.acceptAlert();
//            action.explicitWait(10000);
            }

            boolean Storevailableselected = false;
            int trycount=2;

            while(check==false && trycount==2){
                try {
                    Storevailableselected = SelectAvailableStore(test);
                    action.click(Continue_payment, "Continue payment", test);
                    action.explicitWait(20000);
                    String checkUrl =action.getCurrentURL();
                    if(checkUrl.toUpperCase().contains("PAYMENT")){
                        check=true;
                    }
                    else{
                        trycount--;
                    }
                } catch (Exception e) {

                    trycount--;
                }
            }
                action.CompareResult("Selection of nearest store location where the product is available", "true", String.valueOf(check), test);
        }else{
            action.CompareResult("Find Store close to me option is present ", "True", "False", test);

        }
     }
     public boolean SelectAvailableStore(ExtentTest test) throws IOException{
         boolean flagRes=false;

         List<WebElement> allstore = Storelist;
            for(WebElement el: allstore) {
                String Displayed_checkbox = action.getAttribute(el, "style");
                if(!Displayed_checkbox.contains("display: none")){
                    action.click(el, "avialabel store selected ", test);
                    flagRes=true;
                    break;
                }
            }
            action.CompareResult("List of Store option available with product is found ", "true", String.valueOf(flagRes), test);
        return  flagRes;
     }

}

