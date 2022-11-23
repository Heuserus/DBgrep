package de.hdm.datacontainer;

import com.fasterxml.jackson.annotation.JsonIgnore;

import picocli.CommandLine.Option;

public class ConnectionInfo {
    @JsonIgnore
    public String url;

    @Option(names = "--driver", description = "", required = true)
    private String driver;

    @Option(names = "--host", description = "", required = true)
    private String host;

    @Option(names = "--port", description = "", required = true)
    private Integer port;

    @Option(names = "--dbname", description = "", required = true)
    private String dbname;

    @Option(names = "--username", description = "", required = true)
    private String username;

    @Option(names = "--password", description = "", required = true)
    private String password;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

