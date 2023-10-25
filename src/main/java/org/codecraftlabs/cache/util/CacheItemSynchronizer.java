package org.codecraftlabs.cache.util;

import org.codecraftlabs.cache.model.CacheItemOperation;

import javax.annotation.Nonnull;

public interface CacheItemSynchronizer {
    void submitCacheOperation(@Nonnull CacheItemOperation cacheItemOperation);
}
