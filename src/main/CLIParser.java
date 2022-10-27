package main;

import main.helper.ConnectionInfo;
import main.helper.Request;
import main.Controller;

public class CLIParser {
    
    void parse (String[] args) {

        if(args[0] == "--help"){
          controller.help(args[1]);
        }
        else{}
        
          //send string directly to output
          //output handles specific help request

        ConnectionInfo connectionInfo = new ConnectionInfo();
        Request request = new Request();
        Controller controller = new Controller();
        controller.run(connectionInfo,request);


    }
}
