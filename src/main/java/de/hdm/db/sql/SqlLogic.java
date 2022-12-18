package de.hdm.db;

import de.hdm.datacontainer.Query;
import de.hdm.datacontainer.Result;
import de.hdm.db.ILogic;
import de.hdm.db.SQLConnection;

public class SqlLogic implements ILogic {
    

    public SQLConnection sqlConnection;

     
    public int count(Result result){

        return 0;

    }
    public int count(String[] result){
        return 0;

    }
    public Result request(Query query){

        
        return null;
        
    }
    
}
