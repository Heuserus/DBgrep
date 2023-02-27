package de.hdm.cli;

import de.hdm.datacontainer.ConnectionInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class DBConnectionParserTest {
    DBConnectionParser dbConnectionParser;

    @BeforeEach
    void setuUp() {
        dbConnectionParser = new DBConnectionParser();
    }

    @Test
    public void testParse_forSetConnectionInfo() throws IOException {
        var connectionInfo = createConnectionInfo();
        dbConnectionParser.setConnectionInfo(connectionInfo);
        assertDoesNotThrow(() -> dbConnectionParser.parse());
        ConnectionInfo result = dbConnectionParser.parse();

        assertEquals(result.getDbname(), "dbgrep");
        assertEquals(result.getDriver(), "drivers\\mariadb-java-client-3.1.0.jar");
        assertEquals(result.getHost(), "localhost");
        assertEquals(result.getPort(), 5432);
        assertEquals(result.getProtocol(), "jdbc:postgresql");
        assertEquals(result.getUsername(), "root");
        assertEquals(result.getPassword(), "example");
        assertEquals(result.getUrl(), "jdbc:postgresql://localhost:5432/dbgrep");
    }

    @Test
    public void testParse_forProvidedProfile() throws IOException {

        dbConnectionParser.setProfile(Optional.of("./profiles/profileTest.yml"));

        assertDoesNotThrow(() -> dbConnectionParser.parse());
        ConnectionInfo result = dbConnectionParser.parse();
        assertEquals(result.getDbname(), "dbgrep");
        assertEquals(result.getDriver(), "drivers\\mariadb-java-client-3.1.0.jar");
        assertEquals(result.getHost(), "localhost");
        assertEquals(result.getPort(), 5432);
        assertEquals(result.getProtocol(), "jdbc:postgresql");
        assertEquals(result.getUsername(), "root");
        assertEquals(result.getPassword(), "example");
        assertEquals(result.getUrl(), "jdbc:postgresql://localhost:5432/dbgrep");
    }

    @Test
    public void testParse_forProvidedProfile_notFound() throws IOException {

        dbConnectionParser.setProfile(Optional.of("./no/valid/path.yml"));

        assertThrowsExactly(FileNotFoundException.class, () -> dbConnectionParser.parse());
    }

    public static ConnectionInfo createConnectionInfo() {
        ConnectionInfo connectionInfo = new ConnectionInfo();
        connectionInfo.setDbname("dbgrep");
        connectionInfo.setDriver("drivers\\mariadb-java-client-3.1.0.jar");
        connectionInfo.setHost("localhost");
        connectionInfo.setPort(5432);
        connectionInfo.setProtocol("jdbc:postgresql");
        connectionInfo.setUsername("root");
        connectionInfo.setPassword("example");
        connectionInfo.setUrl("jdbc:postgresql://localhost:5432/dbgrep");
        return connectionInfo;
    }
}