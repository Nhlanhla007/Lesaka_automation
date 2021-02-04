package customreports;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import Logger.Log;
import utils.Values;

/**
 * <copyright file="Reporter.java" company="Modi's Consulting Group"> Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic,
 * mechanical or otherwise, is prohibited without the prior written permission
 * of the copyright owner. </copyright>
 * 
 * @author 
 */

public class Reporter {
	public static final String PASS_TEXT = "Passed";
	public static final String FAIL_TEXT = "Failed";
	public static final String SKIP_TEXT = "Skipped";

	private static final String LINE_SEP = System.getProperty("line.separator"); 
	private static final String TITLE = "Test Automation Report";
	private static final String TITLE_PLACEHOLDER = "#TITLE#";
	private static final String TOTAL_TEST_SUITES_PLACEHOLDER = "#TOTALTESTSUITS#";
	private static final String PASSED_TEST_SUITES_PLACEHOLDER = "#PASSEDTESTSUITS#";
	private static final String FAILED_TEST_SUITES_PLACEHOLDER = "#FAILEDTESTSUITS#";
	private static final String DATE_TIME_PLACEHOLDER = "#DATETIME#";
	private static final String PASS_PERCENT_PLACEHOLDER = "#PASSPERCENTAGE#";
	private static final String RESULT_PLACEHOLDER = "#RESULTDETAILS#";
	private static final String ADD_ANIME_DIV_PLACEHOLDER = "#ADDANIMEDIV#";
	private static final String SHOW_HIDE_ALL_PLACEHOLDER = "#SHOW_HIDE_ALL#";
	private static final String EXECUTION_START_TIME_PLACEHOLDER = "#EXECUTIONSTARTTIME#";
	private static final String EXECUTION_END_TIME_PLACEHOLDER = "#EXECUTIONENDTIME#";
	private static final String TOTAL_EXECUTION_TIME_PLACEHOLDER = "#TOTALEXECUTIONTIME#";
	private static final String OS_PLACEHOLDER = "#OS#";
	private static final String APPURL_PLACEHOLDER = "#APPURL#";
	private static final String TCTOTAL_PLACEHOLDER = "#TCTOTAL#";
	private static final String TCPASS_PLACEHOLDER = "#TCPASS#";
	private static final String TCFAIL_PLACEHOLDER = "#TCFAIL#";
	private static final String TCPASSPERCENTAGE_PLACEHOLDER = "#TCPASSPERCENTAGE#";
	private static final String TEMPLATE_PATH = "template\\template.html";
	private static final String HTML_REPORT_PATH = "reports\\customreports\\";
	//	private static final String EXCEL_REPORT_PATH = "reports_excel\\";
	private static final String RESULTDETAILS1 = "#RESULTDETAILS1#";
	private static final String PASSPERCENTAGE = "#PASSPERCENTAGE#";
	private static final String FAILPERCENTAGE = "#FAILPERCENTAGE#";
	private static final String PASSEDTESTCASESCOUNT = "#PASSEDTESTCASESCOUNT#";
	private static final String FAILEDTESTCASESCOUNT = "#FAILEDTESTCASESCOUNT#";
	static Logger logger = Log.getLogData(Reporter.class.getSimpleName());
	private static DecimalFormat round = new DecimalFormat("##.00");

	public static String currentDate;
	private static int tcTotal = 0, tcPass = 0, tcFail = 0, passed = 0,
			failed = 0, total = 0;;
			private static long totalTime = 0;

			private static List<String> suiteNames;
			private static HashMap<String, TestSuites> suites;

			static {
				currentDate = new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss")
						.format(new Date());
			}

			/* ===============================
	  Initialize objects to store the results of different suites
	===============================*/
			public static void initialize() {
				suiteNames = new ArrayList<>();
				suites = new HashMap<>();
			}

