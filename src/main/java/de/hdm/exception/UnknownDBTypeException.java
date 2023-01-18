package de.hdm.exception;

import de.hdm.constants.DBGrepConstants.ExitCode;

public class UnknownDBTypeException extends DBGrepException {

    public UnknownDBTypeException(String message) {
        super(ExitCode.UNKNOWN_DATABASE, message);
    }
    
}
