package de.hdm.cli;

import java.util.LinkedHashMap;

import de.hdm.datacontainer.Result;

public class Output {

   

    public static void printResult(Result result){
        var tableNames = result.getTablenames();
        var columnNames = result.getColumnNames();
        var objects = result.getObjects();

        if(tableNames != null){
            for (String s : tableNames) {
                System.out.println(s);
            }
            return;

        } else if(columnNames != null){
            for (String s : columnNames) {
                System.out.println(s);
            }
            return;

        } else if(objects != null){
            objects.forEach((table, rows) -> {
                System.out.println(table);
                for (var row : rows){
                    int longest_key = row.keySet().stream().reduce((a, b) -> a.length() > b.length() ? a : b).get().length();
                    int longest_val = row.values().stream().reduce((a, b) -> a.length() > b.length() ? a : b).get().length();
                    
                    row.forEach((key, val) -> {
                        System.out.format("%" + (-longest_key - 2) + "s %" + (-longest_val - 2) + "s", key+":", val);
                    });
                    System.out.println();
                }
                System.out.println();
            });
        }
        // nothing found -> return
    }
}
