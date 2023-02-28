package de.hdm;

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

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;


public class Controller {

    ConnectionInfo connectionInfo;
    List<Query> queryList;

    public Controller(final ConnectionInfo connectionInfo, final List<Query> queryList) {
        this.connectionInfo = connectionInfo;
        this.queryList = queryList;
    }

    public void run() throws SQLException, IOException {

        //TODO: query muss geprueft werden
        IDBConnection dbConnection;
        ILogic logic;

        // Import Profile if missing
        if (connectionInfo.getDriver() != null) {
            var driver = JDBCDriverLoader.loadDriver(connectionInfo.getDriver());
            DriverManager.registerDriver(driver);
        }

        // JDBC Stuff
        if (connectionInfo.getProtocol().toLowerCase().contains("jdbc")) {
            dbConnection = new SQLConnection();
            logic = new SqlLogic((SQLConnection) dbConnection);

            dbConnection.connect(connectionInfo);
            for (Query query : queryList) {
                Result result = logic.request(query);
                
                Output.printResult(result);
            }
        }

        // MongoDB Stuff
        else if (connectionInfo.getProtocol().toLowerCase().contains("mongo")) {
            try (var mongoConnection = new MongoConnect(connectionInfo)) {
                //iterate over query
                for (Query query:queryList) {
                    Result result = mongoConnection.request(query);
                    Output.printResult(result);
                }
            }
        } else {
            throw new UnknownDBTypeException("Unknown database type for given protocol '" + connectionInfo.getProtocol() + "'.");
        }
    }
}