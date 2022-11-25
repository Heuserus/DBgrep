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
        connectionInfo.url = "dbgrep.o3uj6ms.mongodb.net/?retryWrites=true&w=majority";
        connectionInfo.setUsername("user");
        connectionInfo.setPassword("87654321");

        MongoConnection mongoClient = new MongoConnection(connectionInfo);

        //WIP -> Gonna change it to docker database later
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("testdb");

        Assert.assertTrue(collection.find().first().containsKey("hstbenennung"));

    }

    @Before
    public void assertSetupDatabase() throws IOException, InterruptedException {


        //not yet working
        Process p = Runtime.getRuntime().exec("start_db.sh");
        p.waitFor();
        FillDB.setupDatabases();
    }

}
