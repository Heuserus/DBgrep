package de.hdm.db;

import de.hdm.datacontainer.Query;
import de.hdm.datacontainer.Result;

import java.sql.SQLException;
import java.util.List;

public class SqlLogic implements ILogic {
    private SQLConnection sqlConnection;
    private Query query;
    
    public SqlLogic(SQLConnection sqlConnection){
        this.sqlConnection = sqlConnection;
        
    }

    private String[] getConditions(Query query){
        


        return null;
    }

    public int count(Result result) {

        return 0;

    }

    public int count(String[] result) {
        return 0;

    }

    public Result request(List<List<List<String>>> query) throws SQLException {
        String argument = query.get(query.get(0).size() - 1).get(0).get(0);
        System.out.println(query);
        switch (argument) {
            case "--table":
            case "-t":
                return sqlConnection.searchTableNames(query.get(query.get(0).size() - 1).get(1).get(0));


            case "-c":
            case "--column":
                return sqlConnection.searchColumnNames(query.get(query.get(0).size() - 1).get(1).get(0), query.get(query.get(0).size() - 2).get(1).get(0));
            

            case "-o":
            case "--object":
            System.out.println("Object");
            break;
        }
        
        return null;
    }

    

    
}
