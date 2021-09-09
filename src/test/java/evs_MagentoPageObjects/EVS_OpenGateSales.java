package evs_MagentoPageObjects;

import com.aventstack.extentreports.ExtentTest;
import ic_MagentoPageObjects.ic_Magento_Login;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Action;
import utils.DataTable2;

import java.util.List;

public class EVS_OpenGateSales {

    WebDriver driver;
    Action action;
    DataTable2 dataTable2;

    int ajaxTimeOutInSeconds = EVS_Magento_Login.ajaxTimeOutInSeconds;

    public EVS_OpenGateSales(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2 = dataTable2;
    }

    @FindBy(id = "menu-magento-sales-sales")
    public WebElement magentoSalesTab;

    @FindBy(xpath = "//li[contains(@class,'item-sales-opengate-sales')]")
    public WebElement openGateSalesTab;

    @FindBy(xpath = "//button[contains(text(),'Filters')]")
    public WebElement magentoFilterTab;

    @FindBy(name = "increment_id")
    public WebElement magentoIdSearchField;

    @FindBy(xpath = "//span[contains(text(),'Apply Filters')]")
    public WebElement magentoApplyFilterTab;

    @FindBy(className = "data-row")
    public List<WebElement> magentoTableRecords;

    @FindBy(xpath = "//button[contains(text(),'Clear all')]")
    public WebElement clearFilters;

    @FindBy(xpath = "//tbody/tr[1]/td[9]/div")
    public WebElement magentoOrderStatus;

    @FindBy(xpath = "//table[@class='data-grid data-grid-draggable']/tbody/tr/td/div")
    public List<WebElement> tableData;

    public void navigateOpenGatSalesPage(ExtentTest test) throws Exception {
        if (action.waitUntilElementIsDisplayed(magentoSalesTab, 10000)) {
            action.click(magentoSalesTab, "Sales tab", test);
        }
        if (action.waitUntilElementIsDisplayed(openGateSalesTab, 10000)) {
            action.click(openGateSalesTab, "Open Gate Sales tab", test);
        }
    }

    public void searchForOrder(String idToSearch, ExtentTest test) throws Exception {
        try {
            action.waitForPageLoaded(ajaxTimeOutInSeconds);
            action.ajaxWait(ajaxTimeOutInSeconds, test);
            if (action.waitUntilElementIsDisplayed(clearFilters, 10)) {
                action.javaScriptClick(clearFilters, "Cleared Filters", test);
                action.ajaxWait(ajaxTimeOutInSeconds, test);
            }
            action.explicitWait(5000);
            action.javaScriptClick(magentoFilterTab, "Filter tab", test);
            action.writeText(magentoIdSearchField, idToSearch, "Search Id", test);
            action.explicitWait(3000);
            action.click(magentoApplyFilterTab, "Apply Filters", test);
            action.waitForJStoLoad(20);
            action.ajaxWait(ajaxTimeOutInSeconds, test);
            action.explicitWait(5000);

        } catch (Exception e) {
            throw new Exception("Unable to filter Order ID: "+e.getMessage());

        }
    }


    public void viewOrder(ExtentTest test, String idToSearch) throws Exception {
        try {
            boolean ajaxLoadCompleted = action.ajaxWait(ajaxTimeOutInSeconds, test);
            boolean searchOrderFlag = false;
            if (magentoTableRecords.size() >= 1) {
                if (!ajaxLoadCompleted) {
                    driver.navigate().refresh();
                    action.waitForPageLoaded(ajaxTimeOutInSeconds);
                    action.ajaxWait(ajaxTimeOutInSeconds, test);
                }

                for (WebElement ele : tableData) {
                    boolean searchOrderId = ele.getText().contains(idToSearch);
                    if (searchOrderId) {
                        searchOrderFlag = true;
                        break;
                    }
                }

                action.CompareResult("Order Id : " + idToSearch + " is displayed", String.valueOf(true), String.valueOf(searchOrderFlag),
                        test);

            }
        } catch (Exception e) {
            throw new Exception("Unable to Search Order ID " + e.getMessage());
        }
    }


    public void searchOrderOpenGateSales(ExtentTest test) throws Exception {
        String idToSearch = dataTable2.getValueOnOtherModule("ic_RetriveOrderID", "orderID", 0);
        action.ajaxWait(ajaxTimeOutInSeconds, test);
        action.explicitWait(5000);
        navigateOpenGatSalesPage(test);
        searchForOrder(idToSearch, test);
        viewOrder(test, idToSearch);

    }
}
