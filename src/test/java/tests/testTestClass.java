package tests;

import KeywordManager.KMCKeyManager;
import base.TestCaseBase;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import utils.*;
import Logger.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

//@Listeners(listner.TestNGListener.class)

public class testTestClass<moduleName> {
    public WebDriver driver;
    protected DataTable dataTable = null;
    protected ConfigFileReader configFileReader;
    protected String browserName;
    protected String navigateURL;
    protected DataTable2 dataTable2;

    public String currentSuite;
    public String currentKeyWord;
    HashMap<String, Integer> occCount=null;
    int testcaseID;
    KMCKeyManager km=null;
    ExtentReportGenerator reportJD=null;
    Logger logger = Log.getLogData(this.getClass().getSimpleName());
    protected LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> dataMap2 = new LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>>();
    Action action = null;
    
    @BeforeClass
    @Parameters({"moduleName","CurSuite"})
    public void once(String moduleName,String CurSuite) throws Exception {
        JavaUtils.deleteFiles("./Reports/screenshots");
        dataTable2= new DataTable2();
        dataTable2.setPath(moduleName);
        dataMap2=dataTable2.getExcelData();
        currentSuite=CurSuite;
        reportJD=new ExtentReportGenerator(currentSuite);
        km=new KMCKeyManager(driver,dataTable2,dataMap2);
    }

    @BeforeMethod
    public void setUp() throws Exception {
        occCount=new HashMap<String, Integer>();
        startBrowserSession();
        action = new Action(driver);

    }
    //Start Tests-----------------------------------------------------------------------
    
    @Test(testName ="35_Sales_Order_Using_EFT_Pro_Payment_Methods_Sleepmasters" )
    public void Sales_Order_Using_EFT_Pro_Payment_Methods_Sleepmasters() throws Exception {
        String testMethodName="Sales_Order_Using_EFT_Pro_Payment_Methods_Sleepmasters";
        ExtentTest test =reportJD.createTest("35_Sales_Order_Using_EFT_Pro_Payment_Methods_Sleepmasters");
        int TCIndex=getTestCaseIndex(testMethodName);
        runner(TCIndex,test);
    }

    //End Tests-------------------------------------------------------------------------

    public void runner(int TCIndex,ExtentTest test) throws Exception {
        try {
            runAllKeys(TCIndex,test);
            endBrowserSession();
            test.getStatus();
            if(!test.getStatus().toString().toLowerCase().equals("pass")){
                throw new Exception();
            }
        } catch (Exception e) {
            endBrowserSession();
            e.printStackTrace();
            throw e;
        }
    }

    public int getTestCaseIndex(String testMethodName){
        System.out.println("------------------------------------------------------------");
        System.out.println("Test_Case_Name:"+testMethodName);
        int numTC=dataMap2.get(currentSuite).get("Test_Case_Name").size();
        int index=0;
        for(int i=0;i<numTC;i++){
            if(testMethodName.equals(dataMap2.get(currentSuite).get("Test_Case_Name").get(i)+"_"+currentSuite)){
                index=i;
                testcaseID=Integer.parseInt(dataMap2.get(currentSuite).get("TestCaseID").get(i));
                dataTable2.setTestCaseID(testcaseID);
            }
        }
        return index;
    }

