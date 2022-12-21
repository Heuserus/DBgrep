package de.hdm.db;

import java.sql.SQLException;

import org.postgresql.core.SqlCommand;

import de.hdm.datacontainer.Query;
import de.hdm.datacontainer.Result;
import de.hdm.db.ILogic;
import de.hdm.db.SQLConnection;

public class SqlLogic implements ILogic {
    private SQLConnection sqlConnection;
    private Query query;
    
    public SqlLogic(SQLConnection sqlConnection){
        this.sqlConnection = sqlConnection;
        
    }

     
    public int count(Result result){

        return 0;

    }
    public int count(String[] result){
        return 0;

    }
    public Result request(Query query) throws SQLException{
        switch (query.queryString[query.queryString.length-1][0]) {
            case "--table":
            System.out.println("--table");
                break;
                
                     
            case "--column":  

                     break;
            case "--table-names":

                     break;
        }
        
        return null;
        
    }
    
}
