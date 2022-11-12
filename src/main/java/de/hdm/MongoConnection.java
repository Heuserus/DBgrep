package de.hdm;
import com.mongodb.*;
import com.mongodb.ServerApi;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.eq;
//import com.mongodb.client.MongoDatabase;

public class MongoConnection {
    //public static MongoClient mongoClient;
    //public static DBCollection con;
    //public static DB database;

    //private static String uri = "mongodb+srv://lv042:<K6Uu9882EFFeWvgU>@dbgrep.o3uj6ms.mongodb.net/?retryWrites=true&w=majority";

    static MongoClientSettings settings; //only for testing purposes static
    static ConnectionString connectionString;
    static MongoClient mongoClient;
    //Constructor for MongoClient
    public MongoConnection(String url, String user, String password) {
        connectionString = new ConnectionString("mongodb+srv://user:87654321@dbgrep.o3uj6ms.mongodb.net/?retryWrites=true&w=majority");
        settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();
        mongoClient = MongoClients.create(settings);
    }
    public static void main(String[] args) {
        //Own constructor for testing lol
        connectionString = new ConnectionString("mongodb+srv://user:87654321@dbgrep.o3uj6ms.mongodb.net/?retryWrites=true&w=majority");
        settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();
        mongoClient = MongoClients.create(settings);


        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("testdb");
        System.out.println("Collection testdb selected successfully");

        //Find object in collection
        Document myDoc = collection.find(eq("title", "Star Wars")).first();
        System.out.println(myDoc.toJson());




    }

    public MongoDatabase getDatabase(String databaseName)  {
        return mongoClient.getDatabase(databaseName);
    }

    public void close() {
        mongoClient.close();
    }

    public MongoCollection<Document> getCollection(String collectionName, MongoDatabase database) {
        return database.getCollection(collectionName);
    }

    public Document findFirst(String collectionName, MongoDatabase database, String key, String value) {

        return database.getCollection(collectionName).find(eq(key, value)).first();
    }

    public void find(String collectionName, MongoDatabase database, String key, String value) {
        database.getCollection(collectionName).find(eq(key, value)).forEach((Consumer<? super Document>) System.out::println);
    }

}