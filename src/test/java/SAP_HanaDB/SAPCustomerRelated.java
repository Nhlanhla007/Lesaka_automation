package SAP_HanaDB;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import JDGroupPageObjects.ic_Login;
import ic_MagentoPageObjects.MagentoAccountInformation;
import ic_MagentoPageObjects.MagentoRetrieveCustomerDetailsPage;
import ic_MagentoPageObjects.ic_MagentoOrderSAPnumber;
import utils.Action;
import utils.hana;


public class SAPCustomerRelated {

	WebDriver driver;
    Action action;
    HashMap<String, HashMap<String, ArrayList<String>>> dataMap2 =null;
    String bp = SAPorderRelated.BPnumber; //BP Number -->Customer BP number, if line 99 is null set this.
    hana hn;
    MagentoRetrieveCustomerDetailsPage magentoRetrieve;
    MagentoAccountInformation magentoVerification;
    static Map<String, String> dataStore;
    public SAPCustomerRelated(WebDriver driver,HashMap<String, HashMap<String, ArrayList<String>>> dataMap2) {
    	  this.driver = driver;
	        PageFactory.initElements(driver, this);
	        action = new Action(driver);
	        dataStore = new LinkedHashMap<>();
	        this.dataMap2=dataMap2;
	        magentoRetrieve = new MagentoRetrieveCustomerDetailsPage(driver);
	        magentoVerification = new MagentoAccountInformation(driver);
    }
    
    public int getConnectionRow(String Instance){
    	HashMap<String, ArrayList<String>> connectiondetailSheet = dataMap2.get("DB_connection_master++");//Db connection h
    	int finalrow=-1;
    	int noofRows = connectiondetailSheet.get("DB_Instance").size();
    	for(int con =0;con<noofRows;con++){
    		if(Instance == connectiondetailSheet.get("DB_Instance").get(con)){
    			finalrow=con;
    			
    		}
    	}
    	return finalrow;
    }
  
    
    
    enum kna1Columns{
		KUNNR,NAME1,ADRNR,ANRED,//ERDAT,ERNAM
	}
	
	enum adrcColumns{
		CITY1,POST_CODE1,STREET,TEL_NUMBER,TIME_ZONE,COUNTRY,REGION,
	}
	
	enum but000Columns{
		
		NAME_LAST,
		NAME_FIRST,
		INITIALS,
		BIRTHDT,
		XSEXM,
		XSEXF,

	}
	
	enum vatNumberColumns{
		TAXNUM
	}
	
