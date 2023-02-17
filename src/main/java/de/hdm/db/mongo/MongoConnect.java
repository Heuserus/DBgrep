package de.hdm.db.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.datacontainer.Query;
import de.hdm.datacontainer.Result;

import java.util.List;

public class MongoConnect implements AutoCloseable {

    MongoClient mongoClient;
    ConnectionInfo connectionInfo;

    /**
     * Connects to a MongoDB instance with the given connection info.
     *
     * @param connectionInfo Contains information on how to reach the MongoDB instance.
     */
    public MongoConnect(ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
        // connect to mongodb and save connection
        var _uri = connectionInfo.getProtocol() + "://"
                + connectionInfo.getUsername() + ":"
                + connectionInfo.getPassword() + "@"
                + connectionInfo.getHost() + ":"
                + connectionInfo.getPort() + "/";
        ConnectionString uri = new ConnectionString(_uri);

        var settings = MongoClientSettings.builder()
                .applyConnectionString(uri)
                .serverApi(
                        ServerApi.builder()
                                .version(ServerApiVersion.V1).build())
                .build();
        mongoClient = MongoClients.create(settings);
    }


    public Result request(List<Query> query) {
        // do something with query object
        // maybe utilize MongoLogic
        return null;
    }


    @Override
    public void close() {
        mongoClient.close();

    }
}
