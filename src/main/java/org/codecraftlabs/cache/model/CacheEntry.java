package org.codecraftlabs.cache.model;

import java.util.Objects;

public class CacheEntry {
    private String key;
    private String value;

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.key);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!this.getClass().equals(other.getClass())) {
            return false;
        }

        CacheEntry instance = (CacheEntry) other;
        return Objects.equals(this.getKey(), instance.getKey());
    }
}
