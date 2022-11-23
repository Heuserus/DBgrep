package de.hdm;

import java.sql.Statement;
import java.sql.ResultSet;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.hdm.helper.ConnectionInfo;
import de.hdm.helper.Request;
import de.hdm.helper.ResultOutput;
import de.hdm.SQLConnection;
import org.bson.Document;


public class Controller {
    
    ConnectionInfo connectionInfo;
    Request request;
    
    Output output;
    SQLConnection sqlConnection;

    Boolean sql;
    
    public void run(ConnectionInfo cI, Request rQ){

        connectionInfo = cI;
        request = rQ;

        //remove later
        sql = true;

        //Profile Loading Stuff
        connectionInfo = profileLoader.getInfo(connectionInfo);

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
            connectionInfo.username = "user";
            connectionInfo.password = "87654321";

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
        ResultOutput resultOutput = new ResultOutput("Test");

        //Output gibt Ergebnis aus
        output.print(result);

    }

    //maybe machen wir ein allgemeines output object oder wir verlagern logik in den output. Das müssen wir noch überlegen
    public void help(String arg){
        output.print(arg);
    }

    public void setConnectionInfo(ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    
    

}
