package org.codecraftlabs.sqs.util;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;

public class JobConfiguration {
    private static final String DELIMITER = ",";
    private final PropertiesFileReader propertiesFileReader;

    public JobConfiguration(@Nonnull PropertiesFileReader propertiesFileReader) {
        this.propertiesFileReader = propertiesFileReader;
    }

    @Nonnull
    public Optional<String> getQueueUrl() {
        return propertiesFileReader.getProperties(ConfigEntry.SQS_URL.getConfigKey());
    }

    @Nonnull
    public Optional<Set<String>> getCacheNodes() {
        Optional<String> cacheNodes = propertiesFileReader.getProperties(ConfigEntry.CACHE_NODES.getConfigKey());
        if (cacheNodes.isEmpty()) {
            return Optional.empty();
        }

        String nodes = cacheNodes.get();
        StringTokenizer stringTokenizer = new StringTokenizer(nodes, DELIMITER);
        Set<String> items = new HashSet<>();
        while (stringTokenizer.hasMoreTokens()) {
            String item = stringTokenizer.nextToken().strip();
            items.add(item);
        }
        return Optional.of(items);
    }
}
