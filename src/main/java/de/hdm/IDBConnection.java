package de.hdm;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.datacontainer.Result;

public interface IDBConnection {



//    -c, --column	column to be searched, table must be specified [if used without table search in every column the name provided matches]



//                -C, --column-name	search for all column names (keys) of the specified name
    public Result searchColumnNames(String column, String table);

//    [-c, --count ]	displays result count
    public Result searchTableNames(String table);

//    -t, --table	specifies table to be searched
    public Result searchObjects(String table, String[] conditions);

    public void connect(ConnectionInfo connectionInfo) throws SQLException;
//    -T, --table-name	specifies a table name to be searched

//    - r, --recursive	follow foreign keys

//    , --and	boolean AND operator specifying optional condition

//    , --or	boolean OR operator specifying optional condition

//    , --color	luca implements



}
