package de.hdm.cli;

import de.hdm.Controller;
import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.datacontainer.Query;
import de.hdm.exception.DBGrepException;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name = "dbgrep", mixinStandardHelpOptions = true, version = "dbgrep 0.0.1", description = "offers features to conveniently search thorugh a database")
public class CLIParser implements Callable<Integer> {

  @ArgGroup(exclusive = true, multiplicity = "1", heading = "Connection Details")
  DBConnectionParser connectionProperties;

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

  
  @Override
  public Integer call() throws Exception {
    ConnectionInfo connectionInfo = null;
    try {
      connectionInfo = connectionProperties.parse();
    } catch (Exception e) {
      if (e instanceof DBGrepException) {
        DBGrepException exception = (DBGrepException) e;
        return exception.getExitCode().getCode();
      }
      return -1;
    }
    Controller controller = new Controller(connectionInfo, new Query());
    return 0;
  }

  public DBConnectionParser getConnectionProperties() {
    return connectionProperties;
  }

  public void setConnectionProperties(DBConnectionParser connectionProperties) {
    this.connectionProperties = connectionProperties;
  }

  public boolean isHelp() {
    return help;
  }

  public void setHelp(boolean help) {
    this.help = help;
  }

  public String getColumn() {
    return column;
  }

  public void setColumn(String column) {
    this.column = column;
  }

  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public String getTable() {
    return table;
  }

  public void setTable(String table) {
    this.table = table;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public boolean isRecursive() {
    return recursive;
  }

  public void setRecursive(boolean recursive) {
    this.recursive = recursive;
  }  
}