    public void runAllKeys(int index, ExtentTest test) throws Exception {
            ExtentTest node=null;
            ExtentTest test1=null;
            try {
                    for (int j = 0; j < 40; j++) {
                        String actionToRunLable = "Action" + (j + 1);
                        String actionToRun = "";
                        try {
                            actionToRun = dataMap2.get(currentSuite).get(actionToRunLable).get(index);
                            test1 = test.createNode(actionToRun);
                        } catch (Exception e) {

                        }
                        currentKeyWord = actionToRun;
                        dataTable2.setOccurenceCount(0);
                        dataTable2.setModule(currentKeyWord);
                        if (!actionToRun.equals("")) {
                            System.out.println("xxxxxxxxxxxxx :" + actionToRun);
                            if (!occCount.containsKey(currentKeyWord)) {
                                occCount.put(currentKeyWord, 0);
                            } else {
                                int occNum = occCount.get(currentKeyWord);
                                occNum++;
                                occCount.put(currentKeyWord, occNum);
                            }
                            dataTable2.setOccurenceCount(occCount.get(currentKeyWord));
                            km.runKeyWord(actionToRun, testcaseID, occCount, test1);
                            KMCTests sample = new KMCTests();
                            sample.writeToExcel(sample.createFile());
                            reportJD.endReport();

                        }
                    }
                }
                catch(Exception e) {
                /*logger.info(e.getMessage());
                e.printStackTrace();
                e.getCause();
                System.out.println(e.getMessage());
                String screenShot = GenerateScreenShot.getScreenShot(driver);
                node.fail(e.getMessage() + node.addScreenCaptureFromPath(screenShot));*/
                e.printStackTrace();
                test1.createNode("Exception").fail(MarkupHelper.createLabel("Exception occurred: ", ExtentColor.RED).getMarkup() + "<br>"+ e.getMessage()+ "</br>", MediaEntityBuilder.createScreenCaptureFromBase64String(action.takeScreenShotAsBase64()).build());
                }
            }


    public void runKeyWord (String actionToRun, ExtentTest test) throws Exception {
        String moduleToRun = actionToRun;
        
        ExtentTest test1 = test.createNode(moduleToRun);
        int rowNumber = -1;
        if (dataMap2.containsKey(currentKeyWord + "++")) {
            rowNumber = findRowToRun(dataMap2.get(currentKeyWord + "++"), occCount.get(currentKeyWord), testcaseID);
        }
        int i = 0;
        WebElement el = null;
        switch (moduleToRun) {
            case "Login":
//                ic.login(dataMap2.get(currentKeyWord + "++"), test1, rowNumber);
//                break;               
//            case "icSearchNoResultsReturned":
//                icReturnNoResults.ic_DoesNotExtistSearch(dataMap2.get(currentKeyWord + "++"), test1, rowNumber);
//                break;
           


        }
    }
    public int findRowToRun(HashMap<String, ArrayList<String>> input,int occCount,int testcaseID){
        int numberRows=input.get("TCID").size();
        int rowNumber=-1;
        occCount=occCount+1;
        for(int i=0;i<numberRows;i++){
            if(input.get("TCID").get(i).equals(Integer.toString(testcaseID))&&input.get("occurence").get(i).equals(Integer.toString(occCount))){
                rowNumber=i;
            }
        }
        return rowNumber;
    }
    public void startBrowserSession()
    {
        driver=null;
        if(driver == null){
            configFileReader = new ConfigFileReader();
            logger.info("Initializing the driver");
            browserName = System.getProperty("BrowserType");
            if(browserName==null){
                logger.info("System property returned Null browser type. So getting data from Config file");
                browserName=ConfigFileReader.getPropertyVal("BrowserType");
            }
            driver = TestCaseBase.initializeTestBaseSetup(browserName);
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
            navigateURL = System.getProperty("URL");
//            if(navigateURL==null){
//                logger.info("System property returned Null URL. So getting data from Config file");
//                navigateURL = ConfigFileReader.getPropertyVal("URL");
//            }
            navigateURL = ConfigFileReader.getPropertyVal("URL");
        }
        logger.info("Navigate to URL");
        //driver.navigate().to(navigateURL);
        driver.manage().window().maximize();
        driver.navigate().refresh();
        logger.info("Browser name is "+browserName);
        logger.info("App URL: "+ navigateURL);
        Values.app= navigateURL;
        Values.browser=browserName;
        km.setDriver(driver);
    }

    public void endBrowserSession() throws IOException {
        driver.quit();
    }


    @AfterClass
        public void closeReport(){
            reportJD.endReport();
        }
}