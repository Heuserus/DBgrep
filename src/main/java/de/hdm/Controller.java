package de.hdm;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.datacontainer.Query;
import de.hdm.datacontainer.Result;
import de.hdm.db.MongoConnection;
import de.hdm.db.SQLConnection;

import org.bson.Document;


public class Controller {
    
    ConnectionInfo connectionInfo;
    Query request;
    
    Result result;
    SQLConnection sqlConnection;

    Boolean sql;
    
    public Controller (ConnectionInfo connectionInfo, Query request){
        this.connectionInfo = connectionInfo;
        this.request = request;
    }
    public void run(){
        //remove later
        sql = true;


        if(sql){
             //driver Loader Stuff

        //Connector baut connection
        Connection connection = sqlConnection.connection(connectionInfo);
        Statement statement = sqlConnection.getStatement(connection);

        //Request logic
        String example = "SELECT COUNT(\"hstbenennung\") as \"a\" FROM fahrzeuginfo";
        //connection führt request aus
        ResultSet result = sqlConnection.query(statement,example);

            
        }
        else{
            //Mongo
            connectionInfo.url = "dbgrep.o3uj6ms.mongodb.net/?retryWrites=true&w=majority";
            connectionInfo.setUsername("user");
            connectionInfo.setPassword("87654321");

            //String buildUri = "mongodb+srv://" + connectionInfo.username + ":" + connectionInfo.password + "@" + connectionInfo.url;
            MongoConnection mongoClient = new MongoConnection(connectionInfo);


            MongoDatabase database = mongoClient.getDatabase("test");
            MongoCollection<Document> collection = database.getCollection("testdb");
            System.out.println("Collection testdb selected successfully");

            //Find object in collection
            //System.out.println(findFirst(collection, database, "title", "Star Wars"));

            //all bjects in array

            Document[] all = mongoClient.findAll(collection, database, "title", "Star Wars");
            for (int i = 0; i < all.length; i++) {
                System.out.println(all[i]);
            }
            
        }
       

        //controller baut result object
        Result resultOutput = new Result("Test");

        //Output gibt Ergebnis aus
        // result.print(resultOutput);

    }

    //maybe machen wir ein allgemeines output object oder wir verlagern logik in den output. Das müssen wir noch überlegen
    public void help(String arg){
        result.print(arg);
    }
}
