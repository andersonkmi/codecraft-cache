package org.codecraftlabs.cache.controller;

import org.codecraftlabs.cache.model.CacheEntry;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController extends BaseControllerMkI {
    @PutMapping(value = "/cache", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> upsert(@RequestBody CacheEntry cacheEntry) {
        return ResponseEntity.ok("Done");
    }
}
