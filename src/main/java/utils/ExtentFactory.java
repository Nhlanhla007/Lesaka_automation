package utils;

import com.aventstack.extentreports.ExtentTest;

public class ExtentFactory {

	private ExtentFactory() {
	}

	private static ExtentFactory instance = new ExtentFactory();
	//public static ThreadLocal<ExtentTest> childExtent = new ThreadLocal<ExtentTest>();

	public static ExtentFactory getInstance() {
		return instance;
	}
	//MAIN TEST GETS STORED HERE
	public static ThreadLocal<ExtentTest> extent = new ThreadLocal<ExtentTest>();

	public ExtentTest getExtent() {
		return extent.get();
	}

	public void setExtent(ExtentTest extentTest) {
		extent.set(extentTest);
	}
	//THE BELOW METHOD CREATES THE CHILD AND IS CALLED INSIDE OF THE ACTION CLASS  
	public ExtentTest createCase(String childTest) {
		ExtentTest test = ((ExtentTest)extent.get()).createNode(childTest);
		//childExtent.set(test);
		return test;
		
	}
}
