package org.codecraftlabs.cache.service.validator;

import org.codecraftlabs.cache.model.CacheEntry;

import javax.annotation.Nonnull;

interface CacheEntryValidationPolicy {
    void apply(@Nonnull CacheEntry cacheEntry);
}
