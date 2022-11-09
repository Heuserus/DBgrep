package de.hdm;

import de.hdm.helper.ConnectionInfo;
import de.hdm.helper.Request;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name = "dbgrep", mixinStandardHelpOptions = true, version = "dbgrep 0.0.1", description = "offers features to conveniently search thorugh a database")
public class CLIParser implements Callable<Integer> {

  @Option(names = { "-h", "--help" }, usageHelp = true, description = "display help message")
  private boolean help = false;

@Option(names={"-p"," --profile"},description = "connection path/profile")
private String profile;

@Option(names={"-c", "-column"},description = "column to be searched, table must be specified [if used without table search in every column the name provided matches")
private String column;

@Option(names={"-C", "--column-name"},description = "search for all column names (keys) of the specified name")
private String columnName;

@Option(names={"-t", "--table"},description = "specifies table to be searched")
private String table;

@Option(names={"-T", "--table-name"},description = "specifies a table name to be searched")
private String tableName;

@Option(names={"-r", "--recursive"},description = "follow foreign keys")
private boolean recursive;

  
  Controller controller;

  void parse(String[] args) {

    if (args[0] == "--help") {
      controller.help(args[1]);
    } else {
    }

    // send string directly to output
    // output handles specific help request

    ConnectionInfo connectionInfo = new ConnectionInfo();
    Request request = new Request();
    Controller controller = new Controller();
    controller.run(connectionInfo, request);

  }

  @Override
  public Integer call() throws Exception {
    if (help) {
      System.out.println("help wanted");
      return 1;
      // controller.help(args[1]);
    } else {
      System.out.println("no help wanted");
      return 0;
    }
  }
}
