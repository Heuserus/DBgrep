package de.hdm.cli;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import de.hdm.constants.DBGrepConstants.ExitCode;
import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.exceptions.MissingProfileException;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Option;

public class DBConnectionParser {
    @ArgGroup(exclusive = false, heading = "Connection Properties")
    ConnectionInfo connectionInfo;

    @Option(names = { "-p", " --profile" }, description = "connection path/profile")
    Optional<String> profile;


    public ConnectionInfo parse()
            throws StreamReadException, DatabindException, IOException, MissingProfileException {
        if (connectionInfo != null) {
            return connectionInfo;
        }

        if (!profile.isPresent()) {
            throw new MissingProfileException(ExitCode.MISSING_PROFILE);
        }

        File file = new File(profile.get());
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

        ConnectionInfo profile = objectMapper.readValue(file, ConnectionInfo.class);
        return profile;
    }
    
    public ConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }

    public Optional<String> getProfile() {
        return profile;
    }
}
