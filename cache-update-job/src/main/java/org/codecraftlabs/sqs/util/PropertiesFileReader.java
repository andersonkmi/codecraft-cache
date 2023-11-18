package org.codecraftlabs.sqs.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class PropertiesFileReader {
    private static final Logger logger = LogManager.getLogger(PropertiesFileReader.class);
    private final Properties properties = new Properties();

    public void loadProperties(@Nonnull String configurationFile) {
        try (InputStream input = new FileInputStream(configurationFile)) {
            properties.load(input);
        } catch (IOException exception) {
            logger.error("Failed to load configuration file", exception);
        }
    }

    @Nonnull
    public Optional<String> getProperties(@Nonnull String key) {
        return Optional.ofNullable(properties.getProperty(key));
    }
}
