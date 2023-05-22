package org.codecraftlabs.cache.repository;

import org.codecraftlabs.cache.model.CacheEntry;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface CacheRepository {
    void insert(@Nonnull CacheEntry cacheEntry);
    void update(@Nonnull CacheEntry cacheEntry);
    void remove(@Nonnull String key);
    void clear();
    long getCacheSize();
    Optional<CacheEntry> getItem(@Nonnull String key);
}
