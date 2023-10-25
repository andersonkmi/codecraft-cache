package org.codecraftlabs.cache.model;

import javax.annotation.Nonnull;

public class CacheItemOperation {
    private final Operation operation;
    private CacheEntry cacheEntry;

    public CacheItemOperation(@Nonnull Operation operation) {
        this.operation = operation;
    }

    public CacheItemOperation(@Nonnull Operation operation, @Nonnull CacheEntry cacheEntry) {
        this(operation);
        this.cacheEntry = cacheEntry;
    }

    public Operation getOperation() {
        return operation;
    }

    public CacheEntry getCacheEntry() {
        return cacheEntry;
    }
}
