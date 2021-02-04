package gui;

import java.io.BufferedWriter;
import java.io.File;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
import Logger.Log;
import utils.Values;

/**
 * <copyright file="Run.java" company="ITC INFOTECH"> Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic,
 * mechanical or otherwise, is prohibited without the prior written permission
 * of the copyright owner. </copyright>
 * 
 */

public class Run {

	public static LinkedHashMap<String, ArrayList<String>> testCaseNamesMap = new LinkedHashMap<String, ArrayList<String>>();

	public static void main(String[] args) {
		formXML();
	}

	/**
	 * formXML() method contructs testng xml file based on the inputs provided
	 * in Driver.xlsx
	 * 
	 * @author 11763
	 */

	public static void formXML() {

		testCaseNamesMap = ReadTestFlowsSheet.readTestFlowSheets();
		
		
		Set<String> setOfKeySet = testCaseNamesMap.keySet();

		for (String key : setOfKeySet) {
		
		String xml = "<!DOCTYPE suite SYSTEM \"http://testng.org/testng-1.0.dtd\">"
				+ "<suite name=\"" + key + "Tests"+"\">"+ "<parameter name=\"platform\" value=\"" + Values.settings.get("PlatformType") + "\" />"
				+ "<parameter name=\"browserType\" value=\"" + Values.settings.get("BrowserType") + "\" />" + "<listeners>"
				+ "<listener class-name=\"listner.TestNGListener\" />" + "</listeners>";

	
		

			String value=ReadExecutionSheet.mainMap.get(key);
			String[] valueList=value.split("@");
			Log.info("\nTest name : " + key + "\nList of TestNames of " + key + ":");
			
			for (String testCasename : testCaseNamesMap.get(key)) {
				xml += "<test name=\"" + testCasename + "Tests" + "\" " + "preserve-order=\"true\">";
				xml += "<parameter name=\"appURL\" value=\""+ valueList[0] +  "\" />";
				xml += "<classes><class name=\"tests." + valueList[1] +"\">";
				xml += "<methods>";
				xml += "<include name=\"" + testCasename + "\"/>";
				xml +=  "</methods>";
				xml +="</class>";
				xml +="</classes>";
				xml += "</test>";
			}
		

			xml += "</suite>";
			Log.info("Constructed XML::"+xml);
			execute(xml,key);
		}

		
	}

	/**
	 * execute() method converts the string xml constructed by formXML() method
	 * to xml and writes it in text.xml file and instructs testng framework to
	 * execute the same
	 * 
	 * @author Deepa Elenor Mathias
	 */
	private static void execute(String xml,String suiteName) {
		try {
			String PATH = "suitefiles\\"+suiteName+".xml";
			File f = new File(PATH);
			if (!f.exists())
				f.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(f, false));
			bw.write(xml);
			bw.close();
			/*	TestNG testng = new TestNG();
			List<String> suites = Lists.newArrayList();
			suites.add(PATH);
			testng.setTestSuites(suites);
			testng.run();*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
