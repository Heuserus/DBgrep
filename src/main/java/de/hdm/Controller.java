package de.hdm;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;

import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.datacontainer.Query;
import de.hdm.datacontainer.Result;
import de.hdm.DBConnection;


public class Controller {
    
    ConnectionInfo connectionInfo;
    Query query;
    
    Logic logic;
    
    public void run(ConnectionInfo cI, Query rQ) throws SQLException{

        connectionInfo = cI;
        query = rQ;
        DBConnection dbConnection = new SqlConnection();

        if(true){
            dbConnection = new SqlConnection();
        }
        else{
        }
        

        dbConnection.connect(cI);


        
}
}
