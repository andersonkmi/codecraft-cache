package org.codecraftlabs.cache.service;

import org.codecraftlabs.cache.model.CacheEntry;

import javax.annotation.Nonnull;

class CacheEntryValidator {
    public void validate(@Nonnull CacheEntry cacheEntry) {
        if (cacheEntry.getKey() == null) {
            throw new InvalidCacheEntryException("Null key");
        }

        if (cacheEntry.getKey().isEmpty()) {
            throw new InvalidCacheEntryException("Empty key");
        }

        if (cacheEntry.getValue() == null || cacheEntry.getValue().isEmpty()) {
            throw new InvalidCacheEntryException("Empty or null value");
        }
    }
}
