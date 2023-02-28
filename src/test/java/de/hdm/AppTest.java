package de.hdm;

import de.hdm.cli.CLIParser;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for simple App.
 */
public class AppTest {
    CLIParser app = new CLIParser();
    CommandLine cmd = new CommandLine(app);
    final String VALID_PROFILE = "-p=./profiles/profileTest.yml ";
    final String INVALID_PROFILE = "-p ./no/valid/path.yml ";
    private final String[][] validQueries = {
            {VALID_PROFILE, "-t=Fahrzeuginfo"},
            {VALID_PROFILE, "-t=Fahrze%"},
            {VALID_PROFILE, "-t=%info"},
            {VALID_PROFILE, "-c=\"HST Benennung\" =Volkswagen"},
            {VALID_PROFILE, "-t=Fahrzeuginfo", "-c=Neupreis Netto"},
            {VALID_PROFILE, "-t=Fahrzeuginfo", "-c=Neupreis Brutto", "<5"},
            {VALID_PROFILE, "-t=Fahrzeuginfo", "-c=Neupreis Brutto", ">5", "-c=Neupreis Brutto", "<20"},
            {VALID_PROFILE, "-t=Fahrzeuginfo", "-c=Neupreis Brutto", ">5", "-c=Neupreis Brutto", "20"},
            {VALID_PROFILE, "-t=Fahrzeuginfo", "-c=Neupreis Brutto", ">5", "-c=Neupreis Brutto", "20"},
    };

    private final String[][] invalidQueries = {
            {INVALID_PROFILE, validQueries[0][1]},
            {VALID_PROFILE, "-t=Fahrzeuginfo", "-c=", "Neupreis Brutto"},
            {VALID_PROFILE, "-t=Fahrzeuginfo", "-c", "Neupreis Brutto", ">5", "-c=", "Neupreis Brutto", "<20"},
    };

    // black box testing
    @Test
    void testApp_forValidQueries() {
        for (String[] query : validQueries) {
            int exitCode = cmd.execute(query);
            assertEquals(0, exitCode);
            cmd = new CommandLine(app);
        }
    }

    @Test
    void testApp_forInvalidQueries() {
        for (String[] query : invalidQueries) {
            int exitCode = cmd.execute(query);
            assertEquals(2, exitCode);
            cmd = new CommandLine(app);
        }
    }
}
