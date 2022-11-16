package de.hdm;
import com.mongodb.*;
import com.mongodb.ServerApi;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.hdm.helper.ConnectionInfo;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collection;
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
    public MongoConnection(ConnectionInfo connectionInfo) {
        String buildUri = "mongodb+srv://" + connectionInfo.username + ":" + connectionInfo.password + "@" + connectionInfo.url;
        connectionString = new ConnectionString(buildUri);

        settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();
        mongoClient = MongoClients.create(settings);
    }
    public static void main(String[] args) {

        ConnectionInfo connectionInfo = new ConnectionInfo();
        connectionInfo.url = "dbgrep.o3uj6ms.mongodb.net/?retryWrites=true&w=majority";
        connectionInfo.username = "user";
        connectionInfo.password = "87654321";

        String buildUri = "mongodb+srv://" + connectionInfo.username + ":" + connectionInfo.password + "@" + connectionInfo.url;
        //Own constructor for testing lol
        connectionString = new ConnectionString(buildUri);
        settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();
        mongoClient = MongoClients.create(settings);


        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("testdb");
        System.out.println("Collection testdb selected successfully");

        //Find object in collection
        //System.out.println(findFirst(collection, database, "title", "Star Wars"));

        //all bjects in array

        Document[] all = findAll(collection, database, "title", "Star Wars");
        for (int i = 0; i < all.length; i++) {
            System.out.println(all[i]);
        }




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

    public static Document findFirst(String collectionName, MongoDatabase database, String key, String value) {
        return database.getCollection(collectionName).find(eq(key, value)).first();
    }

    public static Document findFirst(MongoCollection<Document> collection, MongoDatabase database, String key, int value) {
            return collection.find(eq(key, value)).first();
    }

    public static Document findFirst(MongoCollection<Document> collection, MongoDatabase database, String key, String value) {
            return collection.find(eq(key, value)).first();
    }

    public static Document findFirst(MongoCollection<Document> collection, MongoDatabase database, String key, double value) {
            return collection.find(eq(key, value)).first();
    }

    public static Document findFirst(MongoCollection<Document> collection, MongoDatabase database, String key, long value) {
            return collection.find(eq(key, value)).first();
    }

    public static Document findFirst(MongoCollection<Document> collection, MongoDatabase database, String key, boolean value) {
            return collection.find(eq(key, value)).first();
    }


    public static Document findFirst(MongoCollection<Document> collection, MongoDatabase database, String key, short value) {
            return collection.find(eq(key, value)).first();
    }

    public static Document findFirst(MongoCollection<Document> collection, MongoDatabase database, String key, float value) {
            return collection.find(eq(key, value)).first();
    }

    public static void findPrint(String collectionName, MongoDatabase database, String key, String value) {
        database.getCollection(collectionName).find(eq(key, value)).forEach((Consumer<? super Document>) System.out::println);
    }


    public static Document[] findAll(MongoCollection<Document> collection, MongoDatabase database, String key, int value) {
        ArrayList<Document> documents = new ArrayList<>();
        collection.find(eq(key, value)).forEach((Consumer<? super Document>) documents::add);


        return documents.toArray(new Document[documents.size()]);
    }

    public static Document[] findAll(MongoCollection<Document> collection, MongoDatabase database, String key, String value) {
        ArrayList<Document> documents = new ArrayList<>();
        collection.find(eq(key, value)).forEach((Consumer<? super Document>) documents::add);


        return documents.toArray(new Document[documents.size()]);
    }

    public 
 Document[] findAll(MongoCollection<Document> collection, MongoDatabase database, String key, boolean value) {
        ArrayList<Document> documents = new ArrayList<>();
        collection.find(eq(key, value)).forEach((Consumer<? super Document>) documents::add);


        return documents.toArray(new Document[documents.size()]);
    }

    public 
 Document[] findAll(MongoCollection<Document> collection, MongoDatabase database, String key, double value) {
        ArrayList<Document> documents = new ArrayList<>();
        collection.find(eq(key, value)).forEach((Consumer<? super Document>) documents::add);


        return documents.toArray(new Document[documents.size()]);
    }

    public 
 Document[] findAll(MongoCollection<Document> collection, MongoDatabase database, String key, long value) {
        ArrayList<Document> documents = new ArrayList<>();
        collection.find(eq(key, value)).forEach((Consumer<? super Document>) documents::add);


        return documents.toArray(new Document[documents.size()]);
    }

    public 
 Document[] findAll(MongoCollection<Document> collection, MongoDatabase database, String key, float value) {
        ArrayList<Document> documents = new ArrayList<>();
        collection.find(eq(key, value)).forEach((Consumer<? super Document>) documents::add);


        return documents.toArray(new Document[documents.size()]);
    }

    public 
 Document[] findAll(MongoCollection<Document> collection, MongoDatabase database, String key, short value) {
        ArrayList<Document> documents = new ArrayList<>();
        collection.find(eq(key, value)).forEach((Consumer<? super Document>) documents::add);


        return documents.toArray(new Document[documents.size()]);
    }
}