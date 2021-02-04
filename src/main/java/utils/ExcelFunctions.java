package utils;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Logger.Log;


/**
 * <copyright file="ExcelFunction.java" company="Modi's Consulting Group"> Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic,
 * mechanical or otherwise, is prohibited without the prior written permission
 * of the copyright owner. </copyright>
 * 
 */

public class ExcelFunctions {

	private static XSSFWorkbook workbook;
	private static XSSFSheet sheet;
	private static int sheetNumber;
	private static ConcurrentHashMap<String, String> dataMap = null;
	private static HashMap<String, HashMap<String, ArrayList<String>>> dataMap2 = null;
	static Logger logger = Log.getLogData(ExcelFunctions.class.getSimpleName());



	/**
	 * @param fileLocation
	 *            Location Location of the excel file
	 */

	public  void initializeExcelSheet(String fileLocation) {
		try {
			FileInputStream fis = new FileInputStream(fileLocation);
			workbook = new XSSFWorkbook(fis);
		} catch (Exception e) {
			logger.error("Error in excel initialization");
			e.printStackTrace();
		}
	}

	/**
	 * @param sheetName
	 *            Name of the configuration to be executed.
	 * @return Returns the sheet number of corresponding configuration Name
	 */

	public  int getSheetNumber(String sheetName) {

		int numberOfSheets = workbook.getNumberOfSheets();
		for (int i = 0; i < numberOfSheets; i++) {
			Sheet aSheet = workbook.getSheetAt(i);
			if (aSheet.getSheetName().equals(sheetName)) {
				sheetNumber = i;
				break;

			}
		}
		return sheetNumber;
	}

	/**
	 * @param testcaseName
	 *            Name of the configuration to be executed.
	 * @return Row number corresponding to the configuration name
	 * 
	 */

