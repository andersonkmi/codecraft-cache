package org.codecraftlabs.sqs.util;

import javax.annotation.Nonnull;

enum ConfigEntry {
    SQS_URL("sqsUrl"),
    CACHE_NODES("cacheNodes");

    private final String configKey;

    ConfigEntry(String configKey) {
        this.configKey = configKey;
    }

    @Nonnull
    public String getConfigKey() {
        return configKey;
    }
}
