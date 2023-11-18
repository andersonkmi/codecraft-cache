package org.codecraftlabs.sqs.util;

import java.util.Map;

public class AppArguments {
    public static final String SQS_URL_OPTION = "sqsUrl";

    private final Map<String, String> arguments;

    public AppArguments(Map<String, String> arguments) {
        this.arguments = arguments;
    }

    public String option(String key) {
        return arguments.getOrDefault(key, "");
    }
}
