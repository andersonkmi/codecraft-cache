package org.codecraftlabs.cache.repository;

import org.codecraftlabs.cache.model.CacheEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.String.format;

@Repository("simpleCacheRepository")
class SimpleCacheRepository implements CacheRepository {
    private static final Logger logger = LoggerFactory.getLogger(SimpleCacheRepository.class);
    private final Map<String, String> cache = new ConcurrentHashMap<>();

    @Override
    public void upsert(@Nonnull CacheEntry cacheEntry) {
        String previousValue = cache.put(cacheEntry.getKey(), cacheEntry.getValue());
        if (previousValue != null) {
            logger.info(format("Cache entry updated - {key: '%s', previousValue: '%s', newValue = '%s'}",
                    cacheEntry.getKey(),
                    previousValue,
                    cacheEntry.getValue())
            );
            if (previousValue.equals(cacheEntry.getValue())) {
                logger.warn(format("Cache item '%s' updated with the same value as before", cacheEntry.getKey()));
            }
        } else {
            logger.info(format("Cache entry inserted - {key: '%s', newValue = '%s'}",
                    cacheEntry.getKey(),
                    cacheEntry.getValue())
            );
        }
    }

    @Override
    public boolean remove(@Nonnull String key) {
        String value = cache.remove(key);
        if (value == null) {
            logger.warn("Cache item was not in the cache: " + key);
            return false;
        }
        logger.info("Cache item has just been removed: " + key);
        return true;
    }

    @Override
    public void clear() {
        logger.info("Removing all cache entries");
        this.cache.clear();
    }

    @Override
    public long getCacheSize() {
        return this.cache.size();
    }

    @Override
    @Nonnull
    public Optional<CacheEntry> getItem(@Nonnull String key) {
        String value = this.cache.get(key);
        if (value == null) {
            logger.warn(format("Cache item '%s' not found in the cache.", key));
            return Optional.empty();
        } else {
            CacheEntry cacheEntry = new CacheEntry();
            cacheEntry.setKey(key);
            cacheEntry.setValue(value);
            return Optional.of(cacheEntry);
        }
    }
}
