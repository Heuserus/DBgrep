package de.hdm.exception;

import de.hdm.constants.DBGrepConstants.ExitCode;

public class UnknownCommandException extends DBGrepException {

    public UnknownCommandException(ExitCode exitCode) {
        super(exitCode, "The provided command is unknown. Please use --help");
    }
}
