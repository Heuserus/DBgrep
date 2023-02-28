package de.hdm.db;

import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.datacontainer.Result;

import java.sql.SQLException;

import org.apache.commons.collections4.MultiValuedMap;

public interface IDBConnection extends AutoCloseable {

    /**
     * Finds column names of tables
     * @param columns Searchword
     * @param table Tables in which so search
     * @return Result filled with found Columns
     * @throws SQLException
     */
    public Result searchColumnNames(MultiValuedMap<String, String> columns, String table) throws SQLException;

    /**
     * Finds Tables in the Database
     * @param table Searchword
     * @return Result filled with tablenames
     * @throws SQLException
     */
    public Result searchTableNames(String table) throws SQLException;

    /**
     * Finds Objects in the Database
     * @param table Table in which to search
     * @param columns Conditions for the search
     * @return Result filled with found Objects
     * @throws SQLException
     */
    public Result searchObjects(String table, MultiValuedMap<String, String> columns) throws SQLException;

    /**
     * Builds connection to the database
     * @param connectionInfo Authentication and Connectioninfo
     * @throws SQLException
     */
    public void connect(ConnectionInfo connectionInfo) throws SQLException;


}
