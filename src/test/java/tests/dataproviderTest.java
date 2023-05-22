package tests;

import base.TestCaseBase;
import com.aventstack.extentreports.ExtentTest;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.*;
import utils.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

public class dataproviderTest extends BaseTest {
    ExtentReportGenerator reportJD;
    public String currentSuite;
    public String currentKeyWord;
    HashMap<String, Integer> occCount=null;
    int testcaseID;
    DataTable2 dataTable2;
    LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> dataMap2 = new LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>>();

    @BeforeClass
    public void setUp(){
        reportJD=new ExtentReportGenerator("IC");
    }
    @DataProvider (name = "data-provider")
     public Object[][] dpMethod() throws Exception {
         dataTable2= new DataTable2();
         dataTable2.setPath("MAIN");
         dataMap2=dataTable2.getExcelData();
         //Please update you module name here and copy jdgroupMAIN.xlsx to jdgroupTA104.xlsx

         LinkedHashMap<String, ArrayList<String>> icTestCase=dataMap2.get("IC");
         int numberOfICTestCases=icTestCase.get("Execute").size();
         int count=0;
         String elementBuilder="";
         for(int i=0;i<numberOfICTestCases;i++){
             System.out.println(icTestCase.get("Execute").get(i));
             if(icTestCase.get("Execute").get(i).toLowerCase().equals("yes")){
                 elementBuilder=elementBuilder+icTestCase.get("TestCaseID").get(i)+",";
                 count++;
             }
         }
         elementBuilder = elementBuilder.substring(0, elementBuilder.length() - 1);
         Object[][] dp = new Object[count][3];
         String []testIdArray=elementBuilder.split(",");

         for(int i=0;i<testIdArray.length;i++){
             for(int j=0;j<numberOfICTestCases;j++){
                 if(testIdArray[i].equals(icTestCase.get("TestCaseID").get(j))){
                     dp[i][0]=icTestCase.get("TestCaseID").get(j);
                     dp[i][1]=icTestCase.get("Test Case Name").get(j);
                     dp[i][2]=String.valueOf(j);
                     break;
                 }
             }

         }
         return dp;
     }


     @Test (dataProvider = "data-provider")
     public void myTest (String testcaseIDPara,String testCaseDescriptionPara,String index) throws Exception {
//         System.out.println("Passed Parameter Is : " + val1);
//         System.out.println("Passed Parameter Is : " + val2);
         System.out.println();
         System.out.println();
         System.out.println();
         HashMap<String, ArrayList<String>> singleSuiteData=dataMap2.get("IC");
         occCount=new HashMap<String, Integer>();
         String testCaseDescription=testCaseDescriptionPara;
         testcaseID=Integer.parseInt(testcaseIDPara) ;
         dataTable2.setTestCaseID(testcaseID);
         ExtentTest test=reportJD.createTest(testcaseID+" : "+testCaseDescription);
         startBrowserSession();

//         try {
             for(int j=0;j<1;j++){
                 String actionToRunLable="Action"+(j+1);
                 String actionToRun= "";
                 try {
                     actionToRun = singleSuiteData.get(actionToRunLable).get(Integer.parseInt(index));
                 }catch (Exception e){

                 }
                 currentKeyWord=actionToRun;
                 System.out.println("actionToRunLable:"+actionToRunLable);
                 System.out.println("currentKeyWord:"+currentKeyWord);
                 if(!currentKeyWord.equals("")){
                     if(!occCount.containsKey(currentKeyWord)){
                         occCount.put(currentKeyWord,0);
                     }else{
                         int occNum=occCount.get(currentKeyWord);
                         occNum++;
                         occCount.put(currentKeyWord,occNum);
                     }
//                     dataTable2.setTestCaseID(actionToRun);
                     dataTable2.setOccurenceCount(occCount.get(currentKeyWord));
                     runKeyWord(actionToRun,test);
//						writeToExcel(new File(dataTable2.filePath()));
                     writeToExcel(createFile());

                 }
             }
//         } catch (Exception e) {
//             logger.info(e.getMessage());
//             e.printStackTrace();
//             String screenShot= GenerateScreenShot.getScreenShot(driver);
//             ExtentTest node = test.createNode("Exception");
//             node.fail(e.getMessage()+node.addScreenCaptureFromPath(screenShot));
//         }
         endBrowserSession();
     }

