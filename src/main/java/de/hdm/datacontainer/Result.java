package de.hdm.datacontainer;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Result {
    
    private String[] tablenames;
    private LinkedHashMap<String, String[]> columnNames;
    private LinkedHashMap<String, LinkedHashMap<String, String>[]> objects;

    public Result(String[] tablenames, LinkedHashMap<String, String[]> columnNames, LinkedHashMap<String, LinkedHashMap<String, String>[]> objects) {
        this.tablenames = tablenames;
        this.columnNames = columnNames;
        this.objects = objects;
    }

    public String[] getTablenames() {
        return tablenames;
    }

    public LinkedHashMap<String, String[]> getColumnNames() {
        return columnNames;
    }

    public LinkedHashMap<String, LinkedHashMap<String, String>[]> getObjects() {
        return objects;
    }

    public void processResultSet(ResultSet rs) throws SQLException {
        // Create a HashMap to store the data from the ResultSet
        LinkedHashMap<String, LinkedHashMap<String, String>[]> result = this.objects;
        LinkedHashMap<String, String>[] oneTable = (LinkedHashMap<String, String>[]) new LinkedHashMap[10];
      
        // Get the metadata for the ResultSet
        ResultSetMetaData metadata = rs.getMetaData();
        String tableName = metadata.getTableName(0);
      
        // Get the number of columns in the ResultSet
        int numColumns = metadata.getColumnCount();
        int n = 0;
      
        // Iterate over the rows in the ResultSet
        while (rs.next()) {
          // Create a new HashMap to store the row data
          LinkedHashMap<String, String> row = new LinkedHashMap<>();
      
          // Iterate over the columns in the row
          for (int i = 1; i <= numColumns; i++) {
            // Get the name and value of the column
            String columnName = metadata.getColumnName(i);
            String columnValue = rs.getString(i);
      
            // Add the column data to the row HashMap
            row.put(columnName, columnValue);
          }
          oneTable[n] = row;
          n++;
          
          
        }
        result.put(tableName, oneTable);
      
        this.objects = result;
      }
}
