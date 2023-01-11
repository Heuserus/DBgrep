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
        
        // count already loaded drivers
        URLClassLoader _classLoader = new URLClassLoader(new URL[]{});
        ServiceLoader<Driver> _serviceLoader= ServiceLoader.load(Driver.class, _classLoader);
        Iterator<Driver> _it = _serviceLoader.iterator(); // TODO catch ServiceConfigurationError for robus code
        var _count = 0;
        while(_it.hasNext()){
            _it.next();
            _count++;
        }

        // get last element of iterator. This seems to be the driver loaded above
        var count = 0;
        Driver driver = null;
        while (it.hasNext()){
            driver = it.next();
            count++;
        }
        
        // Warn if more than one driver was loaded
        if(count - _count != 1){
            System.err.println("JDBCDriverLoader WARNING: More than one driver was loaded!");
        }
        return new DBGrepDriver(driver);
    }
}