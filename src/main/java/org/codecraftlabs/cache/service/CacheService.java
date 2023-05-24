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
public class CacheService {
    private final CacheEntryValidator cacheEntryValidator;

    @Qualifier("simpleCacheRepository")
    private final CacheRepository cacheRepository;

    @Autowired
    public CacheService(@Nonnull CacheEntryValidator cacheEntryValidator, @Nonnull CacheRepository cacheRepository) {
        this.cacheEntryValidator = cacheEntryValidator;
        this.cacheRepository = cacheRepository;
    }

    /**
     * Inserts a new item into the cache
     * @param cacheEntry Cache item to be inserted
     * @throws org.codecraftlabs.cache.service.validator.InvalidCacheEntryException If the cache entry does not have
     * either key or value configured.
     * @throws OperationNotPerformedException If the cache item is already present in the cache
     */
    public void insert(@Nonnull CacheEntry cacheEntry) {
        this.cacheEntryValidator.validate(cacheEntry);
        if (!this.cacheRepository.insert(cacheEntry)) {
            throw new OperationNotPerformedException("Cache item was not inserted. Another item is already linked to the key.");
        }
    }

    /**
     * Updates an existing item in the cache
     * @param cacheEntry Cache item to be inserted
     * @throws org.codecraftlabs.cache.service.validator.InvalidCacheEntryException If the cache entry does not have
     * either key or value configured.
     */
    public void update(@Nonnull CacheEntry cacheEntry) {
        this.cacheEntryValidator.validate(cacheEntry);
        if(!this.cacheRepository.update(cacheEntry)) {
            throw new OperationNotPerformedException("Cache item was not updated.");
        }
    }

    /**
     * Retrieves the cache item associated with the informed key.
     * @param key Key linked to the cache item
     * @return Cache entry if present, otherwise an empty item.
     */
    public Optional<CacheEntry> retrieve(@Nonnull String key) {
        return this.cacheRepository.getItem(key);
    }

    /**
     * Removes an item from the cache linked to the specified key.
     * @param key Cache item key.
     */
    public void remove(@Nonnull String key) {
        this.cacheRepository.remove(key);
    }

    /**
     * Removes all items from the cache.
     */
    public void clear() {
        this.cacheRepository.clear();
    }
}
