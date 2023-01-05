package de.hdm.db;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public Result request(ArrayList<List<List<String>>> query) throws SQLException{
        String argument = query.get(0).get(query.get(0).size()-1).get(0);
        System.out.println(query);
        switch (argument) {
            case "--table":
            case "-t":
            return sqlConnection.searchTableNames(query.get(0).get(query.get(0).size()-1).get(1) );
                
                
            case "-c":    
            case "--column":  
            return sqlConnection.searchColumnNames(query.get(0).get(query.get(0).size()-1).get(1), query.get(0).get(query.get(0).size()-2).get(1));
            case "--table-names":

                     break;
        }
        
        return null;
        
    }
    
}
