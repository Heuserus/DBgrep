package de.hdm.helper;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import de.hdm.DBGrepConstants.ExitCode;
import de.hdm.Exceptions.MissingProfileException;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Option;

public class DBConnectionProperties {
    @ArgGroup(exclusive = false, heading = "Connection Properties")
    ConnectionInfo connectionInfo;

    @Option(names = { "-p", " --profile" }, description = "connection path/profile")
    Optional<String> profile;


    public ConnectionInfo prepare()
            throws StreamReadException, DatabindException, IOException, MissingProfileException {
        if (connectionInfo != null) {
            return connectionInfo;
        }

        if (!profile.isPresent()) {
            throw new MissingProfileException(ExitCode.MISSING_PROFILE);
        }

        File file = new File(profile.get());
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

        Profile profile = objectMapper.readValue(file, Profile.class);
        return ConnectionInfo.fromProfile(profile);
    }

    public ConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }

    public Optional<String> getProfile() {
        return profile;
    }
}