			/* ===============================
	  @param actualValue
	       The actual value that was found after test step execution.
	  @param expectedValue
	       The expected value after test execution.
	  @param tr
	       The testng object that stores various details about the test step executed.
	  @param status
	       The enum that holds the result of the test step executed. It can be either of PASSED, FAILED or SKIPPED.
    ===============================*/
			public static void report(String actualValue, String expectedValue,
					ITestResult tr, Output status, String xmlTestName,String description) {
				Results result = null;
				logger.info("TestCaseName"+xmlTestName);
				if (status.equals(Output.PASSED)) {
					result = new Results(tr, PASS_TEXT, "Actual value '" + actualValue
							+ "' matches expected value", xmlTestName);
					logger.info("test case passed" + xmlTestName);
				}

				else if (status.equals(Output.FAILED)) {
					result = new Results(tr, FAIL_TEXT,
							"Actual value '" + actualValue
							+ "' does not match expected value '"
							+ expectedValue + "'", xmlTestName);

					result.setScreenshotPath(Values.screenshotPath);
					logger.info("test case failed" + xmlTestName);
				} else if (status.equals(Output.SKIPPED)) {
					result = new Results(tr, SKIP_TEXT, "Case Skipped Altogether",
							xmlTestName);
				}
				String suitName = result.getSuiteName();
				String testCaseName = xmlTestName;
				if (suites.containsKey(suitName)) {
					TestSuites ts = suites.get(suitName);
					if (ts.testCaseExists(testCaseName)) {
						TestCases tc = ts.getTestCase(testCaseName);
						tc.setDescription(description);
						tc.addStep(result);
					} else {
						TestCases tc = new TestCases(result.getTestCaseName(),
								description);
						tc.addStep(result);
						ts.addTestCase(result.getTestCaseName(), tc);
					}
				} else {
					TestSuites ts = new TestSuites(result.getSuiteName());
					TestCases tc = new TestCases(result.getTestCaseName(),
							description);
					tc.addStep(result);
					ts.addTestCase(result.getTestCaseName(), tc);
					suites.put(result.getSuiteName(), ts);
					suiteNames.add(result.getSuiteName());
				}


			}




