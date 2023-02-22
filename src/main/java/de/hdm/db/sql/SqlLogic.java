package de.hdm.db.sql;

import java.sql.SQLException;
import java.util.List;

import de.hdm.datacontainer.Query;
import de.hdm.datacontainer.Result;
import de.hdm.db.ILogic;

public class SqlLogic implements ILogic {
    private SQLConnection sqlConnection;
    private Query query;
    
    public SqlLogic(SQLConnection sqlConnection){
        this.sqlConnection = sqlConnection;
        
    }

    private String[] getConditions(Query query){
        


        return null;
    }
     
    public int count(Result result){

        return 0;

    }
    public int count(String[] result){
        return 0;

    }
    public Result request(List<List<String>> query) throws SQLException{
        String argument = query.get(query.size()-1).get(0);
        System.out.println(query);
        switch (argument) {
            case "--table":
            case "-t":
            return sqlConnection.searchTableNames(query.get(query.size()-1).get(1) );
                
                
            case "-c":    
            case "--column":
            String columnName = query.get(query.size()-1).get(1);
            String tableName;
            if(query.size() > 1 && (query.get(query.size()-2).get(0).equals("-t")||query.get(query.size()-2).get(0).equals("--table"))){
                tableName = query.get(query.get(0).size()-2).get(1);
            }
            else{
                tableName = "%";
            }

            return sqlConnection.searchColumnNames(columnName, tableName);
            

            default:
            
            break;
        }
        
        return null;
    }



    

    
}
