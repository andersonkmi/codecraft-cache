package org.codecraftlabs.cache.service;

public class InvalidCacheEntryException extends RuntimeException {
    public InvalidCacheEntryException(String message) {
        super(message);
    }
}
