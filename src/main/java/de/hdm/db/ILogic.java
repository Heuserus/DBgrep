package de.hdm.db;

import de.hdm.datacontainer.Query;
import de.hdm.datacontainer.Result;

import java.sql.SQLException;

public interface ILogic {
    
    
    public int count(Result result);
    public int count(String[] result);

    public Result request(Query query) throws SQLException;

}