			/*===============================
	   Write the results to the HTML file using template.html file stored under template folder.
    	===============================*/
			public static void writeResults() throws AddressException, MessagingException {

				try {
					currentDate = Values.initializeEndTime();
					String reportIn = new String(Files.readAllBytes(Paths
							.get(TEMPLATE_PATH)));
					reportIn = reportIn.replace(DATE_TIME_PLACEHOLDER, Values.endTime);

					StringBuffer details = new StringBuffer();
					ArrayList<String> expList = new ArrayList<String>();

					StringBuffer details1 = new StringBuffer();
					ArrayList<String> expList1 = new ArrayList<String>();
					int totalCount = 0;
					int executedCount = 0;
					int passedCount = 0;
					int failedCount = 0;
					int skippedCount = 0;
					DecimalFormat decimalFormat = new DecimalFormat("#.##");

					for (int i = 0; i < suites.size(); i++) {
						expList1.add("ts" + (i + 1));
						String suiteName = suiteNames.get(i);
						TestSuites ts = suites.get(suiteName);
						details1.append(" <tr>");
					String summaryColor;
						if(ts.isPassed())
							summaryColor="green";
						else
							summaryColor="#a50000";
						if(ts.isSkipped())
							summaryColor="darkmagenta";

					
						details1.append("<th class=\"blueNormal\"; style=\"text-decoration: none; font-size: 1em;color:"
								+summaryColor
								+ "\">"
								+"<strong>"
								+suiteName
								+"</strong></th>");
						details1.append("<th class=\"blueNormal\"> "+ts.getNoOfTestCases()+" </th>");
						details1.append("<th class=\"blueNormal\"> "+(ts.getNoOfPassedTestCases()+ts.getNoOfFailedTestCases())+" </th>");
						details1.append("<th class=\"blueNormal\"> "+ts.getNoOfPassedTestCases()+" </th>");
						details1.append("<th class=\"blueNormal\"> "+ts.getNoOfFailedTestCases()+" </th>");
						//details1.append("<th class=\"blueNormal\"> "+(ts.getNoOfTestCases()-ts.getNoOfPassedTestCases()-ts.getNoOfFailedTestCases())+" </th>");
						details1.append("<th class=\"blueNormal\"> "+ts.getNoOfSkippedTestCases()+" </th>");
						details1.append("<th class=\"blueNormal\"> "+decimalFormat.format((float)(((ts.getNoOfPassedTestCases()+ts.getNoOfFailedTestCases())*100)/ts.getNoOfTestCases()))+"% </th>");
						details1.append("<th class=\"blueNormal\">"+decimalFormat.format(100*ts.getNoOfPassedTestCases()/(float)ts.getNoOfTestCases())+"% </th>");
						details1.append(" </tr>");
						totalCount=totalCount+ts.getNoOfTestCases();
						executedCount=executedCount+(ts.getNoOfPassedTestCases()+ts.getNoOfFailedTestCases());
						passedCount=passedCount+ts.getNoOfPassedTestCases();
						failedCount=failedCount+ts.getNoOfFailedTestCases();
						//skippedCount= (ts.getNoOfTestCases()-ts.getNoOfPassedTestCases()-ts.getNoOfFailedTestCases());
						skippedCount=skippedCount+ts.getNoOfSkippedTestCases();
					}
					logger.info("Skipeed count"+skippedCount);
					details1.append(" <tr>");
					details1.append("<th class=\"blue\"><i>Total</i></th>");
					details1.append("<th class=\"blue\">"+totalCount+"</th>");
					details1.append("<th class=\"blue\">"+executedCount+"</th>");
					details1.append("<th class=\"blue\">"+passedCount+"</th>");
					details1.append("<th class=\"blue\">"+failedCount+"</th>");
					details1.append("<th class=\"blue\">"+skippedCount+"</th>");
					details1.append("<th class=\"blue\">"+decimalFormat.format((float)(((passedCount+failedCount)*100)/totalCount))+"% </th>");
					details1.append("<th class=\"blue\">"+decimalFormat.format(100*passedCount/(float)totalCount)+"% </th>");
					details1.append(" </tr>");
					reportIn = reportIn
							.replace(RESULTDETAILS1, new String(details1));
					reportIn = reportIn.replace(PASSPERCENTAGE, new String(round.format(100*passedCount/(float)totalCount)));
					reportIn = reportIn.replace(FAILPERCENTAGE, new String(round.format(100*failedCount/(float)totalCount)));
					reportIn = reportIn.replace(PASSEDTESTCASESCOUNT, new String(Integer.toString(passedCount)));
					reportIn = reportIn.replace(FAILEDTESTCASESCOUNT, new String(Integer.toString(failedCount)));

					for (int i = 0; i < suites.size(); i++) {
						expList.add("ts" + (i + 1));
						String suiteName = suiteNames.get(i);
						TestSuites ts = suites.get(suiteName);

						String suiteColor;
						if(ts.isPassed())
							suiteColor="green";
						else
							suiteColor="#a50000";
						if(ts.isSkipped())
							suiteColor="darkmagenta";
						
						details.append("<div class=\"testcasepadding\" style=\"overflow:hidden\"><p><a href=\"javascript:animatedcollapse.toggle('ts"
								+ (i + 1)
								+ "')\" style=\"text-decoration: none; font-size: 2em; color: "
								+suiteColor
							
								+ "\" name=\"testsuite"
								+ (i + 1)
								+ "\"><b>"
								+ suiteName
								+ "</b></a></p></div><div id=\"ts"
								+ (i + 1) + "\" class=\"texter\">");
						details.append("<table name=\"SummaryTable-ts"
								+ (i + 1)
								+ "\" style=\"table-layout: fixed; width: 50%\" align=\"center\" id=\"summarytable-tc"
								+ (i + 1)
								+ "\" cellpadding=\"10\" cellspacing=\"0\" summary=\"Summary table for Test Case\"><br>");
						details.append("<tbody>");
						details.append("    <tr>");
						details.append("        <th class=\"blue\" width=\"30%\">Application URL</th>");
						details.append("        <td style=\"border-top-right-radius:10px\">"
								+ ts.getApplicationURL() + "</td>");
						details.append("    </tr>");

						details.append("    <tr>");
						details.append("        <th class=\"blue\" width=\"30%\">Browser Tested</th>");
						details.append("        <td style=\"border-top-right-radius:10px\">"
								+ ts.getBrowserName() + "</td>");
						details.append("    </tr>");

						details.append("    <tr>");
						details.append("        <th class=\"blue\" width=\"30%\">Total Test Cases</th>");
						details.append("        <td style=\"border-top-right-radius:10px\">"
								+ ts.getNoOfTestCases() + "</td>");
						details.append("    </tr>");
						details.append("    <tr>");
						details.append("        <th class=\"blue\" width=\"30%\">Test Cases Passed</th>");
						details.append("        <td>" + (ts.getNoOfPassedTestCases())
								+ "</td>");
						details.append("    </tr>");
						details.append("  <tr>");
						details.append("<th class=\"blue\" width=\"30%\">Test Cases Failed</th>");
						details.append("<td>" + (ts.getNoOfFailedTestCases()) + "</td>");
						details.append("</tr>");
						details.append("  <tr>");
						details.append("  <tr>");
						details.append("<th class=\"blue\" width=\"30%\">Test Cases Skipped</th>");
						details.append("<td>" + (ts.getNoOfSkippedTestCases()) + "</td>");
						details.append("</tr>");
						details.append("  <tr>");
						details.append("<th class=\"blue\" width=\"30%\">Pass Percentage</th>");
						details.append("<td>"
								+ round.format(100 * ts.getNoOfPassedTestCases()
										/ (float) ts.getNoOfTestCases()) + "%"
										+ "</td>");
						details.append("</tr>");
						details.append(" <tr>");
						details.append("<th class=\"blue\" width=\"30%\">Total Duration</th>");
						details.append("   <td>" + (ts.getTimeTaken() / 1000)
								+ "s</td>");
						details.append("</tr>");
						details.append("</tbody>");
						details.append("</table>");
						details.append("<p></p>");
						for (int j = 0; j < ts.getNoOfTestCases(); j++) {
							TestCases tc = ts.getTestCaseAt(j);
							tcTotal++;
							if (tc.isPassed())
								tcPass++;
							else
								tcFail++;
							expList.add("ts" + (i + 1) + "tc" + (j + 1));
							String cls = tc.isPassed() ? "pass" : "fail";

							String testcaseColor;
							if(tc.isPassed())
								testcaseColor="green";
							else
								testcaseColor="#a50000";
							if(tc.isSkipped())
								testcaseColor="darkmagenta";

							
							details.append("<div class=\"iterationpadding\" style=\"overflow:hidden\"><a href=\"javascript:animatedcollapse.toggle('ts"
									+ (i + 1)
									+ "tc"
									+ (j + 1)
									+ "')\" style=\"text-decoration: none; font-size: 1.3em; color: "
									+ testcaseColor
									+ "\"><b>"
									+ tc.getName()
									+ "</b></a></div><div id=\"ts"
									+ (i + 1) + "tc" + (j + 1) + "\" class=\"texter\">");

							details.append("<table name=\"SummaryTable-ts"
									+ (i + 1)
									+ "tc"
									+ (j + 1)
									+ "\" style=\"table-layout: fixed; width: 50%\" align=\"center\" id=\"summarytable-tc"
									+ (i + 1)
									+ "\" cellpadding=\"10\" cellspacing=\"0\" summary=\"Summary table for Test Case\"><br>");
							details.append("<tbody>");
							details.append("    <tr>");
							details.append("        <th class=\"blue\" width=\"30%\">Description</th>");
							details.append("        <td style=\"border-top-right-radius:10px\">"
									+ tc.getDescription() + "</td>");
							details.append("    </tr>");
							details.append("</tbody>");
							details.append("</table>");
							details.append("<p></p>");

							details.append("<table name=\"table-ts"
									+ (i + 1)
									+ "tc"
									+ (j + 1)
									+ "\" style=\"table-layout: fixed; width: 50%\" align=\"center\" id=\"table-ts"
									+ (i + 1)
									+ "tc"
									+ (j + 1)
									+ "\" cellpadding=\"10\" cellspacing=\"0\" summary=\"Summary table for Iteration\"><br><thead><tr><th class=\""
									/*+ cls
							+ "\" style=\"width:8%\">Test Case Number</th><th class=\""
							+ cls + "\">Test Case Name</th><th class=\""*/ 
									+ cls
									+ "\">Duration (ms)</th><th class=\"" + cls
									+ "\">Execution Status</th><th class=\"" + cls
									+ "\">Reason</th><th class=\"" + cls
									+ "\">ScreenShots</th></tr></thead><tbody>");
							for (int k = 0; k < tc.getNoOfSteps(); k++) {
								Results res = tc.getResultAt(k);
								totalTime += res.getTime();
								details.append("<tr>");
								/*details.append("<td>" + (k + 1) + "</td>");
						details.append("<td>" + res.getMethodName() + "</td>");*/
								details.append("<td>" + (res.getTime()) + "</td>");
								details.append("<td><font color=\""
										+ ((res.getResult().equals(Reporter.FAIL_TEXT)) ? "red"
												:(res.getResult().equals(Reporter.SKIP_TEXT)) ? "darkmagenta"
														: (res.getResult().equals(Reporter.PASS_TEXT) ? "green"
																: "gray")) + "\"><b>"
																+ res.getResult() + "</b></font></td>");
								StringWriter sw = new StringWriter();
								PrintWriter pw = new PrintWriter(sw);


								if (res.getITestResult().getThrowable() != null)
									if (res.getITestResult().getThrowable().getStackTrace() != null) {

										res.getITestResult().getThrowable().printStackTrace(pw);
										String stackTrace = sw.getBuffer().toString();

										stackTrace=filterTrace(stackTrace);


										details.append("<td>"
												+ ((res.getResult().equals(Reporter.FAIL_TEXT)) |(res.getResult().equals(Reporter.SKIP_TEXT) ) ? stackTrace
														: "NA") + "</td>");
									}
								details.append("<td>" + res.getScreenshotText()
								+ "</td>");
								details.append("</tr>");
							}
							details.append("</tbody>");
							details.append("</table>");
							details.append("</div>");
							details.append("<p></p>");
						}
						details.append("</div>");
					}

					String addDivStatements = "";
					String showHideAll = "";
					for (int i = 0; i < expList.size(); i++) {
						addDivStatements = addDivStatements
								+ "animatedcollapse.addDiv('" + expList.get(i)
								+ "', 'fade=1')\n";
						showHideAll += "'" + expList.get(i) + "',";
					}
					showHideAll = showHideAll.substring(0, showHideAll.length() - 1);

					for (int i = 0; i < suiteNames.size(); i++) {
						TestSuites s = suites.get(suiteNames.get(i));
						if (s.isPassed())
							passed++;
						else
							failed++;
						total++;
					}
					String tcPassPercentage = round.format(100 * tcPass
							/ (float) tcTotal)
							+ "%";
					reportIn = reportIn.replace(OS_PLACEHOLDER, System.getProperty("os.name"));
					//	reportIn = reportIn.replace(BROWSER_PLACEHOLDER, Values.browser);
					reportIn = reportIn.replace(APPURL_PLACEHOLDER, Values.app);
					//	reportIn = reportIn.replace(URL_COUNT, Values.urlsCount);
					reportIn = reportIn.replace(TCTOTAL_PLACEHOLDER,
							String.valueOf(tcTotal));
					reportIn = reportIn.replace(TCPASS_PLACEHOLDER,
							String.valueOf(tcPass));
					reportIn = reportIn.replace(TCFAIL_PLACEHOLDER,
							String.valueOf(tcFail));
					reportIn = reportIn.replace(TCPASSPERCENTAGE_PLACEHOLDER,
							tcPassPercentage);
					reportIn = reportIn.replace(EXECUTION_START_TIME_PLACEHOLDER,
							Values.startTime);
					reportIn = reportIn.replace(EXECUTION_END_TIME_PLACEHOLDER,
							Values.endTime);
					reportIn = reportIn.replace(TOTAL_EXECUTION_TIME_PLACEHOLDER,
							String.valueOf(totalTime / 1000) + "s");
					reportIn = reportIn.replace(TOTAL_TEST_SUITES_PLACEHOLDER,
							String.valueOf(total));
					reportIn = reportIn.replace(PASSED_TEST_SUITES_PLACEHOLDER,
							String.valueOf(passed));
					reportIn = reportIn.replace(FAILED_TEST_SUITES_PLACEHOLDER,
							String.valueOf(failed));
					reportIn = reportIn.replace(PASS_PERCENT_PLACEHOLDER,
							String.valueOf(100 * passed / total) + "%");
					reportIn = reportIn.replace(SHOW_HIDE_ALL_PLACEHOLDER, showHideAll);
					reportIn = reportIn.replace(TITLE_PLACEHOLDER, TITLE);
					reportIn = reportIn.replace(ADD_ANIME_DIV_PLACEHOLDER,
							addDivStatements);
					reportIn = reportIn
							.replace(RESULT_PLACEHOLDER, new String(details));

					String htmlReportPath = HTML_REPORT_PATH + "report_" + currentDate
							+ ".html";
					/*	new File(Logger.LOG_FILE).renameTo(new File("log\\log_"
					+ currentDate + ".txt"));*/
					// new File(Logger.LOG_FILE).delete();

					Files.write(Paths.get(htmlReportPath), reportIn.getBytes(),
							StandardOpenOption.CREATE);
					File htmlFile = new File(htmlReportPath);
					//writeResultsToExcel();
				//	GenerateEmail.generateAndSendEmail(htmlReportPath,currentDate,totalTime,suites,suiteNames);
					Desktop.getDesktop().browse(htmlFile.toURI());

				} catch (IOException e) {
					//	e.printStackTrace();
				}
			}

