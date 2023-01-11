package de.hdm.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.hdm.datacontainer.*;

public interface ILogic {
    
    
    public int count(Result result);
    public int count(String[] result);
    public Result request(List<List<String>> query) throws SQLException;

}
