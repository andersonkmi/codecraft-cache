package org.codecraftlabs.cache.repository;

import org.codecraftlabs.cache.model.CacheEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CacheRepositoryTest {
    private CacheRepository cacheRepository;

    @BeforeEach
    void beforeEach() {
        this.cacheRepository = new CacheRepository();
    }

    @Test
    public void testInsertSingleCacheEntrySuccessfully() {
        CacheEntry item = createCacheEntry("key001", "value001");
        this.cacheRepository.insert(item);
        assertEquals(1, cacheRepository.getCacheSize());
        Optional<CacheEntry> returnedItem = cacheRepository.getItem("key001");
        assertTrue(returnedItem.isPresent());
        assertEquals(item, returnedItem.get());
    }

    @Test
    public void testEmptyOptionalWhenKeyDoesNotExist() {
        Optional<CacheEntry> item = cacheRepository.getItem("key001");
        assertTrue(item.isEmpty());
    }

    @Test
    public void testInsertExistingItemShouldNotOverwrite() {
        CacheEntry item = createCacheEntry("key001", "value001");
        this.cacheRepository.insert(item);
        assertEquals(1, cacheRepository.getCacheSize());
        CacheEntry item2 = createCacheEntry("key001", "value002");
        this.cacheRepository.insert(item2);
        assertEquals(1, cacheRepository.getCacheSize());
        Optional<CacheEntry> returnedItem = cacheRepository.getItem("key001");
        assertTrue(returnedItem.isPresent());
        assertEquals(item, returnedItem.get());
    }

    @Test
    public void testUpdateExistingCacheItemSuccess() {
        CacheEntry entry = createCacheEntry("key002", "value002");
        this.cacheRepository.insert(entry);
        assertEquals(1, cacheRepository.getCacheSize());
        Optional<CacheEntry> returnedItem = cacheRepository.getItem("key002");
        assertTrue(returnedItem.isPresent());
        assertEquals(entry, returnedItem.get());

        // Update an existing item
        entry.setValue("value002 - changed");
        this.cacheRepository.update(entry);
        returnedItem = cacheRepository.getItem("key002");
        assertTrue(returnedItem.isPresent());
        assertEquals(entry, returnedItem.get());
    }

    @Test
    public void whenUpdatingNonExistingItemNothingHappens() {
        CacheEntry entry = createCacheEntry("key002", "value002");
        this.cacheRepository.update(entry);
        assertEquals(0, cacheRepository.getCacheSize());
        Optional<CacheEntry> returnedItem = cacheRepository.getItem("key002");
        assertTrue(returnedItem.isEmpty());
    }

    @Nonnull
    private CacheEntry createCacheEntry(@Nonnull String key, @Nonnull String value) {
        CacheEntry item = new CacheEntry();
        item.setKey(key);
        item.setValue(value);
        return item;
    }
}
