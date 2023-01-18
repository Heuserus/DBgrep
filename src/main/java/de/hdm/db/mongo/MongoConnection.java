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



    public static void main(String[] args) {
        

        String buildUri = "mongodb+srv://lv042:<K6Uu9882EFFeWvgU>@dbgrep.o3uj6ms.mongodb.net/?retryWrites=true&w=majority";
        ConnectionString cs = new ConnectionString(buildUri);

        var st = MongoClientSettings.builder()
                .applyConnectionString(cs)
                .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();
        //active connection
        var mongoClient = MongoClients.create(st);
        //but als needs to select the database before

        var db = mongoClient.getDatabase("test");
    }

    //constructor??
    public void connect(ConnectionInfo connectionInfo) {
        if (connectionInfo.getDbname() == null || connectionInfo.getDbname().equals("") || connectionInfo.getPassword() == null || connectionInfo.getPassword().equals("")
                || connectionInfo.getUsername() == null || connectionInfo.getUsername().equals("") || connectionInfo.url == null || connectionInfo.url.equals("")) {
            throw new IllegalArgumentException("ConnectionInfo is not valid");
        }

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


    public Result searchColumnNames(String column, String table) throws SQLException{
        MongoCollection<Document> collection = database.getCollection(table);
        FindIterable<Document> documents = collection.find(new Document("column", new Document("$exists", true)));

        // put the names of the columns in an array of strings
        String[] columnNames = new String[(int) collection.countDocuments()];
        int i = 0;
        MongoCursor<Document> cursor = documents.iterator();
        while(cursor.hasNext()){
            Document doc = cursor.next();
            if(doc.containsKey(column)) {
                columnNames[i] = column;
                i++;
            }
        }

        return new Result(null, columnNames, null);
    }

    public Result searchObjects(String table, String[] conditions) {
        MongoCollection<Document> collection = database.getCollection(table);
        List<Document> documentList = new ArrayList<>();
        //if conditions are empty, return all documents
        if (conditions.length == 0) {
            FindIterable<Document> documents = collection.find();
            for (Document doc : documents) {
                documentList.add(doc);
            }
        } else {
            //if conditions are not empty, return only the documents that match the conditions
            for (int i = 0; i < conditions.length; i++) {
                String[] condition = conditions[i].split("=");
                String key = condition[0];
                String value = condition[1];
                FindIterable<Document> documents = collection.find(new Document(key, value));
                for (Document doc : documents) {
                    documentList.add(doc);
                }
            }
        }
        System.out.println("searchObjects: "+ documentList);
        //Result rs = new Result(null, null, documentList);
        return null;
    }



    //might be unimportant
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