package de.hdm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.datacontainer.Result;
import de.hdm.db.IDBConnection;
import de.hdm.db.ILogic;
import de.hdm.db.SQLConnection;
import de.hdm.db.SqlLogic;


public class Controller {
    
    ConnectionInfo connectionInfo;
    ArrayList<List<List<String>>> queryList;
    
    ILogic logic;
    
    public Controller(ConnectionInfo connectionInfo, ArrayList<List<List<String>>> query) {
        this.connectionInfo = connectionInfo;
        this.queryList = query;
    }

    public void run() throws SQLException{

        //TODO: query muss geprueft werden
        IDBConnection dbConnection;
        ILogic logic;

        if(true){
            System.out.println("Contr");
            dbConnection = new SQLConnection();
            logic = new SqlLogic((SQLConnection) dbConnection);
        }
        else{
            
        }
        dbConnection.connect(connectionInfo);
        Result result = logic.request(queryList);


        
    }
}