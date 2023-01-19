package de.hdm.db.mongo;
import com.mongodb.*;
import com.mongodb.ServerApi;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import de.hdm.datacontainer.ConnectionInfo;

import org.bson.Document;

import java.util.ArrayList;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.eq;
//import com.mongodb.client.MongoDatabase;

public class MongoConnection {

    static MongoClientSettings settings; //only for testing purposes static
    static ConnectionString connectionString;
    static MongoClient mongoClient;

    //Constructor for MongoClient
    public MongoConnection(ConnectionInfo connectionInfo) {
        String buildUri = "mongodb+srv://" + connectionInfo.getUsername() + ":" + connectionInfo.getPassword() + "@" + connectionInfo.getUrl() + "/?retryWrites=true&w=majority";
        connectionString = new ConnectionString(buildUri);

        settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();
        mongoClient = MongoClients.create(settings);
    }


    //please do not delete since docker container is not working
    public static void main(String[] args) {

        ConnectionInfo connectionInfo = new ConnectionInfo();

        connectionString = new ConnectionString("mongodb+srv://user:ojOmZKcroUDQURKS@dbgrep.o3uj6ms.mongodb.net/?retryWrites=true&w=majority");
        settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();
        mongoClient = MongoClients.create(settings);


        MongoDatabase database = mongoClient.getDatabase("dbgrep");
        MongoCollection<Document> collection = database.getCollection("fahrzeuginfo");
        System.out.println("Collection testdb selected successfully");

    }
}

