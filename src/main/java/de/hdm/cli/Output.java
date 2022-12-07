package de.hdm.cli;

import java.util.LinkedHashMap;

import de.hdm.datacontainer.Result;

public class Output {

    public static void main(String[] args) {
        var s = new String[]{"a", "bb", "ccc", "dddd", "eeeee", "ffffff"};

        var _h = new LinkedHashMap<String, String>();
        var count = 0;
        for (String _s : s){
            _h.put("Column " + count++, _s);
        }
        var h = new LinkedHashMap<String, LinkedHashMap<String, String>[]>();
        LinkedHashMap<String, String>[] _h_arr = new LinkedHashMap[3];
        _h_arr[0] = _h;
        _h_arr[1] = _h;
        _h_arr[2] = _h;
        h.put("Table 1",_h_arr);

        Result res = new Result(null, null, h);
        Output.printResult(res);
    }

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
