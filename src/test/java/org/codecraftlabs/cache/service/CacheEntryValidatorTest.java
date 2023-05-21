package org.codecraftlabs.cache.service;

import org.codecraftlabs.cache.model.CacheEntry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CacheEntryValidatorTest {
    private CacheEntryValidator cacheEntryValidator;

    @BeforeEach
    void beforeEach() {
        this.cacheEntryValidator = new CacheEntryValidator();
    }

    @Test
    public void whenNullKeyShouldRaiseException() {
        CacheEntry cacheEntry = new CacheEntry();
        cacheEntry.setValue("Simple value");
        InvalidCacheEntryException exception = Assertions.assertThrows(InvalidCacheEntryException.class, () -> cacheEntryValidator.validate(cacheEntry));
        Assertions.assertEquals("Null key", exception.getMessage());
    }

    @Test
    public void whenEmptyKeyShouldRaiseException() {
        CacheEntry cacheEntry = new CacheEntry();
        cacheEntry.setKey("");
        cacheEntry.setValue("Simple value");
        InvalidCacheEntryException exception = Assertions.assertThrows(InvalidCacheEntryException.class, () -> cacheEntryValidator.validate(cacheEntry));
        Assertions.assertEquals("Empty key", exception.getMessage());
    }
}
