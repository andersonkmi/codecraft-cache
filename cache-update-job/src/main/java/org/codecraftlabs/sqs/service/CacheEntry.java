package org.codecraftlabs.sqs.service;

public record CacheEntry(String key, String value) {

    public String toJson() {
        return "{'key' = '" + key + "', 'value' = '" + value + "'}";
    }
}
