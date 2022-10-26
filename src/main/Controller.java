package main;



import main.helper.ConnectionInfo;
import main.helper.Request;
import main.helper.Result;


public class Controller {
    
    ConnectionInfo connectionInfo;
    Request request;
    
    ProfileLoader profileLoader;
    Output output;
    


    public Controller(ConnectionInfo cI, Request rQ){
        connectionInfo = cI;
        request = rQ;
    }

    public void run(){

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

    
    

}
