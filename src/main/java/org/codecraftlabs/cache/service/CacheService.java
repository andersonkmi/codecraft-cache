package org.codecraftlabs.cache.service;

import org.codecraftlabs.cache.model.CacheEntry;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Optional;

@Service
public class CacheService {
    public void upsert(@Nonnull CacheEntry cacheEntry) {
        // nothing implemented yet
    }

    public Optional<CacheEntry> retrieve(@Nonnull String key) {
        return Optional.empty();
    }

    public void remove(@Nonnull String key) {
        // Nothing implemented yet
    }

    public void clear() {
        // Nothing implemented yet
    }
}
