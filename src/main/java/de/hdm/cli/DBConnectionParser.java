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
    @ArgGroup(exclusive = false, heading = "Properties specifying the database connection. Either database properties must be provided as arguments or via profile path.")
    private ConnectionInfo connectionInfo;

    @Option(names = {"-p", " --profile"}, description = "Path to profile containing properties specifying the database connection. Either database properties must be provided as arguments or via profile path.")
    private Optional<String> profile;

    /**
     * For testing purposes only.
     *
     * @param connectionInfo parsed from the commandline.
     */
    public void setConnectionInfo(ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    /**
     * For testing purposes only.
     *
     * @param profile profile from the commandline.
     */
    public void setProfile(Optional<String> profile) {
        this.profile = profile;
    }

    /**
     * Handles the provided database properties provided by the user. If properties are provided as arguments returns a valid {@link ConnectionInfo} object or tries to create an {@link ConnectionInfo} object from a profile loaded from the provided profile path.
     *
     * @return a valid {@link ConnectionInfo} object specifying the database connection properties.
     * @throws IOException             if underlying input contains invalid content
     * @throws MissingProfileException if neither connection properties nor a profile path was provided.
     */
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
