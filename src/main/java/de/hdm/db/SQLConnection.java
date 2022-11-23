package de.hdm.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.datacontainer.ConnectionInfo;

public class SQLConnection {

  public Connection connection(ConnectionInfo connectionInfo) {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(connectionInfo.url, connectionInfo.getUsername(),
          connectionInfo.getPassword());
    } catch (SQLException e) {
      // TODO throw meaningfull excfeption with our ownerror code
      e.printStackTrace();
    }

    return connection;
  }

  public Statement getStatement(Connection connection) {
    Statement statement = null;
    try {
      statement = connection.createStatement();
    } catch (SQLException e) {
      // TODO throw meaningfull excfeption with our ownerror code
      e.printStackTrace();
    }
    return statement;
  }

  public ResultSet query(Statement statement, String query) {
    ResultSet resultSet = null;
    try {
      resultSet = statement.executeQuery(query);
    } catch (SQLException e) {
      // TODO throw meaningfull excfeption with our ownerror code
      e.printStackTrace();
    }
    return resultSet;
  }
}
