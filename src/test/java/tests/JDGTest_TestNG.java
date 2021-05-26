package tests;

import JDGroupPageObjects.*;
import KeywordManager.JDGKeyManager;
import SAP_HanaDB.SAPCustomerRelated;
import SAP_HanaDB.SAPorderRelated;
import SAP_HanaDB.SapRSI;
import base.TestCaseBase;
import com.aventstack.extentreports.ExtentTest;
import emailverification.ICGiftCardVerification;
import emailverification.ICWishlistverification;
import emailverification.ic_PasswordForgotEmailVerification;
import emailverification.ic_ResetPasswordEmailLink;
import ic_MagentoPageObjects.*;
import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
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

public class JDGTest_TestNG<moduleName> {
    public WebDriver driver;
    protected DataTable dataTable = null;
    protected ConfigFileReader configFileReader;
    protected String browserName;
    protected String navigateURL;
    protected DataTable2 dataTable2;
    ExtentReportJD reportJD=new ExtentReportJD("IC");
    public String currentSuite;
    public String currentKeyWord;
    HashMap<String, Integer> occCount=null;
    int testcaseID;
    JDGKeyManager km=null;
    Logger logger = Log.getLogData(this.getClass().getSimpleName());
    protected LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> dataMap2 = new LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>>();

    @BeforeClass
    @Parameters({"moduleName"})
    public void once(String moduleName) throws Exception {
        dataTable2= new DataTable2();
        dataTable2.setPath(moduleName);
        dataMap2=dataTable2.getExcelData();
        km=new JDGKeyManager(driver,dataTable2,dataMap2);
    }

    @BeforeMethod
    public void setUp() throws Exception {
        occCount=new HashMap<String, Integer>();
        startBrowserSession();
    }
    //Start Tests-----------------------------------------------------------------------
    //${AllTestMethods}
    @Test(testName ="31_Create_Customer_Account_from_Sales_Order" )
    public void Create_Customer_Account_from_Sales_Order() throws Exception {
        String testMethodName="Create_Customer_Account_from_Sales_Order";
        ExtentTest test =reportJD.createTest(testMethodName);
        int TCIndex=getTestCaseIndex(testMethodName);
        runner(TCIndex,test);
    }

    @Test(testName ="26_Create_Sales_Order_Guest_User_Thorugh_Product_Search" )
    public void Create_Sales_Order_Guest_User_Thorugh_Product_Search() throws Exception {
        String testMethodName="Create_Sales_Order_Guest_User_Thorugh_Product_Search";
        ExtentTest test =reportJD.createTest(testMethodName);
        int TCIndex=getTestCaseIndex(testMethodName);
        runner(TCIndex,test);
    }

    @Test(testName ="2_Create_new_customer_in_IC_with_ID_Number" )
    public void Create_new_customer_in_IC_with_ID_Number() throws Exception {
        String testMethodName="Create_new_customer_in_IC_with_ID_Number";
        ExtentTest test =reportJD.createTest(testMethodName);
        int TCIndex=getTestCaseIndex(testMethodName);
        runner(TCIndex,test);
    }

    @Test(testName ="42_Click_the_IC_logo_to_go_home_page" )
    public void Click_the_IC_logo_to_go_home_page() throws Exception {
        String testMethodName="Click_the_IC_logo_to_go_home_page";
        ExtentTest test =reportJD.createTest(testMethodName);
        int TCIndex=getTestCaseIndex(testMethodName);
        runner(TCIndex,test);
    }
    @Test(testName ="45_Validating_the_minimum_search_characters" )
    public void Validating_the_minimum_search_characters() throws Exception {
        String testMethodName="Validating_the_minimum_search_characters";
        ExtentTest test =reportJD.createTest(testMethodName);
        int TCIndex=getTestCaseIndex(testMethodName);
        runner(TCIndex,test);
    }

    @Test(testName ="Verify_Message_after_submit_email_on_Forgot_Password_Page" )
    public void Verify_Message_after_submit_email_on_Forgot_Password_Page() throws Exception {
        String testMethodName="Verify_Message_after_submit_email_on_Forgot_Password_Page";
        ExtentTest test =reportJD.createTest(testMethodName);
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
        int numTC=dataMap2.get("IC").get("Test_Case_Name").size();
        int index=0;
        for(int i=0;i<numTC;i++){
            if(testMethodName.equals(dataMap2.get("IC").get("Test_Case_Name").get(i))){
                index=i;
                testcaseID=Integer.parseInt(dataMap2.get("IC").get("TestCaseID").get(i));
                dataTable2.setTestCaseID(testcaseID);
            }
        }
        return index;
    }

    public void runAllKeys(int index, ExtentTest test) throws Exception {

        for(int j=0;j<20;j++) {
            String actionToRunLable = "Action" + (j + 1);
            String actionToRun = "";
            try {
                actionToRun = dataMap2.get("IC").get(actionToRunLable).get(index);
            } catch (Exception e) {

            }
            currentKeyWord=actionToRun;
            dataTable2.setOccurenceCount(0);
            dataTable2.setModule(currentKeyWord);
            if(!actionToRun.equals("")) {
                System.out.println("xxxxxxxxxxxxx :"+actionToRun);
                if(!occCount.containsKey(currentKeyWord)){
                    occCount.put(currentKeyWord,0);
                }else{
                    int occNum=occCount.get(currentKeyWord);
                    occNum++;
                    occCount.put(currentKeyWord,occNum);
                }
                dataTable2.setOccurenceCount(occCount.get(currentKeyWord));
//                runKeyWord(actionToRun, test);
                km.runKeyWord(actionToRun,testcaseID,occCount,test);
            }
        }
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
        driver.navigate().to(navigateURL);
        driver.manage().window().maximize();
        driver.navigate().refresh();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.info("Browser name is "+browserName);

        logger.info("App URL: "+ navigateURL);
        Values.app= navigateURL;
        Values.browser=browserName;
        km.setDriver(driver);
    }

    public void endBrowserSession() throws IOException {
        driver.close();
    }

    @AfterClass
    public void closeReport(){
        reportJD.endReport();
    }
}
