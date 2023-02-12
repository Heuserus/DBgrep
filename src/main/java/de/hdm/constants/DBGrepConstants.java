package de.hdm.constants;

public class DBGrepConstants {
    public enum ExitCode {
        OK(0, "search successfull"),
        MISSING_PROFILE(1, "Missing profile or Database connection properties."),
        INVALID_QUERY(2, "Provided database query is invalid.");

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

        @Override
        public String toString() {
            return String.format("code: %, message: %", code, message);
        }
    }

    public enum QueryType {
        SEARCH_TABLE_NAMES,
        SEARCH_COLUMN_NAMES,
        SEARCH_OBJECTS
    }

    public enum OutputType {
        DATABASE_OBJECT,
        DATABASE_INFORMATION

    }

    public static final String LOG_OP_EQUALS = "(\\w*[ä|Ä|ö|Ö|ü|Ü]*\\w*)+=(\\w*[ä|Ä|ö|Ö|ü|Ü]*\\w*)+";
}
