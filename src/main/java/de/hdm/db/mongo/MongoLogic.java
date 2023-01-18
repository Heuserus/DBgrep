package de.hdm.db.mongo;

import de.hdm.datacontainer.Query;
import de.hdm.datacontainer.Result;
import de.hdm.db.ILogic;
import de.hdm.db.sql.SQLConnection;

import java.sql.SQLException;
import java.util.List;

public class MongoLogic implements ILogic {
     
    public int count(Result result){

        return 0;

    }
    public int count(String[] result){
        return 0;

    }
    /*
    --driver
test
--host
localhost
--port
1
--dbname
test
--username
test
--protocol
michael
--password
test

[[-t,fahrzeuginfo],
[-c,breite],
[-c,gewicht], [-c breite]]

     */






    public Result request(List<List<String>> query) throws SQLException{
        String argument = query.get(query.get(0).size()-1).get(0);
        System.out.println(query);
        switch (argument) {
            case "--table":
            case "-t":
                return null;


            case "-c":
            case "--column":
                return null;


            case "-o":
            case "--object":
                System.out.println("Object");
                break;
        }

        return null;
    }


    
}
