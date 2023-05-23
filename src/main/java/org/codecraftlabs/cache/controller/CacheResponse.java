package org.codecraftlabs.cache.controller;

public class CacheResponse {
    private final String message;

    public CacheResponse(String message) {
        this.message = message;
    }

     public String getMessage() {
        return message;
     }
}
