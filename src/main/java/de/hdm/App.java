package de.hdm;

import de.hdm.CLIParser;
import picocli.CommandLine;

public class App {

    public static void main(String[] args) {
        System.out.println(args);
        int exitCode = new CommandLine(new CLIParser()).execute(args);
        System.exit(exitCode);
    }
}
