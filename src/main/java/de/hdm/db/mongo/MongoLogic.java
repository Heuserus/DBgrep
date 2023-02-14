package de.hdm.db.mongo;
import de.hdm.datacontainer.Query;
import de.hdm.datacontainer.Result;
import de.hdm.db.ILogic;
import java.util.List;

public class MongoLogic implements ILogic {

    public int count(Result result){

        return 0;

    }
    public int count(String[] result){
        return 0;

    }



    //example query
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






    public Result request(List<List<String>> query) {
        //currently the method of the sql logic is used


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