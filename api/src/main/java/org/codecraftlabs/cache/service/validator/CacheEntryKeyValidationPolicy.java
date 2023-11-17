package org.codecraftlabs.cache.service.validator;

import org.codecraftlabs.cache.model.CacheEntry;

import javax.annotation.Nonnull;

class CacheEntryKeyValidationPolicy implements CacheEntryValidationPolicy {
    @Override
    public void apply(@Nonnull CacheEntry cacheEntry) {
        if (cacheEntry.key() == null) {
            throw new InvalidCacheEntryException("Null key");
        }

        if (cacheEntry.key().isEmpty()) {
            throw new InvalidCacheEntryException("Empty key");
        }
    }
}
