package SAP_HanaDB;

import com.aventstack.extentreports.ExtentTest;

import ic_MagentoPageObjects.ic_Magento_Login;

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

    int ajaxTimeOutInSeconds = ic_Magento_Login.ajaxTimeOutInSeconds;
	int timeOutInSeconds ;
	int pollingTimeInSeconds;

    LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> dataMap2 =null;
    public SapRSI(WebDriver driver,DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2 = dataTable2;
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

    @FindBy(id = "store-change-button")
    WebElement changeStoreView;
    
    @FindBy(xpath = "//*[@class=\"modal-content\"]//*[contains(text(),'scope')]")
    WebElement changeStoreViewPopUp;
    
    @FindBy(xpath = "//button[@class=\"action-primary action-accept\"]")
    WebElement changeStoreViewAcceptBtn;
    
    public void getDataFromSAPDB(ExtentTest test) throws Exception {
        hana hn =connectToSap(test) ;
        String channelID=dataTable2.getValueOnCurrentModule("channelID");
        String rough_stock_value=dataTable2.getValueOnCurrentModule("rough_stock_value");
        String Query= "select * from SAPABAP1.\"/OAA/RSI_SNP\" " +
                "where channel_id = '"+channelID+"' " +
                "and ROUGH_STOCK_DATE >=to_date(now()) " +
                "and AGGR_AVAIL_QTY>0 and " +
                "rough_stock_value = '"+rough_stock_value+"' " +
                "order by rand() limit 1";

        ResultSet rs = hn.ExecuteQuery(Query,test);
        int rowsCountReturned = hn.GetRowsCount(rs);
        if(rowsCountReturned >= 1) {
        String SKUCode=getColumnValue(hn,rs ,"ARTICLE_ID");
        String AGGR_AVAIL_QTY_1=getColumnValue(hn,rs ,"AGGR_AVAIL_QTY");;
        String AGGR_AVAIL_QTY=AGGR_AVAIL_QTY_1.split("\\.")[0];

        dataTable2.setValueOnCurrentModule("SKUCode",SKUCode);
        dataTable2.setValueOnCurrentModule("AGGR_AVAIL_QTY",AGGR_AVAIL_QTY);
        hn.closeDB();
        }else {
        	hn.closeDB();
        	throw new Exception("No Data Is Returned From SAP");
        }
    }

	public void getDataFromSAPDBAfterCheckout(ExtentTest test) throws Exception {
		hana hn = connectToSap(test);
		String channelID = dataTable2.getValueOnOtherModule("SapRSIGetDataFromSAPDB", "channelID", 0);
		String ARTICLE_ID = dataTable2.getValueOnOtherModule("SapRSIGetDataFromSAPDB", "SKUCode", 0);
		String AGGR_AVAIL_QTY = dataTable2.getValueOnOtherModule("SapRSIGetDataFromSAPDB", "AGGR_AVAIL_QTY", 0);
		String rough_stock_value = dataTable2.getValueOnOtherModule("SapRSIGetDataFromSAPDB", "rough_stock_value", 0);
		pollingTimeInSeconds=Integer.parseInt(dataTable2.getValueOnOtherModule("SapRSIGetDataFromSAPDB", "PollingTimeInSeconds", 0));
		timeOutInSeconds = Integer.parseInt(dataTable2.getValueOnOtherModule("SapRSIGetDataFromSAPDB", "TImeOutInSeconds", 0));
		boolean quantityExistance =true;
		
		action.click(catalogTab, "catalogTab", test);
		action.waitUntilElementIsDisplayed(productsTab, 10000);
		action.click(productsTab, "productsTab", test);
		//action.explicitWait(9000);
		action.waitForPageLoaded(ajaxTimeOutInSeconds);
		action.ajaxWait(ajaxTimeOutInSeconds, test);
		action.explicitWait(5000);
		if (action.waitUntilElementIsDisplayed(Clearbutton, 10000)) {
			action.javaScriptClick(Clearbutton, "Clearbutton", test);
			action.ajaxWait(ajaxTimeOutInSeconds, test);
		}		
		action.click(magentoFilterTab, "magentoFilterTab", test);
		action.ajaxWait(ajaxTimeOutInSeconds, test);
		action.writeText(sku, ARTICLE_ID, "skuInputTest", test);
		action.click(magentoApplyFilterTab, "magentoApplyFilterTab", test);
		action.ajaxWait(ajaxTimeOutInSeconds, test);
		if (action.waitUntilElementIsDisplayed(clickEdit, 6000)) {
			action.javaScriptClick(clickEdit, "clickEdit", test);
			action.waitForPageLoaded(ajaxTimeOutInSeconds);
			action.ajaxWait(ajaxTimeOutInSeconds, test);
		} else {
			throw new Exception("No Records Have Been Found");
		}
		String AGGR_AVAIL_QTYAfterOneCheckout = "" ;
		String Query = "select * from SAPABAP1.\"/OAA/RSI_SNP\" " + "where channel_id = '" + channelID + "' "
				+ "and ROUGH_STOCK_DATE >=to_date(now()) " + "and ARTICLE_ID='" + ARTICLE_ID + "' "
				+ "and rough_stock_value = '" + rough_stock_value + "' " + "order by rand() limit 1";
		int polling = pollingTimeInSeconds;
		for(int x = polling;pollingTimeInSeconds<=timeOutInSeconds;) {
		ResultSet rs = hn.ExecuteQuery(Query,test);
		int rowsCountReturned = hn.GetRowsCount(rs);
		  if(rowsCountReturned >= 1) {
		String SKUCode = getColumnValue(hn, rs, "ARTICLE_ID");
		String AGGR_AVAIL_QTY_1 = getColumnValue(hn, rs, "AGGR_AVAIL_QTY");
		AGGR_AVAIL_QTYAfterOneCheckout = AGGR_AVAIL_QTY_1.split("\\.")[0];

		if(AGGR_AVAIL_QTY.equalsIgnoreCase(AGGR_AVAIL_QTYAfterOneCheckout)) {
			action.CompareResult("AGGR_AVAIL_QTY In SAPDB After Sales Order", AGGR_AVAIL_QTY,AGGR_AVAIL_QTYAfterOneCheckout, test);	
			quantityExistance =false;
			break;
		}else {
			action.explicitWait(x*1000);
			pollingTimeInSeconds += x;						
		}
		
		 }else {
	        	hn.closeDB();
	        	throw new Exception("No Data Is Returned From SAP");
	        }	
		}
		if(quantityExistance) {
			action.CompareResult("AGGR_AVAIL_QTY In SAPDB After Sales Order After Waiting For " + timeOutInSeconds/60 + " Minutes", AGGR_AVAIL_QTY, AGGR_AVAIL_QTYAfterOneCheckout, test);
			hn.closeDB();
		}else {
			hn.closeDB();
		}

		
		//action.explicitWait(5000);
		List<WebElement> storeCount = driver.findElements(By.xpath("//*[@class=\"data-row\"]"));
		storeCount.addAll(driver.findElements(By.xpath("//*[@class=\"data-row _odd-row\"]")));
		for (WebElement i : storeCount) {
			WebElement z1 = i.findElement(By.xpath("./child::td[1]"));
			WebElement z4 = i.findElement(By.xpath("./child::td[4]/div/div[2]/input"));
			String store = dataTable2.getValueOnOtherModule("SapRSIGetDataFromSAPDB", "Store", 0);
			if (z1.getText().equals(store)) {
				action.scrollElemetnToCenterOfView(i, "Data Table", test);
				/*
				 * System.out.println(z1.getText()); System.out.println(z2.getText());
				 * System.out.println(z3.getText());
				 * System.out.println(z4.getAttribute("value"));
				 * System.out.println(z5.getText()); System.out.println(z6.getText());
				 * System.out.println("----");
				 */
				action.CompareResult("Magento Item Quantity After Sales Order ",
						dataTable2.getValueOnOtherModule("SapRSIGetDataFromSAPDB", "AGGR_AVAIL_QTY", 0),
						z4.getAttribute("value"), test);

					}
		}
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


    public void getRSIItemInMagento(ExtentTest test) throws IOException, Exception {
        action.click(catalogTab,"catalogTab",test);
        action.waitUntilElementIsDisplayed(productsTab, 10000);
        action.javaScriptClick(productsTab,"productsTab",test);
        action.waitForPageLoaded(ajaxTimeOutInSeconds);
		action.ajaxWait(ajaxTimeOutInSeconds, test);
		
        if(action.waitUntilElementIsDisplayed(Clearbutton, 10000)) {
        action.javaScriptClick(Clearbutton,"Clearbutton",test);
        action.ajaxWait(ajaxTimeOutInSeconds, test);
        }
        
        action.javaScriptClick(magentoFilterTab,"magentoFilterTab",test);
        action.writeText(sku,dataTable2.getValueOnOtherModule ("SapRSIGetDataFromSAPDB","SKUCode",0),"skuInputTest",test);
        action.click(magentoApplyFilterTab,"magentoApplyFilterTab",test);        
        action.ajaxWait(ajaxTimeOutInSeconds, test);
        
        if(action.waitUntilElementIsDisplayed(clickEdit, 6000)) {
        action.javaScriptClick(clickEdit, "clickEdit", test);        
        action.waitForPageLoaded(ajaxTimeOutInSeconds);
		action.ajaxWait(ajaxTimeOutInSeconds, test);
        }else {        	
        	throw new Exception("No Records Have Been Found");        	
        }
    
      		action.click(changeStoreView, "Change Store View", test);
      		action.explicitWait(2000);
      		action.click(changeStoreView.findElement(By.xpath("./following::ul//a[contains(text(),'Incredible Connection')]")), "Select Special Store View", test);
      						
      		action.elementExistWelcome(changeStoreViewPopUp, ajaxTimeOutInSeconds, "Change Store View", test);
      		action.click(changeStoreViewAcceptBtn, "Accept Change View", test);
      		action.waitForPageLoaded(ajaxTimeOutInSeconds);
      		action.ajaxWait(ajaxTimeOutInSeconds, test);
        
//        action.explicitWait(5000);
        List<WebElement> storeCount = driver.findElements(By.xpath("//*[@id=\"container\"]/div/div[2]/div[2]/div[2]/fieldset/div[2]/div/div[2]/table/tbody/tr"));
        for ( WebElement i : storeCount ) {
            WebElement z1= i.findElement(By.xpath("./child::td[1]"));
            WebElement z4= i.findElement(By.xpath("./child::td[4]/div/div[2]/input"));
            String store=dataTable2.getValueOnOtherModule("SapRSIGetDataFromSAPDB","Store",0);
            if(z1.getText().equals(store)) {
				/*
				 * System.out.println(z1.getText()); System.out.println(z2.getText());
				 * System.out.println(z3.getText());
				 * System.out.println(z4.getAttribute("value"));
				 * System.out.println(z5.getText()); System.out.println(z6.getText());
				 * System.out.println("----");
				 */
                dataTable2.getValueOnOtherModule("SapRSIGetDataFromSAPDB","AGGR_AVAIL_QTY",0);
                action.CompareResult(" Item Qty SapDB ", z4.getAttribute("value"), dataTable2.getValueOnOtherModule("SapRSIGetDataFromSAPDB","AGGR_AVAIL_QTY",0), test);

            }

        }
        action.waitUntilElementIsDisplayed(sapDataTab, 10000);
        action.click(sapDataTab,"sapDataTab",test);
        action.scrollElemetnToCenterOfView(roughStockIndicatorAct, "Rough Stock Indicator", test);
        
        timeOutInSeconds = Integer.parseInt(dataTable2.getValueOnOtherModule("SapRSIGetDataFromSAPDB", "TImeOutInSeconds", 0));
        pollingTimeInSeconds=Integer.parseInt(dataTable2.getValueOnOtherModule("SapRSIGetDataFromSAPDB", "PollingTimeInSeconds", 0));
        
        int polling = pollingTimeInSeconds;        
        boolean roughStockFlag = true;
		for(int x = polling;pollingTimeInSeconds<=timeOutInSeconds;) {
		String roughStockValue = action.getAttribute(roughStockIndicatorAct, "value");// roughStockIndicatorAct.getText();		
        if(roughStockValue.isEmpty() | roughStockValue == null) {
        	action.refresh();   
        	action.ajaxWait(ajaxTimeOutInSeconds, test);
        	action.click(sapDataTab,"sapDataTab",test);
            action.scrollElemetnToCenterOfView(roughStockIndicatorAct, "Rough Stock Indicator", test);
            action.explicitWait(x*1000);
        	pollingTimeInSeconds += x;
        }else {
        action.CompareResult(" rough Stock Indicator SAP DB ", dataTable2.getValueOnOtherModule("SapRSIGetDataFromSAPDB","rough_stock_value",0), roughStockValue, test);
        roughStockFlag = false;
        break;
        }
    }
		if(roughStockFlag) {
			action.CompareResult("Rough Stock Indicator has not appeared in " + timeOutInSeconds/60 + " minutes", "true", "false", test);
		}
    }
    
    public void getSellableArticle(ExtentTest test) throws Exception {
  	  	hana hn =connectToSap(test) ;
        String channelID=dataTable2.getValueOnOtherModule("SapRSIGetDataFromSAPDB","channelID",0);
			
		String Query = "select * from SAPABAP1.\"/OAA/RSI_SNP\" where " + "channel_id = '" + channelID + "' and "
				+ "ROUGH_STOCK_DATE >=to_date(now())and" + " AGGR_AVAIL_QTY between 1 and 50000 "
				+ "and rough_stock_value = 'G' order by rand() limit 1";
 
			/*
			 * String Query =
			 * "select * from SAPABAP1.\"/OAA/RSI_SNP\" where channel_id = 'SO08' and ROUGH_STOCK_DATE >=to_date(now())"
			 * +
			 * "and AGGR_AVAIL_QTY between 1 and 50000 and rough_stock_value = 'G' and article_id = '000000000010115998' order by rand() limit 1"
			 * ;
			 */
		
        ResultSet rs = hn.ExecuteQuery(Query,test);
        int rowsCountReturned = hn.GetRowsCount(rs);
        if(rowsCountReturned >= 1) {
        String SKUCode=getColumnValue(hn,rs ,"ARTICLE_ID");
        String AGGR_AVAIL_QTY_1=getColumnValue(hn,rs ,"AGGR_AVAIL_QTY");
        String AGGR_AVAIL_QTYFinal=AGGR_AVAIL_QTY_1.split("\\.")[0];
        String AGGR_AVAIL_QTY_AFTER_CHECKOUT = String.valueOf((Integer.parseInt(AGGR_AVAIL_QTYFinal)-1));
        dataTable2.setValueOnOtherModule ("SapRSIGetDataFromSAPDB","SKUCode",SKUCode,0);
        dataTable2.setValueOnOtherModule("ProductSearch", "specificProduct", SKUCode, 0);
        dataTable2.setValueOnOtherModule("SapRSIGetDataFromSAPDB","AGGR_AVAIL_QTY",AGGR_AVAIL_QTY_AFTER_CHECKOUT,0);
        hn.closeDB();
        }else {
        	hn.closeDB();
        	throw new Exception("No Data Is Returned From SAP");
        }
  }

}
