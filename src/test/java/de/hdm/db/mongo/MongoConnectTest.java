package de.hdm.db.mongo;

import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.datacontainer.Query;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for the {@link MongoConnect} class.
 * NOTE: The specified MongoDB instance needs to be running and reachable under localhost.
 *       Moreover, it needs to contain the data 'fahrzeuginfo'.
 *       Best use the {@link de.hdm.devutils.FillDB} Script.
 */
class MongoConnectTest {

    private static MongoConnect mongo;

    @BeforeAll
    public static void setup(){
        var con = new ConnectionInfo();
        con.setHost("localhost");
        con.setPort(27017);
        con.setProtocol("mongodb");
        con.setDbname("dbgrep");
        con.setUsername("root");
        con.setPassword("example");

        mongo = new MongoConnect(con);
    }

    @AfterAll
    public static void teardown(){
        mongo.close();
    }

    @Test
    public void testSearchTableNames(){
        var q = createQuery("fahr.*", null);
        var res = mongo.request(q);
        assertEquals("fahrzeuginfo", res.getTablenames()[0]);

        var q2 = createQuery("blah", null);
        var res2 = mongo.request(q2);
        assertEquals(0, res2.getTablenames().length);
    }


    @Test
    public void testSearchColumnNames(){
        // test literal
        var q = createQuery(null, createCol("KW"));
        var resp = mongo.request(q);
        var res = resp.getColumnNames().get("fahrzeuginfo")[0];
        assertEquals("KW", res);

        // test regex
        var q2 = createQuery(null, createCol("K.*"));
        var resp2 = mongo.request(q2);
        var res2 = new HashSet<String>(List.of(resp2.getColumnNames().get("fahrzeuginfo")));
        assertTrue(res2.contains("KW"));
        assertTrue(res2.contains("KSTAMotor"));
        assertTrue(res2.contains("Kraftstoffart"));
        assertTrue(res2.contains("KSTAMotor"));

        // test nonexistent
        var q3 = createQuery(null, createCol("blah"));
        var resp3 = mongo.request(q3).getColumnNames();
        assertNull(resp3);
    }


    @Test
    public void testSearchObjectsEqual(){
        var q = createQuery("fahrzeuginfo", createCol("KW:=101"));
        var resp = mongo.request(q);
        var res = resp.getObjects().get("fahrzeuginfo");
        assertEquals(7, res.length);
    }


    @Test
    public void testSearchObjectsNotEqual(){
        var q = createQuery("fahrzeuginfo", createCol("KW:!=101"));
        var resp = mongo.request(q);
        var res = resp.getObjects().get("fahrzeuginfo");
        assertEquals(991, res.length);
    }


    @Test
    public void testSearchObjectsLessThan(){
        // the less than will look for alphabetic order because the numbers are saved as strings
        var q = createQuery("fahrzeuginfo", createCol("KW:<101"));
        var resp = mongo.request(q);
        var res = resp.getObjects().get("fahrzeuginfo");
        assertEquals(12, res.length);
    }


    @Test
    public void testSearchObjectsGreaterThan(){
        // the greater than will look for alphabetic order because the numbers are saved as strings
        var q = createQuery("fahrzeuginfo", createCol("KW:>101"));
        var resp = mongo.request(q);
        var res = resp.getObjects().get("fahrzeuginfo");
        assertEquals(979, res.length);
    }

    // Fun Fact: There are 998 documents in the MongoDB in total and
    // the expected results from the Methods testSearchObjectsEqual, testSearchObjectsLessThan and
    // testSearchObjectsGreaterThan added together are equal to that: 7 + 12 + 979 = 998


    @Test
    public void testSearchObjectsRegex(){
        var q = createQuery("fahrzeuginfo", createCol("HSTBenennung:+Mercedes-Benz"));
        var resp = mongo.request(q);
        var res = resp.getObjects().get("fahrzeuginfo");
        assertEquals(173, res.length);
    }


    private Query createQuery(String table, ArrayListValuedHashMap<String, String> col){
        var q = new Query();
        q.setTable(table);
        if (col == null) col = new ArrayListValuedHashMap<String, String>();
        q.setColumns(col);
        return q;
    }

    private ArrayListValuedHashMap<String, String> createCol(String ... queries){
        var res = new ArrayListValuedHashMap<String, String>();
        for (var q : queries) {
            if (q.contains(":")) {
                var _q = q.split(":");
                res.put(_q[0], _q[1]);
            } else {
                res.put(q, "");
            }
        }
        return res;
    }
}