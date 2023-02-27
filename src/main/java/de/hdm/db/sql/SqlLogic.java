package de.hdm.db.sql;

import de.hdm.datacontainer.Query;
import de.hdm.datacontainer.Result;
import de.hdm.db.ILogic;

import java.sql.SQLException;

public class SqlLogic implements ILogic {
    private SQLConnection sqlConnection;
    private Query query;

    /**
     * Constructor for SQLLogic.
     * @param sqlConnection JDBC Connection object
     */
    public SqlLogic(SQLConnection sqlConnection) {
        this.sqlConnection = sqlConnection;
    }

    /**
     * Request function to execute the correct sql query.
     * @param query Query object 
     * @return Resultobject filled with the result data.
     */
    public Result request(Query query) throws SQLException {
        
        switch (query.getQueryType()) {
            case SEARCH_TABLE_NAMES:
                
                return sqlConnection.searchTableNames(query.getTable());
            case SEARCH_COLUMN_NAMES:
                return sqlConnection.searchColumnNames(query.getColumns(), query.getTable()); 
            case SEARCH_OBJECTS:
                return sqlConnection.searchObjects(query.getTable(), query.getColumns());
                
        }
        return null;
    }
}
