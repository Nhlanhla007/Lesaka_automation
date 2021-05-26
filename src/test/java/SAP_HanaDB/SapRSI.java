package SAP_HanaDB;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Action;
import utils.DataTable2;
import utils.hana;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class SapRSI {
    WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> dataMap2 =null;
    public SapRSI(WebDriver driver,DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2 = dataTable2;
    }
    public void getDataFromSAPDB(ExtentTest test) throws IOException, SQLException {
        String DBinstance = dataTable2.getValueOnCurrentModule("DB_Instance");
        //ECCQA

        String primaryKey="DB_Instance";
        String conSheet="DB_connection_master";
        String Server =dataTable2.getRowUsingReferenceAndKey(conSheet,primaryKey,DBinstance,"Host");
        String Port =  dataTable2.getRowUsingReferenceAndKey(conSheet,primaryKey,DBinstance,"port");
        String Username =  dataTable2.getRowUsingReferenceAndKey(conSheet,primaryKey,DBinstance,"Username");
        String Password =  dataTable2.getRowUsingReferenceAndKey(conSheet,primaryKey,DBinstance,"Password");
        String TypeOfDB = dataTable2.getRowUsingReferenceAndKey(conSheet,primaryKey,DBinstance,"TypeOfDB");

        String channelID=dataTable2.getValueOnCurrentModule("channelID");
        String rough_stock_value=dataTable2.getValueOnCurrentModule("rough_stock_value");

        String Query= "select * from SAPABAP1.\"/OAA/RSI_SNP\" where channel_id = '"+channelID+"' and ROUGH_STOCK_DATE >=to_date(now()) and AGGR_AVAIL_QTY>0 and rough_stock_value = '"+rough_stock_value+"' order by rand() limit 1";
        System.out.println("Query:"+Query);
        hana hn =new hana(TypeOfDB,Server,Port,Username,Password,test);
        ResultSet rs = hn.ExecuteQuery(Query);
        int rowsCountReturned = hn.GetRowsCount(rs);
        System.out.println("rowsCountReturned: "+rowsCountReturned);

        List<String> alldataSKUCode = hn.GetRowdataByColumnName(rs, "ARTICLE_ID");
        String SKUCode=alldataSKUCode.get(0);
        System.out.println("SKUCode: "+SKUCode);

        List<String> alldataAGGR_AVAIL_QTY = hn.GetRowdataByColumnName(rs, "AGGR_AVAIL_QTY");
        String AGGR_AVAIL_QTY_1=alldataAGGR_AVAIL_QTY.get(0);
        String AGGR_AVAIL_QTY=AGGR_AVAIL_QTY_1.split("\\.")[0];
        System.out.println("AGGR_AVAIL_QTY: "+AGGR_AVAIL_QTY);

        dataTable2.setValueOnCurrentModule("SKUCode",SKUCode);
        dataTable2.setValueOnCurrentModule("AGGR_AVAIL_QTY",AGGR_AVAIL_QTY);
    }

    @FindBy(xpath = "//*[@class=\"admin__menu\"]/ul[@id='nav']/li[@id=\"menu-magento-catalog-catalog\"]/a/span[contains(text(),\"Catalog\")]")
    WebElement catalogTab;

    @FindBy(xpath = "//li[@role=\"menu-item\"]/a/span[contains(text(),'Products')]")
    WebElement productsTab;

    @FindBy(xpath = "//button[contains(text(),'Filters')]")
    WebElement magentoFilterTab;

    @FindBy(name = "sku")
    public WebElement sku;

    @FindBy(xpath = "//span[contains(text(),'Apply Filters')]")
    public WebElement magentoApplyFilterTab;

    @FindBy(xpath = "//span[contains(text(),'SAP Data')]")
    public WebElement sapDataTab;

    @FindBy(xpath = "//a[contains(text(),'Edit')]")
    public WebElement clickEdit;

    @FindBy(xpath = "//button[contains(text(),'Clear all')]")
    public WebElement Clearbutton;



    @FindBy(name = "product[rough_stock_indicator]")
    public WebElement roughStockIndicatorAct;

    public void getRSIItemInMagento(ExtentTest test) throws IOException {
        action.click(catalogTab,"catalogTab",test);
        action.click(productsTab,"productsTab",test);
        action.explicitWait(5000);
        action.click(Clearbutton,"Clearbutton",test);
        action.explicitWait(5000);
        action.click(magentoFilterTab,"magentoFilterTab",test);
        action.writeText(sku,dataTable2.getValueOnOtherModule ("SapRSIGetDataFromSAPDB","SKUCode",0),"skuInputTest",test);
        action.click(magentoApplyFilterTab,"magentoApplyFilterTab",test);
        action.explicitWait(5000);
        action.javaScriptClick(clickEdit, "clickEdit", test);
        action.explicitWait(5000);
        action.click(sapDataTab,"sapDataTab",test);
        System.out.println(action.getText(roughStockIndicatorAct,"roughStockIndicator",test));
    }
}
