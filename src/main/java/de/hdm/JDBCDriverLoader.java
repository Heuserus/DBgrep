package de.hdm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Iterator;
import java.util.ServiceLoader;


public class JDBCDriverLoader {

    /**
     * Loads a JDBC Driver from a JAR file using the Service Provider Interface pattern.
     * @param path Path to the JAR file
     * @return {@link Driver} object that can be registered via the method {@link DriverManager#registerDriver(Driver)}
     */
    public static Driver loadDriver(String path) throws IOException{
        File jar = new File(path);
        if(!jar.exists()){
            throw new FileNotFoundException("Driver not found at: " + jar.toString());
        }
        URL[] urls = {jar.toURI().toURL()};
        URLClassLoader classLoader = new URLClassLoader(urls);
        ServiceLoader<Driver> serviceLoader= ServiceLoader.load(Driver.class, classLoader);
        Iterator<Driver> it = serviceLoader.iterator(); // TODO catch ServiceConfigurationError for robus code
        // get last element of iterator. This seems to be the driver loaded above
        Driver driver = null;
        while (it.hasNext()){
            driver = it.next();
        }
        return driver;
    }
}