package de.hdm.exceptions;

import de.hdm.constants.DBGrepConstants.ExitCode;

public abstract class DBGrepException extends Exception{
    private ExitCode exitCode;
    private String message;

    public DBGrepException(ExitCode exitCode) {
        this.exitCode = exitCode;
        this.message = exitCode.getMessage();
    }

    public DBGrepException(ExitCode exitCode, String message) {
        this.exitCode = exitCode;
        this.message = message;
    }

    public ExitCode getExitCode() {
        return exitCode;
    }

    public String getMessage() {
        return message;
    }
}
