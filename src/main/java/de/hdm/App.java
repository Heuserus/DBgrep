package de.hdm;

import picocli.*;

public class App {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new CLIParser()).execute(args);
        System.exit(exitCode);
    }
}
