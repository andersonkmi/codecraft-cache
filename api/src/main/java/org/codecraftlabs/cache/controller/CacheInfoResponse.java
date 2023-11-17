package org.codecraftlabs.cache.controller;

public class CacheInfoResponse extends CacheResponse {
    private final long size;

    public CacheInfoResponse(String message, long size) {
        super(message);
        this.size = size;
    }

    public long getSize() {
        return size;
    }
}
