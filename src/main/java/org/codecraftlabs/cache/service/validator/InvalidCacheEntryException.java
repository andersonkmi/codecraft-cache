package org.codecraftlabs.cache.service.validator;

public class InvalidCacheEntryException extends RuntimeException {
    public InvalidCacheEntryException(String message) {
        super(message);
    }
}
