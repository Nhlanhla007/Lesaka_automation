package SRS;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class srs_LogonStoreByOrderPayload {
	WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    public srs_LogonStoreByOrderPayload(WebDriver driver,DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2=dataTable2;
    }
    //--------------Used global variable-----------------
    public static HashMap<String,String> OrderpayloadData;
    //-----------------------------------------------------
    
    
	 @FindBy(xpath = "/html/body/form[3]/p/table/tbody/tr[2]/td[2]/table/tbody/tr[1]/td/input")
	 public WebElement LogOn;
	 
	 @FindBy(xpath="//iframe[@id='application-ZEccSRS-display']")
	 private WebElement FrameStore;
	 @FindBy(xpath="/html/body/form[3]/p/table/tbody/tr[1]/td[3]/input[1]")
	 private WebElement StoreFeild1;
	 
	 @FindBy(xpath = "/html/body/table/tbody/tr[1]/td[2]/table/tbody/tr[1]/td[3]/a/strong[contains(text(),'Sales Order')]")
	 private WebElement SalerOrder;
      public void srsLogonByAptStore(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException, InterruptedException, AWTException{
    	  String orderIdvalue =dataTable2.getValueOnOtherModule("ic_RetriveOrderID", "orderID", 0);
    	  System.out.println("orderIdvalue"+orderIdvalue);
    	  Thread.sleep(10000);
    	  String DeliverySite = dataTable2.getValueOnCurrentModule("KeyTolookInPayload");//"DeliverySite";
    	  String payloadfilepath = GetOrderFilePath(orderIdvalue);
    	  HashMap<String,String> allOrderdata = RetiveDatafromOrderPayload(payloadfilepath, DeliverySite);
    	  String StoreNumber = allOrderdata.get(DeliverySite);
    	  
    	  Thread.sleep(10000);
    	  LoginWithStore(StoreNumber, test);
    	  action.click(SalerOrder, "Saler Order", test);
      
      }
      public void LoginWithStore(String Store,ExtentTest test) throws IOException, InterruptedException, AWTException{
    	  boolean valis = action.elementExists(StoreFeild1, 11);
    	  
    	  
    	  action.switchToFrameUsingWebElement(FrameStore);
    	  
    	  StoreFeild1.click();
    	  action.Robot_WriteText(Store);
    	  
    	
    	  Thread.sleep(10000);
    	  if(action.elementExists(SalerOrder, 10)){
    		  
    		  action.CompareResult("Login with Store successful", "True", "True", test);
    	  }else{
    		  action.CompareResult("Login with Store successful", "True", "False", test);
    	  }
    	  
      }
      public String GetOrderFilePath(String OrderID) throws IOException{
  		String userHome = System.getProperty("user.home");
  		String FolernameToLook ="Downloads";
  		String sFilename = OrderID+"_payload.txt";
  		String FullPath = userHome+"\\"+FolernameToLook+ "\\" +sFilename;
  		System.out.println("FullPath -"+FullPath);
  		return FullPath;
  		
      }
      
      public HashMap<String,String> RetiveDatafromOrderPayload(String fileName,String ExpKeys)throws IOException{
      	OrderpayloadData= new HashMap<String,String>();
      	try {
			File file = new File(fileName);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while((line = br.readLine()) != null){
			    //process the line
			    System.out.println(line);
			    String[] allkeys = ExpKeys.split(",");
			    for(int i=0;i<allkeys.length;i++){
			    	if(line.contains(allkeys[i])){
				    	String value = line.split(allkeys[i].trim()+"] =>")[1].trim();
				    	OrderpayloadData.put(allkeys[i].trim(), value);
				    	break;
				    }
			    }
			    
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}
  		return OrderpayloadData;
      	
      }
}
