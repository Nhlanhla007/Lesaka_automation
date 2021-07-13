package evs_MagentoPageObjects;

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

public class EVS_Admin_Reorder {
	 	WebDriver driver;
	    Action action;
	    EVS_MagentoOrderStatusPage orderStatus;
		DataTable2 dataTable2;

	    public EVS_Admin_Reorder(WebDriver driver, DataTable2 dataTable2) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	        action = new Action(driver);
	        this.dataTable2= dataTable2;
	        orderStatus = new EVS_MagentoOrderStatusPage(driver, dataTable2);

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
	    
	    @FindBy(id = "cellphone_number")
	    private WebElement cellPhoneNumber;
	    
	    @FindBy(id = "order-billing_address_telephone")
	    private WebElement billingCellPhoneNumber;
	    
	    
    public void editOrder(ExtentTest test) throws Exception{
    	String orderComment = dataTable2.getValueOnCurrentModule("orderComment");
    	String refreshWaitTime = dataTable2.getValueOnOtherModule("evs_GenerateOrderSAPnumber", "totalCounter", 0);
    	String typeOfUser = dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "UserType", 0);
    	//String orderQty = dataTable2.getValueOnCurrentModule("orderQty");
    	//String OrderAction = dataTable2.getValueOnCurrentModule("OrderAction");
    	int TimeOutinSecond = Integer.parseInt(dataTable2.getValueOnOtherModule("evs_GenerateOrderSAPnumber","TimeOutinSecond", 0));
    	boolean flagres = false;
    	String oldSAPnumber = dataTable2.getValueOnOtherModule("evs_GenerateOrderSAPnumber", "OrderSAPnumber", 0);//action.getText(OrderDetailSAPNumber, "oldSAPnumber",test);
    	action.mouseover(OrderDetailSAPNumber, "Get old SAPnumber");
    	dataTable2.setValueOnCurrentModule("OldPO number", oldSAPnumber.replace("[RabbitMQ] Order SAP Number: ",""));
    	action.explicitWait(5000);
    	
    	action.click(admin_Reorder, "create reorder", test);
    	
    	action.scrollElemetnToCenterOfView(admin_orderComment, "Comments", test);
    	action.mouseover(admin_orderComment, "Comments");
    	action.explicitWait(5000);
    	action.clear(admin_orderComment, "Clear Comment Field");
    	action.writeText(admin_orderComment, orderComment,"Write a reorder comment", test);
    	
    	if(typeOfUser.equalsIgnoreCase("Guest")) {
    		String billingCellPhone = action.getText(billingCellPhoneNumber, "Billing Cell Phone Number", test);
    		action.scrollElemetnToCenterOfView(cellPhoneNumber, "Cell Phone Number", test);
    		action.writeText(cellPhoneNumber, billingCellPhone, "CellPhone Number", test);
    	}
    	
    	action.click(admin_SubmitOrder, "Submit Re-Order", test);
    	
    	action.explicitWait(5000);
    	String invoiceMsg = action.getText(admin_invoiceMessage, "invoice message",test);
    	action.CompareResult("Popup-message generated invoice", "Automatically generated invoice.", invoiceMsg, test);
    	
    	String reOrderCreated = action.getText(admin_ReOrderCreated, "invoice message",test);
    	action.CompareResult("Popup-message create order", "You created the order.", reOrderCreated, test);
    	
    	action.mouseover(admin_commentVerify, "verify comment");
    	String reorderComm = action.getText(admin_commentVerify, "reorder comment",test);
    	action.CompareResult("Reorder", "Reorder", reorderComm, test);
    	
    	
    	
    	String newPOnumber = action.getText(admin_NewPOnumber, "New PO number",test);
    	newPOnumber = newPOnumber.replace("#", "");
    	dataTable2.setValueOnCurrentModule("ReorderPO number", newPOnumber);
    	//Need WHILE loop here.
    	int elapsedTime = 0; 
    	long startTime = System.currentTimeMillis();
    	while(elapsedTime<=TimeOutinSecond & flagres==false) {
    	String newSAPnumber = OrderDetailSAPNumber.getText();//action.getText(OrderDetailSAPNumber, "oldSAPnumber",test);
    	newSAPnumber = newSAPnumber.replace("[RabbitMQ] Order SAP Number: ","");
    	
		if (newSAPnumber.equalsIgnoreCase(oldSAPnumber)) {
			action.explicitWait(Integer.valueOf(refreshWaitTime)*1000);
			action.refresh();
		} else {
			flagres = true;
			dataTable2.setValueOnOtherModule("evs_RetriveOrderID", "orderID", newPOnumber, 0);
			action.scrollElemetnToCenterOfView(OrderDetailSAPNumber, "Get New SAP Number", test);
			newSAPnumber = newSAPnumber.substring(newSAPnumber.indexOf("000"));
			dataTable2.setValueOnOtherModule("evs_GenerateOrderSAPnumber", "OrderSAPnumber", newSAPnumber, 0);
			dataTable2.setValueOnCurrentModule("NewSAP number", newSAPnumber);
		}
    	
    	long endTime = System.currentTimeMillis();
		long elapsedTimeInMils = endTime-startTime;
		elapsedTime = ((int) elapsedTimeInMils)/1000;
		System.out.println("elapsedTime: "+elapsedTime);
		
    	}
    	if(flagres == false) {
    		throw new Exception("New ReOrder SAP number is not generated");
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
