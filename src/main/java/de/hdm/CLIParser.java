package de.hdm;

import de.hdm.Exceptions.DBGrepException;
import de.hdm.helper.DBConnectionProperties;
import de.hdm.helper.ConnectionInfo;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name = "dbgrep", mixinStandardHelpOptions = true, version = "dbgrep 0.0.1", description = "offers features to conveniently search thorugh a database")
public class CLIParser implements Callable<Integer> {

  @ArgGroup(exclusive = true, multiplicity = "1", heading = "Connection Details")
  DBConnectionProperties connectionProperties;

  @Option(names = { "-h", "--help" }, usageHelp = true, description = "display help message")
  private boolean help = false;

  @Option(names = { "-c",
      "-column" }, description = "column to be searched, table must be specified [if used without table search in every column the name provided matches")
  private String column;

  @Option(names = { "-C", "--column-name" }, description = "search for all column names (keys) of the specified name")
  private String columnName;

  @Option(names = { "-t", "--table" }, description = "specifies table to be searched")
  private String table;

  @Option(names = { "-T", "--table-name" }, description = "specifies a table name to be searched")
  private String tableName;

  @Option(names = { "-r", "--recursive" }, description = "follow foreign keys")
  private boolean recursive;

  Controller controller = new Controller();

  @Override
  public Integer call() throws Exception {
    ConnectionInfo connectionInfo = null;
    try {
      connectionInfo = connectionProperties.prepare();
    } catch (Exception e) {
      if (e instanceof DBGrepException) {
        DBGrepException exception = (DBGrepException) e;
        return exception.getExitCode().getCode();
      }
      return -1;
    }
    controller.setConnectionInfo(connectionInfo);
    return 0;
  }
}
