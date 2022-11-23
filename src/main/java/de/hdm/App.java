package de.hdm;

import de.hdm.cli.CLIParser;
import picocli.*;

public class App {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new CLIParser()).execute(args);
        System.exit(exitCode);
    }
}
