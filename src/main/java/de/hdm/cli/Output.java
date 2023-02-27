package de.hdm.cli;

import de.hdm.datacontainer.Result;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Output {

   

    public static void printResult(Result result){
        if (result == null) return;

        var tableNames = result.getTablenames();
        var columnNames = result.getColumnNames();
        var objects = result.getObjects();

        if (tableNames != null) {
            for (String s : tableNames) {
                System.out.println(s);
            }
            return;

        } else if (columnNames != null) {
            for (String key : columnNames.keySet()) {
                System.out.println(key);
                for (var value : columnNames.get(key)){
                    System.out.println("  " + value);
                }
            }
            return;

        } else if(objects != null){
            objects.forEach((table, rows) -> {
                System.out.println(table);
                var longest_keys = getMaxKeyLength(rows);
                var longest_vals = getMaxValueLength(rows);
                for (var row : rows){

                    row.forEach((key, val) -> {
                        System.out.format("%" + (-longest_keys.get(key) - 1) + "s %" + (-longest_vals.get(key) - 2) + "s", key + ":", val);
                    });
                    System.out.println();
                }
                System.out.println();
            });
        }
        // nothing found -> return
    }


    private static HashMap<String, Integer> getMaxKeyLength(LinkedHashMap<String, String>[] rows){
        var res = new HashMap<String, Integer>();
        for ( var row : rows ) {
            for ( var key : row.keySet()) {
                if ( res.containsKey(key)) {
                    // Update the value if the key is longer
                    var len = res.get(key);
                    if(len < key.length()) {
                        res.replace(key, key.length());
                    }
                } else {
                    // Insert new key with length
                    res.put(key, key.length());
                }
            }
        }
        return res;
    }

    private static HashMap<String, Integer> getMaxValueLength(LinkedHashMap<String, String>[] rows) {
        var res = new HashMap<String, Integer>();
        for ( var row : rows ) {
            for ( var entry : row.entrySet()) {
                if ( res.containsKey(entry.getKey())) {
                    // Update the value if the key is longer
                    var len = res.get(entry.getKey());
                    if(len < entry.getValue().length()) {
                        res.replace(entry.getKey(), entry.getValue().length());
                    }
                } else {
                    // Insert new key with length
                    res.put(entry.getKey(), entry.getValue().length());
                }
            }
        }
        return res;
    }
}
