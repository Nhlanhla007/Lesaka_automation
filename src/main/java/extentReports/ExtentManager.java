package extentReports;

import org.apache.log4j.Logger;
import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import Logger.Log;
import utils.Action;
import utils.Values;

 

 

public class ExtentManager {
    
     static ExtentReports extent;
    static Logger logger = Log.getLogData(Action.class.getSimpleName());
       // final static String filePath = "Extent.html";
        public synchronized static ExtentReports getReporter() {
            if (extent == null) {
                logger.info("Fileptah"+Values.extentReportPath);
                 ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(Values.extentReportPath);
          
                 htmlReporter.loadXMLConfig(System.getProperty("user.dir") + "\\src\\test\\resources\\configs\\config.xml");
                 extent = new ExtentReports();
                 extent.setSystemInfo("os", "Windows");
                 extent.setSystemInfo("Browser", "Chrome");
                 extent.setAnalysisStrategy(AnalysisStrategy.SUITE);
                  extent.attachReporter(htmlReporter);
           
            }
            
            return extent;
        }
}