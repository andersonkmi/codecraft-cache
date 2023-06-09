package org.codecraftlabs.cache.service;

import org.codecraftlabs.cache.model.CacheEntry;
import org.codecraftlabs.cache.repository.CacheRepository;
import org.codecraftlabs.cache.service.validator.CacheEntryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Optional;

@Service
class CacheServiceMkI implements CacheService {
    private final CacheEntryValidator cacheEntryValidator;

    @Qualifier("simpleCacheRepository")
    private final CacheRepository cacheRepository;

    @Autowired
    public CacheServiceMkI(@Nonnull CacheEntryValidator cacheEntryValidator, @Nonnull CacheRepository cacheRepository) {
        this.cacheEntryValidator = cacheEntryValidator;
        this.cacheRepository = cacheRepository;
    }

    /**
     * Updates an existing item in the cache
     * @param cacheEntry Cache item to be inserted
     * @throws org.codecraftlabs.cache.service.validator.InvalidCacheEntryException If the cache entry is missing the key or value fields
     */
    @Override
    public void upsert(@Nonnull CacheEntry cacheEntry) {
        this.cacheEntryValidator.validate(cacheEntry);
        this.cacheRepository.upsert(cacheEntry);
    }

    /**
     * Retrieves the cache item associated with the informed key.
     * @param key Key linked to the cache item
     * @return Cache entry if present, otherwise an empty item.
     */
    @Override
    @Nonnull
    public Optional<CacheEntry> retrieve(@Nonnull String key) {
        return this.cacheRepository.getItem(key);
    }

    /**
     * Removes an item from the cache linked to the specified key.
     * @param key Cache item key.
     */
    @Override
    public boolean remove(@Nonnull String key) {
        return this.cacheRepository.remove(key);
    }

    /**
     * Returns the current size cache.
     * @return Number of items inside the cache.
     */
    @Override
    public long getCacheSize() {
        return this.cacheRepository.getCacheSize();
    }
}
