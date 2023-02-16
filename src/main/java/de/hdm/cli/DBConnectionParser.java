package de.hdm.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import de.hdm.constants.DBGrepConstants.ExitCode;
import de.hdm.datacontainer.ConnectionInfo;
import de.hdm.exception.MissingProfileException;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class DBConnectionParser {
    @ArgGroup(exclusive = false, heading = "Connection Properties")
    ConnectionInfo connectionInfo;

    @Option(names = {"-p", " --profile"}, description = "connection path/profile")
    Optional<String> profile;


    public ConnectionInfo parse() throws IOException, MissingProfileException {
        if (connectionInfo != null) {
            return connectionInfo;
        }

        if (profile.isEmpty()) {
            throw new MissingProfileException(ExitCode.MISSING_PROFILE);
        }

        File file = new File(profile.get());
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

        return objectMapper.readValue(file, ConnectionInfo.class);
    }
}
