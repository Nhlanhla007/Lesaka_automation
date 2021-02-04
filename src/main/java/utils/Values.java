package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * <copyright file="Values.java" company="Modi's Consulting Group"> Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic,
 * mechanical or otherwise, is prohibited without the prior written permission
 * of the copyright owner. </copyright>
 * 
 * @author 
 */

public class Values {
	public static final int TEST_STEP_BREAK = 1000;
	public static final String WEB = "Web";
	public static final String MOBILE = "Mobile";

	public static  String actual = "";
	public static String screenshotPath = "";
	public  static String expected = "";
	public static String description = "";
	public static String browser = "";
	public static String app = "";
	public static String platform = "";
	public static String startTime = "";
	public static String endTime = "";
	public static String author = "";
	public static String PLATFORM = "";
	public static long startTimeMilli = 0;
	public static String os = "";;
	public static String originalHandle="";
	public static String tcDescription="";
	public static boolean IsreportGenerated=false;
	public static String extentReportPath;
	public static long time=4000;
	public static int counter=20;

	public static HashMap<String, String> appURLList = new HashMap<String, String>();
	public static HashMap<String, String> browserList = new HashMap<String, String>();
	public static HashMap<String,String> settings = new HashMap<String, String>();
	public static final String EXTENT_REPORT_PATH = "reports//extentreports//";

	private static final String FORMAT = "EEE, MMM dd  yyyy; KK:mm:ss aa";
	private static final String FORMAT_2 = "dd-MM-yyyy_HH_mm_ss";

	public static void initializeStartTime() {
		startTimeMilli = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
		startTime = sdf.format(new Date());
	}

	public static String initializeEndTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
		endTime = sdf.format(date);
		SimpleDateFormat sdf_re = new SimpleDateFormat(FORMAT_2);
		return sdf_re.format(date);
	}
}