			/*@SuppressWarnings("deprecation")
	public static void writeResultsToExcel() {

		try {

			currentDate = Values.initializeEndTime();
			String excelReportPath = EXCEL_REPORT_PATH + "report_"
					+ currentDate + ".xlsx";
			String reportIn = new String(Files.readAllBytes(Paths
					.get(TEMPLATE_PATH)));
			reportIn = reportIn.replace(DATE_TIME_PLACEHOLDER, Values.endTime);
			long totalTime = 0;

			ArrayList<String> expList = new ArrayList<String>();

			// Create blank workbook
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook();
			// Create a blank sheet
			XSSFSheet spreadsheet1 = workbook.createSheet(" Test Summary");
			// Create row object
			XSSFRow row;

			// setting color
			XSSFCellStyle style1 = workbook.createCellStyle();
			style1.setFillForegroundColor(IndexedColors.BLUE.getIndex());
			style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//			style1.setAlignment(CellStyle.ALIGN_LEFT);

			row = spreadsheet1.createRow(6);
			row.createCell(8).setCellValue("Operating System");
			row.createCell(9).setCellValue(Values.os);
			row.getCell(8).setCellStyle(style1);

			row = spreadsheet1.createRow(7);
			row.createCell(8).setCellValue("Browser Tested");
			row.createCell(9).setCellValue(Values.browserName);
			row.getCell(8).setCellStyle(style1);

			row = spreadsheet1.createRow(8);
			row.createCell(8).setCellValue("Application URL");
			row.createCell(9).setCellValue(Values.app);
			row.getCell(8).setCellStyle(style1);

			row = spreadsheet1.createRow(9);
			row.createCell(8).setCellValue("Total Test Cases");
			row.createCell(9).setCellValue(tcTotal);
			row.getCell(8).setCellStyle(style1);

			row = spreadsheet1.createRow(10);
			row.createCell(8).setCellValue("Test Cases Passed");
			row.createCell(9).setCellValue(tcPass);
			row.getCell(8).setCellStyle(style1);

			row = spreadsheet1.createRow(11);
			row.createCell(8).setCellValue("Test Cases Failed");
			row.createCell(9).setCellValue(tcFail);
			row.getCell(8).setCellStyle(style1);

			row = spreadsheet1.createRow(12);
			row.createCell(8).setCellValue("Test Case Pass Percentage");
			row.createCell(9).setCellValue(
					String.valueOf(100 * passed / total) + "%");
			row.getCell(8).setCellStyle(style1);

			row = spreadsheet1.createRow(13);
			row.createCell(8).setCellValue("Execution Start Time");
			row.createCell(9).setCellValue(Values.startTime);
			row.getCell(8).setCellStyle(style1);

			row = spreadsheet1.createRow(14);
			row.createCell(8).setCellValue("Execution End Time");
			row.createCell(9).setCellValue(Values.endTime);
			row.getCell(8).setCellStyle(style1);

			row = spreadsheet1.createRow(15);
			row.createCell(8).setCellValue("Total Execution Time");
			row.createCell(9).setCellValue(
					String.valueOf(totalTime / 1000) + "s");
			row.getCell(8).setCellStyle(style1);


			// setting border

			@SuppressWarnings("unused")
			CellRangeAddress region = new CellRangeAddress(6, 15, 8, 9);
			spreadsheet1.autoSizeColumn(8);
			spreadsheet1.autoSizeColumn(9);

			XSSFFont font = workbook.createFont();
			font.setFontHeightInPoints((short) 30);
			font.setFontName("IMPACT");
			font.setItalic(true);
			font.setColor(HSSFColor.BRIGHT_GREEN.index);

			// Set font into style
			XSSFCellStyle style = workbook.createCellStyle();
			style.setFont(font);

			XSSFSheet spreadsheet2 = workbook.createSheet(" Test Results");
			row = spreadsheet2.createRow(0);

			row.createCell(0).setCellValue("Test Suite");
			row.getCell(0).setCellStyle(style1);
			row.createCell(1).setCellValue("Test Case");
			row.getCell(1).setCellStyle(style1);
			row.createCell(2).setCellValue("Test Steps");
			row.getCell(2).setCellStyle(style1);
			row.createCell(3).setCellValue("Execution Time");
			row.getCell(3).setCellStyle(style1);
			row.createCell(4).setCellValue("Result");
			row.getCell(4).setCellStyle(style1);
			row.createCell(5).setCellValue("Reason");
			row.getCell(5).setCellStyle(style1);
			row.setRowStyle(style);
			int rowid = 1;
			for (int i = 0; i < suites.size(); i++) {
				expList.add("ts" + (i + 1));
				String suiteName = suiteNames.get(i);
				TestSuites ts = suites.get(suiteName);
				row = spreadsheet2.createRow(rowid);
				row.createCell(0).setCellValue(suiteName);

				for (int j = 0; j < ts.getNoOfTestCases(); j++) {
					TestCases tc = ts.getTestCaseAt(j);
					row.createCell(1).setCellValue(tc.getName());

					for (int k = 0; k < tc.getNoOfSteps(); k++) {

						Results res = tc.getResultAt(k);
						totalTime += res.getTime();

						row.createCell(2).setCellValue(
								res.getMethodName().toString());
						row.createCell(3).setCellValue(res.getTime() / 1000);
						row.createCell(4).setCellValue(res.getResult());
						if (res.getITestResult().getThrowable() != null
								&& res.getITestResult().getThrowable()
										.getMessage() != null) {
							row.createCell(5).setCellValue(
									res.getITestResult().getThrowable()
											.getMessage());
						} else {
							row.createCell(5).setCellValue("PASS");
						}
						row = spreadsheet2.createRow(++rowid);
					}

				}
			}

			spreadsheet2.autoSizeColumn(0);
			spreadsheet2.autoSizeColumn(1);
			spreadsheet2.autoSizeColumn(2);
			spreadsheet2.autoSizeColumn(3);
			spreadsheet2.autoSizeColumn(4);
			spreadsheet2.autoSizeColumn(5);
			FileOutputStream out = new FileOutputStream(excelReportPath);
			workbook.write(out);
			out.close();
			Logger.log("Writesheet.xlsx written successfully");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/

			static String filterTrace(String trace) {
				StringReader stringReader = new StringReader(trace);
				BufferedReader bufferedReader = new BufferedReader(stringReader);
				StringBuilder buf = new StringBuilder();

				try {
					// first line contains the thrown exception
					String line = bufferedReader.readLine();
					if(line == null) {
						return "";
					}
					buf.append(line).append(LINE_SEP);

					//
					// the stack frames of the trace
					//
					String[] excludedStrings = new String[] {
							"org.testng",
							"reflect",
							"org.gradle",
							"org.apache.maven.surefire"
					};

					int excludedCount = 0;
					while((line = bufferedReader.readLine()) != null) {
						boolean isExcluded = false;
						for (String excluded : excludedStrings) {
							if(line.contains(excluded)) {
								isExcluded = true;
								excludedCount++;
								break;
							}
						}
						if (! isExcluded) {
							buf.append(line).append(LINE_SEP);
						}
					}
					if (excludedCount > 0) {
						buf.append("... Removed ").append(excludedCount).append(" stack frames");
					}
				}
				catch(IOException ioex) {
					// do nothing
				}

				return buf.toString();
			}
}