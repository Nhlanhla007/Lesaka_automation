package SAP_HanaDB;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import ic_PageObjects.ICDelivery;
import ic_PageObjects.IC_Cart;
import ic_PageObjects.Ic_Products;
import ic_PageObjects.ic_PayUPayment;
import Logger.Log;
import utils.Action;
import utils.Base64Decoding;
import utils.DataTable2;
import utils.hana;

//CHANGES MADE ON LINE 110, ProductData,ICDelivery



	public class SAPorderRelated {
		WebDriver driver;
	    Action action;
	    DataTable2 dataTable2;
	    Base64Decoding decodePassword;
	     LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> dataMap2 =null;
	    public SAPorderRelated(WebDriver driver,LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> dataMap2,DataTable2 dataTable2) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	        action = new Action(driver);
	        this.dataMap2=dataMap2;
	        this.dataTable2 = dataTable2;
	        decodePassword = new Base64Decoding();
	    }
	    
	    static Logger logger = Log.getLogData(Action.class.getSimpleName());
	    
	    //refer this-------------------------
	    public static String BPnumber;
	    //------------------------------------
	    enum Primarykey {
	    	VBELN,
	    	}
	    enum Tablename{
	    	VBAK,
	    	VBAP,
	    }
	    enum coulnmnames{
	    	KUNNR,// email
	    	NETWR,//Net worth
	    	TELF1,//Telephone
	    	LIFSK,//Delivery block
	    	BSTNK,//Purchase
	    	ARKTX,//Product description
	    	WERKS,//Site name 
	    }
	    enum schemas{
	    	SAPEQ1,
	    }
	    public int getConnectionRow(String Instance){
	    	LinkedHashMap<String, ArrayList<String>> connectiondetailSheet = dataMap2.get("DB_connection_master++");
	    	int finalrow=-1;
	    	int noofRows = connectiondetailSheet.get("DB_Instance").size();
	    	for(int con =0;con<noofRows;con++){
	    		if(Instance == connectiondetailSheet.get("DB_Instance").get(con)){
	    			finalrow=con;
	    			
	    			
	    		}
	    	}
	    	return finalrow;
	    }
	  
		public void SAP_OrderDetailVadidation(LinkedHashMap<String, ArrayList<String>> input, ExtentTest test,int rowNumber) throws Exception{
			boolean allcheckpoint =true;
			
			String DBinstance = input.get("DB_Instance").get(rowNumber);
			//ECCQA
			int irow = getConnectionRow(DBinstance);
			
			String Server = dataMap2.get("DB_connection_master++").get("Host").get(irow);//"11.19.2.172";
			String Port =  dataMap2.get("DB_connection_master++").get("port").get(irow);
			String Username =  dataMap2.get("DB_connection_master++").get("Username").get(irow);
			String Password =  dataMap2.get("DB_connection_master++").get("Password").get(irow);
			Password = decodePassword.decode(Password);
			String TypeOfDB = dataMap2.get("DB_connection_master++").get("TypeOfDB").get(irow);
			//String name = "DBconnect";
			//String DBType ="ECC_QA";
			//String Query ="Select * from SAPEQ1.VBAP Limit 9";
			
			 Primarykey key = Primarykey.VBELN;
			 
			//Expected al details to be validated--------------------------------------
			String cartSum = dataTable2.getValueOnOtherModule("ProductSearch", "CartTotal", 0);
			String SAP_orderNo=dataTable2.getValueOnOtherModule("GenerateOrderSAPnumber","OrderSAPnumber",0);
			String ExpPurchaseOrderNo =dataTable2.getValueOnOtherModule("ic_RetriveOrderID","orderID",0);
			String ExpGrandTotal =String.valueOf(cartSum);//comes from cart total
			
			List<String> ExpProductName =new ArrayList<>();
			
			Map<String,List<String>> AllICprducts = Ic_Products.productData;
			for(Map.Entry map : AllICprducts.entrySet()) {
				String produts = (String)map.getKey();
				//sum += (Integer.parseInt(quantity)*Integer.parseInt(price.replace("R", "").replace(",", "")));
				ExpProductName.add(produts);
			} 
			
			String ExpCITY=ICDelivery.Cityname.toLowerCase().trim();//"Pietersburg";
			String ExpSTREET=ICDelivery.Streetname.toLowerCase().trim();//"Gemsbok Street";
			String ExpPostalcode =ICDelivery.Postalcode.trim();
			
			
			//--------------------------------------------------------------------------
			
			Tablename Table1=Tablename.VBAK;
			Tablename Table2=Tablename.VBAP;
			schemas Schema =schemas.SAPEQ1;
			//"Select * from SAPEQ1.VBAK FULL OUTER JOIN SAPEQ1.VBAP ON SAPEQ1.VBAK.VBELN=SAPEQ1.VBAP.VBELN WHERE SAPEQ1.VBAK.VBELN ='0005231326' ";
			String Query= "Select * from "+Schema+"."+Table1+" FULL OUTER JOIN "+Schema+"."+Table2+" ON "+Schema+"."+Table1+"."+key+" = "+Schema+"."+Table2+"."+key+" WHERE "+Schema+"."+Table1+"."+key+" = '"+SAP_orderNo+"' ";
//			String Query= "SELECT * FROM SAPEQ1."+Table1+" WHERE "+key+" = '"+SAP_orderNo+"'";
			//System.out.println("Query:"+Query);
			hana hn =new hana(TypeOfDB,Server,Port,Username,Password,test);
			ResultSet rs = hn.ExecuteQuery(Query);
			
			int ExpRowcount=1;
			int rowsCountReturned = hn.GetRowsCount(rs);
			//System.out.println("rowsCountReturned: "+rowsCountReturned);
			//check a single record is found for the SAP order no.
			if( rowsCountReturned>=0){
				action.CompareResult(" SAP #Order :"+SAP_orderNo+" SAP hana DB record count is greater than O, Populated rows:"+rowsCountReturned, "True", "True", test);
			}else{
				allcheckpoint=false;
				action.CompareResult(" SAP #Order :"+SAP_orderNo+" SAP hana DB record count is greater than O, Populated rows:"+rowsCountReturned, "True", "False", test);
			}
			
			if(allcheckpoint){
				//Purchase order verification ---------------------------------------------
				List<String> alldataPurchaseorder = hn.GetRowdataByColumnName(rs, "BSTNK");
				//System.out.println("Purchase order number is  : "+alldataPurchaseorder);
				logger.info("Purchase order number is  : "+alldataPurchaseorder);
				String ActPurchaseOrderNo = String.join("", alldataPurchaseorder);
				
				action.CompareResult("Purchase Order Number in SAP DB ", ExpPurchaseOrderNo, ActPurchaseOrderNo, test);
				
				//Verify the  total price -------------------------------------------------
				List<String> alldataOrderQuantity = hn.GetRowdataByColumnName(rs, "KWMENG");
				
				List<String> alldataPrice = hn.GetRowdataByColumnName(rs, "CMPRE");
				String ActualPrice ="";
				float Totalsum=0;
				//System.out.println("alldataOrderQuantity.size()  : "+alldataOrderQuantity.size());
				
				for(int i=0;i<alldataOrderQuantity.size();i++){
					//System.out.println("counter is : "+i);
					float eachOrder = Float.parseFloat(alldataOrderQuantity.get(i));
					float eachPrice = Float.parseFloat(alldataPrice.get(i));
					
					float eachproductSumation = eachOrder*eachPrice;
					Totalsum = Totalsum+eachproductSumation;
				}
				ActualPrice = Float.toString(Totalsum);
				if(Float.parseFloat(ActualPrice)>=Float.parseFloat(ExpGrandTotal)){
					action.CompareResult(" Total Cart Price for all products in SAP DB "+"ActualPrice :"+ActualPrice+" Expected :"+ExpGrandTotal, "True", "True", test);
				}else{
					action.CompareResult(" Total Cart Price for all products in SAP DB "+"ActualPrice :"+ActualPrice+" Expected :"+ExpGrandTotal, "True", "False", test);
				}
				
				
				
				//Verify all product description----------------------------------------------
				/*
				 * List<String> alldataProductdesc= hn.GetRowdataByColumnName(rs, "ARKTX");
				 * //System.out.println("Product name is  : "+alldataProductdesc);
				 * logger.info("Product name is  : "+alldataProductdesc); for(int
				 * k=0;k<ExpProductName.size();k++){ String eachProduct = ExpProductName.get(k);
				 * String AllProductsNameDB =String.join("", alldataProductdesc); //
				 * System.out.println("ExpeachProduct "+eachProduct+" Actual "+AllProductsNameDB
				 * ); action.CompareResult(" Products Purchased Description in SAP DB",
				 * eachProduct.trim().toUpperCase(), AllProductsNameDB.trim().toUpperCase(),
				 * test);
				 * 
				 * }
				 */ 
				String isSkuPresent = dataTable2.getValueOnOtherModule("OrderStatusSearch", "IsBundleArticleSKUPresent", 0);
				 if(isSkuPresent != "false") {
					 //List<String> bundleArticleSKU= MagentoOrderStatusPage.AllSKU;
					List<String> bundleArticleSKU =Arrays.asList(dataTable2.getValueOnOtherModule("OrderStatusSearch", "BundleArticleSKU", 0).split("#"));
					List<String> alldataSKU= hn.GetRowdataByColumnName(rs, "MATNR");
					//logger.info("Product name is  : "+alldataProductdesc);
					 for(int k=0;k<bundleArticleSKU.size();k++){
						 boolean skuPresent = false;
						 for(int i=0;i<alldataSKU.size();i++) {
							 if(alldataSKU.get(i).equalsIgnoreCase(bundleArticleSKU.get(k))) {
								 skuPresent = true;
								 action.CompareResult("Magento SKU Is Present In SAP Database", alldataSKU.get(i), bundleArticleSKU.get(k), test);
							 }
						 }
						 
						 if(skuPresent == false) {
							 action.CompareResult("Magento SKU for "+ bundleArticleSKU.get(k)+" Is Not Present In SAP Database", "true", "false", test);
						 }
						 //String eachProduct = ExpProductName.get(k);
						 //String AllProductsNameDB =String.join("", alldataProductdesc);
						 //System.out.println("ExpeachProduct "+eachProduct+" Actual "+AllProductsNameDB);
						 //action.CompareResult(" Products Purchased Description in SAP DB", eachProduct.trim().toUpperCase(), AllProductsNameDB.trim().toUpperCase(), test);
					
					 }
				 }
				// verify Delivery Block ----------------------------------------------------
				 List<String> alldataDelivery_block= hn.GetRowdataByColumnName(rs, "LIFSK");
			   //  System.out.println("Delivery Block is  : "+alldataDelivery_block);
			     String ActualDeliveryBlock = String.join(",", alldataDelivery_block).replace(" ","").replace(",", "");
			   
			     if(ActualDeliveryBlock.length()<=1){
			    	 action.CompareResult(" Delivery block is lifted ", "EMPTY", "EMPTY", test);
			     }else{
			    	 action.CompareResult(" Delivery block is lifted ", "EMPTY", ActualDeliveryBlock, test);
			     }
			     //Collect the BP number for validating Customer details details -------------------------------
			     List<String> allBPnumber= hn.GetRowdataByColumnName(rs, "KUNNR");
			    // System.out.println("BP number is  : "+allBPnumber);
			     logger.info("BP number is  : "+allBPnumber);
			     BPnumber = String.join("", allBPnumber).trim();
			     BPnumber=BPnumber.replace(" ", "");			     
			     //commenting this line but will check for other testcase dependencies
//			     input.get("BP_Number").set(rowNumber,BPnumber);
			}
			
			 
			//String Query= "Select * from "+Schema+"."+Table1+" FULL OUTER JOIN "+Schema+"."+Table2+" ON "+Schema+"."+Table1+"."+key+" = "+Schema+"."+Table2+"."+key+" WHERE "+Schema+"."+Table1+"."+key+" = '"+SAP_orderNo+"' ";
			String Query1 = "select * from  SAPEQ1.ADRC where SAPEQ1.ADRC.ADDRNUMBER IN (select SAPEQ1.VBPA.ADRNR from SAPEQ1.VBPA where SAPEQ1.VBPA.VBELN = '"+SAP_orderNo+"' and SAPEQ1.VBPA.PARVW = 'WE' and SAPEQ1.VBPA.POSNR<>'')";
			
			ResultSet rs1 = hn.ExecuteQuery(Query1);
			int Rowcount = hn.GetRowsCount(rs1);
		//	System.out.println("TotalRowcount"+Rowcount);
			List<String> allcolsdata =  hn.Getallcolumns(rs1);
		//	System.out.println("ALL COLS DATA : "+allcolsdata);
			//Verify the address---------------------------------------------------------
			
			
			List<String> alldataADRNR = hn.GetRowdataByColumnName(rs1, "ADDRNUMBER");
			//System.out.println("ADDRESS number is  : "+alldataADRNR);
			logger.info("ADDRESS number is  : "+alldataADRNR);
			
			List<String> alldataSTREET = hn.GetRowdataByColumnName(rs1, "STREET");
		//	System.out.println("STREET is  : "+alldataSTREET);
			String ActualStreet =String.join(" ", alldataSTREET).toLowerCase();
			action.CompareResult(" Street name from SAP DB ", ExpSTREET, ActualStreet, test);
			
			List<String> alldataCITY = hn.GetRowdataByColumnName(rs1, "CITY1");
		//	System.out.println("CITY is  : "+alldataCITY);
			String ActualCity = String.join(",", alldataCITY).toLowerCase();
			action.CompareResult(" CITY name from SAP DB ", ExpCITY, ActualCity, test);
			
			List<String> alldataPOST_CODE = hn.GetRowdataByColumnName(rs1, "POST_CODE1");
		//	System.out.println("POST_CODE number is  : "+alldataPOST_CODE);
			String ActualPostalCode = String.join(" ", alldataPOST_CODE);
			action.CompareResult(" Postal code from SAP DB ", ExpPostalcode, ActualPostalCode, test);
			
			hn.closeDB();
			//System.out.println("Closing database");
			logger.info("Closing Database");
		}
		

}

