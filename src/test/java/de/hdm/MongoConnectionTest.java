package de.hdm;

import java.io.IOException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.hdm.datacontainer.ConnectionInfo;
import org.bson.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import de.hdm.db.MongoConnection;
import de.hdm.devutils.FillDB;

public class MongoConnectionTest {

    //this class is not yet working but this is a basic structure
    @Test
    public void assertFindCollumn (){
        ConnectionInfo connectionInfo = new ConnectionInfo();
        connectionInfo.url = "localhost/";
        connectionInfo.setUsername("root");
        connectionInfo.setPassword("example");

        MongoConnection mongoClient = new MongoConnection(connectionInfo);

        //WIP -> Gonna change it to docker database later
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("testdb");

        Assert.assertTrue(collection.find().first().containsKey("hstbenennung"));

    }

    @Before
    public void assertSetupDatabase() throws IOException, InterruptedException {


        //not yet working
        //System.out.println("Working Directory = " + System.getProperty("user.dir"));
        //start file
        Process p = Runtime.getRuntime().exec(new String[]{"./start_db.sh"});

        p.waitFor();
        FillDB.setupDatabases();
    }

}
