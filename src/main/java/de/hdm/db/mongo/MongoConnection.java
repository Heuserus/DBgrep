package de.hdm.db.mongo;
import com.mongodb.*;
import com.mongodb.ServerApi;
import com.mongodb.client.*;

import com.mongodb.connection.ClusterDescription;
import de.hdm.datacontainer.ConnectionInfo;

import de.hdm.datacontainer.Result;
import de.hdm.db.IDBConnection;
import org.bson.Document;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.eq;
//import com.mongodb.client.MongoDatabase;

public class MongoConnection implements IDBConnection {
    //private static String uri = "mongodb+srv://lv042:<K6Uu9882EFFeWvgU>@dbgrep.o3uj6ms.mongodb.net/?retryWrites=true&w=majority";

    MongoDatabase database;


    //constructor??
    public void connect(ConnectionInfo connectionInfo) throws SQLException {
        String buildUri = "mongodb+srv://" + connectionInfo.getUsername() + ":" + connectionInfo.getPassword() + "@" + connectionInfo.url;
        connectionString = new ConnectionString(buildUri);

        settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();
        //active connection
        mongoClient = MongoClients.create(settings);
        //but als needs to select the database before
        database = mongoClient.getDatabase(connectionInfo.getDbname());
    }






    public Result searchTableNames(String table) throws SQLException{
        //search for table names in the mongo database
        MongoCollection<Document> collection = database.getCollection(table);
        System.out.println("searchTableNames: "+ collection);




        return null;
    }


    public Result searchColumnNames(String column, String table) throws SQLException{
        MongoCollection<Document> collection = database.getCollection(table);
        FindIterable<Document> documents = collection.find(new Document("column", new Document("$exists", true)));

        List<Document> documentList = new ArrayList<>();
        for (Document doc : documents) {
            documentList.add(doc);
        }
        System.out.println("searchColumnNames: "+ documentList);


        return null;
    }

    public Result searchObjects(String table, String[] conditions) throws SQLException{

        //how should this be implemented in mongo?


        return null;
    }


    public DatabaseMetaData getDBMetaData(Connection connection) throws SQLException{
        return connection.getMetaData();
    }

    public ClusterDescription getDBMetaData() {
        //not sure if this makes sense in mongo

        //Get the cluster description
        ClusterDescription clusterDescription = mongoClient.getClusterDescription();

        return clusterDescription;
    }


    public ResultSet getTableNames(String pattern) throws SQLException{

        String [] types = {"Table"};
        return getDBMetaData(connection).getTables(null,null,pattern,types);
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }




















































    static MongoClientSettings settings; //only for testing purposes static
    static ConnectionString connectionString;
    static MongoClient mongoClient;
    //Constructor for MongoClient
    public MongoConnection(ConnectionInfo connectionInfo) {
        String buildUri = "mongodb+srv://" + connectionInfo.getUsername() + ":" + connectionInfo.getPassword() + "@" + connectionInfo.url;
        connectionString = new ConnectionString(buildUri);

        settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();
        mongoClient = MongoClients.create(settings);
    }
    public static void main(String[] args) {

        ConnectionInfo connectionInfo = new ConnectionInfo();

        //mongodb://root:example@localhost/
        connectionInfo.url = "localhost/";
        connectionInfo.setUsername("root");
        connectionInfo.setPassword("example");

        String buildUri = "mongodb://" + connectionInfo.getUsername() + ":" + connectionInfo.getPassword() + "@" + connectionInfo.url;
        //Own constructor for testing lol
        connectionString = new ConnectionString(buildUri);
        settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();
        mongoClient = MongoClients.create(settings);


        MongoDatabase database = mongoClient.getDatabase("dbgrep");
        MongoCollection<Document> collection = database.getCollection("fahrzeuginfo");
        System.out.println("Collection testdb selected successfully");

        //Find object in collection
        //System.out.println(findFirst(collection, database, "title", "Star Wars"));

        //all bjects in array

        Document[] all = findAll(collection, database, "HSTBenennung", "Volkswagen");
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