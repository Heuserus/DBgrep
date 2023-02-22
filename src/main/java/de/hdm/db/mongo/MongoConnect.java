package de.hdm.db.mongo;

import java.io.ByteArrayOutputStream;
import java.sql.ResultSet;
import java.util.*;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;

import de.hdm.cli.Output;
import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.datacontainer.Query;
import de.hdm.datacontainer.Result;
import org.apache.commons.collections4.MultiValuedMap;
import org.bson.Document;
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
            case SEARCH_OBJECTS:
                MultiValuedMap<String, String> columns = query.getColumns();
                String table = query.getTable();
                for (String column : columns.keySet()) {
                    List<String> conditions = (List<String>) columns.get(column);
                    for (String condition : conditions) {
                        Document filter = new Document(column, condition);
                        FindIterable<Document> results = db.getCollection(table).find(filter);
                        for (Document result : results) {
                            matchingDocuments.add(result);
                        }
                    }
                }
                System.out.println(matchingDocuments);
                break;
            case SEARCH_TABLE_NAMES:
                String tableNameRegex = query.getTable();
                String[] tableNames = filterCollectionNames(tableNameRegex);
                for (String tableName : tableNames) {
                    Document tableDocument = new Document("table", tableName);
                    matchingDocuments.add(tableDocument);

                }
                System.out.println(matchingDocuments);
                break;
            case SEARCH_COLUMN_NAMES:
                String columnNameRegex = query.getColumns().keySet().iterator().next();
                HashMap<String, String[]> documentKeys = filterDocumentKeys(columnNameRegex);
                for (String tableName : documentKeys.keySet()) {
                    for (String columnName : documentKeys.get(tableName)) {
                        Document columnDocument = new Document("table", tableName).append("column", columnName);
                        matchingDocuments.add(columnDocument);

                    }
                }
                System.out.println(matchingDocuments);
                break;
        }

        String[] tableNames = new String[matchingDocuments.size()];
        String[] columnNames = new String[matchingDocuments.size()];
        LinkedHashMap<String, LinkedHashMap<String, String>[]> objects = new LinkedHashMap<>();

        for (int i = 0; i < matchingDocuments.size(); i++) {
            Document document = matchingDocuments.get(i);
            Set<String> keySet = document.keySet();
            LinkedHashMap<String, String>[] rows = new LinkedHashMap[1];
            LinkedHashMap<String, String> row = new LinkedHashMap<>();

            for (String key : keySet) {
                Object value = document.get(key);
                String valueString = (value == null) ? "" : value.toString();
                row.put(key, valueString);
            }

            rows[0] = row;
            objects.put(document.getString("table"), rows);
            tableNames[i] = document.getString("table");
            columnNames[i] = document.getString("column");
        }

        return new Result(tableNames, columnNames, objects);
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


    private HashMap<String, String[]> filterDocumentKeys(String query){
        HashMap<String, String[]> res = new HashMap<>();
        var collections = db.listCollectionNames();

        for (String collectionName : collections) {
            HashSet<String> keys = new HashSet<>();
            var collection = db.getCollection(collectionName);

            collection.find().forEach((var doc) ->{
                doc.keySet().forEach((var key) -> {
                    if(key.matches(query)){
                        keys.add(key);
                    }
                });
            });
            res.put(collectionName, keys.toArray(new String[keys.size()]));
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
