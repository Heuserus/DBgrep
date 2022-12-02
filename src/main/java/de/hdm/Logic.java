package de.hdm;

import de.hdm.datacontainer.*;

public interface Logic {
    
    
    public int count(Result result);
    public int count(String[] result);
    public Result request(Query query);

}
