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

    
    

}
