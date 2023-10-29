package org.codecraftlabs.cache.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class CustomConfig {
    private static final String SQS_URL = "cacheservice.sqs.url";
    private final String sqsUrl;

    @Autowired
    public CustomConfig(Environment environment) {
        sqsUrl = environment.getProperty(SQS_URL);
    }

    public String getSqsUrl() {
        return sqsUrl;
    }
}
