package de.hdm.db;

import de.hdm.datacontainer.Query;
import de.hdm.datacontainer.Result;

import java.sql.SQLException;

public interface ILogic {
    
     /**
     * Request function to execute the correct query
     * @param query Query object 
     * @return Resultobject filled with the result data.
     */
    public Result request(Query query) throws SQLException;

}
