package de.hdm;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class JDBCDriverLoaderTest {
    
    @Test
    public void assertLoadOracleDriver(){
        try{
            DBGrepDriver d = (DBGrepDriver) JDBCDriverLoader.loadDriver("drivers/ojdbc11.jar");
            Assert.assertTrue(d.getDriver().toString().contains("oracle.jdbc.OracleDriver"));
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void assertLoadMariaDriver(){
        try{
            DBGrepDriver d = (DBGrepDriver) JDBCDriverLoader.loadDriver("drivers/mariadb-java-client-3.1.0.jar");
            Assert.assertTrue(d.getDriver().toString().contains("org.mariadb.jdbc.Driver"));
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @Test(expected = FileNotFoundException.class)
    public void assertFileNotFound() throws IOException{
            JDBCDriverLoader.loadDriver("blah/fasel");
    }
}