	enum adrc6Columns{
		SMTP_ADDR,SMTP_SRCH,
	}
	
	
	enum adrc2Columns{
		TELNR_LONG
	}
	
	
	enum but0IDColumns{
		IDNUMBER,
	}
	String vatNumberFlag ;
	String email ;
	String website;
	public void sapDbTests(HashMap<String, ArrayList<String>> input,ArrayList<HashMap<String, ArrayList<String>>> mySheets,ExtentTest test,int testcaseID,int rowNumber) throws Exception {
		int sheetRow1= findRowToRun(mySheets.get(0), 0, testcaseID); //accountCreation
		int sheetRow2= findRowToRun(mySheets.get(1), 0, testcaseID); //deliverypopulation
		//int sheetRow3= findRowToRun(mySheets.get(2), 0, testcaseID); //typeOf sap validation //uses input.get("Value").get(rowNumber)
		int sheetRow4= findRowToRun(mySheets.get(2), 0, testcaseID); //updatesheet
		
		/*
		 * String DBinstance = input.get("DB_Instance").get(rowNumber); //ECCQA int irow
		 * = getConnectionRow(DBinstance);
		 * 
		 * String Server =
		 * dataMap2.get("DB_connection_master++").get("Host").get(irow);//"11.19.2.172";
		 * String Port = dataMap2.get("DB_connection_master++").get("port").get(irow);
		 * String Username =
		 * dataMap2.get("DB_connection_master++").get("Username").get(irow); String
		 * Password = dataMap2.get("DB_connection_master++").get("Password").get(irow);
		 * String TypeOfDB =
		 * dataMap2.get("DB_connection_master++").get("TypeOfDB").get(irow);
		 * 
		 * 
		 */
		
		System.out.println(mySheets.size());
		String typeOfSAPValidation = input.get("typeOfSapValidation").get(rowNumber); 
		//System.out.println(mySheets.get(2).get("firstName_output").get(sheetRow4));
		email = mySheets.get(0).get("emailAddress").get(sheetRow1);
		website = mySheets.get(0).get("WebSite").get(sheetRow1);
		
		//Add construct that checks when if the flag is selected, if not take email from sign in 
		if(typeOfSAPValidation.equalsIgnoreCase("Customer Creation")) {
			navigateToCustomerBpNumber(email, website, test);
		}
		
		//If the update flag is checked it takes the latest updated email, 
		//if not checked it takes the original email
		if(typeOfSAPValidation.equalsIgnoreCase("Customer Update")) {
			String updatedName = mySheets.get(2).get("firstName_output").get(sheetRow4);
			String updatedLastName = mySheets.get(2).get("lastName_output").get(sheetRow4);	
			String updateEmailFlag = mySheets.get(2).get("email").get(sheetRow4);
			String updateEmail = mySheets.get(2).get("email_output").get(sheetRow4);
			if(updateEmailFlag.equalsIgnoreCase("yes")) {
			navigateToCustomerBpNumber(updateEmail, website, test);
			}else {
				//email that logged into ic with
				navigateToCustomerBpNumber(ic_Login.Username, website, test);
			}
		}
		
		magentoVerification.VadidateCustomerInfo_backend(mySheets.get(0), test, sheetRow1);
		
		String SAPorderNumber=MagentoAccountInformation.ActualBPnumber;
			
		
		//SAPorderNumber=SAPorderNumber.replace("[RabbitMQ] Order SAP Number: ", ""); 
		vatNumberFlag = mySheets.get(0).get("vatNumberFlag").get(sheetRow1);//Refer to partner number
		Map<String, String> customerDetails = customerSAPDetails(SAPorderNumber);//This is the partner number		
		switch (typeOfSAPValidation) {
		case "Customer Update":
			//Need the sheet from Bongi
			String firstName;
			String lastName;
			String taxVatNumeber;
			String Email;
			String billingAddress;
			
			break;
		case "Customer Creation":
			String customerCreationname = mySheets.get(0).get("firstName").get(sheetRow1);
			String customerCreationlastName = mySheets.get(0).get("lastName").get(sheetRow1);
			String vatNumber = null;
			String passportOrIdFlag = mySheets.get(0).get("identityType").get(sheetRow1);
			String passportOrId = null; 					    
			
			String SAPFirstName = customerDetails.get("NAME_FIRST");
			action.CompareResult("SAP First name", customerCreationname, SAPFirstName, test);
			
			String SAPLastName = customerDetails.get("NAME_LAST");
			action.CompareResult("SAP Last name", customerCreationlastName, SAPLastName, test);
			
			String SAPEmail = customerDetails.get("SMTP_ADDR");
			action.CompareResult("SAP Email", email, SAPEmail, test);
					
							
			if (vatNumberFlag.equalsIgnoreCase("Yes")) {
				vatNumber = mySheets.get(0).get("vatNumber").get(sheetRow1);
				String sapVatnumber = customerDetails.get("TAXNUM");
				action.CompareResult("SAP Vat number", vatNumber, sapVatnumber, test);
			}

			if (passportOrIdFlag.equalsIgnoreCase("ID")) {
				passportOrId = mySheets.get(0).get("identityNumber/passport").get(sheetRow1);
				String SAID = customerDetails.get("IDNUMBER");
				action.CompareResult("SAP SA ID", passportOrId, SAID, test);
			} else if (passportOrIdFlag.equalsIgnoreCase("Passport")) {
				passportOrId = mySheets.get(0).get("identityNumber/passport").get(sheetRow1);
				String passport = customerDetails.get("IDNUMBER");
				action.CompareResult("Passport", passportOrId, passport, test);
			}
			break;

		default:
			break;
		}

	}
	
	public void navigateToCustomerBpNumber(String emailToSearch,String website,ExtentTest test) throws IOException {
		magentoRetrieve.navigateToCustomer(test);
		magentoRetrieve.searchForCustomer(emailToSearch, test);
		magentoRetrieve.tableData(emailToSearch, website, test);		
	}
	
	static Map<String, String> custData ;
	public Map<String, String> customerSAPDetails(String sapOrderNumber) throws Exception {
		 String Server = "11.19.2.172";
		 String Port = "30215";
		 String Username = "225505";
		 String Password = "Welc0me@2021";
		 String name = "DBconnect";
		 String DBType ="ECC_QA";
		
		String Query ="Select * from SAPEQ1.KNA1 WHERE KUNNR = '"+sapOrderNumber+"' Limit 1";
		hn =new hana("HANA",Server,Port,Username,Password); //Remove ECC_QA enter variable from Excel --DONE
		ResultSet rs = hn.ExecuteQuery(Query);
		
		int rowsCountReturned = hn.GetRowsCount(rs);
		System.out.println("Row count returned :"+rowsCountReturned);
		List<String> alldatainrows = hn.GetRowdataByColumnName(rs, "KUNNR");
		System.out.println("Data from all rows : "+alldatainrows);
		//loop through the enum to get the names of all the fields
					//List<String> d =hn.GetRowdataByColumnName(rs, "KUNNR");
					//System.out.println(d.get(0));
		custData = kna1ColumnData(rs);
		
		return custData;
	}
	
