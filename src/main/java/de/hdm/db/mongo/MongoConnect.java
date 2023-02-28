package de.hdm.db.mongo;

import java.util.*;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;

import com.mongodb.client.model.Filters;
import de.hdm.constants.DBGrepConstants;
import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.datacontainer.Query;
import de.hdm.datacontainer.Result;
import de.hdm.exception.UnknownCommandException;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.regex.Pattern;

public class MongoConnect implements AutoCloseable {

    private MongoClient mongoClient;
    private ConnectionInfo connectionInfo;
    private MongoDatabase db;

    static List<Query> queryList; //for testing purposes


    /**
     * Connects to a MongoDB instance with the given {@link ConnectionInfo}.
     * @param connectionInfo Contains information on how to reach the MongoDB instance.
     */
    public MongoConnect(ConnectionInfo connectionInfo){
        // suppress a log warning
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE);

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
        db = mongoClient.getDatabase(connectionInfo.getDbname());
    }


    /**
     * Search in a MongoDB for the parameters specified in the {@link Query}-object.
     * @param query This contains the parameters for what should be searched in the MongoDB.
     * @return A {@link Result}-object with the Data retrieved from the MongoDB.
     */
    public Result request(Query query) {
        // Initialize a list to store the matching documents
        List<Document> matchingDocuments = new ArrayList<>();

        // Use a switch statement to handle different types of queries
        switch (query.getQueryType()) {
            // If the query type is SEARCH_OBJECTS, search the database for objects that match the criteria specified in the query
            case SEARCH_OBJECTS -> {
                var objects = searchObjects(query);
                return new Result(null, null, objects);
            }
            // If the query type is SEARCH_TABLE_NAMES, search the database for table names that match the criteria specified in the query
            case SEARCH_TABLE_NAMES -> {
                var tables = filterCollectionNames(query.getTable());
                return new Result(tables, null, null);
            }
            // If the query type is SEARCH_COLUMN_NAMES, search the database for column names that match the criteria specified in the query
            case SEARCH_COLUMN_NAMES -> {
                LinkedHashMap<String, String[]> res;
                var key = query.getColumns().keys().iterator().next();
                if (query.getTable() == null){ // if no table query is specified search in every table
                    res = filterDocumentKeys(key); // -c docKey -> searches in whole database
                } else {
                    res = filterDocumentKeys(query.getTable(), key); // -t test -c docKey -> searches in specific table
                }
                return new Result(null, res, null);
            }
            // If the query type is unknown, throw a RuntimeException
            default -> {
                throw new RuntimeException("Query is of unknown Type");
            }
        }
    }


    /**
     * Search through the information stored in the MongoDB.
     * @param query Search parameters containing information on what should be searched.
     * @return A {@link LinkedHashMap} containing the per collection the documents that were retrieved.
     */
    private LinkedHashMap<String, LinkedHashMap<String, String>[]> searchObjects(Query query){
        // Create the query and send it to the DB
        var collection = db.getCollection(query.getTable());
        var and_query = new ArrayList<Bson>();
        for (var key : query.getColumns().keys()) {
            for (var col : query.getColumns().get(key)) {
                and_query.add(parse_object_query(key, col));
            }
        }
        var found = collection.find(Filters.and(and_query));

        // Create result object from the found data
        var cols = new ArrayList<LinkedHashMap<String, String>>();
        for ( var f : found) {
            var col = new LinkedHashMap<String, String>();
            for ( var key : f.keySet()){
                col.put(key, f.get(key).toString());
            }
            cols.add(col);
        }
        if (cols.isEmpty()) {
            return null;
        } else {

            var res = new LinkedHashMap<String, LinkedHashMap<String, String>[]>();
            res.put(query.getTable(), cols.toArray(new LinkedHashMap[cols.size()]));
            return res;
        }
    }


    /**
     * Translate the user input into a Mongo search query.
     * @param key The document key.
     * @param col The input of the user that should match the value of the document.
     * @return A {@link Bson}-object which represents the Mongo search query.
     */
    private Bson parse_object_query(String key, String col) {
        // This function takes in a key (column name) and a col (column condition) and returns a Bson object
        // representing the query for that column and condition.

        // If the condition starts with '=', create an 'eq' query (equals)
        if (col.startsWith("=")){
            return Filters.eq(key, col.substring(1));
        }
        // If the condition starts with '!=', create a 'ne' query (not equals)
        else if (col.startsWith("!=")) {
            return Filters.ne(key, col.substring(2));
        }
        // If the condition starts with '<', create a 'lt' query (less than)
        else if (col.startsWith("<")) {
            return Filters.lt(key, col.substring(1));
        }
        // If the condition starts with '>', create a 'gt' query (greater than)
        else if (col.startsWith(">")) {
            return Filters.gt(key, col.substring(1));
        }
        // If the condition is a string, create a regex query
        else if (col.startsWith("+")) {
            // Compile the regex pattern and create a 'regex' query
            Pattern regexPattern = Pattern.compile(col.substring(1), Pattern.CASE_INSENSITIVE);
            return Filters.regex(key, regexPattern);
        } else {
            throw new UnknownCommandException(DBGrepConstants.ExitCode.UNKNOWN_COMMAND);
        }
    }


    /**
     * Get the names of all existing MongoDB collections and match them against the query.
     * @param query The collection names need to match this query.
     * @return All found collection names matching the query.
     */
    private String[] filterCollectionNames(String query){
        ArrayList<String> res = new ArrayList<>();
        var names = db.listCollectionNames();

        for(String name : names){
            if (name.matches(query)){
                res.add(name);
            }
        }
        return res.toArray(new String[res.size()]);
    }


    /**
     * Get the keys from all existing Documents in the DB and match them against the query.
     * @param query The keys need to match this query.
     * @return All found keys matching the query.
     */
    private LinkedHashMap<String, String[]> filterDocumentKeys(String query){
        LinkedHashMap<String, String[]> res = new LinkedHashMap<>();
        var collections = db.listCollectionNames();

        for (String collectionName : collections) {
            var keys = new LinkedHashSet<>();
            var collection = db.getCollection(collectionName);

            collection.find().forEach((doc) ->{
                doc.keySet().forEach((key) -> {
                    if(key.matches(query)){
                        keys.add(key);
                    }
                });
            });
            res.put(collectionName, keys.toArray(new String[keys.size()]));
        }
        // check if keys were found
        for( var key : res.keySet()){
            if (res.get(key).length != 0) {
                return res;
            }
        }
        return null;
    }


    /**
     * Get the keys from all documents that match the collectionQuery and match them against the docKeyQuery.
     * @param collectionQuery The query matching the collection names.
     * @param docKeyQuery The query matching the keys of the documents in the found collections.
     * @return A {@link LinkedHashMap} containing the document keys per collection.
     */
    private LinkedHashMap<String, String[]> filterDocumentKeys(String collectionQuery, String docKeyQuery){
        var collectionNames = filterCollectionNames(collectionQuery);
        LinkedHashMap<String, String[]> res = new LinkedHashMap<>();
        for(var name : collectionNames){
            var collection = db.getCollection(name);
            var keys = new LinkedHashSet<String>();
            collection.find().forEach((doc) ->{
                doc.keySet().forEach((var key) -> {
                    if(key.matches(docKeyQuery)){
                        keys.add(key);
                    }
                });
            });
            res.put(name, keys.toArray(new String[keys.size()]));
        }
        // check if keys were found
        for( var key : res.keySet()){
            if (res.get(key).length != 0) {
                return res;
            }
        }
        return res;
    }


    @Override
    public void close() {
        mongoClient.close();

    }
}
