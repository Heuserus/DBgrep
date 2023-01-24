package de.hdm.db;

import de.hdm.datacontainer.Result;

import java.sql.SQLException;
import java.util.List;

public interface ILogic {
    
    
    public int count(Result result);
    public int count(String[] result);

    public Result request(List<List<List<String>>> query) throws SQLException;

}
