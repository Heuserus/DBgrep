package de.hdm.cli;

import de.hdm.datacontainer.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class OutputTest {

    private static PrintStream backup;
    private static ByteArrayOutputStream stream;
    private static PrintStream printStream;

    @BeforeAll
    public static void setupStreams() {
        backup = System.out;
        stream = new ByteArrayOutputStream();
        printStream = new PrintStream(stream);
        System.setOut(printStream);
    }

    @AfterAll
    public static void resetStreams() {
        System.setOut(backup);
        printStream.close();

    }

    @Test
    public void testNull() {
        var res = new Result(null, null, null);
        stream.reset();
        Output.printResult(res);
        assertEquals("", stream.toString().replace("\r", ""));
    }

    @Test
    public void testTableNames() {
        var res = new Result(new String[]{"a", "b", "c", "d"}, null, null);
        stream.reset();
        Output.printResult(res);
        String testString = getFileContent("test_files/test_table_names_1");
        assertEquals(testString, stream.toString().replace("\r", ""));
    }

    @Test
    public void testColumnNames() {
        var res = new Result(null, new String[]{"a", "b", "c", "d"}, null);
        stream.reset();
        Output.printResult(res);
        String testString = getFileContent("test_files/test_table_names_1");
        assertEquals(testString, stream.toString().replace("\r", ""));
    }

    @Test
    public void testContent() {
        // create Hash map
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

        // test
        var res = new Result(null, null, h);
        stream.reset();
        Output.printResult(res);
        String testString = getFileContent("test_files/test_objects_1");
        assertEquals(testString, stream.toString().replaceAll("\r", ""));
    }

    private String getFileContent(String path) {
        String testString = "";
        try (var reader = new BufferedReader(new FileReader(path))){
            String line;
            while ((line = reader.readLine()) != null) {
                testString += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return testString;
    }
}
