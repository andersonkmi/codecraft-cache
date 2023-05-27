package org.codecraftlabs.cache.service;

import org.codecraftlabs.cache.model.CacheEntry;
import org.codecraftlabs.cache.service.validator.CacheEntryValidator;
import org.codecraftlabs.cache.service.validator.InvalidCacheEntryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals("Null key", exception.getMessage());
    }

    @Test
    public void whenEmptyKeyShouldRaiseException() {
        CacheEntry cacheEntry = new CacheEntry();
        cacheEntry.setKey("");
        cacheEntry.setValue("Simple value");
        InvalidCacheEntryException exception = Assertions.assertThrows(InvalidCacheEntryException.class, () -> cacheEntryValidator.validate(cacheEntry));
        assertEquals("Empty key", exception.getMessage());
    }

    @Test
    public void whenNullValueShouldRaiseException() {
        CacheEntry cacheEntry = new CacheEntry();
        cacheEntry.setKey("key");
        InvalidCacheEntryException exception = Assertions.assertThrows(InvalidCacheEntryException.class, () -> cacheEntryValidator.validate(cacheEntry));
        assertEquals("Null value", exception.getMessage());
    }

    @Test
    public void whenEmptyValueShouldRaiseException() {
        CacheEntry cacheEntry = new CacheEntry();
        cacheEntry.setKey("key");
        cacheEntry.setValue("");
        InvalidCacheEntryException exception = Assertions.assertThrows(InvalidCacheEntryException.class, () -> cacheEntryValidator.validate(cacheEntry));
        assertEquals("Empty value", exception.getMessage());
    }

    @Test
    public void whenCacheEntryIsValidNoExceptionShouldThrow() {
        CacheEntry cacheEntry = new CacheEntry();
        cacheEntry.setKey("key");
        cacheEntry.setValue("{'name' : 'test'}");
        assertDoesNotThrow(() -> cacheEntryValidator.validate(cacheEntry));
    }
}
