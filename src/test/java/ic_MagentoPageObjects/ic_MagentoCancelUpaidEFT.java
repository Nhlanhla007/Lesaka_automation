package ic_MagentoPageObjects;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import org.openqa.selenium.support.FindBy;
import utils.Action;
import utils.DataTable2;

public class ic_MagentoCancelUpaidEFT {

    WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    int ajaxTimeOutInSeconds = ic_Magento_Login.ajaxTimeOutInSeconds;

    public ic_MagentoCancelUpaidEFT(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2 = dataTable2;
    }

    @FindBy(xpath = "//*[@class=\"action-default scalable cancel\"]")
    private WebElement admin_Cancel;

    @FindBy(xpath = "//*[@class=\"action-primary action-accept\"]/span")
    private WebElement popUP_OK;

    @FindBy(xpath = "//div[contains(text(),'You canceled the order.')]")
    private WebElement popUP_canceledOrder;

    public void IC_cancelUpaidEFT(ExtentTest test) throws Exception {

        //action.explicitWait(5000);
        action.click(admin_Cancel, "Cancelling the order", test);
        //action.explicitWait(5000);
        //action.waitForPageLoaded(ajaxTimeOutInSeconds);
        action.ajaxWait(ajaxTimeOutInSeconds, test);
        action.click(popUP_OK, "Click ok", test);
        //action.explicitWait(5000);
        action.ajaxWait(ajaxTimeOutInSeconds, test);
        String messageConfirm = action.getText(popUP_canceledOrder, "Confirmation message", test);
        action.CompareResult("Confirm cancel order", "You canceled the order.", messageConfirm, test);

    }

}