	public Map<String, String> kna1ColumnData(ResultSet data) throws Exception {		
		//loop through the enum to get the names of all the fields
		String valueOfADNR = null ;
		String bpNumber = null;
		for(kna1Columns column : kna1Columns.values()) {
			List<String> d =hn.GetRowdataByColumnName(data, column.toString());
			dataStore.put(column.toString(), d.get(0));
			if(column.toString().equalsIgnoreCase("ADRNR")) {
				 valueOfADNR =  d.get(0);
			}
			if(column.toString().equalsIgnoreCase("KUNNR")) {
				bpNumber = d.get(0);
			}
		}
		but0ID(bpNumber);
		if(vatNumberFlag.equalsIgnoreCase("yes")) {
		vatNumber(bpNumber);
		}
		adr6Data(valueOfADNR);
		adrcData(valueOfADNR);
		but000Data(bpNumber);
		return  dataStore;
	}
	
	//need method that accepts ARDC, and searches 
	public void adrcData(String ADRCNumber) throws Exception {
		String query = "SELECT * FROM SAPEQ1.ADRC WHERE ADDRNUMBER = '"+ADRCNumber+"'";
		ResultSet set = hn.ExecuteQuery(query);		
		int rowsCountReturned = hn.GetRowsCount(set);
		System.out.println("Row count returned :"+rowsCountReturned);
		for(adrcColumns adrc : adrcColumns.values()) {
			List<String> d =hn.GetRowdataByColumnName(set, adrc.toString());
			dataStore.put(adrc.toString(), d.get(0));
		}
	}
	
	//method for but000
	public void but000Data(String bpNumber) throws Exception {
		String Query ="Select * from SAPEQ1.BUT000 AS adr WHERE PARTNER = '"+bpNumber.trim()+"' Limit 1";
		ResultSet set = hn.ExecuteQuery(Query);
		int rowsCountReturned = hn.GetRowsCount(set);
		System.out.println("Row count returned :"+rowsCountReturned);
		for(but000Columns but000 : but000Columns.values()) {
			List<String> d =hn.GetRowdataByColumnName(set, but000.toString().trim());
			dataStore.put(but000.toString(), d.get(0));
		}
	}
	public void adr6Data(String ADRCNumber) throws SQLException {
		String query = "SELECT * FROM SAPEQ1.ADR6 WHERE ADDRNUMBER = '"+ADRCNumber+"' AND FLG_NOUSE = ''";
		ResultSet set = hn.ExecuteQuery(query);
		int rowsCountReturned = hn.GetRowsCount(set);
		System.out.println("Row count returned :"+rowsCountReturned);
		for(adrc6Columns but000 : adrc6Columns.values()) {
			List<String> d =hn.GetRowdataByColumnName(set, but000.toString().trim());
			dataStore.put(but000.toString(), d.get(0));
		}
	}
	
	public void vatNumber(String bpNumber) throws SQLException {
		String query = "SELECT * FROM SAPEQ1.DFKKBPTAXNUM  WHERE PARTNER = '"+bpNumber+"'";
		ResultSet set = hn.ExecuteQuery(query);
		int rowsCountReturned = hn.GetRowsCount(set);
		System.out.println("Row count returned :"+rowsCountReturned);
		for(vatNumberColumns but000 : vatNumberColumns.values()) {
			List<String> d =hn.GetRowdataByColumnName(set, but000.toString().trim());
			dataStore.put(but000.toString(), d.get(0));
		}
	}
	
	public void adr2(String ADRCNumber) throws Exception {
		String query = "SELECT * FROM SAPEQ1.ADRC2 WHERE ADDRNUMBER = '"+ADRCNumber+"' AND FLG_NOUSE = ''";
		ResultSet set = hn.ExecuteQuery(query);
		int rowsCountReturned = hn.GetRowsCount(set);
		System.out.println("Row count returned :"+rowsCountReturned);
		for(adrc2Columns but000 : adrc2Columns.values()) {
			List<String> d =hn.GetRowdataByColumnName(set, but000.toString().trim());
			dataStore.put(but000.toString(), d.get(0));
		}
	}
	
	public void but0ID(String bpNumber) throws Exception {
		String query = "SELECT * FROM SAPEQ1.BUT0ID WHERE PARTNER = '"+bpNumber+"'";
		ResultSet set = hn.ExecuteQuery(query);
		int rowsCountReturned = hn.GetRowsCount(set);
		System.out.println("Row count returned :"+rowsCountReturned);
		for(but0IDColumns but000 : but0IDColumns.values()) {
			List<String> d =hn.GetRowdataByColumnName(set, but000.toString().trim());
			dataStore.put(but000.toString(), d.get(0));
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
