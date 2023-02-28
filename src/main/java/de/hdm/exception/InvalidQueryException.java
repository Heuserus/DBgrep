package de.hdm.exception;

import de.hdm.constants.DBGrepConstants.ExitCode;

public class InvalidQueryException extends DBGrepException {

    public InvalidQueryException(ExitCode exitCode) {
        super(exitCode, "The Query provided is invalid. Please use '--help'.");
    }
}
