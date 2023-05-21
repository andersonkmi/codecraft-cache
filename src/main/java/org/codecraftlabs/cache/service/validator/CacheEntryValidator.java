package org.codecraftlabs.cache.service.validator;

import org.codecraftlabs.cache.model.CacheEntry;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Set;

@Component
public class CacheEntryValidator {
    private final Set<CacheEntryValidationPolicy> policies = Set.of(new CacheEntryKeyValidationPolicy(), new CacheEntryValueValidationPolicy());

    public void validate(@Nonnull CacheEntry cacheEntry) {
        for (CacheEntryValidationPolicy policy : policies) {
            policy.apply(cacheEntry);
        }
    }
}
