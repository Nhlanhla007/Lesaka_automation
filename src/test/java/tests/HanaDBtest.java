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
		//String Query ="Select * from SAPEQ1.KNA1 where KUNNR LIKE '%104022744'";
		
		String Query ="Select * from SAPEQ1.VBAP Limit 9";
		hana hn =new hana("ECC_QA",Server,Port,Username,Password);
		ResultSet rs = hn.ExecuteQuery(Query);
		
		int rowsCountReturned = hn.GetRowsCount(rs);
		System.out.println("Row count returned :"+rowsCountReturned);
		List<String> alldatainrows = hn.GetRowdataByColumnName(rs, "NAME1");
		System.out.println("Data from all rows : "+alldatainrows);
		
		
		
		/*//int countofRows = hn.checkRecordCount(rs);
		List<String> AllcolumnData = hn.Getallcolumns(rs);
		System.out.println("All column Headers  :"+AllcolumnData);
		System.out.println("Total column Headers size :"+AllcolumnData.size());
		
		ArrayList<HashMap<String, String>> Datarec = hn.RetriveRowsData(rs);
		System.out.println("SIZE OF RECORDS : "+Datarec.size());
		System.out.println(Datarec.get(5).get("NAME1"));
		//List<String> AllcolumnData = hn.Getallcolumns(rs);
*/		
		
		
		
	}
}
