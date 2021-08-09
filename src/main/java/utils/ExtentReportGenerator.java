package utils;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;

import java.io.IOException;

public class ExtentReportGenerator {
    public ExtentReports extent;
    public ExtentTest test;
    public String reportName=null;

   public ExtentReportGenerator(String reportName){
        this.reportName =reportName;
        extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter(System.getProperty("user.dir") + "/reports/"+reportName+".html")
                .viewConfigurer()
                .viewOrder()
                .as(new ViewName[] { ViewName.DASHBOARD, ViewName.TEST, ViewName.AUTHOR, ViewName.CATEGORY, ViewName.DEVICE })
                .apply();

        spark.config().setDocumentTitle("Automation Report");
        spark.config().setReportName("Extent report");
        spark.config().setTheme(Theme.STANDARD);
        extent.attachReporter(spark);
    }

    public ExtentTest createTest(String testName) throws IOException {
        return extent.createTest(testName);
    }

    public void createSubTest(String testName) throws IOException {
        test.createNode("Image is Present");;
    }

    public void endReport()  {
        extent.flush();
        /*try {
            Desktop.getDesktop().browse(new File(System.getProperty("user.dir") + "/reports/"+reportName+".html").toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }



}
