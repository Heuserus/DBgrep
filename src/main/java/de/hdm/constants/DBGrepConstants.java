package de.hdm.constants;

public class DBGrepConstants {
    public enum ExitCode {
        OK(0, "search successfull"),
        MISSING_PROFILE(1, "Missing profile or Database connection properties."),
        INVALID_QUERY(2, "Provided database query is invalid."),
        UNKNOWN_DATABASE(3, "Could not determine the database type (JDBC or MongoDB)");

        // fields
        private final int code;
        private final String message;

        // constructor
        ExitCode(final int code, final String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

    public enum QueryType {
        SEARCH_TABLE_NAMES,
        SEARCH_COLUMN_NAMES,
        SEARCH_OBJECTS
    }

    public static final String QUERY_ARGUMENT = "\\p{L}+$";
    public static final String QUERY_REGEX_ARGUMENT = "[\\p{L}]*%[\\p{L}]*$";
    public static final String LOG_OP_EQUALS = "=\\d+|=\\p{L}+$+";
    public static final String LOG_OP_EQUALS_NOT = "!=\\d+|=\\p{L}+$+|!=\\d{1,2}\\.\\d{1,2}\\.\\d{4}|!=\\d{1,2}\\.\\d{1,2}\\.\\d{2}";
    public static final String LOG_OP_LT = "<\\d+$|<\\d{1,2}\\.\\d{1,2}\\.\\d{4}|<\\d{1,2}\\.\\d{1,2}\\.\\d{2}";
    public static final String LOG_OP_GT = ">\\d+$|>\\d{1,2}\\.\\d{1,2}\\.\\d{4}|>\\d{1,2}\\.\\d{1,2}\\.\\d{2}";
    public static final String LOG_OP_CONTAINS = ">\\d+";
}
