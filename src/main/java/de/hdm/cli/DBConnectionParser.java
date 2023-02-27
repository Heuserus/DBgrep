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
    private ConnectionInfo connectionInfo;

    @Option(names = {"-p", " --profile"}, description = "connection path/profile")
    private Optional<String> profile;

    /**
     * For testing purposes only.
     *
     * @return parsed from the commandline.
     */
    public ConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }

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
     * @return profile parsed from the commandline.
     */
    public Optional<String> getProfile() {
        return profile;
    }

    /**
     * For testing purposes only.
     *
     * @param profile profile from the commandline.
     */
    public void setProfile(Optional<String> profile) {
        this.profile = profile;
    }

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
