package de.hdm;

import de.hdm.cli.CLIParser;
import picocli.*;

public class App {

    public static void main(String[] args) {
        CLIParser cliParser = new CLIParser();
        CommandLine cmd = new CommandLine(cliParser);
        int exitCode = -1;
        if(cliParser.isHelp()){
            cmd.usage(System.out);
            System.exit(0);
        } else {
            
            exitCode = cmd.execute(args);
        }
        System.exit(exitCode);
    }
}
