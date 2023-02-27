package de.hdm.db.sql;

import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.datacontainer.Result;
import de.hdm.db.IDBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;

public class SQLConnection implements IDBConnection {

    private Statement statement;
    private Connection connection;
    private DatabaseMetaData metaData;
    private String w = "T";

    public void connect(ConnectionInfo connectionInfo) throws SQLException {
        connection = DriverManager.getConnection(connectionInfo.getUrl(), connectionInfo.getUsername(), connectionInfo.getPassword());
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
        ResultSet.CONCUR_READ_ONLY,
        ResultSet.CLOSE_CURSORS_AT_COMMIT);
        metaData = connection.getMetaData();
    }

    public Result searchTableNames(String table) throws SQLException {
        
        List<String> matchingTables = getTableNames(table);
        String[] strings = matchingTables.stream().toArray(String[]::new);
        Result result = new Result(strings, null, null);

        return result;
    }

    public List<String> getTableNames(String table) throws SQLException {
        ResultSet rs = metaData.getTables(null, null, null, new String[]{"TABLE"});

        List<String> tables = new ArrayList<>();
        while (rs.next()) {
            

            if (rs.getObject("table_type") != null && rs.getObject("table_type", w.getClass()).equals("TABLE")) {
                
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

    public Result searchColumnNames(String column, String table) throws SQLException {
        List<String> tables = getTableNames(table);
        List<String> matchingColumns = getColumnNames(column, tables.get(0));

        for (int i = 1; i < tables.size(); i++) {
            List<String> columnsOfTable = getColumnNames(column, tables.get(i));
            matchingColumns.addAll(columnsOfTable);
        }

        String[] strings = matchingColumns.stream().toArray(String[]::new);
        Result result = new Result(null, strings, null);
        return result;
    }

    List<String> getColumnNames(String column, String table) throws SQLException {
        ResultSet rs = statement.executeQuery("select * from " + table);
        ResultSetMetaData rsMetaData = rs.getMetaData();
        int count = rsMetaData.getColumnCount();
        List<String> columns = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columns.add(rsMetaData.getColumnName(i));
        }
        List<String> matchingColumns = getMatchingStrings(columns, column);
        return matchingColumns;
    }

    public String[] getConditions(MultiValuedMap<String, String> columns) {
        System.out.println(columns.size());
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
                    operator = con.substring(0,2);
                    value = con.substring(2, con.length());
                }
                else{
                    operator = con.substring(0,1);
                    value = con.substring(1, con.length());
                }
                
                conditions[conditionIt] = column + " " + operator + "'" + value + "'";
                conditionIt++;
            }
        }
        

        return conditions;
    }

    public LinkedHashMap<String, LinkedHashMap<String,String>[]> rsToHashMap(ResultSet result) throws SQLException{
        LinkedHashMap<String, LinkedHashMap<String,String>[]> resultMap = new LinkedHashMap<String, LinkedHashMap<String,String>[]>();
        ResultSetMetaData meta = result.getMetaData();
        String tableName = meta.getTableName(1);
        int columnCount = meta.getColumnCount();
        String[] columns = new String[columnCount+1];
        int size = 0;
        while (result.next()) {
            size++;
        }
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
      System.out.println(queryBuilder.toString());
      ResultSet result  = statement.executeQuery(queryBuilder.toString());
      Result resultObj = new Result(null, null, rsToHashMap(result));
      return resultObj;
    }
    

    

    

    @Override
    public void close() throws Exception {
        connection.close();
    }

    

    
}

