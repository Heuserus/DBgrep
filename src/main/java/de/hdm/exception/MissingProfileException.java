package de.hdm.exception;

import de.hdm.constants.DBGrepConstants.ExitCode;

public class MissingProfileException extends DBGrepException {

    public MissingProfileException(ExitCode exitCode) {
        super(exitCode,
                "Neither profile nor database connection properties provided. Please provide either a profile with the corresponding filepath or the connection properties as arguments. Use 'dbgrep --help' for more information.");
    }

    public MissingProfileException(ExitCode exitCode, String message) {
        super(exitCode, message);
    }

}
