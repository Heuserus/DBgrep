package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import de.hdm.helper.ConnectionInfo;

public class SQLConnection {

    public Connection connection(ConnectionInfo connectionInfo){
      Connection connection = null;
      connection = DriverManager.getConnection(connectionInfo.url, connectionInfo.username, connectionInfo.password);

      return connection;
    }

    public Statement getStatement(Connection connection){
      return connection.createStatement();
    }
    
    public ResultSet query(Statement statement, String query){
      return statement.executeQuery(query);
    }
}
