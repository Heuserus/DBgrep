package de.hdm;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JDBCDriverLoaderTest {

    @Test
    public void assertLoadOracleDriver() {
        try {
            DBGrepDriver d = (DBGrepDriver) JDBCDriverLoader.loadDriver("drivers/ojdbc11.jar");
            assertTrue(d.getDriver().toString().contains("oracle.jdbc.OracleDriver"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void assertLoadMariaDriver() {
        try {
            DBGrepDriver d = (DBGrepDriver) JDBCDriverLoader.loadDriver("drivers/mariadb-java-client-3.1.0.jar");
            assertTrue(d.getDriver().toString().contains("org.mariadb.jdbc.Driver"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void assertFileNotFound() throws IOException {
        assertThrowsExactly(FileNotFoundException.class, () -> JDBCDriverLoader.loadDriver("blah/fasel"));
    }
}
