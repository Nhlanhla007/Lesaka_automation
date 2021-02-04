package utils;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import Logger.Log;


public class DataTable {

	public static final String TESTDATA_FILENAME="";
	public static ExcelFunctions excelFunc=new ExcelFunctions();
	public static ConcurrentHashMap<String, String> dataMap = null;
	static Logger logger = Log.getLogData(DataTable.class.getSimpleName());

	public static void initializeTestDataWorkbook()
	{
		excelFunc.initializeExcelSheet(TESTDATA_FILENAME);
	}


	public  ConcurrentHashMap<String, String> getExcelData(String sheetName, String testCaseName) throws Exception
	{
		dataMap=new ConcurrentHashMap <String, String>();
		int sheetNumber=excelFunc.getSheetNumber(sheetName);
		String startRowNumber=excelFunc.locateTestCaseRow(testCaseName, sheetNumber);
		if(startRowNumber == "")
		{
			logger.error("not able to locate test case in the test data sheet");
			//throw new Exception("not able to locate test case in the test data sheet");
		}
		else
		{
		int endRowNumber = excelFunc.findEndRow(sheetNumber, Integer.parseInt(startRowNumber));
		logger.info("startRowNumber"+startRowNumber);
		logger.info("endRow"+endRowNumber);
		int endColumNumber=excelFunc.findLastColumn( Integer.parseInt(startRowNumber), sheetNumber);
		logger.info("endColumNumber"+endColumNumber);
		dataMap=excelFunc.getExcelData( Integer.parseInt(startRowNumber),endRowNumber,endColumNumber,sheetNumber);
		}
		return dataMap;


	}
	
	public Object[][] getTestData(String sheetName, String testCaseName) {

		Object[][] obj = null ;
		excelFunc.initializeExcelSheet(TESTDATA_FILENAME);
		int sheetNumber=excelFunc.getSheetNumber(sheetName);
		String startRowNumber=excelFunc.locateTestCaseRow(testCaseName, sheetNumber);
		if(startRowNumber == "")
		{
			logger.error("not able to locate test case in the test data sheet");
		}
		else
		{


			int endRowNumber = excelFunc.findEndRow(sheetNumber, Integer.parseInt(startRowNumber));
			logger.info("startRowNumber"+startRowNumber);
			logger.info("endRow"+endRowNumber);
			int endColumNumber=excelFunc.findLastColumn( Integer.parseInt(startRowNumber), sheetNumber);
			logger.info("endColumNumber"+endColumNumber);

			obj= new Object[(endRowNumber-1)-Integer.parseInt(startRowNumber)][1];

			int k=0;
			for (int i= Integer.parseInt(startRowNumber)+1 ; i < endRowNumber; i++) {
				Map<Object, Object> datamap = new HashMap<>();
				for (int j=1; j < endColumNumber; j++) {
					datamap.putAll(excelFunc.getRowData(Integer.parseInt(startRowNumber),i ,j, sheetNumber));
				}
				obj[k][0] = datamap;
				k++;
			}
		}

		return obj;
	}
}
