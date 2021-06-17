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

public class EVS_SapRSI {
    WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> dataMap2 =null;
    public EVS_SapRSI(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2 = dataTable2;
    }
    public void getDataFromSAPDB(ExtentTest test) throws IOException, SQLException {
        hana hn =connectToSap(test) ;
        String channelID=dataTable2.getValueOnCurrentModule("channelID");
        String rough_stock_value=dataTable2.getValueOnCurrentModule("rough_stock_value");
        String Query= "select * from SAPABAP1.\"/OAA/RSI_SNP\" " +
                "where channel_id = '"+channelID+"' " +
                "and ROUGH_STOCK_DATE >=to_date(now()) " +
                "and AGGR_AVAIL_QTY>0 and " +
                "rough_stock_value = '"+rough_stock_value+"' " +
                "order by rand() limit 1";

        System.out.println("Query:"+Query);
        ResultSet rs = hn.ExecuteQuery(Query);
        int rowsCountReturned = hn.GetRowsCount(rs);
        System.out.println("rowsCountReturned: "+rowsCountReturned);

        String SKUCode=getColumnValue(hn,rs ,"ARTICLE_ID");
        System.out.println("SKUCode: "+SKUCode);

        String AGGR_AVAIL_QTY_1=getColumnValue(hn,rs ,"AGGR_AVAIL_QTY");;
        String AGGR_AVAIL_QTY=AGGR_AVAIL_QTY_1.split("\\.")[0];
        System.out.println("AGGR_AVAIL_QTY: "+AGGR_AVAIL_QTY);

        dataTable2.setValueOnCurrentModule("SKUCode",SKUCode);
        dataTable2.setValueOnCurrentModule("AGGR_AVAIL_QTY",AGGR_AVAIL_QTY);
    }

    public void getDataFromSAPDBWithQty(ExtentTest test) throws IOException, SQLException {
        hana hn =connectToSap(test) ;
        String channelID=dataTable2.getValueOnOtherModule ("EVS_SapRSIGetDataFromSAPDB","channelID",0);
        String rough_stock_value=dataTable2.getValueOnOtherModule ("EVS_SapRSIGetDataFromSAPDB","rough_stock_value",0);
        String Query= "select * from SAPABAP1.\"/OAA/RSI_SNP\" " +
                "where channel_id = '"+channelID+"' " +
                "and ROUGH_STOCK_DATE >=to_date(now()) " +
                "and AGGR_AVAIL_QTY=9999999999.000 " +
                "and rough_stock_value = '"+rough_stock_value+"' " +
                "order by rand() limit 1";
        System.out.println("Query:"+Query);
        ResultSet rs = hn.ExecuteQuery(Query);
        int rowsCountReturned = hn.GetRowsCount(rs);
        System.out.println("rowsCountReturned: "+rowsCountReturned);

        String SKUCode=getColumnValue(hn,rs ,"ARTICLE_ID");
        System.out.println("SKUCode: "+SKUCode);

        String AGGR_AVAIL_QTY_1=getColumnValue(hn,rs ,"AGGR_AVAIL_QTY");;
        String AGGR_AVAIL_QTY=AGGR_AVAIL_QTY_1.split("\\.")[0];
        System.out.println("AGGR_AVAIL_QTY: "+AGGR_AVAIL_QTY);

        dataTable2.setValueOnOtherModule ("EVS_SapRSIGetDataFromSAPDB","SKUCode",SKUCode,0);
        dataTable2.setValueOnOtherModule ("EVS_SapRSIGetDataFromSAPDB","AGGR_AVAIL_QTY",AGGR_AVAIL_QTY,0);
        dataTable2.setValueOnOtherModule ("evs_ProductSearch","specificProduct",SKUCode,0);

    }

