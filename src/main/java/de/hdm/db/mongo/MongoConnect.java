package de.hdm.db.mongo;

import java.io.ByteArrayOutputStream;
import java.sql.Array;
import java.sql.ResultSet;
import java.util.*;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;

import com.mongodb.client.model.Filters;
import de.hdm.cli.Output;
import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.datacontainer.Query;
import de.hdm.datacontainer.Result;
import org.apache.commons.collections4.MultiValuedMap;
import org.bson.BSONCallback;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.logging.Logger;
import java.util.logging.Level;

public class MongoConnect implements AutoCloseable {

    private MongoClient mongoClient;
    private ConnectionInfo connectionInfo;
    private MongoDatabase db;

    static List<Query> queryList; //for testing purposes


    /**
     * Connects to a MongoDB instance with the given connection info.
     * @param connectionInfo Contains information on how to reach the MongoDB instance.
     */
    public MongoConnect(ConnectionInfo connectionInfo){
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE);

        if(false) LucasMongoConnection(connectionInfo); //for the poor mac users whos docker containers are not working :(
        else {

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
    }

    public void LucasMongoConnection(ConnectionInfo connectionInfo){
        ConnectionString connectionString = new ConnectionString("mongodb+srv://user:BTulHuo6VgfpUkeu@dbgrep.o3uj6ms.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        mongoClient = MongoClients.create(settings);
        db = mongoClient.getDatabase(connectionInfo.getDbname());
    }


    public Result request(Query query) {
        List<Document> matchingDocuments = new ArrayList<>();

        switch (query.getQueryType()) {
            case SEARCH_OBJECTS -> {
//                MultiValuedMap<String, String> columns = query.getColumns();
//                String table = query.getTable();
//                for (String column : columns.keySet()) {
//                    List<String> conditions = (List<String>) columns.get(column);
//                    for (String condition : conditions) {
//                        Document filter = new Document(column, condition);
//                        FindIterable<Document> results = db.getCollection(table).find(filter);
//                        for (Document result : results) {
//                            matchingDocuments.add(result);
//                        }
//                    }
//                }
//                System.out.println(matchingDocuments);
//                break;
                var objects = searchObjects(query);
                if (objects == null) {
                    return new Result(null, null, null); // found nothing
                } else {
                    return new Result(null, null, objects);
                }
            }
            case SEARCH_TABLE_NAMES -> {
//                String tableNameRegex = query.getTable();
//                String[] tableNames = filterCollectionNames(tableNameRegex);
//                for (String tableName : tableNames) {
//                    Document tableDocument = new Document("table", tableName);
//                    matchingDocuments.add(tableDocument);
//
//                }
//                System.out.println(matchingDocuments);
                var tables = filterCollectionNames(query.getTable());
                return new Result(tables, null, null);
            }
            case SEARCH_COLUMN_NAMES -> {
//                String columnNameRegex = query.getColumns().keySet().iterator().next();
//                HashMap<String, String[]> documentKeys = filterDocumentKeys(columnNameRegex);
//                for (String tableName : documentKeys.keySet()) {
//                    for (String columnName : documentKeys.get(tableName)) {
//                        Document columnDocument = new Document("table", tableName).append("column", columnName);
//                        matchingDocuments.add(columnDocument);
//
//                    }
//                }
//                System.out.println(matchingDocuments);
                LinkedHashMap<String, String[]> _res;
                var key = query.getColumns().keys().iterator().next();
                if (query.getTable() == null){ // if no table query is specified search in every table
                    _res = filterDocumentKeys(key); // -c docKey -> searches in whole database
                } else {
                    _res = filterDocumentKeys(query.getTable(), key); // -t test -c docKey -> searches in specific table
                }

                var res = new LinkedHashSet<String>();
                for (var _key : _res.values()) {
                    res.addAll(Arrays.asList(_key));
                }
                return new Result(null, res.toArray(new String[res.size()]), null);
            }
            default -> {
                throw new RuntimeException("Query is of unknown Type"); //todo throw correct exception
            }
        }

//        String[] tableNames = new String[matchingDocuments.size()];
//        String[] columnNames = new String[matchingDocuments.size()];
//        LinkedHashMap<String, LinkedHashMap<String, String>[]> objects = new LinkedHashMap<>();
//
//        for (int i = 0; i < matchingDocuments.size(); i++) {
//            Document document = matchingDocuments.get(i);
//            Set<String> keySet = document.keySet();
//            LinkedHashMap<String, String>[] rows = new LinkedHashMap[1];
//            LinkedHashMap<String, String> row = new LinkedHashMap<>();
//
//            for (String key : keySet) {
//                Object value = document.get(key);
//                String valueString = (value == null) ? "" : value.toString();
//                row.put(key, valueString);
//            }
//
//            rows[0] = row;
//            objects.put(document.getString("table"), rows);
//            tableNames[i] = document.getString("table");
//            columnNames[i] = document.getString("column");
//        }
//
//        return new Result(tableNames, columnNames, objects);
    }

    // columnname: ["=xxx", "!=blah", ...]
    // columnname2: [">xxx", "<blah", ...]

    private LinkedHashMap<String, LinkedHashMap<String, String>[]> searchObjects(Query query){
        // todo: aus query objekt eine Mongo query machen
        //  zurückgeben in der from linkedHashMap<ColletionName, LinkedHashMap<Key, Value>[]> (also pro Collection Name die 'Zeilen' der Documents
        //  https://www.mongodb.com/docs/drivers/java/sync/v4.3/fundamentals/crud/query-document/
        var collection = db.getCollection(query.getTable());
        var and_query = new ArrayList<Bson>();
        for (var key : query.getColumns().keys()) {
            for (var col : query.getColumns().get(key)) {
                and_query.add(parse_object_query(key, col));
            }
        }
        var found = collection.find(Filters.and(and_query));

        // Create result object
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

    private Bson parse_object_query(String key, String col) {
        if (col.startsWith("=")){
            return Filters.eq(key, col.substring(1));
        } else if (col.startsWith("!=")) {
            return Filters.ne(key, col.substring(2));
        } else if (col.startsWith("<")) {
            return Filters.lt(key, col.substring(1));
        } else if (col.startsWith(">")) {
            return Filters.gt(key, col.substring(1));
        } else {
            throw new RuntimeException("Unsupported operand type. Suported types are =, !=, < and >"); // todo throw correct exception type.
        }
    }


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


    private LinkedHashMap<String, String[]> filterDocumentKeys(String query){
        LinkedHashMap<String, String[]> res = new LinkedHashMap<>();
        var collections = db.listCollectionNames();

        for (String collectionName : collections) {
            ArrayList<String> keys = new ArrayList<>();
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
        return res;
    }

    private LinkedHashMap<String, String[]> filterDocumentKeys(String collectionQuery, String docKeyQuery){
        // todo: richtige doku
        // filtern nach allen keys, die zu `docKeyQuery` passen.
        //  Aber nur in den Collections, die zu `collectionQuery` passen.
        var collectionNames = filterCollectionNames(collectionQuery);
        LinkedHashMap<String, String[]> res = new LinkedHashMap<>();
        for(var name : collectionNames){
            var collection = db.getCollection(name);
            ArrayList<String> keys = new ArrayList<>();
            collection.find().forEach((doc) ->{
                doc.keySet().forEach((var key) -> {
                    if(key.matches(docKeyQuery)){
                        keys.add(key);
                    }
                });
            });
            res.put(name, keys.toArray(new String[keys.size()]));
        }
        return res;
    }


    public static void main(String[] args) {
        ConnectionInfo info = new ConnectionInfo();
        info.setDbname("dbgrep");
        info.setHost("localhost");
        info.setPassword("example");
        info.setUsername("root");
        info.setPort(27017);
        info.setProtocol("mongodb");
        info.setDbname("test"); //Collection in the mongo cloud

// simons code:
//        try(MongoConnect m = new MongoConnect(info)){
//            // Arrays.stream(m.filterCollectionNames(".*")).forEach((s) -> System.out.println(s));
//            m.filterDocumentKeys(".*").forEach((var key, var val) -> {
//                System.out.println(key);
//                Arrays.stream(val).forEach((var s) -> System.out.println("  " + s));
//            });
//        } catch(Exception e){
//            e.printStackTrace();
//        }


        try(var mongoConnection = new MongoConnect(info)){
            for (Query query : queryList) {
                Result result = mongoConnection.request(query);
                Output.printResult(result);
            }
        }

        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        mongoClient.close();

    }
}
