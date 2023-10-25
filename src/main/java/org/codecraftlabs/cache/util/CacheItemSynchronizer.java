package org.codecraftlabs.cache.util;

import javax.annotation.Nonnull;

public interface CacheItemSynchronizer {
    void submitCacheOperation(@Nonnull CacheItemOperation cacheItemOperation);
}
