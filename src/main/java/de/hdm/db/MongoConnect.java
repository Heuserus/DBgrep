package de.hdm.db;

import java.sql.SQLException;

import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.datacontainer.Result;

public class MongoConnect implements AutoCloseable {

    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub
        
    }

    public Result searchColumnNames(String column, String table) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    public Result searchTableNames(String table) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    public Result searchObjects(String table, String[] conditions) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    public void connect(ConnectionInfo connectionInfo) throws SQLException {
        // TODO Auto-generated method stub
        
    }
    
}
