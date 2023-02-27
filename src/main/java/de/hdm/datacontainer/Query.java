package de.hdm.datacontainer;

import de.hdm.constants.DBGrepConstants;
import de.hdm.constants.DBGrepConstants.QueryType;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import picocli.CommandLine.IParameterConsumer;
import picocli.CommandLine.Model.ArgSpec;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Query {
    @Option(names = {"-c",
            "--column"}, description = "column to be searched, table must be specified [if used without table search in every column the name provided matches]",
            parameterConsumer = QueryPreprocessor.class)
    private MultiValuedMap<String, String> columns = new ArrayListValuedHashMap<>();
    @Option(names = {"-t", "--table"}, description = "Specifies table to be searched. If no column is provided will return all table names in the database matching the provided name. If an additional column is provided with conditions database objects matching the criteria will be returned.")
    private String table;

    public MultiValuedMap<String, String> getColumns() {
        return columns;
    }

    public void setColumns(MultiValuedMap<String, String> columns) {
        this.columns = columns;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public QueryType getQueryType() {
        final boolean columnConditionsPresent = !columns.isEmpty() && !columns.values().stream().allMatch(""::equals);
        final boolean columnQueryPresent = !columns.isEmpty() && columns.values().stream().allMatch(""::equals);
        final boolean tablePresent = table != null;

        if (tablePresent && columnConditionsPresent) // TODO: Bug?: Wenn beides nicht present ist, wird trotzdem collumn names zurück gegeben
            return QueryType.SEARCH_OBJECTS;
        else if (!columnQueryPresent && tablePresent)
            return QueryType.SEARCH_TABLE_NAMES;
        else if (tablePresent)
            return QueryType.SEARCH_COLUMN_NAMES;
        else // this is the case if columns.isEmpty() == true and table == null
            throw new RuntimeException("Something is wrong with the query"); // Todo: Throw correct exception
    }

    public static class QueryPreprocessor implements IParameterConsumer {
        @Override
        public void consumeParameters(Stack<String> args, ArgSpec argSpec, CommandSpec commandSpec) {
            if (args.isEmpty()) {
                return;
            }
            var configuredOptions = commandSpec.options()
                    .stream()
                    .flatMap(optionSpec -> Arrays.stream(optionSpec.names()))
                    .collect(Collectors.toList());

            consumeColumn(argSpec.getValue(), configuredOptions, args);
        }

        /**
         * Consumes columns, their respective names and its conditions of the passed application
         * arguments by adding them to the {@link Query#columns columns} map of the current
         * {@link Query query object}. If there are no conditions provided regarding the current
         * processed column option, an empty map will be added as condition instead.
         *
         * @param columns    map of the current query object
         * @param optionList list of options configured for this application
         * @param args       arguments passed to this app
         */
        private void consumeColumn(
                MultiValuedMap<String, String> columns,
                List<String> optionList,
                Stack<String> args) {
            if (args.isEmpty() || optionList.contains(args.peek()))
                throw new IllegalArgumentException();  //TODO: throw proper exception

            final String columnName = args.pop();
            final List<String> columnConditions = new ArrayList<>();

            while (!args.isEmpty() && !optionList.contains(args.peek())) {
                if (!isValidArgument(args.peek(), optionList)) {
                    throw new IllegalArgumentException(); //TODO: throw proper exception
                }
                columnConditions.add(args.pop());
            }
            columns.putAll(columnName, columnConditions.isEmpty() ? List.of("") : columnConditions);
        }

        private boolean isValidArgument(final String argument, final List<String> availableCLIOptions) {
            return isOptionArgument(argument, availableCLIOptions) ||
                    isLogOpArgument(argument) ||
                    isRegexArgument(argument);
        }

        private boolean isLogOpArgument(final String argument) {
            return argument.matches(DBGrepConstants.LOG_OP_EQUALS) ||
                    argument.matches(DBGrepConstants.LOG_OP_EQUALS_NOT) ||
                    argument.matches(DBGrepConstants.LOG_OP_LT) ||
                    argument.matches(DBGrepConstants.LOG_OP_GT);
        }

        private boolean isOptionArgument(
                final String argument,
                final List<String> availableCLIOptions) {
            return !availableCLIOptions.contains(argument) &&
                    argument.matches(DBGrepConstants.QUERY_ARGUMENT);
        }

        private boolean isRegexArgument(final String argument) {
            return argument.matches(DBGrepConstants.QUERY_REGEX_ARGUMENT);
        }
    }
}