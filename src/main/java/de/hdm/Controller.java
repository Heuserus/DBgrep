package de.hdm;

import de.hdm.cli.Output;
import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.datacontainer.Query;
import de.hdm.datacontainer.Result;
import de.hdm.db.IDBConnection;
import de.hdm.db.ILogic;
import de.hdm.db.SQLConnection;
import de.hdm.db.SqlLogic;

import java.sql.SQLException;
import java.util.List;


public class Controller {

    ConnectionInfo connectionInfo;
    List<Query> queryList;

    ILogic logic;


    public Controller(final ConnectionInfo connectionInfo, final List<Query> queryList) {
        this.connectionInfo = connectionInfo;
        this.queryList = queryList;
    }

    public void run() throws SQLException {

        //TODO: query muss geprueft werden
        IDBConnection dbConnection;
        ILogic logic;

        if (true) {

            dbConnection = new SQLConnection();
            logic = new SqlLogic((SQLConnection) dbConnection);
        } else {

        }
        dbConnection.connect(connectionInfo);
        for (Query query : queryList) {
            Result result = logic.request(query);
            Output.printResult(result);
        }
    }
}