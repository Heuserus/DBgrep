package de.hdm.datacontainer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import picocli.CommandLine.Option;

/**
 * Specifies the properties to set up a database connection.
 */
public class ConnectionInfo {

    @JsonIgnore
    public String url; // needed for mongodb

    @Option(names = "--protocol", description = "The protocol of the connection. Something like 'mongodb' or 'jdbc:mariadb'.", required = true)
    private String protocol;

    @Option(names = "--host", description = "Hostname of the host on which the database management system is running.", required = true)
    private String host;

    @Option(names = "--port", description = "Port of the database management system.", required = true)
    private Integer port;

    @Option(names = "--dbname", description = "The name of the database.", required = true)
    private String dbname;

    @Option(names = "--username", description = "Username for authentication.", required = true)
    private String username;

    @Option(names = "--password", description = "Password for authentication.", required = true)
    private String password;

    @Option(names = "--driver", description = "Path to the JDBC driver of the database. The Postgresql and MongoDB drivers are already contained in this package.", required = false)
    private String driver;

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

    public void setHost(String host){
        this.host = host;
    }

    public String getHost(){
        return host;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getUrl(){
        return protocol + "://" + host + ":" + port + "/" + dbname;
    }

    public void setUrl(String url){
        this.url = url;
    }

}

