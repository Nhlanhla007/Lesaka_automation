package gui;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import utils.Values;



/**
 * <copyright file="ReadTestFlowsSheet.java" company="Modi's Consulting Group"> Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic,
 * mechanical or otherwise, is prohibited without the prior written permission
 * of the copyright owner. </copyright>
 * 
 * @author 
 */
public class ReadTestFlowsSheet extends ReadExecutionSheet{


	private static ArrayList<String> testCaseList;
	private static LinkedHashMap<String,ArrayList<String>> testCaseNameMap= new LinkedHashMap<String, ArrayList<String>>(); 


	/**
	 * readTestFlowSheets() method iterates through hash map created by Read Execution sheet.
	 *  Based on the sheet name, it reads the corresponding flow sheet to get the test method name
	 *  It stores the test case names each flow into array list.
	 * Once all test case names of particular flow are read, they are stored into hashmap with key as flow
	 * suite name
	 * @return HashMap with key as Flow Name and value as array list of testcaseNames
	 * @author Deepa Elenor Mathias
	 */

	public static LinkedHashMap<String,ArrayList<String>> readTestFlowSheets()
	{
		int sheetNumber,testEndRow;
		String suiteName;
		ReadExecutionSheet.readDriverSheet();

		for (Entry<String, String> entry : mainMap.entrySet()) {
			suiteName=entry.getKey();
			sheetNumber=excel.getSheetNumber(suiteName);
			testEndRow=excel.findLastRow(sheetNumber);

			testCaseList=new ArrayList<String>();
			for(int i=1;i<=testEndRow;i++)
			{
				String testCaseName=excel.getCellData(i, 1, sheetNumber);
				String executionStatus=excel.getCellData(i, 3, sheetNumber);
				if(executionStatus.equalsIgnoreCase("Yes"))
					testCaseList.add(testCaseName);	

			}
			testCaseNameMap.put(suiteName, testCaseList);
		}
		Set<String> setOfKeySet = testCaseNameMap.keySet();

		// for-each loop
		for(String key : setOfKeySet) {

			System.out.println("\nSuite name : "  + key 
					+ "\nList of Test case names of " + key + ":");

			for(String testcase : testCaseNameMap.get(key)) {
				System.out.println("\t\t\t\t" + testcase);

			}
		}
		readSettings();
		return testCaseNameMap;



	}

	/**
	 * readSettings() method reads entire Settings sheet under Driver.xlsx
	 * Stores all values in Settings Hashmap
	 * @author Deepa Elenor Mathias
	 */
	public static void readSettings(){

		int sheetNumber;

		sheetNumber=excel.getSheetNumber("Settings");

		for(int i=0;i<=excel.findLastRow(sheetNumber);i++)
		{
			String type = excel.getCellData(i, 0, sheetNumber);
			String value =  excel.getCellData(i, 1, sheetNumber);
			Values.settings.put(type, value);
		}

		for (Map.Entry<String, String> entry : Values.settings.entrySet()) {
			System.out.println(entry.getKey()+" : "+entry.getValue());
		}

	}


}
