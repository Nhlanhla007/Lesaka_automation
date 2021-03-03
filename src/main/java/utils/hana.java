package utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;

import Logger.Log;
public class hana {
	private static final ExtentTest test = null;
	static Logger logger = Log.getLogData(Action.class.getSimpleName());
	WebDriver driver;
	static Connection hanaconnet = null;
	 //ResultSet resultSet=null;
	public hana(String DBType, String Server, String Port,String Username,String Password) throws SQLException, IOException {
		Server = "11.19.2.172";
		Port = "30215";
		Username = "225505";
		Password = "Welc0me@2021";
		DBType ="ECC_QA";
		//String name = "DBconnect";
		 hanaconnet = hanaDBconnect(DBType, Server, Port, Username, Password);
		 
	}
	Action action =new Action(driver);
	
	public Connection hanaDBconnect(String DBType,String Server, String Port, String Username,String Password) throws SQLException, IOException{
		boolean connectFlag=false;
		Connection connection = null;
		
		//ExtentTest node=test.createNode("Hana DB connect");
		try {
			switch (DBType) {
			case "ECC_QA":
				Server = "11.19.2.172";
				Port = "30215";
				Username = "225505";
				Password = "Welc0me@2021";
				String name = "DBconnect";
				connection = DriverManager.getConnection("jdbc:sap://"+Server+":"+Port+"/?autocommit=true", Username,Password);
				break;
			case "CRM_QA":
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//logger.info("Unable Connect to Hana DB: "+ e.getMessage());
			e.printStackTrace();
		}
		if (connection != null) {
			connectFlag =true;
			System.out.println("Connected");
			//node.pass("DB -connected sucessfully");
		}else{
			System.out.println("NOT Connected");
			//node.fail("Error:DB -connection not sucessfull");
		}
		
		return connection;
		
	}

	public static ResultSet ExecuteQuery(String Query) throws SQLException{
		//Statement stmt1 = hanaconnet.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
		java.sql.Statement stmt1 = hanaconnet.createStatement();
	    ResultSet resultSet = stmt1.executeQuery(Query);
		return resultSet;
		
	}
	public int checkRecordCount(ResultSet Res) throws SQLException{
		 List<String> colHeader = Getallcolumns(Res);
		 int counter =0;
		 while (Res.next()){
	         counter++;
	         List<String> listofRows = new ArrayList<>();
	         for (int j=1;j<=colHeader.size();j++) {
	             String eachcelldata = Res.getString(j);
	             if (eachcelldata != "") {
	                 listofRows.add(eachcelldata);
	             } else {
	                 listofRows.add("null");
	             }
	         }
	         logger.info("All Row"+counter+" data : "+listofRows);
	         
	         logger.info("All Row"+counter+" data size is : "+listofRows.size());
	         
	     }
	    return counter;
	}
	ArrayList <HashMap<String, String>> DBrecords =null;
	
	public ArrayList<HashMap<String, String>> RetriveRowsData(ResultSet Res) throws SQLException{
		DBrecords = new ArrayList <HashMap<String, String>>();
		HashMap<String, String> row = null;
		 List<String> colHeader = Getallcolumns(Res);
		 int counter =0;
		 
		 while (Res.next()){
	         counter++;
	         row = new HashMap<String, String>();
	         List<String> listofRows = new ArrayList<>();
	         for (int j=0;j<colHeader.size();j++) {
	             String eachcelldata = Res.getString(j+1);
	             if (eachcelldata != "") {
	                 //listofRows.add(eachcelldata);
	                 row.put(colHeader.get(j), eachcelldata);
	             } else {
	            	 row.put(colHeader.get(j), " ");
	                 //listofRows.add("null");
	             }
	         }
//	         logger.info("All Row"+counter+" data : "+listofRows);
//	         
//	         logger.info("All Row"+counter+" data size is : "+listofRows.size());
	         DBrecords.add(row);
	     }
		
	    return DBrecords;
	}
	public int GetRowsCount(ResultSet Res) throws SQLException{
		DBrecords = new ArrayList <HashMap<String, String>>();
		DBrecords = RetriveRowsData(Res);
		
		return DBrecords.size();
		
	}
	public List<String> GetRowdataByColumnName(ResultSet Res,String CoulmnNameToSearch) throws SQLException{
		//DBrecords = new ArrayList <HashMap<String, String>>();
		//DBrecords = RetriveRowsData(Res);
		List<String> Dataforcols = new ArrayList<>();
		for(int i=0;i<DBrecords.size();i++){
			Dataforcols.add(DBrecords.get(i).get(CoulmnNameToSearch));
		}
		return Dataforcols;
		
	}
	public List<String> Getallcolumns(ResultSet Res) throws SQLException{
	    List<String> listofColumns = new ArrayList<>();
	    ResultSetMetaData RsMeta = Res.getMetaData();
	    int iAllcolumns = RsMeta.getColumnCount();
	    logger.info("Total columns present = "+iAllcolumns);
	    
	    if(iAllcolumns>=1){
	        for(int i=1;i<=iAllcolumns;i++){
	            String eachcol = RsMeta.getColumnName(i);
	            listofColumns.add(eachcol);
	        }
	    }else{
	        listofColumns.add(null);
	    }
	    return listofColumns;
	}
	
}




/*
    public int hanaconnector(int bpNumber) throws ClassNotFoundException {
        Connection connection1 = null;
        Connection connection2 = null;
        Connection connection3 = null;
        try {

            connection2 = DriverManager.getConnection("jdbc:sap://11.19.2.172:30215/?autocommit=true", "225505", "Welc0me@2021");

        } catch (SQLException e) {
            System.err.println("Connection Failed:");
            System.err.println(e);

        }
        int counter =0;
        if (connection2 != null) {
            try {
                System.out.println("Connection to HANA successful!");
                Statement stmt1 = connection2.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_READ_ONLY);

                ResultSet resultSet2 = stmt1.executeQuery("Select * from SAPEQ1.KNA1 where KUNNR = '"+bpNumber+"'");
                hana hanacon =new hana();
                List<String> colHeader = hanacon.Getallcolumns(resultSet2);
                System.out.println("All header data : "+colHeader);
                System.out.println("All header data : "+colHeader.size());

                while (resultSet2.next()){
                    counter++;
                    List<String> listofRows = new ArrayList<>();
                    for (int j=1;j<=colHeader.size();j++) {
                        String eachcelldata = resultSet2.getString(j);
                        if (eachcelldata != "") {
                            listofRows.add(eachcelldata);
                        } else {
                            listofRows.add("null");
                        }
                    }
                    System.out.println("All Row"+counter+" data : "+listofRows);
                    System.out.println("All Row"+counter+" data size is : "+listofRows.size());
                }
                System.out.println(" Total row by counter : "+counter);

            }catch (Exception e) {
                e.printStackTrace();
                System.err.println("Query failed!");
            }
        }
        return counter;
    }
    */

    

