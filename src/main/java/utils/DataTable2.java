package utils;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import Logger.Log;


public class DataTable2 {

	//Added custom sheet for TA31...Change it when merging
    public static String TESTDATA_FILENAME="src/test/resources/data/jdgroup.xlsx";
    public static ExcelFunctions excelFunc=new ExcelFunctions();
    public static ConcurrentHashMap<String, String> dataMap = null;
    public static LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> dataMap2 = null;
    public static int testCaseID = -1;
    public static String currentModule = "";
    public static int occCount = -1;
    static Logger logger = Log.getLogData(DataTable.class.getSimpleName());
    public static void initializeTestDataWorkbook()
    {
        excelFunc.initializeExcelSheet(TESTDATA_FILENAME);
    }
    public void setPath(String moduleName)
    {
        TESTDATA_FILENAME="src/test/resources/data/jdgroup"+moduleName.toUpperCase()+ ".xlsx";
    }
    public  LinkedHashMap<String, LinkedHashMap<String,ArrayList<String>>> getExcelData() throws Exception
    {
        initializeTestDataWorkbook();
        dataMap=new ConcurrentHashMap <String, String>();
        dataMap2=new LinkedHashMap<String, LinkedHashMap<String,ArrayList<String>>>();
        dataMap2=excelFunc.getExcelData();
        return dataMap2;
    }
    public String filePath(){
        return TESTDATA_FILENAME;
    }
    public void setTestCaseID(int TestCaseID){
        this.testCaseID=TestCaseID;
    }
    public void setModule(String currentModule){
        this.currentModule=currentModule;
    }
    public void setOccurenceCount(int occCount){
        this.occCount=occCount;
    }


    public LinkedHashMap<String, ArrayList<String>> getModuleData(String sheetName){
        return dataMap2.get(sheetName);
    }

    public int findRowToRun(String SheetName,int occCount,int testcaseID){
        int numberRows=getSheetRows(SheetName);
        int rowNumber=-1;
        occCount=occCount+1;
        for(int i=0;i<numberRows;i++){
            if(dataMap2.get(SheetName).get("TCID").get(i).equals(Integer.toString(testcaseID))&&dataMap2.get(SheetName).get("occurence").get(i).equals(Integer.toString(occCount))){
                rowNumber=i;
            }
        }
        return rowNumber;
    }

    public int getSheetRows(String sheetName){
        return dataMap2.get(sheetName).get("TCID").size();
    }
    public String getValueOnOtherModule(String sheetName,String colName,int occCount){
        //occCount = 0 refers occurence 1 in the excelsheet and so on.
        return dataMap2.get(sheetName+"++").get(colName).get(findRowToRun(sheetName+"++",occCount,testCaseID));
    }
    public String getValueOnCurrentModule(String colName){
        return dataMap2.get(currentModule+"++").get(colName).get(findRowToRun(currentModule+"++",occCount,testCaseID));
    }
    public String setValueOnCurrentModule(String colName,String colValue){
        return dataMap2.get(currentModule+"++").get(colName).set(findRowToRun(currentModule+"++",occCount,testCaseID),colValue);
    }
    public String setValueOnOtherModule(String sheetName,String colName,String colValue,int occCount){
        //occCount = 0 refers occurence 1 in the excelsheet and so on.
        return dataMap2.get(sheetName+"++").get(colName).set(findRowToRun(sheetName+"++",occCount,testCaseID),colValue);
    }

    /*
        SheetName:name of sheet to get data from
        keyColumnName: primary key column to search for valueInColumn
        valueInColumn: primary value to use and get row
        columnValueToGet: value in row to get
     */

    public String getRowUsingReferenceAndKey(String SheetName,String keyColumnName,String valueInColumn,String columnValueToGet){
        LinkedHashMap<String, ArrayList<String>> sheetData =dataMap2.get(SheetName+"++");
        int finalrow=-1;
        int noOfRows = sheetData.get(keyColumnName).size();
        for(int i =0;i<noOfRows;i++){
            if(valueInColumn.equals(sheetData.get(keyColumnName).get(i))){
                finalrow=i;
                break;
            }
        }
        return dataMap2.get(SheetName+"++").get(columnValueToGet).get(finalrow);
    }

    public void setRowUsingReferenceAndKey(String SheetName,String keyColumnName,String valueInColumn,String columnValueToSet,String ValueToSet) throws IOException {
        LinkedHashMap<String, ArrayList<String>> sheetData =dataMap2.get(SheetName+"++");
        int finalrow=-1;
        int noOfRows = sheetData.get(keyColumnName).size();
        for(int i =0;i<noOfRows;i++){
            if(valueInColumn.equals(sheetData.get(keyColumnName).get(i))){
                finalrow=i;
                break;
            }
        }

        dataMap2.get(SheetName+"++").get(columnValueToSet).set(finalrow,ValueToSet);
        excelFunc.updateSheet(SheetName+"++",TESTDATA_FILENAME);
    }
    public String getCurrentModule(){
        return currentModule;
    }

    public int getCurrentOccCount(){
        return occCount;
    }
    public int getCurrentTestCaseID(){
        return testCaseID;
    }

}
