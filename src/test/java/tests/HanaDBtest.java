package tests;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import utils.hana;

public class HanaDBtest {
	
	
@Test	
	public  void testHanadb() throws SQLException, IOException{
		 String Server = "11.19.2.172";
		 String Port = "30215";
		 String Username = "225505";
		 String Password = "Welc0me@2021";
		 String name = "DBconnect";
		 String DBType ="ECC_QA";
		//String Query ="Select * from SAPEQ1.KNA1 where KUNNR LIKE '%104022744'";and SAPEQ1.VBPA.PARVW = 'SH'
		 //String Query1 = "select * from SAPEQ1.VBPA where SAPEQ1.VBPA.VBELN = '0005231326' and SAPEQ1.VBPA.POSNR='000010' and SAPEQ1.VBPA.KUNNR='0103774559'";
		 //String Query1 = "select * from  SAPEQ1.ADRC where SAPEQ1.ADRC.ADDRNUMBER IN (select SAPEQ1.VBPA.ADRNR from SAPEQ1.VBPA where SAPEQ1.VBPA.VBELN = '0005231326' and SAPEQ1.VBPA.PARVW = 'WE' and SAPEQ1.VBPA.POSNR<>'')";
		String Query ="Select * from SAPEQ1.VBAK FULL OUTER JOIN SAPEQ1.VBAP ON SAPEQ1.VBAK.VBELN=SAPEQ1.VBAP.VBELN WHERE SAPEQ1.VBAK.VBELN ='0005231326' ";
		hana hn =new hana("ECC_QA",Server,Port,Username,Password);
		ResultSet rs = hn.ExecuteQuery(Query);
		
		int rowsCountReturned = hn.GetRowsCount(rs);
		
		/*System.out.println("Row count returned :"+rowsCountReturned);
		List<String> alldata = hn.Getallcolumns(rs);
		System.out.println("Cols returned :"+alldata);
		List<String> alldataTelephone = hn.GetRowdataByColumnName(rs, "ADDRNUMBER");
		System.out.println("ADDRNUMBER number is  : "+alldataTelephone);
		*/
		
		List<String> alldataADRNR = hn.GetRowdataByColumnName(rs, "ADRNR");
		System.out.println("ADRNR number is  : "+alldataADRNR);
		List<String> alldataPOSNR = hn.GetRowdataByColumnName(rs, "POSNR");
		System.out.println("POSNR number is  : "+alldataPOSNR);
		List<String> alldataKUNNR = hn.GetRowdataByColumnName(rs, "KUNNR");
		System.out.println("KUNNR number is  : "+alldataKUNNR);
		
		List<String> alldataNetworth = hn.GetRowdataByColumnName(rs, "NETWR");
		System.out.println("Net value is  : "+alldataNetworth);
		
		
		List<String> alldataTelephone = hn.GetRowdataByColumnName(rs, "TELF1");
		System.out.println("Telephone number is  : "+alldataTelephone);
		
		
		List<String> alldataDelivery_block= hn.GetRowdataByColumnName(rs, "LIFSK");
		System.out.println("Delivery Block is  : "+alldataDelivery_block);
		
		List<String> alldataPurchaseorder = hn.GetRowdataByColumnName(rs, "BSTNK");
		System.out.println("Purchase order number is  : "+alldataPurchaseorder);
		String Po_number = alldataPurchaseorder.get(alldataPurchaseorder.size()-1);
		
		List<String> alldataProductdesc= hn.GetRowdataByColumnName(rs, "ARKTX");
		System.out.println("Product name is  : "+alldataProductdesc);
		
		List<String> alldataSiteName= hn.GetRowdataByColumnName(rs, "WERKS");
		System.out.println("SITE name is  : "+alldataSiteName);
	
		
		
		
			
	}
}
