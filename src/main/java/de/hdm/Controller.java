

package de.hdm;

import java.sql.Statement;
import java.sql.ResultSet;
import java.math.*;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.hdm.helper.ConnectionInfo;
import de.hdm.helper.Request;
import de.hdm.helper.ResultOutput;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;


public class Controller {

    ConnectionInfo connectionInfo;
    Request request;

    ProfileLoader profileLoader;
    Output output;
    MongoConnection mongoConnection;

    public void run(ConnectionInfo cI, Request rQ){

        connectionInfo = cI;
        request = rQ;

        //Profile Loading Stuff
        connectionInfo = profileLoader.getInfo(connectionInfo);


        //Connector baut connection
        mongoConnection = new MongoConnection(connectionInfo);
        MongoDatabase database = mongoConnection.getDatabase("test");
        //MongoCollection<Document> collection = database.getCollection("testdb");

        database = mongoConnection.getDatabase("test");
        collection = mongoConnection.getCollection("testdb");
        mongoConnection.findFirst(collection, database, "title", "Star Wars");
        //Find object in collection
        Document myDoc = mongoConnection.findFirst("test", database, "title", "Star Wars");


        //controller baut result object
        ResultOutput resultOutput = new ResultOutput("Test");


        //Output gibt Ergebnis aus
        output.print(myDoc.toJson());

    }

    //maybe machen wir ein allgemeines output object oder wir verlagern logik in den output. Das müssen wir noch überlegen
    public void help(String arg){
        output.print(arg);
    }




}