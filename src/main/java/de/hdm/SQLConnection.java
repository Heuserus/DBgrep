package de.hdm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.helper.ConnectionInfo;

public class SQLConnection {

    public Connection connection(ConnectionInfo connectionInfo) throws SQLException{
      Connection connection = null;
      connection = DriverManager.getConnection(connectionInfo.url, connectionInfo.getUsername(), connectionInfo.getPassword());

      return connection;
    }

    public Statement getStatement(Connection connection) throws SQLException{
      return connection.createStatement();
    }
    
    public ResultSet query(Statement statement, String query) throws SQLException{
      return statement.executeQuery(query);
    }
}
