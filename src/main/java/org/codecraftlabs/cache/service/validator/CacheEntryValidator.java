package org.codecraftlabs.cache.service.validator;

import org.codecraftlabs.cache.model.CacheEntry;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class CacheEntryValidator {
    private final Set<CacheEntryValidationPolicy> policies = new LinkedHashSet<>();

    public CacheEntryValidator() {
        policies.add(new CacheEntryKeyValidationPolicy());
        policies.add(new CacheEntryValueValidationPolicy());
    }

    public void validate(@Nonnull CacheEntry cacheEntry) {
        for (CacheEntryValidationPolicy policy : policies) {
            policy.apply(cacheEntry);
        }
    }
}
