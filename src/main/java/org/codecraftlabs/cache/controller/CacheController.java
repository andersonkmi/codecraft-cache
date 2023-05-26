package org.codecraftlabs.cache.controller;

import org.codecraftlabs.cache.model.CacheEntry;
import org.codecraftlabs.cache.service.CacheService;
import org.codecraftlabs.cache.service.OperationNotPerformedException;
import org.codecraftlabs.cache.service.validator.InvalidCacheEntryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import java.util.Optional;

@RestController
public class CacheController extends BaseControllerMkI {
    private static final Logger logger = LoggerFactory.getLogger(CacheController.class);

    private final CacheService cacheService;

    @Autowired
    public CacheController(@Nonnull CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @PostMapping(value = "/cache",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CacheResponse> insert(@RequestBody CacheEntry cacheEntry) {
        try {
            this.cacheService.insert(cacheEntry);
            CacheResponse response = new CacheResponse("Done");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (InvalidCacheEntryException exception) {
            logger.info("An error occurred when inserting new item into the cache.", exception);
            CacheResponse response = new CacheResponse(exception.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (OperationNotPerformedException exception) {
            logger.info("Cache item with informed key already present the in cache", exception);
            CacheResponse response = new CacheResponse(exception.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping(value = "/cache",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CacheResponse> update(@RequestBody CacheEntry cacheEntry) {
        try {
            this.cacheService.update(cacheEntry);
            CacheResponse response = new CacheResponse("Done");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (InvalidCacheEntryException exception) {
            logger.info("An error occurred when inserting new item into the cache.", exception);
            CacheResponse response = new CacheResponse(exception.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (OperationNotPerformedException exception) {
            logger.info("Cache item with informed key already present the in cache", exception);
            CacheResponse response = new CacheResponse(exception.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping(value = "/cache/{key}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CacheResponse> delete(@PathVariable String key) {
        if(this.cacheService.remove(key)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(value = "/cache/{key}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CacheEntry> get(@PathVariable String key) {
        Optional<CacheEntry> item = this.cacheService.retrieve(key);
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
