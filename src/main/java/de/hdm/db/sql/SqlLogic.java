package de.hdm.db.sql;

import de.hdm.datacontainer.Query;
import de.hdm.datacontainer.Result;
import de.hdm.db.ILogic;

import java.sql.SQLException;

public class SqlLogic implements ILogic {
    private SQLConnection sqlConnection;
    private Query query;

    public SqlLogic(SQLConnection sqlConnection) {
        this.sqlConnection = sqlConnection;
    }

    

    public int count(Result result) {
        return 0;
    }

    public int count(String[] result) {
        return 0;
    }

    public Result request(Query query) throws SQLException {
        
        switch (query.getQueryType()) {
            case SEARCH_TABLE_NAMES:
                
                return sqlConnection.searchTableNames(query.getTable());
            case SEARCH_COLUMN_NAMES:
                return sqlConnection.searchColumnNames(query.getColumns(), query.getTable()); //TODO: change this call according to what is needed in method
            case SEARCH_OBJECTS:
                return sqlConnection.searchObjects(query.getTable(), query.getColumns());
                
        }
        return null;
    }
}
