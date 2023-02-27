package de.hdm.db;

import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.datacontainer.Result;

import java.sql.SQLException;

import org.apache.commons.collections4.MultiValuedMap;

public interface IDBConnection extends AutoCloseable {


//    -c, --column	column to be searched, table must be specified [if used without table search in every column the name provided matches]


    //                -C, --column-name	search for all column names (keys) of the specified name
    public Result searchColumnNames(MultiValuedMap<String, String> columns, String table) throws SQLException;

//    [-c, --count ]	displays result count
    public Result searchTableNames(String table) throws SQLException;

//    -t, --table	specifies table to be searched
    public Result searchObjects(String table, MultiValuedMap<String, String> columns) throws SQLException;

    public void connect(ConnectionInfo connectionInfo) throws SQLException;
//    -T, --table-name	specifies a table name to be searched

//    - r, --recursive	follow foreign keys

//    , --and	boolean AND operator specifying optional condition

//    , --or	boolean OR operator specifying optional condition

//    , --color	luca implements



}
