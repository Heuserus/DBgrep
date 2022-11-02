package de.hdm;

import de.hdm.helper.ConnectionInfo;
import de.hdm.helper.Request;
import de.hdm.helper.Result;


public class Controller {
    
    ConnectionInfo connectionInfo;
    Request request;
    
    ProfileLoader profileLoader;
    Output output;
    
    public void run(ConnectionInfo cI, Request rQ){

        connectionInfo = cI;
        request = rQ;

        //Profile Loading Stuff
        connectionInfo = profileLoader.getInfo(connectionInfo);
        //driver Loader Stuff

        //Connector baut connection

        //Request logic

        //connection führt request aus

        //controller baut result object
        Result result = new Result("Test");

        //Output gibt Ergebnis aus
        output.print(result);

    }

    //maybe machen wir ein allgemeines output object oder wir verlagern logik in den output. Das müssen wir noch überlegen
    public void help(String arg){
        output.print(arg);
    }

    
    

}
