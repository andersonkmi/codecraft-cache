package org.codecraftlabs.cache.service.validator;

import org.codecraftlabs.cache.model.CacheEntry;

import javax.annotation.Nonnull;

class CacheEntryValueValidationPolicy implements CacheEntryValidationPolicy {
    @Override
    public void apply(@Nonnull CacheEntry cacheEntry) {
        if (cacheEntry.value() == null) {
            throw new InvalidCacheEntryException("Null value");
        }

        if (cacheEntry.value().isEmpty()) {
            throw new InvalidCacheEntryException("Empty value");
        }
    }
}
