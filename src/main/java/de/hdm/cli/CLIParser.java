package de.hdm.cli;

import de.hdm.Controller;
import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.datacontainer.Query;
import de.hdm.exception.DBGrepException;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.List;
import java.util.concurrent.Callable;

@Command(name = "dbgrep", mixinStandardHelpOptions = true, version = "dbgrep 0.0.1", description = "\nOffers features to conveniently search through a database.")
public class CLIParser implements Callable<Integer> {

    @ArgGroup(multiplicity = "1", heading = """
        
        Connection Details:
        Either use '-p'/'--profile' or use the other parameters to specify how to reach the database.
        """)
    DBConnectionParser connectionProperties;

    @ArgGroup(exclusive = false, multiplicity = "1..*", heading = """
            
            Search:
            It is possible to search for table names, column names and the content of rows.
            
            To search for table names only specify '-t'/'--table'. For MongoDB Java Regular Expressions are supported.
            Example: ... -t "tableName"
            
            To search for column names it is possible but not mandatory to specify a table. The argument '-c'/'--column' takes only one parameter. For MongoDB Java regular expressions are supported.
            Example: ... (-t "tableName") -c "columnName"
            
            To search for the content of rows both specify a table and columns. The argument '-c'/'--column' takes two parameters, where the first is the column name and the second is the search query matched against the data.
            Example: ... -t "tableName" -c "columnName" "=value"
            
            """)
    List<Query> queryArguments;

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "Display this help message.")
    private boolean help = false;


    @Override
    public Integer call() throws Exception {
        ConnectionInfo connectionInfo;
        try {
            connectionInfo = connectionProperties.parse();
        } catch (Exception e) {
            if (e instanceof DBGrepException exception) {
                System.err.println("Failed to Parse Profile");
                return exception.getExitCode().getCode();
            }
            e.printStackTrace();
            return -1;
        }

        new Controller(connectionInfo, queryArguments).run();
        return 0;
    }

    public boolean isHelp() {
        return help;
    }
}
