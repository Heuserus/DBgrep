package de.hdm.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;

import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.datacontainer.Result;
import de.hdm.db.IDBConnection;

public class SQLConnection implements IDBConnection {

    private Statement statement;
    private Connection connection;
    private DatabaseMetaData metaData;
    private String w = "T";

    public void connect(ConnectionInfo connectionInfo) throws SQLException{
      connection = DriverManager.getConnection(connectionInfo.getUrl(), connectionInfo.getUsername(), connectionInfo.getPassword());
      statement = connection.createStatement();
      metaData = connection.getMetaData();
    }

    public Result searchTableNames(String table) throws SQLException{
      
      List<String> matchingTables = getTableNames(table);
      String[] strings = matchingTables.stream().toArray(String[]::new);
      Result result = new Result(strings,null,null);
    
      return result;
    }

    public List<String> getTableNames(String table) throws SQLException{
      ResultSet rs = metaData.getTables(null, null, table, null);
      
      List<String> tables = new ArrayList<>();
      while (rs.next()) {
        
        if(rs.getObject("table_type") != null && rs.getObject("table_type",w.getClass()).equals("TABLE")){
          //System.out.println(rs.getString("TABLE_NAME"));
          tables.add(rs.getString("TABLE_NAME"));
        }
      }
      List<String> matchingTables = tables;
      return matchingTables;
    }

    public List<String> getMatchingStrings(List<String> strings, String input) {
      List<String> matchingStrings = new ArrayList<>();
      for (String string : strings) {
          if (string.toLowerCase().contains(input.toLowerCase())) {
              matchingStrings.add(string);
          }
      }
      return matchingStrings;
  }

    public Result searchColumnNames(String column, String table) throws SQLException{
      List<String> tables = getTableNames(table);
      List<String> matchingColumns = getColumnNames(column, tables.get(0));
      
      for(int i = 1; i< tables.size(); i++){
        List<String> columnsOfTable = getColumnNames(column, tables.get(i));
        matchingColumns.addAll(columnsOfTable);
      }
     
      String[] strings = matchingColumns.stream().toArray(String[]::new);
      Result result = new Result(null,strings,null);
      return result;
    }

    List<String> getColumnNames(String column, String table) throws SQLException{
      ResultSet rs = statement.executeQuery("select * from " + table);
      ResultSetMetaData rsMetaData = rs.getMetaData();
      int count = rsMetaData.getColumnCount();
      List<String> columns = new ArrayList<>();
      for(int i = 1; i<=count; i++) {
        columns.add(rsMetaData.getColumnName(i));
      }
      List<String> matchingColumns = getMatchingStrings(columns, column);
      return matchingColumns;
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
    

    

    

    @Override
    public void close() throws Exception {
        connection.close();
    }

    

    
}

