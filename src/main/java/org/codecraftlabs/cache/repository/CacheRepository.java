package org.codecraftlabs.cache.repository;

import org.codecraftlabs.cache.model.CacheEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CacheRepository {
    private static final Logger logger = LoggerFactory.getLogger(CacheRepository.class);

    private final Map<String, String> cache = new ConcurrentHashMap<>();

    public void insert(@Nonnull CacheEntry cacheEntry) {
        logger.info("Inserting new cache item: " + cacheEntry);
        this.cache.put(cacheEntry.getKey(), cacheEntry.getValue());
    }

    public void update(@Nonnull CacheEntry cacheEntry) {
        if (! cache.containsKey(cacheEntry.getKey())) {
            logger.warn("Cache item not found: " + cacheEntry);
            return;
        }
        logger.info("Updating cache item: " + cacheEntry);
        cache.put(cacheEntry.getKey(), cacheEntry.getValue());
    }

    public void remove(@Nonnull String key) {
        String value = cache.remove(key);
        if (value == null) {
            logger.warn("Cache item was not in the cache: " + key);
        } else {
            logger.info("Cache item has just been removed: " + key);
        }
    }

    public void clear() {
        logger.info("Removing all cache entries");
        this.cache.clear();
    }

    public long getCacheSize() {
        return this.cache.size();
    }

    public Optional<CacheEntry> getItem(@Nonnull String key) {
        String value = this.cache.get(key);
        if (value == null) {
            return Optional.empty();
        } else {
            CacheEntry cacheEntry = new CacheEntry();
            cacheEntry.setKey(key);
            cacheEntry.setValue(value);
            return Optional.of(cacheEntry);
        }
    }
}
