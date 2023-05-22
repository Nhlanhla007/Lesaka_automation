package KeywordManager;

import hico_1.create_user;

import com.aventstack.extentreports.ExtentTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Action;
import utils.DataTable2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class JDGKeyManager {
    WebDriver driver;
    DataTable2 dataTable2;
    Action action;
    LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> dataMap2 = new LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>>();

    public JDGKeyManager(WebDriver driver, DataTable2 dataTable2, LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> dataMap2){
        this.driver=driver;
        this.dataTable2=dataTable2;
        this.dataMap2=dataMap2;
        action = new Action(driver);
    }
    public void setDriver(WebDriver driver){
        this.driver=driver;
    }

    public void runKeyWord (String actionToRun,int testcaseID,HashMap<String, Integer> occCount, ExtentTest test1) throws Exception {
        String moduleToRun = actionToRun;
        
        create_user createUser = new create_user(driver, dataTable2);
        
        int rowNumber = -1;
        if (dataMap2.containsKey(moduleToRun + "++")) {
            rowNumber = findRowToRun(dataMap2.get(moduleToRun + "++"), occCount.get(moduleToRun), testcaseID);
        }
        int i = 0;
        WebElement el = null;
        switch (moduleToRun) {
                        
                //Limnia
            case "Creat_user":
            	createUser.add_employee(test1);
                break;
            case "Remove_user":
            	createUser.remove_employee(test1);;
                break;
            case "Editing_user":
            	createUser.editing_employee(test1);
                break;
            case "Validations":
            	createUser.validations(test1);
                break;
                
     
        }
    }
    public int findRowToRun (HashMap < String, ArrayList < String >> input,int occCount, int testcaseID){
        int numberRows = input.get("TCID").size();
        int rowNumber = -1;
        occCount = occCount + 1;
        for (int i = 0; i < numberRows; i++) {
            if (input.get("TCID").get(i).equals(Integer.toString(testcaseID))
                    && input.get("occurence").get(i).equals(Integer.toString(occCount))) {
                rowNumber = i;
            }
        }
        return rowNumber;
    }
}