    public void getDataFromSAPDBAfterCheckout(ExtentTest test) throws IOException, SQLException {
        hana hn =connectToSap(test) ;
        String channelID=dataTable2.getValueOnOtherModule("EVS_SapRSIGetDataFromSAPDB","channelID",0);
        String ARTICLE_ID=dataTable2.getValueOnOtherModule("EVS_SapRSIGetDataFromSAPDB","SKUCode",0);
        String AGGR_AVAIL_QTY=dataTable2.getValueOnOtherModule ("EVS_SapRSIGetDataFromSAPDB","AGGR_AVAIL_QTY",0);
        String rough_stock_value=dataTable2.getValueOnOtherModule ("EVS_SapRSIGetDataFromSAPDB","rough_stock_value",0);
        String Query= "select * from SAPABAP1.\"/OAA/RSI_SNP\" " +
                "where channel_id = '"+channelID+"' " +
                "and ROUGH_STOCK_DATE >=to_date(now()) " +
                "and ARTICLE_ID='"+ARTICLE_ID+"' " +
                "and rough_stock_value = '"+rough_stock_value+"' " +
                "order by rand() limit 1";

        System.out.println("Query:"+Query);
        ResultSet rs = hn.ExecuteQuery(Query);
        int rowsCountReturned = hn.GetRowsCount(rs);
        System.out.println("rowsCountReturned: "+rowsCountReturned);
        String SKUCode=getColumnValue(hn,rs ,"ARTICLE_ID");
        System.out.println("SKUCode: "+SKUCode);
        String AGGR_AVAIL_QTY_1=getColumnValue(hn,rs ,"AGGR_AVAIL_QTY");;
        String AGGR_AVAIL_QTYAfterOneCheckout=AGGR_AVAIL_QTY_1.split("\\.")[0];
        System.out.println("AGGR_AVAIL_QTYAfterOneCheckout: "+AGGR_AVAIL_QTYAfterOneCheckout);
        AGGR_AVAIL_QTY = String.valueOf((Integer.parseInt(AGGR_AVAIL_QTY)-1));
        dataTable2.setValueOnOtherModule ("EVS_SapRSIGetDataFromSAPDB","SKUCode",SKUCode,0);
        dataTable2.setValueOnOtherModule("EVS_SapRSIGetDataFromSAPDB","AGGR_AVAIL_QTYAfterOneCheckout",AGGR_AVAIL_QTYAfterOneCheckout,0);
        action.CompareResult("AGGR_AVAIL_QTY has reduce by 1 after buying",AGGR_AVAIL_QTY,AGGR_AVAIL_QTYAfterOneCheckout,test);
    }





    public String getColumnValue(hana hn,ResultSet rs ,String tableColumn) throws SQLException {
        List<String> alldatatableColumnValue = hn.GetRowdataByColumnName(rs, tableColumn);
        String tableColumnValue=alldatatableColumnValue.get(0);
        return tableColumnValue;
    }
    public hana connectToSap(ExtentTest test) throws IOException, SQLException {
        String DBinstance = dataTable2.getValueOnOtherModule ("SapRSIGetDataFromSAPDB","DB_Instance",0);
        //ECCQA

        String primaryKey="DB_Instance";
        String conSheet="DB_connection_master";
        String Server =dataTable2.getRowUsingReferenceAndKey(conSheet,primaryKey,DBinstance,"Host");
        String Port =  dataTable2.getRowUsingReferenceAndKey(conSheet,primaryKey,DBinstance,"port");
        String Username =  dataTable2.getRowUsingReferenceAndKey(conSheet,primaryKey,DBinstance,"Username");
        String Password =  dataTable2.getRowUsingReferenceAndKey(conSheet,primaryKey,DBinstance,"Password");
        String TypeOfDB = dataTable2.getRowUsingReferenceAndKey(conSheet,primaryKey,DBinstance,"TypeOfDB");
        hana hn =new hana(TypeOfDB,Server,Port,Username,Password,test);
        return hn;
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

    @FindBy(xpath = "//*[@id=\"container\"]/div/div[2]/div[2]/div[2]/fieldset/div[2]/div/div[2]/table/tbody/tr")
    public WebElement productStoreCount;



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
//        action.explicitWait(5000);
        List<WebElement> storeCount = driver.findElements(By.xpath("//*[@id=\"container\"]/div/div[2]/div[2]/div[2]/fieldset/div[2]/div/div[2]/table/tbody/tr"));
        for ( WebElement i : storeCount ) {
            WebElement z1= i.findElement(By.xpath("./child::td[1]"));
            WebElement z2= i.findElement(By.xpath("./child::td[2]"));
            WebElement z3= i.findElement(By.xpath("./child::td[3]"));
            WebElement z4= i.findElement(By.xpath("./child::td[4]/div/div[2]/input"));
            WebElement z5= i.findElement(By.xpath("./child::td[5]"));
            WebElement z6= i.findElement(By.xpath("./child::td[6]"));
            String store=dataTable2.getValueOnOtherModule("SapRSIGetDataFromSAPDB","Store",0);
            if(z1.getText().equals(store)) {
                System.out.println(z1.getText());
                System.out.println(z2.getText());
                System.out.println(z3.getText());
                System.out.println(z4.getAttribute("value"));
                System.out.println(z5.getText());
                System.out.println(z6.getText());
                System.out.println("----");
                dataTable2.getValueOnOtherModule("SapRSIGetDataFromSAPDB","AGGR_AVAIL_QTY",0);
                action.CompareResult(" Item Qty SapDB ", z4.getAttribute("value"), dataTable2.getValueOnOtherModule("SapRSIGetDataFromSAPDB","AGGR_AVAIL_QTY",0), test);

            }

        }
        action.click(sapDataTab,"sapDataTab",test);
        System.out.println(action.getText(roughStockIndicatorAct,"roughStockIndicator",test));
        action.CompareResult(" rough Stock Indicator SAP DB ", dataTable2.getValueOnOtherModule("SapRSIGetDataFromSAPDB","rough_stock_value",0), action.getText(roughStockIndicatorAct,"roughStockIndicator",test), test);

    }
}
