

package de.hdm;

import java.sql.Statement;
import java.sql.ResultSet;

import de.hdm.helper.ConnectionInfo;
import de.hdm.helper.Request;
import de.hdm.helper.ResultOutput;
import main.SQLConnection;


public class Controller {

    ConnectionInfo connectionInfo;
    Request request;

    ProfileLoader profileLoader;
    Output output;
    SQLConnection sqlConnection;
    MongoConnection mongoConnection;

    public void run(ConnectionInfo cI, Request rQ){

        connectionInfo = cI;
        request = rQ;

        //Profile Loading Stuff
        connectionInfo = profileLoader.getInfo(connectionInfo);

        //driver Loader Stuff

        //Connector baut connection
        mongoConnection = new MongoConnection(connectionInfo.url, connectionInfo.username, connectionInfo.password);
        //find



        //Request logic
        String example = "SELECT COUNT(\"hstbenennung\") as \"a\" FROM fahrzeuginfo";
        //connection führt request aus
        //ResultSet result = sqlConnection.query(statement,example);

        //controller baut result object
        ResultOutput resultOutput = new ResultOutput("Test");


        //Output gibt Ergebnis aus
        output.print(result);

    }

    //maybe machen wir ein allgemeines output object oder wir verlagern logik in den output. Das müssen wir noch überlegen
    public void help(String arg){
        output.print(arg);
    }




}