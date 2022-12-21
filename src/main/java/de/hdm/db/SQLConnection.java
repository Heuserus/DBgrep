package de.hdm.db;

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
import de.hdm.db.IDBConnection;

public class SQLConnection implements IDBConnection {

    private Statement statement;
    private Connection connection;

    public void connect(ConnectionInfo connectionInfo) throws SQLException{
      connection = DriverManager.getConnection(connectionInfo.getUrl(), connectionInfo.getUsername(), connectionInfo.getPassword());
      statement = connection.createStatement();
    }

    public Result searchTableNames(String table) throws SQLException{
      ResultSet tablenames = statement.executeQuery("SELECT * FROM sys.TABLES WHERE TABLE_NAME LIKE '"+ table+"'");
      


      return null;
    }

    public Result searchColumnNames(String column, String table) throws SQLException{
      ResultSet columnnames = statement.executeQuery("SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = N'" + table + "'");
      return null;
    }

    public Result searchObjects(String table, String[] conditions) throws SQLException{
      StringBuilder queryBuilder = new StringBuilder();
      queryBuilder.append("SELECT * FROM " + table);

      if (conditions.length > 0) {
          queryBuilder.append(" WHERE ");

          for (int i = 0; i < conditions.length; i++) {
              queryBuilder.append(conditions[i]);

              if (i != conditions.length - 1) {
                  queryBuilder.append(" AND ");
              }
          }
      }
      ResultSet result  = statement.executeQuery(queryBuilder.toString());
      return null;
    }
    

    public DatabaseMetaData getDBMetaData(Connection connection) throws SQLException{
      return connection.getMetaData();
    }

    public ResultSet getTableNames(DatabaseMetaData metadata, String pattern) throws SQLException{
      String [] types = {"Table"};
      return getDBMetaData(connection).getTables(null,null,pattern,types);
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}

