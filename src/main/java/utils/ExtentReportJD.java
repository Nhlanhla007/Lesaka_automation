package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportJD {
    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest logger;

    public ExtentReportJD(String reportName){
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/reports/"+reportName+".html");
        // Create an object of Extent Reports
        extent = new ExtentReports();

        htmlReporter.config().setDocumentTitle(reportName);
//        htmlReporter.config().setAutoCreateRelativePathMedia(true);
        htmlReporter.config().setReportName(reportName);

        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("Environment", "QA");

//        htmlReporter.config().setCSS(".r-img { width:500px; height:600px}");
//        htmlReporter.config().
        // Name of the report
//        htmlReporter.config().;
        // Dark Theme
        htmlReporter.config().setTheme(Theme.STANDARD);

    }
    public ExtentTest createTest(String testName) throws IOException {
        return extent.createTest(testName);
    }

    public void createSubTest(String testName) throws IOException {
        logger.createNode("Image is Present");;
    }
    public void endReport() {
        extent.flush();
    }


}
