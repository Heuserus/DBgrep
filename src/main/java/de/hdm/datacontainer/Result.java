package de.hdm.datacontainer;

import java.util.LinkedHashMap;

public class Result {
    
    private String[] tablenames;
    private String[] columnNames;
    private LinkedHashMap<String, LinkedHashMap<String, String>[]> objects;
    
    public Result(String[] tablenames, String[] columnNames, LinkedHashMap<String, LinkedHashMap<String, String>[]> objects) {
        this.tablenames = tablenames;
        this.columnNames = columnNames;
        this.objects = objects;
    }

    public String[] getTablenames() {
        return tablenames;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public LinkedHashMap<String, LinkedHashMap<String, String>[]> getObjects() {
        return objects;
    }

    
}
