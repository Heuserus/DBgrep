package de.hdm.datacontainer;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

public class Result {
    
    private String[] tablenames;
    private String[] columnNames;
    private HashMap<String, HashMap<String, String>[]> objects;

    public void processResultSet(ResultSet rs) throws SQLException {
        // Create a HashMap to store the data from the ResultSet
        HashMap<String, HashMap<String, String>[]> result = this.objects;
        HashMap<String, String>[] oneTable = (HashMap<String, String>[]) new HashMap[10];
      
        // Get the metadata for the ResultSet
        ResultSetMetaData metadata = rs.getMetaData();
        String tableName = metadata.getTableName(0);
      
        // Get the number of columns in the ResultSet
        int numColumns = metadata.getColumnCount();
        int n = 0;
      
        // Iterate over the rows in the ResultSet
        while (rs.next()) {
          // Create a new HashMap to store the row data
          HashMap<String, String> row = new HashMap<>();
      
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
