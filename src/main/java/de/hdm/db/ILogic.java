package de.hdm.db;

import de.hdm.datacontainer.Query;
import de.hdm.datacontainer.Result;

import java.sql.SQLException;

public interface ILogic {
    
    


    public Result request(Query query) throws SQLException;

}
