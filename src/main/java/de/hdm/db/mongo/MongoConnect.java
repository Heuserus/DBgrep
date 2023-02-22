package de.hdm.db.mongo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import de.hdm.cli.Output;
import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.datacontainer.Query;
import de.hdm.datacontainer.Result;

public class MongoConnect implements AutoCloseable {

    private MongoClient mongoClient;
    private ConnectionInfo connectionInfo;
    private MongoDatabase db;

    /**
     * Connects to a MongoDB instance with the given connection info.
     * @param connectionInfo Contains information on how to reach the MongoDB instance.
     */
    public MongoConnect(ConnectionInfo connectionInfo){
        if(true) LucasMongoConnection(connectionInfo); //for the poor mac users whos docker containers are not working :(
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


    public Result request(Query query){
        // do something with query object
        // maybe utilize MongoLogic
        return null;
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

        try(MongoConnect m = new MongoConnect(info)){
            // Arrays.stream(m.filterCollectionNames(".*")).forEach((s) -> System.out.println(s));
            m.filterDocumentKeys(".*").forEach((var key, var val) -> {
                System.out.println(key);
                Arrays.stream(val).forEach((var s) -> System.out.println("  " + s));
            });
        } catch(Exception e){
            e.printStackTrace();
        }
        try(var mongoConnection = new MongoConnect(connectionInfo)){
            for (Query query : queryList) {
                Result result = mongoConnection.request(query);
                Output.printResult(result);
            }
        }
    }

    @Override
    public void close() {
        mongoClient.close();

    }
}
