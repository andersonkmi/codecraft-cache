package org.codecraftlabs.cache.controller;

import org.codecraftlabs.cache.model.CacheEntry;
import org.codecraftlabs.cache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;

@RestController
public class CacheController extends BaseControllerMkI {
    private final CacheService cacheService;

    @Autowired
    public CacheController(@Nonnull CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @PutMapping(value = "/cache", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> upsert(@RequestBody CacheEntry cacheEntry) {
        this.cacheService.upsert(cacheEntry);
        return ResponseEntity.ok("Done");
    }
}
