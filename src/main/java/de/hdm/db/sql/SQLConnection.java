package de.hdm.db.sql;

import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.datacontainer.Result;
import de.hdm.db.IDBConnection;
import org.apache.commons.collections4.MultiValuedMap;

import java.sql.*;
import java.util.*;

public class SQLConnection implements IDBConnection {

    private Statement statement;
    private Connection connection;
    private DatabaseMetaData metaData;
    private String w = "T";

    /**
     * Builds Connection to the database
     * @param connectionInfo Connectioninfo and authentication parameters.
     */
    public void connect(ConnectionInfo connectionInfo) throws SQLException {
        connection = DriverManager.getConnection(connectionInfo.getUrl(), connectionInfo.getUsername(), connectionInfo.getPassword());
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
        ResultSet.CONCUR_READ_ONLY,
        ResultSet.CLOSE_CURSORS_AT_COMMIT);
        metaData = connection.getMetaData();
    }

    /**
     * Searches through all tables of the database
     * @param table Searchword
     * @return Resultobject filled with tablenames
     */
    public Result searchTableNames(String table) throws SQLException {
        
        List<String> matchingTables = getTableNames(table);
        String[] strings = matchingTables.stream().toArray(String[]::new);
        Result result = new Result(strings, null, null);

        return result;
    }

    /**
     * Searches through all tables of the database
     * @param table Searchword
     * @return String with tables matching the searchword
     */
    private List<String> getTableNames(String table) throws SQLException {
        ResultSet rs = metaData.getTables(null, null, null, new String[]{"TABLE"});
        if(table==null){
            table ="";
        }
        List<String> tables = new ArrayList<>();
        while (rs.next()) {
            

            if (rs.getObject("table_type") != null && rs.getObject("table_type", w.getClass()).equals("TABLE")) {
                
                tables.add(rs.getString("TABLE_NAME"));
            }
        }
        List<String> matchingTables = getMatchingStrings(tables, table);
        return matchingTables;
    }

    /**
     * Helper function to get all strings of a list that contain the input string
     * @param strings List to search through
     * @param input Input string 
     * @return List of matching strings
     */
    private List<String> getMatchingStrings(List<String> strings, String input) {
        List<String> matchingStrings = new ArrayList<>();
        for (String string : strings) {
            if (string.toLowerCase().contains(input.toLowerCase())) {
                matchingStrings.add(string);
            }
        }
        return matchingStrings;
    }

    /**
     * Searches through all columns of a set of tables
     *
     * @param table   Searchword
     * @param columns Searchword
     * @return Resultobject filled with columns and the tables they belong to
     */
    public Result searchColumnNames(MultiValuedMap<String, String> columns, String table) throws SQLException {
        List<String> tables = getTableNames(table);
        LinkedHashMap<String,String[]> resColumns = new LinkedHashMap<>();
        
        boolean foundone = false;
        for (int i = 0; i < tables.size(); i++) {
            List<String> columnsOfTable = getColumnNames(columns, tables.get(i));
            if(columnsOfTable.size()>0){
                foundone= true;
                resColumns.put(tables.get(i),columnsOfTable.stream().toArray(String[]::new));
            }
            
        }

        if(!foundone){
            resColumns = null;
        }
        
        Result result = new Result(null, resColumns, null);
        return result;
        
    }

    /**
     * Returns matching tables from one table
     * @param query Searchword
     * @param table Single found table
     * @return Columns of a table matching the searchword
     * @throws SQLException
     */
    private List<String> getColumnNames(MultiValuedMap<String, String> query, String table) throws SQLException {
        ResultSet rs = statement.executeQuery("select * from " + table);
        String column = query.keys().iterator().next();
        ResultSetMetaData rsMetaData = rs.getMetaData();
        int count = rsMetaData.getColumnCount();
        List<String> columns = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columns.add(rsMetaData.getColumnName(i));
        }
        List<String> matchingColumns = getMatchingStrings(columns, column);
        return matchingColumns;
    }

    /**
     * Turns the Column conditions from the query into a String Array
     * @param columns Multivalued Column Map
     * @return SQL Query Conditions as String Array
     */
    private String[] getConditions(MultiValuedMap<String, String> columns) {
//        System.out.println(columns.size());
        String[] conditions = new String[columns.size()];
        Set<String> keys = columns.keySet();
        Iterator<String> keysIt = keys.iterator();
        int conditionIt = 0;
        while(keysIt.hasNext()){
            String column = keysIt.next();
            Collection<String> keyConditions = columns.get(column);
            Iterator<String> keyConditionsIt = keyConditions.iterator();
            while(keyConditionsIt.hasNext()){
                String con = keyConditionsIt.next();
                String operator = "";
                String value = "";
                
                if(con.length()>2 && con.substring(1,2).equals("=")){
                    if(con.contains("%")){
                        operator = "NOT LIKE";
                        value = con.substring(2, con.length());
                    }
                    else{
                        operator = con.substring(0,2);
                        value = con.substring(2, con.length());

                    }

                  
                }
                else{
                    if(con.contains("%")){
                        operator = "LIKE";
                        value = con.substring(1, con.length());
                    }
                    else{
                        operator = con.substring(0,1);
                        value = con.substring(1, con.length());
                    }
                    
                }
                
                conditions[conditionIt] = column +  " " + operator + "'" + value + "'";
                conditionIt++;
            }
        }
        

        return conditions;
    }


    /**
     * Turns the Resultset from the query into a LinkedHashMap
     * @param result Resultset from SQL Query
     * @return LinkedHashmap of Results
     * @throws SQLException
     */
    private LinkedHashMap<String, LinkedHashMap<String,String>[]> rsToHashMap(ResultSet result) throws SQLException{
        LinkedHashMap<String, LinkedHashMap<String,String>[]> resultMap = new LinkedHashMap<String, LinkedHashMap<String,String>[]>();
        ResultSetMetaData meta = result.getMetaData();
        String tableName = meta.getTableName(1);
        int columnCount = meta.getColumnCount();
        String[] columns = new String[columnCount+1];
        int size = 0;
        while (result.next()) {
            size++;
        }
        //System.out.println("size:" + size);
        result.beforeFirst();
        for (int i = 1; i <= columnCount; i++ ) {
            columns[i] = meta.getColumnName(i);
          }
        int rowIndex = 0;
        LinkedHashMap<String, String>[] rows = new LinkedHashMap[size];
        while(result.next()){
            LinkedHashMap<String,String> row = new LinkedHashMap<String,String>();
            for(int i = 1; i<= columnCount; i++){
            
                row.put(columns[i],result.getString(i));
                
            }
            rows[rowIndex] = row;
            rowIndex++;
        }
        resultMap.put(tableName,rows);
        return resultMap;

    } 

    /**
     * Main Search function. Searches for Object (rows) in a Table
     * @param table Table to be searched in
     * @param columns Conditions for the query
     * @return Result filled with found Objects
     * @throws SQLException
     */
    public Result searchObjects(String table, MultiValuedMap<String, String> columns) throws SQLException {
        StringBuilder queryBuilder = new StringBuilder();
        String[] conditions = getConditions(columns);
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
      //System.out.println(queryBuilder.toString());
      ResultSet result  = statement.executeQuery(queryBuilder.toString());
      Result resultObj = new Result(null, null, rsToHashMap(result));
      return resultObj;
    }
    

    

    

    @Override
    public void close() throws Exception {
        connection.close();
    }

    

    
}

