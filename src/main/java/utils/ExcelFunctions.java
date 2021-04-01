package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
	private static LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> dataMap2 = null;
	static Logger logger = Log.getLogData(ExcelFunctions.class.getSimpleName());
	FileOutputStream outputStream=null;



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
	public  void initializeExcelSheetForWriting(String fileLocation) {
		try {
			FileOutputStream outputStream = new FileOutputStream(fileLocation);
			workbook = new XSSFWorkbook();
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
//			logger.error("There is a exception in reading the data from excel");
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
	public  LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> getExcelData() throws IOException {
//		sheet = workbook.getSheetAt(sheetNumber);
		DataGenerators dg= new DataGenerators();
		LinkedHashMap<String, Integer> allKeys = new LinkedHashMap<String, Integer>();
		int numSheets = workbook.getNumberOfSheets();
		dataMap = new ConcurrentHashMap<String, String>();
		ArrayList<String> columArray = null;
		LinkedHashMap<String, ArrayList<String>> mySheetMap = null;
		String[] headers = null;
		dataMap2 = new LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>>();
		for (int i = 0; i < numSheets; i++) {
			System.out.println("i:" + i);
			sheet = workbook.getSheetAt(i);
			String sheetName=workbook.getSheetName(i);
			logger.info("sheetName :"+sheetName);
			int numRows = sheet.getLastRowNum() + 1;
			mySheetMap = new LinkedHashMap<String, ArrayList<String>>();
			Row row = sheet.getRow(0);
			int noOfColumns = row.getLastCellNum();
			headers = new String[noOfColumns];
			for (int j = 0; j < numRows; j++) {
				row = sheet.getRow(j);
				for (int z = 0; z < noOfColumns; z++) {
					try {
						Cell cell = row.getCell(z);
						String value = getExcelDataBasedOnCellType(cell);
						logger.info(z + ":" + value);
						if (j == 0) {
							headers[z] = value;
							mySheetMap.put(value, new ArrayList<>());

						} else {
							Object Key = mySheetMap.keySet().toArray()[z];
							value=dg.GenerateRequiredData(value);
							mySheetMap.get(headers[z]).add(value);
						}
					}catch (Exception e){

					}
				}
			}
			dataMap2.put(sheetName, mySheetMap);
		}
		workbook.close();
		return dataMap2;
	}
	public void updateSheet(String sheetName,String fileLocation) throws IOException {
		initializeExcelSheetForWriting(fileLocation);
		sheet = workbook.getSheet(sheetName);
		int numCol=dataMap2.get(sheetName).size();
		Object[] colArray = dataMap2.get(sheetName).keySet().toArray();
		int rowNum = dataMap2.get(sheetName).get(colArray[0]).size();
		for(int j=0;j<=rowNum;j++){
			Row row = sheet.createRow(j);
			if (j==0){
				for(int z=0;z<numCol;z++){
					Cell cell = row.createCell(z);
					cell.setCellValue(colArray[z].toString());
				}
			}else{
				for(int z=0;z<numCol;z++){
					Cell cell = row.createCell(z);
					cell.setCellValue((String) dataMap2.get(sheetName).get(colArray[z]).get(j-1));
				}
			}
		}
		workbook.write(outputStream);

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
