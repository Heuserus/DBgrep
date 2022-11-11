package de.hdm;

public class DBGrepConstants {
    enum ExitCode {
        OK(0, "search successfull");

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

}
