package org.codecraftlabs.sqs.util;

import java.util.Map;

public class AppArguments {
    public static final String CONFIGURATION_FILE = "configurationFile";

    private final Map<String, String> arguments;

    public AppArguments(Map<String, String> arguments) {
        this.arguments = arguments;
    }

    public String option(String key) {
        return arguments.getOrDefault(key, "");
    }
}
