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
    MongoClient mongoClient;
    MongoDatabase database;
    MongoClientSettings settings; //only for testing purposes static
    ConnectionString connectionString;



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

    public List<String> getTableNames(String pattern) {
        //how should I implement a pattern in mongo?
        List<String> collectionNames = new ArrayList<>();
        MongoIterable<String> collections = database.listCollectionNames();
        for (String name : collections) {
            collectionNames.add(name);
        }
        System.out.println("getTableNames: "+ collectionNames);
        return collectionNames;
    }

    @Override
    public void close() throws Exception {
        mongoClient.close();
    }

}