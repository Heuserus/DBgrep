package de.hdm.datacontainer;

import de.hdm.constants.DBGrepConstants;
import de.hdm.constants.DBGrepConstants.QueryType;
import de.hdm.exception.InvalidQueryException;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import picocli.CommandLine;
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
    @Option(names = {"-t", "--table"}, description = "Specifies the table to be searched.")
    private String table;

    @Option(names = {"-c",
            "--column"}, description = """
            Columns to be searched. If one argument is given a column name search is executed. If two arguments are given a content search is executed. Supported operators are =, !=, <, >, and +
            The + operator matches for MongoDB a regex pattern.
            """,
            parameterConsumer = QueryPreprocessor.class)


    private MultiValuedMap<String, String> columns = new ArrayListValuedHashMap<>();

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

    /**
     * Determines what type of query shall be executed according to the provided arguments.
     *
     * @return {@link QueryType#SEARCH_TABLE_NAMES} if only the table flag was provided <br>
     * {@link QueryType#SEARCH_COLUMN_NAMES} if only a column flag was provided <br>
     * {@link QueryType#SEARCH_OBJECTS} if a table, column and additional conditions were provided
     */
    public QueryType getQueryType() {
        final boolean columnConditionsPresent = !columns.isEmpty() && !columns.values().stream().allMatch(""::equals);
        final boolean columnQueryPresent = !columns.isEmpty() && columns.values().stream().allMatch(""::equals);
        final boolean tablePresent = table != null;

        if (tablePresent && columnConditionsPresent) // TODO: Bug?: Wenn beides nicht present ist, wird trotzdem collumn names zurück gegeben
            return QueryType.SEARCH_OBJECTS;
        else if (!columnQueryPresent && tablePresent)
            return QueryType.SEARCH_TABLE_NAMES;
        else if (columnQueryPresent)
            return QueryType.SEARCH_COLUMN_NAMES;
        else // this is the case if columns.isEmpty() == true and table == null
            throw new InvalidQueryException(DBGrepConstants.ExitCode.INVALID_QUERY);
    }

    /**
     * Custom processor for table flags.
     */
    public static class QueryPreprocessor implements IParameterConsumer {
        /**
         * Consumes parameters from the commandline.
         *
         * @param args        the command line arguments
         * @param argSpec     the option or positional parameter for which to consume command line arguments
         * @param commandSpec the command that the option or positional parameter belongs to
         */
        @Override
        public void consumeParameters(Stack<String> args, ArgSpec argSpec, CommandSpec commandSpec) {
            if (args.isEmpty()) {
                return;
            }
            var configuredOptions = commandSpec.options()
                    .stream()
                    .flatMap(optionSpec -> Arrays.stream(optionSpec.names()))
                    .collect(Collectors.toList());

            try {
                consumeColumn(argSpec.getValue(), configuredOptions, args);
            } catch (IllegalArgumentException e) {
                throw new CommandLine.MissingParameterException(commandSpec.commandLine(), argSpec, e.getMessage());
            }
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
                throw new IllegalArgumentException("Please provide a column name for flag -c.");

            final String columnName = args.pop();
            final List<String> columnConditions = new ArrayList<>();

            while (!args.isEmpty() && !optionList.contains(args.peek()) && !args.peek().startsWith("-")) {
                if (!isValidArgument(args.peek(), optionList)) {
                    throw new IllegalArgumentException("Invalid condition '" + args.peek() + "'.");
                }
                columnConditions.add(args.pop());
            }
            columns.putAll(columnName, columnConditions.isEmpty() ? List.of("") : columnConditions);
        }

        /**
         * Determines whether the current argument is valid or not.
         *
         * @param argument            current argument on top of the argument stack that shall be validated
         * @param availableCLIOptions list of options available in the programm
         * @return {@code true} if valid, {@code false} if invalid
         */
        private boolean isValidArgument(final String argument, final List<String> availableCLIOptions) {
            return isOptionArgument(argument, availableCLIOptions) ||
                    isLogOpArgument(argument) ||
                    isRegexArgument(argument);
        }

        /**
         * Determines whether the current argument is a valid condition containing a logic operator or not.
         *
         * @param argument current argument on top of the argument stack that shall be validated
         * @return {@code true} if valid, {@code false} if invalid
         */
        private boolean isLogOpArgument(final String argument) {
            return argument.matches(DBGrepConstants.LOG_OP_EQUALS) ||
                    argument.matches(DBGrepConstants.LOG_OP_EQUALS_NOT) ||
                    argument.matches(DBGrepConstants.LOG_OP_LT) ||
                    argument.matches(DBGrepConstants.LOG_OP_GT);
        }

        /**
         * Determines whether the current argument is a valid option or not.
         *
         * @param argument current argument on top of the argument stack that shall be validated
         * @return {@code true} if valid, {@code false} if invalid
         */
        private boolean isOptionArgument(
                final String argument,
                final List<String> availableCLIOptions) {
            return !availableCLIOptions.contains(argument) &&
                    argument.matches(DBGrepConstants.QUERY_ARGUMENT);
        }

        /**
         * Determines whether the current argument is a valid regular expression or not.
         *
         * @param argument current argument on top of the argument stack that shall be validated
         * @return {@code true} if valid, {@code false} if invalid
         */
        private boolean isRegexArgument(final String argument) {
            return argument.matches(DBGrepConstants.QUERY_REGEX_ARGUMENT);
        }
    }
}