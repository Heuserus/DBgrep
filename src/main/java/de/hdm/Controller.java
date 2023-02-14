package de.hdm;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.hdm.cli.Output;
import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.datacontainer.Query;
import de.hdm.datacontainer.Result;
import de.hdm.db.IDBConnection;
import de.hdm.db.ILogic;
import de.hdm.db.mongo.MongoConnect;
import de.hdm.db.sql.SQLConnection;
import de.hdm.db.sql.SqlLogic;
import de.hdm.exception.UnknownDBTypeException;


public class Controller {
    
    ConnectionInfo connectionInfo;
    List<Query> queryList;
    
    ILogic logic;

    
    
    public Controller(ConnectionInfo connectionInfo, List<Query> query) {
        this.connectionInfo = connectionInfo;
        this.queryList = query;
    }

    public void run() throws SQLException, IOException{

        //TODO: query muss geprueft werden
        IDBConnection dbConnection;
        ILogic logic;

        // Import Profile if missing
        if(connectionInfo.getDriver() != null){
            var driver = JDBCDriverLoader.loadDriver(connectionInfo.getDriver());            
            DriverManager.registerDriver(driver);
        }

        // JDBC Stuff
        if(connectionInfo.getProtocol().toLowerCase().contains("jdbc")){
            var query = convertQueryToList(queryList);

            dbConnection = new SQLConnection();
            logic = new SqlLogic((SQLConnection) dbConnection);
            
            dbConnection.connect(connectionInfo);
            for(int i = 0; i < query.size(); i++){
                Result result = logic.request(query.get(i));
                Output.printResult(result);
            }
        }
        // MongoDB Stuff
        else if (connectionInfo.getProtocol().toLowerCase().contains("mongo")) {
            try(var mongoConnection = new MongoConnect(connectionInfo)){
                for (Query query : queryList) {
                    Result result = mongoConnection.request(query);
                    Output.printResult(result);
                }
            }
        }

        else {
            throw new UnknownDBTypeException("Unknown database type for given protocol '" + connectionInfo.getProtocol() + "'.");
        }
    }

    /**
     * Converts the list of {@link Query} objects to a multidimensional list of Strings.
     * @param queryArguments List of {@link Query} objects.
     * @return Multidimensional list of Strings.
     */
    private ArrayList<List<List<String>>> convertQueryToList(List<Query> queryArguments){
        var commandlineArguments = new ArrayList<List<List<String>>>();
        for (Query argument : queryArguments) {
          commandlineArguments.add(argument.parseQuery());
        }
        return commandlineArguments;
    }
}