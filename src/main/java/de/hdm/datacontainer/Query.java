package de.hdm.datacontainer;

import picocli.CommandLine.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Query {

    @Option(names = { "-c",
            "-column" }, required = false, description = "column to be searched, table must be specified [if used without table search in every column the name provided matches")
    private List<String> columns = new ArrayList<String>();

    @Option(names = { "-t", "--table" }, required = false, description = "specifies table to be searched")
    private Optional<String> table;

    public boolean parseAndValidate() {
        // TODO: implement validation of passed arguments
        return false;
    }

    public List<List<String>> parseQuery() {
        var commands = new ArrayList<List<String>>();
        table.ifPresent((table) -> commands.add(List.of("-t", table)));
        


        for (String columnName : columns) {
            commands.add(List.of("-c", columnName));
        }
        return commands;
    }

    public List<String> getColumns() {
        return columns;
    }

    public Optional<String> getTable() {
        return table;
    }
}