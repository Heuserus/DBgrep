package de.hdm.cli;

import de.hdm.Controller;
import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.datacontainer.Query;
import de.hdm.exception.MissingProfileException;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.Callable;

@Command(name = "dbgrep", mixinStandardHelpOptions = true, version = "dbgrep 0.0.1", description = "offers features to conveniently search thorugh a database")
public class CLIParser implements Callable<Integer> {

    @ArgGroup(multiplicity = "1", heading = "Connection Details")
    DBConnectionParser connectionProperties;

    @ArgGroup(exclusive = false, multiplicity = "1..*")
    List<Query> queryArguments;

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "display help message")
    private boolean help = false;

    /**
     * Only implemented for testing purposes
     *
     * @return connectionProperties parsed from the commandline.
     */
    public DBConnectionParser getConnectionProperties() {
        return connectionProperties;
    }

    /**
     * Only implemented for testing purposes
     *
     * @param connectionProperties parsed from the commandline.
     */
    public void setConnectionProperties(DBConnectionParser connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

    /**
     * Only implemented for testing purposes
     *
     * @return queryArguments parsed from the commandline.
     */
    public List<Query> getQueryArguments() {
        return queryArguments;
    }

    /**
     * Only implemented for testing purposes
     *
     * @param queryArguments parsed from the commandline.
     */
    public void setQueryArguments(List<Query> queryArguments) {
        this.queryArguments = queryArguments;
    }

    @Override
    public Integer call() throws Exception {
        ConnectionInfo connectionInfo;
        try {
            connectionInfo = connectionProperties.parse();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            return 2;
        } catch (MissingProfileException e) {
            System.err.println(e.getMessage());
            return e.getExitCode().getCode();
        }

        new Controller(connectionInfo, queryArguments).run();
        return 0;
    }

    public boolean isHelp() {
        return help;
    }
}
