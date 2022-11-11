package de.hdm.helper;

import picocli.CommandLine.Option;

public class ConnectionInfo {

    @Option(names = "--driver", description = "", required = true)
    private String driver;

    @Option(names = "--port", description = "", required = true)
    private Integer port;

    @Option(names = "--dbname", description = "", required = true)
    private String dbname;

    @Option(names = "--username", description = "", required = true)
    private String username;

    @Option(names = "--password", description = "", required = true)
    private String password;

    public static ConnectionInfo fromProfile(Profile profile) {
        final var connectionInfo = new ConnectionInfo();
        connectionInfo.driver = profile.getDriver();
        connectionInfo.port = profile.getPort();
        connectionInfo.dbname = profile.getDbname();
        connectionInfo.username = profile.getUsername();
        connectionInfo.password = profile.getPassword();
        return connectionInfo;
    }

}
