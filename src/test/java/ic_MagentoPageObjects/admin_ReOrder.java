package ic_MagentoPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class admin_ReOrder {
	 WebDriver driver;
	    Action action;
	    MagentoOrderStatusPage orderStatus;
		DataTable2 dataTable2;

	    public admin_ReOrder(WebDriver driver, DataTable2 dataTable2) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	        action = new Action(driver);
	        this.dataTable2= dataTable2;
	        orderStatus = new MagentoOrderStatusPage(driver, dataTable2);

	    }
	    //html[1]/body[1]/div[2]/main[1]/div[1]/div[2]/div[1]/div[1]/button[6]/span[1]
	    //*[@id="order_reorder"]/span
	    @FindBy(xpath="//span[contains(text(),'Reorder')]")
	    private WebElement admin_Reorder;
	    
	    //*[@id="add_products"]/span
	    @FindBy(xpath="//*[@id=\"add_products\"]/span")
	    private WebElement admin_Addproducts;
	    
	  //*[@id="order-items_grid"]/table/tbody/tr/td[3]/input
	    @FindBy(xpath="//*[@id=\"order-items_grid\"]/table/tbody/tr/td[3]/input")
	    private WebElement admin_QTY;
	    
	  //*[@id="order-items_grid"]/table/tbody/tr/td[7]/select
	    @FindBy(xpath="//*[@id=\"order-items_grid\"]/table/tbody/tr/td[7]/select")
	    private WebElement admin_Action;
	    
	    @FindBy(xpath="//textarea[@id='order-comment']")
	    private WebElement admin_orderComment;
	    
	    @FindBy(xpath="//div[contains(text(),'[RabbitMQ] Order SAP Number: ')][1]")
	    private WebElement OrderDetailSAPNumber;
	    
	  //button[@id='submit_order_top_button']
	    @FindBy(xpath="//button[@id='submit_order_top_button']")
	    private WebElement admin_SubmitOrder;
	    
	    //Automatically generated invoice. //div[contains(text(),'Automatically generated invoice.')]
	    @FindBy(xpath="//div[contains(text(),'Automatically generated invoice.')]")
	    private WebElement admin_invoiceMessage;
	    
	    //You created the order. //div[contains(text(),'You created the order.')]
	    @FindBy(xpath="//div[contains(text(),'You created the order.')]")
	    private WebElement admin_ReOrderCreated;
	    
	    @FindBy(xpath="//*[@id=\"order_history_block\"]/ul/li[2]/div")
	    private WebElement admin_SAPnumber;
	    
	    @FindBy(xpath="//*[@id=\"order_history_block\"]/ul/li[1]/div")
	    private WebElement admin_commentVerify;
	    
	    @FindBy(xpath="//*[@id=\"html-body\"]/div[2]/header/div[1]/div/h1")
	    private WebElement admin_NewPOnumber;
	    
	    
    public void editOrder(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException, InterruptedException{
    	String orderComment = dataTable2.getValueOnCurrentModule("orderComment");
    	//String orderQty = dataTable2.getValueOnCurrentModule("orderQty");
    	//String OrderAction = dataTable2.getValueOnCurrentModule("OrderAction");
    	
    	String oldSAPnumber = dataTable2.getValueOnOtherModule("GenerateOrderSAPnumber", "OrderSAPnumber", 0);//action.getText(OrderDetailSAPNumber, "oldSAPnumber",test);
    	action.mouseover(OrderDetailSAPNumber, "Get old SAPnumber");
    	dataTable2.setValueOnCurrentModule("OldPO number", oldSAPnumber.replace("[RabbitMQ] Order SAP Number: ",""));
    	action.explicitWait(5000);
    	
    	action.click(admin_Reorder, "create reorder", test);
    	
    	action.mouseover(admin_orderComment, "go to comments");
    	action.explicitWait(5000);
    	action.clear(admin_orderComment, "clear");
    	action.writeText(admin_orderComment, orderComment,"write the comment", test);
    	
    	action.click(admin_SubmitOrder, "Submit Re-Order", test);
    	
    	action.explicitWait(5000);
    	String invoiceMsg = action.getText(admin_invoiceMessage, "invoice message",test);
    	action.CompareResult("Popup-message generated invoice", "Automatically generated invoice.", invoiceMsg, test);
    	
    	String reOrderCreated = action.getText(admin_ReOrderCreated, "invoice message",test);
    	action.CompareResult("Popup-message create order", "You created the order.", reOrderCreated, test);
    	
    	action.mouseover(admin_commentVerify, "verify comment");
    	String reorderComm = action.getText(admin_commentVerify, "reorder comment",test);
    	action.CompareResult("Reorder", "Reorder", reorderComm, test);
    	
    	action.refresh();
    	
    	String newPOnumber = action.getText(admin_NewPOnumber, "New PO number",test);
    	dataTable2.setValueOnCurrentModule("ReorderPO number", newPOnumber);
    	
    	String newSAPnumber = action.getText(OrderDetailSAPNumber, "oldSAPnumber",test);
    	
    	newSAPnumber = newSAPnumber.substring(newSAPnumber.indexOf("000"));
    	dataTable2.setValueOnOtherModule("GenerateOrderSAPnumber", "OrderSAPnumber", newSAPnumber, 0);
    	if(!(newSAPnumber.equalsIgnoreCase(oldSAPnumber))){
    		dataTable2.setValueOnCurrentModule("NewSAP number", newSAPnumber);
    	} else{
    		action.refresh();
    	}
    }
    
    public int findRowToRun(HashMap<String, ArrayList<String>> input,int occCount,int testcaseID){
		int numberRows=input.get("TCID").size();
		int rowNumber=-1;
		occCount=occCount+1;
		for(int i=0;i<numberRows;i++){
			if(input.get("TCID").get(i).equals(Integer.toString(testcaseID))&&input.get("occurence").get(i).equals(Integer.toString(occCount))){
				rowNumber=i;
			}
		}
		return rowNumber;
	}

}
