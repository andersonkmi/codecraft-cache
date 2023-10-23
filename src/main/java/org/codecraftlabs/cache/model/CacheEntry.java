package org.codecraftlabs.cache.model;

import java.util.Objects;

import static java.util.Objects.hash;

public class CacheEntry {
    final private String key;
    final private String value;
    final private long versionId;

    public CacheEntry(String key, String value, long versionId) {
        this.key = key;
        this.value = value;
        this.versionId = versionId;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public long getVersionId() {
        return versionId;
    }

    @Override
    public int hashCode() {
        return hash(this.key, this.value, this.versionId);
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
        return Objects.equals(this.getKey(), instance.getKey()) &&
                Objects.equals(this.getVersionId(), instance.getVersionId()) &&
                Objects.equals(getValue(), instance.getValue());
    }

    @Override
    public String toString() {
        return "[key = '" + key + "', value = '" + value + "', versionId = '" + versionId + "']";
    }
}
