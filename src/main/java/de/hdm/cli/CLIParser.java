package de.hdm.cli;

import de.hdm.Controller;
import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.datacontainer.Query;
import de.hdm.exception.DBGrepException;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;


@Command(name = "dbgrep", mixinStandardHelpOptions = true, version = "dbgrep 0.0.1", description = "offers features to conveniently search thorugh a database")
public class CLIParser implements Callable<Integer> {

  @ArgGroup(exclusive = true, multiplicity = "1", heading = "Connection Details")
  DBConnectionParser connectionProperties;

  @ArgGroup(exclusive = false, multiplicity = "1..*")
  List<Query> queryArguments;

  @Option(names = { "-h", "--help" }, usageHelp = true, description = "display help message")
  private boolean help = false;


  @Option(names = { "-r", "--recursive" }, description = "follow foreign keys")
  private boolean recursive;

  
  @Override
  public Integer call() throws Exception {
    ConnectionInfo connectionInfo;
    try {
      connectionInfo = connectionProperties.parse();
    } catch (Exception e) {
      if (e instanceof DBGrepException) {
        DBGrepException exception = (DBGrepException) e;
        return exception.getExitCode().getCode();
      }
      return -1;
    }
    var args = new ArrayList<List<List<String>>>(); 
    for (Query argument : queryArguments) {
      args.add(argument.parseQuery());
    }
    queryArguments.get(0).parseQuery();
    System.out.println(args);
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

  public boolean isRecursive() {
    return recursive;
  }

  public void setRecursive(boolean recursive) {
    this.recursive = recursive;
  }  
}
