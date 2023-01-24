package de.hdm.datacontainer;

import picocli.CommandLine.IParameterConsumer;
import picocli.CommandLine.Model.ArgSpec;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Model.OptionSpec;
import picocli.CommandLine.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class Query {
    // -c breite >10 <20 -c länge <5
    @Option(names = {"-c",
            "--column"}, required = false, description = "column to be searched, table must be specified [if used without table search in every column the name provided matches", parameterConsumer = QueryPreprocessor.class)
    private List<List<String>> columns = new ArrayList<>();

    @Option(names = {"-t", "--table"}, required = false, description = "specifies table to be searched", parameterConsumer = Query.QueryPreprocessor.class)
    private List<String> table = new ArrayList<>();

    @Option(names = {"-o", "--object"}, required = false, description = "object to be searched")
    private Optional<String> object;

    public List<List<List<String>>> parseQuery() {
        var queryAsList = new ArrayList<List<List<String>>>();
        queryAsList.add(List.of(table));
        queryAsList.add(columns);
        return queryAsList;
    }

    public static class QueryPreprocessor implements IParameterConsumer {
        @Override
        public void consumeParameters(Stack<String> args, ArgSpec argSpec, CommandSpec commandSpec) {
            if (args.isEmpty()) {
                return;
            }
            List<String> optionList = new ArrayList<>();
            for (Character c : commandSpec.posixOptionsMap().keySet()) {
                optionList.add("-" + c);
            }
            for (String currentOptionFlag : ((OptionSpec) argSpec).names()) {
                switch (currentOptionFlag) {
                    case "--column":
                        currentOptionFlag = currentOptionFlag.substring(0, 2);
                    case "-c":
                        consumeColumn(currentOptionFlag, argSpec.getValue(), optionList, args);
                        break;
                    case "--table":
                        currentOptionFlag = currentOptionFlag.substring(0, 2);
                    case "-t":
                        consumeTable(currentOptionFlag, argSpec.getValue(), optionList, args);
                        break;
                }
            }
        }

        private void consumeTable(String currentOptionFlag, List<String> table, List<String> optionList, Stack<String> args) {
            table.add(currentOptionFlag);
            while (!args.isEmpty() && !optionList.contains(args.peek())) {
                table.add(args.pop());
            }
        }

        private void consumeColumn(String currentOptionFlag, List<List<String>> columns, List<String> optionList, Stack<String> args) {
            List<String> columnParameters = new ArrayList<>();
            columnParameters.add(currentOptionFlag);

            while (!args.isEmpty() && !optionList.contains(args.peek())) {
                columnParameters.add(args.pop());
            }
            columns.add(columnParameters);
        }
    }
}