package org.codecraftlabs.cache.service;

import org.codecraftlabs.cache.model.CacheEntry;
import org.codecraftlabs.cache.service.validator.CacheEntryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Optional;

@Service
public class CacheService {
    private final CacheEntryValidator cacheEntryValidator;

    @Autowired
    public CacheService(@Nonnull CacheEntryValidator cacheEntryValidator) {
        this.cacheEntryValidator = cacheEntryValidator;
    }

    public void upsert(@Nonnull CacheEntry cacheEntry) {
        this.cacheEntryValidator.validate(cacheEntry);
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
