package de.hdm;

import de.hdm.cli.CLIParser;
import de.hdm.devutils.AsciiArt;
import de.hdm.exception.DBGrepException;
import picocli.CommandLine;

public class App {

    public static void main(String[] args) {
        AsciiArt.display_welcome_message();
        CLIParser cliParser = new CLIParser();
        CommandLine cmd = new CommandLine(cliParser);
        int exitCode = -1;
        if(cliParser.isHelp()){
            cmd.usage(System.out);
            System.exit(0);
        } else {
            try {
                exitCode = cmd.execute(args);
            } catch (DBGrepException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
                System.exit(e.getExitCode().getCode());
            }
        }
        System.exit(exitCode);
    }
}
