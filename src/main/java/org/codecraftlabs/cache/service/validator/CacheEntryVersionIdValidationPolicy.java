package org.codecraftlabs.cache.service.validator;

import org.codecraftlabs.cache.model.CacheEntry;

import javax.annotation.Nonnull;

class CacheEntryVersionIdValidationPolicy implements CacheEntryValidationPolicy {
    @Override
    public void apply(@Nonnull CacheEntry cacheEntry) {
        long versionId = cacheEntry.getVersionId();
        if (versionId <= 0) {
            throw new InvalidCacheEntryException("Version id must higher than zero");
        }
    }
}
