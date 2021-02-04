package gui;


import java.util.LinkedHashMap;


import utils.ExcelFunctions;




/**
 * <copyright file="ReadExecutionSheet.java" company="Modi's Consulting group"> Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic,
 * mechanical or otherwise, is prohibited without the prior written permission
 * of the copyright owner. </copyright>
 * 
 * @author 
 */

public class ReadExecutionSheet {


	public static final String DRIVER_SHEET_LOCATION = System.getProperty("user.dir") + "\\src\\test\\resources\\driver.xlsx";
	public static LinkedHashMap<String,String> mainMap=new LinkedHashMap<String,String>();
	protected static ExcelFunctions excel= new ExcelFunctions();

	/**
	 * readDriverSheet() method reads "Execution" sheet of "Driver.xlsx" workbook. If execution status 
	 * is "Yes", suite name/sheet name , application URL and  class name are added to hash map.
	 * @return HashMap with key as suite name and value as "applicationURL$className"
	 * @author Deepa Elenor Mathias
	 */
	public static  LinkedHashMap<String,String> readDriverSheet() {

		int sheetNumber,lastRowNumber;
		excel.initializeExcelSheet(DRIVER_SHEET_LOCATION);
		sheetNumber=excel.getSheetNumber("Main");
		lastRowNumber=excel.findLastRow(sheetNumber);
		for (int rowNo = 1; rowNo <=lastRowNumber  ; rowNo++) {
			String runStatus = excel.getCellData(rowNo, 4, sheetNumber);
			if(runStatus.equals("Yes"))
			{
				String sheetName = excel.getCellData(rowNo, 1, sheetNumber);
				String applicationURL=excel.getCellData(rowNo, 2, sheetNumber);
				String className=excel.getCellData(rowNo, 3, sheetNumber);

				mainMap.put(sheetName,applicationURL+"@"+className);

			}

		}

		return mainMap;
	}




}
