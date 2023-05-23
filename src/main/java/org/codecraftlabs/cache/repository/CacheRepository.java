package org.codecraftlabs.cache.repository;

import org.codecraftlabs.cache.model.CacheEntry;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface CacheRepository {
    boolean insert(@Nonnull CacheEntry cacheEntry);
    boolean update(@Nonnull CacheEntry cacheEntry);
    boolean remove(@Nonnull String key);
    void clear();
    long getCacheSize();
    Optional<CacheEntry> getItem(@Nonnull String key);
}
