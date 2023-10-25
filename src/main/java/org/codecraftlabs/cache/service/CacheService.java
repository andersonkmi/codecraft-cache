package org.codecraftlabs.cache.service;

import org.codecraftlabs.cache.model.CacheEntry;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface CacheService {
    /**
     * Updates an existing item in the cache
     * @param cacheEntry Cache item to be inserted
     * @throws org.codecraftlabs.cache.service.validator.InvalidCacheEntryException If the cache entry is missing the key or value fields
     */
    void upsert(@Nonnull CacheEntry cacheEntry, boolean skipSynchronization);

    /**
     * Retrieves the cache item associated with the informed key.
     * @param key Key linked to the cache item
     * @return Cache entry if present, otherwise an empty item.
     */
    @Nonnull
    Optional<CacheEntry> retrieve(@Nonnull String key);
    /**
     * Removes an item from the cache linked to the specified key.
     * @param key Cache item key.
     */
    boolean remove(@Nonnull String key, boolean skipSynchronization);

    /**
     * Returns the current size cache.
     * @return Number of items inside the cache.
     */
    long getCacheSize();
}
