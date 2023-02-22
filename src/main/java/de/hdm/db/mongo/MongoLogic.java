package de.hdm.db.mongo;

import de.hdm.datacontainer.Query;
import de.hdm.datacontainer.Result;
import de.hdm.db.ILogic;

import java.sql.SQLException;

public class MongoLogic implements ILogic {

    public int count(Result result) {
        return 0; //TODO: needs to be implemented
    }

    public int count(String[] result) {
        return 0; //TODO: needs to be implemented
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
    public Result request(Query query) throws SQLException {
        System.out.println(query);
        switch (query.getQueryType()) {
            case SEARCH_TABLE_NAMES:
                //TODO: searchTableNames
            case SEARCH_COLUMN_NAMES:
                //TODO: searchColumnNames
            case SEARCH_OBJECTS:
                System.out.println("Object");
                break;
        }
        return null;
    }
}