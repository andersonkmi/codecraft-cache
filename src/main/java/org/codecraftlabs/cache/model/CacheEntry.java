package org.codecraftlabs.cache.model;

public record CacheEntry(String key, String value) {
    public String toJson() {
        return "{'key' = '" + key + "', 'value' = '" + value + "'}";
    }
}
