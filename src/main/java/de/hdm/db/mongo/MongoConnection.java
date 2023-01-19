package de.hdm.db.mongo;
import com.mongodb.*;
import com.mongodb.ServerApi;
import com.mongodb.client.*;

import de.hdm.datacontainer.ConnectionInfo;

import de.hdm.datacontainer.Result;
import org.bson.Document;

import java.util.ArrayList;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.eq;
//import com.mongodb.client.MongoDatabase;

public class MongoConnection {

    static MongoClientSettings settings; //only for testing purposes static
    static ConnectionString connectionString;
    static MongoClient mongoClient;

    static MongoDatabase database;

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


        database = mongoClient.getDatabase("dbgrep");
        MongoCollection<Document> collection = database.getCollection("fahrzeuginfo");
        System.out.println("Collection testdb selected successfully");

    }

    public Result searchTableNames(String table) {
        //search for table names in the mongo database
        MongoCollection<Document> collection = database.getCollection(table);

        //create new tableNames array of the same size
        String[] tableNames = new String[(int) collection.countDocuments()];

        //iterate over the collection
        int i = 0;
        MongoCursor<String> cursor = database.listCollectionNames().iterator();
        while(cursor.hasNext()){
            tableNames[i] = cursor.next();
            i++;
        }

        //put the result collection in the result object

        return new Result(tableNames, null, null );
    }

    public Result searchColumnNames(String column, String table){
        MongoCollection<Document> collection = database.getCollection(table);
        FindIterable<Document> documents = collection.find(new Document("column", new Document("$exists", true)));

        // put the names of the columns in an array of strings
        String[] columnNames = new String[(int) collection.countDocuments()];
        int i = 0;
        try (MongoCursor<Document> cursor = documents.iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                if (doc.containsKey(column)) {
                    columnNames[i] = column;
                    i++;
                }
            }
        }

        return new Result(null, columnNames, null);
    }
}

