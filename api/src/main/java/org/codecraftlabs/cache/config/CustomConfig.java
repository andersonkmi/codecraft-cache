package org.codecraftlabs.cache.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class CustomConfig {
    private static final String SQS_URL = "cacheservice.sqs.url";
    private static final String SERVER_MODE = "cacheservice.servermode";
    private final String sqsUrl;
    private final String serverMode;

    @Autowired
    public CustomConfig(Environment environment) {
        sqsUrl = environment.getProperty(SQS_URL);
        serverMode = environment.getProperty(SERVER_MODE, ServerMode.STANDALONE.name());
    }

    public String getSqsUrl() {
        return sqsUrl;
    }

    public ServerMode getServerMode() {
        return ServerMode.valueOf(this.serverMode);
    }
}