    public void runKeyWord(String actionToRun,ExtentTest test) throws Exception {
        String moduleToRun=actionToRun;
               ExtentTest test1=test.createNode(moduleToRun);
        int rowNumber=-1;
        if(dataMap2.containsKey(currentKeyWord+"++")) {
            rowNumber = findRowToRun(dataMap2.get(currentKeyWord + "++"), occCount.get(currentKeyWord), testcaseID);
        }
        int i = 0;
        switch (moduleToRun) {
           
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
            if(navigateURL==null){
                logger.info("System property returned Null URL. So getting data from Config file");
                navigateURL = ConfigFileReader.getPropertyVal("URL");
            }

            navigateURL = ConfigFileReader.getPropertyVal("URL");
        }
        logger.info("Navigate to URL");
//        driver.navigate().to(navigateURL);
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
    }

    public void endBrowserSession() throws IOException {
        driver.close();
    }
    public void writeToSingleSheet(File filePath, String sheetToUpdate) throws IOException, InvalidFormatException {
        FileOutputStream outputStream = new FileOutputStream(filePath);
        FileInputStream fis = new FileInputStream(filePath);
        XSSFWorkbook workbook2 = new XSSFWorkbook(fis);
        XSSFWorkbook workbook= new XSSFWorkbook();
        XSSFSheet sheet;
        for(int i=0;i<dataMap2.size() ;i++) {
            Object[] keys = dataMap2.keySet().toArray();
            if (!keys[i].toString().toLowerCase().equals("suits") && !keys[i].toString().toLowerCase().equals("ic")) {
                if((sheetToUpdate+"++").equals(keys[i].toString())){
                    sheet = workbook2.createSheet(sheetToUpdate);
                }else{
                    sheet = workbook.getSheet(keys[i].toString());
                }
                int numCol = dataMap2.get(keys[i]).size();
                Object[] colArray = dataMap2.get(keys[i]).keySet().toArray();
                int rowNum = dataMap2.get(keys[i]).get(colArray[0]).size();
                for (int j = 0; j <= rowNum; j++) {
                    Row row = sheet.createRow(j);
                    if (j == 0) {
                        for (int z = 0; z < numCol; z++) {
                            Cell cell = row.createCell(z);
                            cell.setCellValue(colArray[z].toString());
                        }
                    } else {

                        for (int z = 0; z < numCol; z++) {
                            Cell cell = row.createCell(z);
                            cell.setCellValue((String) dataMap2.get(keys[i]).get(colArray[z]).get(j - 1));
                        }
                    }
                }
            }
        }
        workbook.write(outputStream);
        workbook.close();
    }
    public void writeToExcel(File filePath) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(filePath);
        XSSFWorkbook workbook= new XSSFWorkbook();;
        XSSFSheet sheet;
        for(int i=0;i<dataMap2.size() ;i++) {
            Object[] keys = dataMap2.keySet().toArray();
            if (!keys[i].toString().toLowerCase().equals("suits") && !keys[i].toString().toLowerCase().equals("ic")) {
                sheet = workbook.createSheet(keys[i].toString());
                workbook.getSheet(keys[i].toString());
                int numCol = dataMap2.get(keys[i]).size();
                Object[] colArray = dataMap2.get(keys[i]).keySet().toArray();
                int rowNum = dataMap2.get(keys[i]).get(colArray[0]).size();
                for (int j = 0; j <= rowNum; j++) {
                    Row row = sheet.createRow(j);
                    if (j == 0) {
                        for (int z = 0; z < numCol; z++) {
                            Cell cell = row.createCell(z);
                            cell.setCellValue(colArray[z].toString());
                        }
                    } else {

                        for (int z = 0; z < numCol; z++) {
                            Cell cell = row.createCell(z);
                            cell.setCellValue((String) dataMap2.get(keys[i]).get(colArray[z]).get(j - 1));
                        }
                    }
                }
            }
        }

        workbook.write(outputStream);
        workbook.close();
    }

    public File createFile() throws IOException {
        File myObj = new File(System.getProperty("user.dir")+"\\reports\\Datasheet.xlsx");
        return myObj;
    }

    @AfterClass
    public void tearDown(){
        reportJD.endReport();
    }

}
