package org.codecraftlabs.cache.service;

import org.codecraftlabs.cache.model.CacheEntry;
import org.codecraftlabs.cache.repository.CacheRepository;
import org.codecraftlabs.cache.service.validator.CacheEntryValidator;
import org.codecraftlabs.cache.model.CacheItemOperation;
import org.codecraftlabs.cache.util.CacheItemSynchronizer;
import org.codecraftlabs.cache.model.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Optional;

@Service
class CacheServiceMkI implements CacheService {
    private final CacheEntryValidator cacheEntryValidator;

    @Qualifier("cacheItemMessageQueueSynchronizer")
    private final CacheItemSynchronizer cacheItemSynchronizer;

    @Qualifier("simpleCacheRepository")
    private final CacheRepository cacheRepository;

    @Autowired
    public CacheServiceMkI(@Nonnull CacheEntryValidator cacheEntryValidator,
                           @Nonnull CacheRepository cacheRepository,
                           @Nonnull CacheItemSynchronizer cacheItemSynchronizer) {
        this.cacheEntryValidator = cacheEntryValidator;
        this.cacheRepository = cacheRepository;
        this.cacheItemSynchronizer = cacheItemSynchronizer;
    }

    /**
     * Updates an existing item in the cache
     * @param cacheEntry Cache item to be inserted
     * @throws org.codecraftlabs.cache.service.validator.InvalidCacheEntryException If the cache entry is missing the key or value fields
     */
    @Override
    public void upsert(@Nonnull CacheEntry cacheEntry, boolean skipSynchronization) {
        this.cacheEntryValidator.validate(cacheEntry);
        Optional<CacheEntry> previousValue = this.cacheRepository.upsert(cacheEntry);
        if (!skipSynchronization) {
            Operation operation;
            if (previousValue.isEmpty()) {
                operation = Operation.INSERT;
            } else {
                operation = Operation.UPDATE;
            }
            CacheItemOperation cacheItemOperation = new CacheItemOperation(operation, cacheEntry);
            this.cacheItemSynchronizer.submitCacheOperation(cacheItemOperation);
        }
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
    public boolean remove(@Nonnull String key, boolean skipSynchronization) {
        boolean result = this.cacheRepository.remove(key);
        if (result && !skipSynchronization) {
            CacheEntry cacheEntry = new CacheEntry(key, "");
            CacheItemOperation cacheItemOperation = new CacheItemOperation(Operation.DELETE, cacheEntry);
            this.cacheItemSynchronizer.submitCacheOperation(cacheItemOperation);
        }
        return result;
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
