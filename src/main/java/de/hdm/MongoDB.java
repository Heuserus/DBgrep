package de.hdm;
import com.mongodb.*;
import com.mongodb.ServerApi;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;
//import com.mongodb.client.MongoDatabase;

public class MongoDB {
    //public static MongoClient mongoClient;
    //public static DBCollection con;
    //public static DB database;

    //private static String uri = "mongodb+srv://lv042:<K6Uu9882EFFeWvgU>@dbgrep.o3uj6ms.mongodb.net/?retryWrites=true&w=majority";

    public static void main(String[] args) {


        ConnectionString connectionString = new ConnectionString("mongodb+srv://user:87654321@dbgrep.o3uj6ms.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("test");
        database.getCollection("testdb").insertOne(new Document("moive", "MongoDB"));
        Document movie = new Document("title", "Star Wars")
                .append("year", 2019)
                .append("director", "Michael Bay")
                .append("actors", "Batman");
        database.getCollection("testdb").insertOne(movie);
        System.out.println(database.getCollection("testdb").find(eq("title", "Star Wars")).first());
               // collection.find(eq("title", "Back to the Future")).first();




    }
}