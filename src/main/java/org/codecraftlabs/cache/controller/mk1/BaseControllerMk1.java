package org.codecraftlabs.cache.controller.mk1;

import org.codecraftlabs.cache.service.CacheService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;

@RestController
@RequestMapping("/v1")
public class BaseControllerMk1 {
    @Qualifier("cacheServiceMkI")
    private final CacheService cacheService;

    public BaseControllerMk1(@Nonnull CacheService cacheService) {
        this.cacheService = cacheService;
    }

    protected CacheService getCacheService() {
        return cacheService;
    }
}
