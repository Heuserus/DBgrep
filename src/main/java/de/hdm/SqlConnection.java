package de.hdm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;

import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.datacontainer.Result;

public class SqlConnection implements DBConnection {

    Statement statement;
    Connection connection;

    public void connect(ConnectionInfo connectionInfo) throws SQLException{
      connection = DriverManager.getConnection(connectionInfo.url, connectionInfo.getUsername(), connectionInfo.getPassword());
      statement = connection.createStatement();
    }

    public Result searchTableNames(String table){

      return null;
    }

    public Result searchColumnNames(String column, String table){

      return null;
    }

    public Result searchObjects(String table, String[] conditions){
      return null;
    }
    
    
    





    public DatabaseMetaData getDBMetaData(Connection connection) throws SQLException{
      return connection.getMetaData();
    }

    public ResultSet getTableNames(DatabaseMetaData metadata, String pattern) throws SQLException{
      String [] types = {"Table"};
      return getDBMetaData(connection).getTables(null,null,pattern,types);
    }
}
