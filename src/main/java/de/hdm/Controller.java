package de.hdm;

import java.sql.SQLException;

import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.datacontainer.Query;
import de.hdm.datacontainer.Result;
import de.hdm.db.IDBConnection;
import de.hdm.db.ILogic;
import de.hdm.db.SQLConnection;
import de.hdm.db.SqlLogic;


public class Controller {
    
    ConnectionInfo connectionInfo;
    Query query;
    
    ILogic logic;
    
    public Controller(ConnectionInfo connectionInfo, Query query) {
        this.connectionInfo = connectionInfo;
        this.query = query;
    }

    public void run() throws SQLException{

        //query muss geprueft werden
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
        Result result = logic.request(query);


        
    }
}