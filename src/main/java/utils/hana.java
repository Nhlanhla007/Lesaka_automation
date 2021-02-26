package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class Hanaconnector {


    public int hanaconnector(int bpNumber) throws ClassNotFoundException {
        Connection connection1 = null;
        Connection connection2 = null;
        Connection connection3 = null;
        try {
//connection1 = DriverManager.getConnection("jdbc:sap://11.19.2.191:30215/?autocommit=true", "225505", "Welcome1");
//connection2 = DriverManager.getConnection("jdbc:sap://jdgsbwhqas00.jdg.co.za:30515/?autocommit=true", "225505", "Welcome1");
//connection2 = DriverManager.getConnection("jdbc:sap://jdgshandev00.jdg.co.za:30215/?autocommit=true", "225505", "Welcome1");
//Password for user 225505 Welc0me@2021
            connection2 = DriverManager.getConnection("jdbc:sap://11.19.2.172:30215/?autocommit=true", "225505", "Welc0me@2021");
//11.19.2.191:30215
//jdgsbwhqas00.jdg.co.za:30515
//jdgshandev00.jdg.co.za:30215
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
                Hanaconnector hanacon =new Hanaconnector();
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

    public List<String> Getallcolumns(ResultSet Res) throws SQLException{
        List<String> listofColumns = new ArrayList<>();
        ResultSetMetaData RsMeta = Res.getMetaData();
        int iAllcolumns = RsMeta.getColumnCount();
        System.out.println("Total columns present = "+iAllcolumns);
        String storedColumnnames="";
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

