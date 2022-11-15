package de.hdm;

import java.sql.Driver;
import java.sql.DriverManager;


public class JDBCDriverLoader {

    /**
     * Loads a JDBC Driver from a JAR file.
     * @param path Path to the JAR file
     * @return {@link Driver} object that can be registered via the method {@link DriverManager#registerDriver(Driver)}
     */
    public Driver loadDriver(String path){
        // TODO
        return null;
    }
}