package de.hdm.db;

import de.hdm.datacontainer.Query;
import de.hdm.datacontainer.Result;

import java.sql.SQLException;

public class SqlLogic implements ILogic {
    private SQLConnection sqlConnection;
    private Query query;

    public SqlLogic(SQLConnection sqlConnection) {
        this.sqlConnection = sqlConnection;
    }

    private String[] getConditions(Query query) {
        return null;
    }

    public int count(Result result) {
        return 0;
    }

    public int count(String[] result) {
        return 0;
    }

    public Result request(Query query) throws SQLException {
        System.out.println(query);
        switch (query.getQueryType()) {
            case SEARCH_TABLE_NAMES:
                return sqlConnection.searchTableNames(query.getTable());
            case SEARCH_COLUMN_NAMES:
                return sqlConnection.searchColumnNames(query.getTable(), query.getTable()); //TODO: change this call according to what is needed in method
            case SEARCH_OBJECTS:
                System.out.println("Object");
                break;
        }
        return null;
    }
}
