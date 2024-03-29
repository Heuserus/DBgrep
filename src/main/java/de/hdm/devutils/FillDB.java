package de.hdm.devutils;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.hdm.JDBCDriverLoader;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class FillDB {
    //Mongo setup is still not working on macos and linux
    public static void main(String[] args) throws IOException, SQLException {
        var driver = JDBCDriverLoader.loadDriver("drivers/mariadb-java-client-3.1.0.jar");
        DriverManager.registerDriver(driver);
        String csvFilePath = "./docker/Fahrzeuginformationen.csv";
        clearDBs("jdbc:postgresql://localhost:5432/dbgrep", "root", "example");
        clearDBs("jdbc:mariadb://localhost:3306/dbgrep", "root", "example");
        sql(csvFilePath, "jdbc:postgresql://localhost:5432/dbgrep", "root", "example");
        sql(csvFilePath, "jdbc:mariadb://localhost:3306/dbgrep", "root", "example");
        noSql(csvFilePath);
    }

    private static void sql(String csvFilePath, String jdbcURL, String username, String password) {
        // String jdbcURL = "jdbc:postgresql://localhost:5432/dbgrep";
        // String username = "root";
        // String password = "example";

        int batchSize = 20; // TODO anpassen zu sinnvoller Zahl

        Connection connection = null;

        try {

            String[] header = {"HSTBenennung", "HTBenennung", "UTBenennung", "Karosserie", "NeupreisBrutto",
                    "Produktgruppe", "Kraftstoffart", "Schadstoffklasse", "CCM", "KW", "HSTPS", "Getriebeart",
                    "GetriebeBenennung", "AnzahlderTüren", "Leergewicht", "Zuladung", "ZulässigesGG", "Länge", "Breite",
                    "Höhe", "CO2Emissionen", "MinEnergieeffizienzklasse", "Antrieb", "KSTAMotor", "HSTHTBenennung"};
            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);

            Statement s = connection.createStatement();

            // create table ------------------------------------------------------------
            String create_table = "CREATE TABLE IF NOT EXISTS fahrzeuginfo " + // 25 spalten
                    "(" +
                    "HSTBenennung VARCHAR(500), " +
                    "HTBenennung VARCHAR(500), " +
                    "UTBenennung VARCHAR(500), " +
                    "Karosserie VARCHAR(500), " +
                    "NeupreisBrutto INTEGER, " +
                    "Produktgruppe VARCHAR(500), " +
                    "Kraftstoffart VARCHAR(500), " +
                    "Schadstoffklasse VARCHAR(500), " +
                    "CCM INTEGER, " +
                    "KW INTEGER, " +
                    "HSTPS INTEGER, " +
                    "Getriebeart VARCHAR(500), " +
                    "GetriebeBenennung VARCHAR(500), " +
                    "AnzahlderTüren INTEGER, " +
                    "Leergewicht INTEGER, " +
                    "Zuladung INTEGER, " +
                    "ZulässigesGG FLOAT, " +
                    "Länge INTEGER, " +
                    "Breite INTEGER, " +
                    "Höhe INTEGER, " +
                    "CO2Emissionen INTEGER, " +
                    "MinEnergieeffizienzklasse VARCHAR(500), " +
                    "Antrieb VARCHAR(500), " +
                    "KSTAMotor VARCHAR(500), " +
                    "HSTHTBenennung VARCHAR(500)" +
                    ")";

            s.executeUpdate(create_table);

            // prepared statement ----------------------------------------------------------
            String sql = "INSERT INTO fahrzeuginfo (" +
                    "HSTBenennung, " +
                    "HTBenennung, " +
                    "UTBenennung, " +
                    "Karosserie, " +
                    "NeupreisBrutto, " +
                    "Produktgruppe, " +
                    "Kraftstoffart, " +
                    "Schadstoffklasse, " +
                    "CCM, " +
                    "KW, " +
                    "HSTPS, " +
                    "Getriebeart, " +
                    "GetriebeBenennung, " +
                    "AnzahlderTüren, " +
                    "Leergewicht, " +
                    "Zuladung, " +
                    "ZulässigesGG, " +
                    "Länge, " +
                    "Breite, " +
                    "Höhe, " +
                    "CO2Emissionen, " +
                    "MinEnergieeffizienzklasse, " +
                    "Antrieb, " +
                    "KSTAMotor, " +
                    "HSTHTBenennung)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            int count = 0;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");
                if (data.length != header.length) {
                    lineReader.close();
                    for (String x : data) {
                        System.out.print(x + ", ");
                    }
                    System.out.print("\n");
                    throw new RuntimeException(
                            "CSV length (" + data.length + ") and header length (" + header.length + ") doesn't match");
                }
                for (int i = 1; i <= header.length; i++) {
                    switch (header[i - 1]) {
                        case "NeupreisBrutto":
                        case "CCM":
                        case "KW":
                        case "HSTPS":
                        case "AnzahlderTüren":
                        case "Leergewicht":
                        case "Zuladung":
                        case "Länge":
                        case "Breite":
                        case "Höhe":
                        case "CO2Emissionen":
                            statement.setInt(i, Integer.parseInt(data[i - 1]));
                            break;
                        case "ZulässigesGG":
                            statement.setFloat(i, Float.parseFloat(data[i - 1]));
                            break;
                        default:
                            statement.setString(i, data[i - 1]);
                    }
                }
                statement.addBatch();
                count++;

                if (count % batchSize == 0) {
                    statement.executeBatch();
                    // System.out.println("Batch");
                }
                // System.out.println("while done: " + count + "\n");
            }

            lineReader.close();
            // execute the remaining queries
            statement.executeBatch();
            ResultSet set = s.executeQuery("SELECT COUNT(\"hstbenennung\") as \"a\" FROM fahrzeuginfo");
            set.next();
            int dataCount = set.getInt("a");
            System.out.println("Filling " + jdbcURL + " done! Data count: " + dataCount);

            connection.commit();
            connection.close();

        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    private static void noSql(String csvFilePath) {
        ConnectionString connectionString = new ConnectionString("mongodb://root:example@localhost/");
        final int batchSize = 20;
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();
        ArrayList<Document> batch = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase database = mongoClient.getDatabase("dbgrep");
            // create collection
            try {
                database.createCollection("fahrzeuginfo");
            } catch (MongoCommandException e) {
                if (e.getErrorCode() == 48) {
                    System.out.println("Collection fahrzeuginfo already exists.");
                } else {
                    throw e;
                }
            }
            MongoCollection<Document> fahrzeuginfo = database.getCollection("fahrzeuginfo");

            String[] header = { "HSTBenennung", "HTBenennung", "UTBenennung", "Karosserie", "NeupreisBrutto",
                    "Produktgruppe", "Kraftstoffart", "Schadstoffklasse", "CCM", "KW", "HSTPS", "Getriebeart",
                    "GetriebeBenennung", "AnzahlderTüren", "Leergewicht", "Zuladung", "ZulässigesGG", "Länge", "Breite",
                    "Höhe", "CO2Emissionen", "MinEnergieeffizienzklasse", "Antrieb", "KSTAMotor", "HSTHTBenennung" };
            
            // Read csv and insert it
            try (BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath))) {
                String lineText = null;
                lineReader.readLine(); // skip header line
                while ((lineText = lineReader.readLine()) != null) {
                    String[] data = lineText.split(",");
                    if (data.length != header.length) {
                        lineReader.close();
                        for (String x : data) {
                            System.out.print(x + ", ");
                        }
                        System.out.print("\n");
                        throw new RuntimeException("CSV length (" + data.length + ") and header length ("
                                + header.length + ") doesn't match");
                    }
                    Document doc = new Document();
                    for (int i = 0; i < header.length; i++) {
                        doc.append(header[i], data[i]);
                    }
                    batch.add(doc);
                    if(batch.size() >= batchSize){
                        fahrzeuginfo.insertMany(batch);
                        batch.clear();
                    }
                }
                fahrzeuginfo.insertMany(batch); // insert remaining
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Find object in collection
            long count = fahrzeuginfo.countDocuments();
            System.out.println("Filling MongoDB done! Data count: " + count);
        }
    }

    private static void clearDBs(String jdbcURL, String username, String password) {
        // String jdbcURL = "jdbc:postgresql://localhost:5432/dbgrep";
        // String username = "root";
        // String password = "example";

        try (Connection conn = DriverManager.getConnection(jdbcURL, username, password)) {
            conn.createStatement().executeUpdate("DROP TABLE fahrzeuginfo");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ConnectionString connectionString = new ConnectionString("mongodb://root:example@localhost/");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
            .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();
        try (MongoClient client = MongoClients.create(settings)){
            client.getDatabase("dbgrep").getCollection("fahrzeuginfo").drop();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
