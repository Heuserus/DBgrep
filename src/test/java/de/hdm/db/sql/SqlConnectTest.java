package de.hdm.db.sql;

import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.datacontainer.Query;

import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;


class SQLConnectTest {

    private static SQLConnection sqlConnect = new SQLConnection();
    private static SqlLogic sqlLogic = new SqlLogic(sqlConnect);

    @BeforeAll
    public static void setup() throws SQLException{
        var con = new ConnectionInfo();
        con.setHost("localhost");
        con.setPort(5432);
        con.setProtocol("jdbc:postgresql");
        con.setDbname("dbgrep");
        con.setUsername("root");
        con.setPassword("example");
        con.setDriver("drivers\\mariadb-java-client-3.1.0.jar");

        sqlConnect.connect(con);
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

    @Test
    public void testSearchTableNames() throws SQLException{
        var q = createQuery("fahr", null);
        var res = sqlLogic.request(q);
        assertEquals("fahrzeuginfo", res.getTablenames()[0]);

        var q2 = createQuery("blah", null);
        var res2 = sqlLogic.request(q2);
        assertEquals(0, res2.getTablenames().length);
    }

    @Test
    public void testSearchColumnNames() throws SQLException{
        // test literal
        Query q = createQuery(null, createCol("KW"));
        var resp = sqlLogic.request(q);
        var res = resp.getColumnNames().get("fahrzeuginfo")[0];
        assertEquals("kw", res);

        // test regex
        var q2 = createQuery(null, createCol("K"));
        var resp2 = sqlLogic.request(q2);
        var res2 = new HashSet<String>(List.of(resp2.getColumnNames().get("fahrzeuginfo")));
        assertTrue(res2.contains("kw"));
        assertTrue(res2.contains("kstamotor"));
        assertTrue(res2.contains("kraftstoffart"));
        assertTrue(res2.contains("karosserie"));

        // test nonexistent
        var q3 = createQuery(null, createCol("blah"));
        var resp3 = sqlLogic.request(q3).getColumnNames();
        assertNull(resp3);
    }

    @Test
    public void testSearchObjectsEqual() throws SQLException{
        var q = createQuery("fahrzeuginfo", createCol("KW:=101"));
        var resp = sqlLogic.request(q);
        var res = resp.getObjects().get("fahrzeuginfo");
        assertEquals(7, res.length);
    }

}
