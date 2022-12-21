package de.hdm.db;

import java.sql.SQLException;

import de.hdm.datacontainer.*;

public interface ILogic {
    
    
    public int count(Result result);
    public int count(String[] result);
    public Result request(Query query) throws SQLException;

}
