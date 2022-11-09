package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class SQLConnection {

    public Connection connection(String jdbcURL, String username, String password){
      Connection connection = null;
      connection = DriverManager.getConnection(jdbcURL, username, password);

      return connection;
    }

    public Statement getStatement(Connection connection){
      return connection.createStatement();
    }
    
    public ResultSet query(Statement statement, String query){
      return statement.executeQuery(query);
    }
}