	public  String locateTestCaseRow(String testcaseName,int sheetNumber) {
		sheet = workbook.getSheetAt(sheetNumber);
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellType() == CellType.STRING) {
					if (cell.getRichStringCellValue().getString().trim()
							.equals(testcaseName)) {
						return Integer.toString(row.getRowNum());
					}
				}
			}
		}
		return "";
	}

	/**
	 * @param sheetNumber
	 *            Sheet number of the configuration
	 * @param value
	 *            Value to be searched
	 * @return Returns start row number based on the value
	 * 
	 */




	/**
	 * @param sheetNumber
	 *            Sheet number of the configuration
	 * @param startRowNumber
	 *            Value to be searched
	 * @return Returns last row number based on the value
	 * 
	 */

	public  int findEndRow(int sheetNumber, int startRowNumber) {
		int rowNo =0;
		try {
			sheet = workbook.getSheetAt(sheetNumber);
			for(rowNo=startRowNumber;rowNo<=sheet.getLastRowNum();rowNo++)
			{
				Row row = sheet.getRow(rowNo);

				Cell cell = row.getCell(1);
				//System.out.println("cell value"+cell.getStringCellValue());
				if ((cell.getCellType() == CellType.BLANK | cell == null))
					break;

			}
		} catch (Exception e) {
			logger.info("Cell is empty");

		}
		return rowNo;
	}

	/**
	 * @param sheetNumber
	 *            Sheet number of the configuration
	 * @return Returns last row number of a particular sheet
	 * 
	 */

	public  int findLastRow(int sheetNumber) {
		sheet = workbook.getSheetAt(sheetNumber);
		return sheet.getLastRowNum();
	}


	/**
	 * @param sheetNumber
	 *            Sheet number of the configuration
	 * @param rowNumber
	 *            Value to be searched
	 * @return Returns last row number of a particular sheet
	 * 
	 */

	public  int findLastColumn(int rowNumber,int sheetNumber) {
		sheet = workbook.getSheetAt(sheetNumber);
		return sheet.getRow(rowNumber).getLastCellNum();
	}
	/**
	 * @param RowNo
	 *            number row number of the configuration
	 * @param sheetNumber
	 *            number sheet number of the configuration
	 * @return Returns the operation type (Insert/Update/Delete)
	 * 
	 */

	public  String getCellData(int RowNo, int columnNumber,int sheetNumber) {
		sheet = workbook.getSheetAt(sheetNumber);
		Row row = sheet.getRow(RowNo);
		Cell cell = row.getCell(columnNumber);
		if (!(cell == null || cell.getCellType() == CellType.BLANK))
			return sheet.getRow(RowNo).getCell(columnNumber).getStringCellValue();
		else
			return "";
	}

	/**
	 * @param keyRowNo
	 *            row number row number of the headers in excel.
	 * @param valueRowNo
	 *            row number row number of the configuration to be executed
	 * @param columnName
	 *            name column name for which data is to be fetched
	 * @param sheetNumber
	 *            sheet number of the configuration
	 * @return Returns cell data for the corresponding row and columns number
	 * 
	 */
	public  String getCellData(int keyRowNo, int valueRowNo,
			String columnName, int sheetNumber) {

		sheet = workbook.getSheetAt(sheetNumber);
		int noOfColumns = sheet.getRow(keyRowNo).getLastCellNum();

		int colNo;
		for (colNo = 0; colNo <= noOfColumns - 1; colNo++) {
			String key = sheet.getRow(keyRowNo).getCell(colNo)
					.getStringCellValue();
			if (key.contains(columnName))
				break;
		}

		return sheet.getRow(valueRowNo).getCell(colNo).getStringCellValue();

	}

	/**
	 * @param sheetNumber
	 *            Sheet number of the configuration
	 * @return Returns number of the iterations
	 * 
	 */

	public  int getNumberofIterations(int sheetNumber) {
		sheet = workbook.getSheetAt(sheetNumber);
		return sheet.getLastRowNum();
	}



	/**
	 * @param cell
	 *            cell of the excel sheet
	 * 
	 * @return Returns the String value of each cell irrespective of its data
	 *         type
	 * 
	 */
	public static  String getExcelDataBasedOnCellType(Cell cell) {
		String value = "";
		try
		{
//			System.out.println("celltype:"+cell.getCellType());
			switch (cell.getCellType()) {
			case STRING:
				value = cell.getStringCellValue();
				break;
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					//	System.out.print(dateFormat.format(cell.getDateCellValue()) + "\t\t");
					value=dateFormat.format(cell.getDateCellValue());
				} 
				else
				{
					Double value1 = cell.getNumericCellValue();
					Long longValue = value1.longValue();
					value = new String(longValue.toString());
				}
				break;
			case BOOLEAN:
				value = Boolean.toString(cell.getBooleanCellValue());
				break;
			case FORMULA:
//				System.out.println(cell.getCellFormula());
				break;
			case BLANK:
				value="";
				break;
			case _NONE:
				value="";
				break;
			default:
				break;
			}
		}
		catch(NullPointerException e)
		{
//			e.printStackTrace();
			logger.error("There is a exception in reading the data from excel");
			return "";
		}
			return value;
		}
		/**
		 *            row number row number of the configuration to be executed
		 * @param startRowNumber
		 *            column number column number from where data needs to be read.
		 * @param endRowNumber
		 *            column number column number till where the data has to be read
		 *
		 */


		public  ConcurrentHashMap<String, String> getExcelData(int startRowNumber,
				int endRowNumber, int endColumnNumber,int sheetNumber) {
			sheet = workbook.getSheetAt(sheetNumber);
			dataMap = new ConcurrentHashMap<String,String>();
			for(int columnNumber=1;columnNumber<endColumnNumber;columnNumber++){
				String key  = sheet.getRow(startRowNumber).getCell(columnNumber).getStringCellValue();
				Row row = sheet.getRow(startRowNumber+1);
				Cell cell = row.getCell(columnNumber);
				String value=getExcelDataBasedOnCellType(cell);
				dataMap.put(key, value);
			}

			return dataMap;

		}
	public  HashMap<String, HashMap<String, ArrayList<String>>> getExcelData() {
//		sheet = workbook.getSheetAt(sheetNumber);
		HashMap<String, Integer> allKeys=new HashMap<String, Integer>();
		int numSheets=workbook.getNumberOfSheets();
		dataMap = new ConcurrentHashMap<String,String>();
		ArrayList<String> columArray=null;
		HashMap<String, ArrayList<String>> mySheetMap =null;
		String[] headers = null;
		dataMap2 = new HashMap<String, HashMap<String, ArrayList<String>>>();
		int numberOfSiuts=0;

		for(int i=0;i<=numSheets;i++){
			if(i==0){
				System.out.println("i:"+i);
				sheet = workbook.getSheet("Suites");
				int numRows=sheet.getLastRowNum()+1;
				mySheetMap=new HashMap<String, ArrayList<String>>();
				Row row = sheet.getRow(0);
				int noOfColumns = row.getLastCellNum();
				headers= new String [noOfColumns];
				for(int j=0;j<numRows;j++){
					row = sheet.getRow(j);
					for(int z=0;z<noOfColumns;z++){
						Cell cell = row.getCell(z);
						String value=getExcelDataBasedOnCellType(cell);
						if(j==0){

							headers[z]=value;
							mySheetMap.put(value,new ArrayList<>());
						}else{
							Object Key = mySheetMap.keySet().toArray()[z];
							mySheetMap.get(headers[z]).add(value);
						}
					}
				}
				dataMap2.put("Suites",mySheetMap);
				numSheets=dataMap2.get("Suites").get("Execute").size();
			}else{
				String execute=dataMap2.get("Suites").get("Execute").get(i-1);
				String SheetName = dataMap2.get("Suites").get("testSuitName").get(i-1);
				System.out.println("SheetName:" + SheetName);

				if(execute.toLowerCase().equals("yes")) {
					sheet = workbook.getSheet(SheetName);
					int numRows = sheet.getLastRowNum() + 1;
					mySheetMap = new HashMap<String, ArrayList<String>>();
					Row row = sheet.getRow(0);
					int noOfColumns = row.getLastCellNum();
					headers = new String[noOfColumns];
					for (int j = 0; j < numRows; j++) {
						row = sheet.getRow(j);
						for (int z = 0; z < noOfColumns; z++) {
							Cell cell = row.getCell(z);
							String value = getExcelDataBasedOnCellType(cell);
							if (j == 0) {
								headers[z] = value;
								mySheetMap.put(value, new ArrayList<>());
							} else {
								Object Key = mySheetMap.keySet().toArray()[z];
								mySheetMap.get(headers[z]).add(value);
								if(z>2&&!value.equals("")){

									if(!allKeys.containsKey(value)){
										allKeys.put(value,0);
									}else{
										int occKeyNum=allKeys.get(value);
										occKeyNum++;
										allKeys.put(value,occKeyNum);
									}
								}
							}
						}
					}
					dataMap2.put(SheetName, mySheetMap);
				}
			}
		}

		//read all data sheets
		Object[] keys = allKeys.keySet().toArray();
		for(int i=0;i<keys.length;i++){

			String SheetName =(String)keys[i]+"++";
			sheet = workbook.getSheet(SheetName);
			int numRows = sheet.getLastRowNum() + 1;
			mySheetMap = new HashMap<String, ArrayList<String>>();
			Row row = sheet.getRow(0);
			int noOfColumns = row.getLastCellNum();
			headers = new String[noOfColumns];
			for (int j = 0; j < numRows; j++) {
				row = sheet.getRow(j);
				for (int z = 0; z < noOfColumns; z++) {
					Cell cell = row.getCell(z);
					String value = getExcelDataBasedOnCellType(cell);
					if (j == 0) {
						headers[z] = value;
						mySheetMap.put(value, new ArrayList<>());
					} else {
						Object Key = mySheetMap.keySet().toArray()[z];
						mySheetMap.get(headers[z]).add(value);
						if(z>2&&!value.equals("")){

							if(!allKeys.containsKey(value)){
								allKeys.put(value,0);
							}else{
								int occKeyNum=allKeys.get(value);
								occKeyNum++;
								allKeys.put(value,occKeyNum);
							}
						}
					}
				}
			}
			dataMap2.put(SheetName, mySheetMap);
		}

		return dataMap2;

	}
		public   Map<Object, Object> getRowData(int keytRowNumber,
				int valueRowNumber, int columnNumber,int sheetNumber) {
			sheet = workbook.getSheetAt(sheetNumber);
		 	Map<Object, Object> datamap = new HashMap<>();
			String key  = sheet.getRow(keytRowNumber).getCell(columnNumber).getStringCellValue();
			Row row = sheet.getRow(valueRowNumber);
			Cell cell = row.getCell(columnNumber);
			String value=getExcelDataBasedOnCellType(cell);
			datamap.put(key, value);
			return datamap;

		}
	}
