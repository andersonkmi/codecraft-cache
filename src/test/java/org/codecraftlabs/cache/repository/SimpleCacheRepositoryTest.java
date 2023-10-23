package org.codecraftlabs.cache.repository;

import org.codecraftlabs.cache.model.CacheEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimpleCacheRepositoryTest {
    private SimpleCacheRepository simpleCacheRepository;

    @BeforeEach
    void beforeEach() {
        this.simpleCacheRepository = new SimpleCacheRepository();
    }

    @Test
    public void testInsertSingleCacheEntrySuccessfully() {
        CacheEntry item = createCacheEntry("key001", "value001");
        this.simpleCacheRepository.upsert(item);
        assertEquals(1, simpleCacheRepository.getCacheSize());
        Optional<CacheEntry> returnedItem = simpleCacheRepository.getItem("key001");
        assertTrue(returnedItem.isPresent());
        assertEquals(item, returnedItem.get());
    }

    @Test
    public void testEmptyOptionalWhenKeyDoesNotExist() {
        Optional<CacheEntry> item = simpleCacheRepository.getItem("key001");
        assertTrue(item.isEmpty());
    }

    @Test
    public void testUpdateExistingCacheItemSuccess() {
        CacheEntry entry = createCacheEntry("key002", "value002");
        this.simpleCacheRepository.upsert(entry);
        assertEquals(1, simpleCacheRepository.getCacheSize());
        Optional<CacheEntry> returnedItem = simpleCacheRepository.getItem("key002");
        assertTrue(returnedItem.isPresent());
        assertEquals(entry, returnedItem.get());

        // Update an existing item
        CacheEntry updatedEntry = createCacheEntry("key002", "value002 - changed");
        this.simpleCacheRepository.upsert(updatedEntry);
        returnedItem = simpleCacheRepository.getItem("key002");
        assertTrue(returnedItem.isPresent());
        assertEquals(updatedEntry, returnedItem.get());
    }

    @Test
    public void testRemoveItemFromCache() {
        CacheEntry entry = createCacheEntry("key002", "value002");
        this.simpleCacheRepository.upsert(entry);
        assertEquals(1, simpleCacheRepository.getCacheSize());
        Optional<CacheEntry> returnedItem = simpleCacheRepository.getItem("key002");
        assertTrue(returnedItem.isPresent());
        assertEquals(entry, returnedItem.get());

        this.simpleCacheRepository.remove("key002");
        assertEquals(0, simpleCacheRepository.getCacheSize());
    }

    @Test
    public void clearCache() {
        CacheEntry item = createCacheEntry("key001", "value001");
        this.simpleCacheRepository.upsert(item);
        assertEquals(1, simpleCacheRepository.getCacheSize());
        CacheEntry item2 = createCacheEntry("key002", "value002");
        this.simpleCacheRepository.upsert(item2);
        assertEquals(2, simpleCacheRepository.getCacheSize());

        // Clear cache
        this.simpleCacheRepository.clear();
        assertEquals(0, simpleCacheRepository.getCacheSize());
    }

    @Nonnull
    private CacheEntry createCacheEntry(@Nonnull String key, @Nonnull String value) {
        return new CacheEntry(key, value, 0);
    }
}
