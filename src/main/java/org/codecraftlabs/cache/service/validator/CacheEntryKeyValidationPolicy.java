package org.codecraftlabs.cache.service.validator;

import org.codecraftlabs.cache.model.CacheEntry;

import javax.annotation.Nonnull;

class CacheEntryKeyValidationPolicy implements CacheEntryValidationPolicy {
    @Override
    public void apply(@Nonnull CacheEntry cacheEntry) {
        if (cacheEntry.getKey() == null) {
            throw new InvalidCacheEntryException("Null key");
        }

        if (cacheEntry.getKey().isEmpty()) {
            throw new InvalidCacheEntryException("Empty key");
        }
    }
}
