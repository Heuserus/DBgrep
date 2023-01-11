package de.hdm.datacontainer;

import picocli.CommandLine.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Query {

    @Option(names = { "-c",
            "-column" }, required = false, description = "column to be searched, table must be specified [if used without table search in every column the name provided matches")
    private Optional<String> column;

    @Option(names = { "-t", "--table" }, required = false, description = "specifies table to be searched")
    private Optional<String> table;

    @Option(names = { "-o", "--object" }, required = false, description = "object to be searched")
    private Optional<String> object;

    public boolean parseAndValidate() {
        // TODO: implement validation of passed arguments
        return false;
    }

    public List<List<String>> parseQuery() {
        var commands = new ArrayList<List<String>>();
        table.ifPresent((table) -> commands.add(List.of("-t", table)));
        column.ifPresent((column) -> commands.add(List.of("-c", column)));
        object.ifPresent((object) -> commands.add(List.of("-o", object)));

        return commands;
    }
}