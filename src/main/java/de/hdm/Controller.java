package de.hdm;

import java.sql.SQLException;

import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.datacontainer.Query;
import de.hdm.db.IDBConnection;
import de.hdm.db.ILogic;
import de.hdm.db.sql.SQLConnection;
import de.hdm.db.mongo.MongoConnection;



public class Controller {
    
    ConnectionInfo connectionInfo;
    Query query;
    
    ILogic logic;
    
    public Controller(ConnectionInfo connectionInfo, Query query) {
        this.connectionInfo = connectionInfo;
        this.query = query;
    }

    public void run(ConnectionInfo cI, Query rQ) throws SQLException{

        connectionInfo = cI;
        query = rQ;
        IDBConnection dbConnection = new SQLConnection();

        if(true){
            dbConnection = new SQLConnection();
        }
        else{

        }
        

        dbConnection.connect(cI);


        
    }
}