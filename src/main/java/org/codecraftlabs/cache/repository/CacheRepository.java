package org.codecraftlabs.cache.repository;

import org.codecraftlabs.cache.model.CacheEntry;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface CacheRepository {
    /**
     * Inserts or updates an entry in the cache
     * @param cacheEntry Cache item to be inserted or updated.
     */
    Optional<CacheEntry> upsert(@Nonnull CacheEntry cacheEntry);

    void insert(@Nonnull CacheEntry cacheEntry);

    /**
     * Removes an entry from the cache
     * @param key Key of the cache item
     * @return true if the removal was performed; false otherwise.
     */
    boolean remove(@Nonnull String key);

    /**
     * Removes all entries from the cache.
     */
    void clear();

    /**
     * Returns the current size of the cache in terms of number of items.
     * @return Number of items in the cache
     */
    long getCacheSize();

    /**
     * Retrieves the cache item based on its key.
     * @param key Key linked to the cache item.
     * @return Optional instance containing the cache entry.
     */
    Optional<CacheEntry> getItem(@Nonnull String key);
}
