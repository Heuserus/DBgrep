package de.hdm.constants;

public class DBGrepConstants {
    public enum ExitCode {
        OK(0, "search successfull"),
        MISSING_PROFILE(2, "Missing profile or Database connection properties."),
        UNKNOWN_DATABASE(2, "Could not determine the database type (JDBC or MongoDB)");

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
    public static final String QUERY_REGEX_ARGUMENT = "=[\\p{L}]*%[\\p{L}]*$";
    public static final String LOG_OP_EQUALS = "^=[\\p{L}\\d.\\-_,!?:() ]+$";
    public static final String LOG_OP_EQUALS_NOT = "^!=[\\p{L}\\d.\\-_,!?:() ]+$";
    public static final String LOG_OP_LT = "^<[\\p{L}\\d.\\-_,!?:() ]+$";
    public static final String LOG_OP_GT = "^>[\\p{L}\\d.\\-_,!?:() ]+$";
}
