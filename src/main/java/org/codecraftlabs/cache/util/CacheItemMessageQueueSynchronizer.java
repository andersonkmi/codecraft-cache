package org.codecraftlabs.cache.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component("cacheItemMessageQueueSynchronizer")
public class CacheItemMessageQueueSynchronizer implements CacheItemSynchronizer {
    private static final Logger logger = LoggerFactory.getLogger(CacheItemMessageQueueSynchronizer.class);

    public void submitCacheOperation(@Nonnull CacheItemOperation cacheItemOperation) {
        // submit the call to a message queue
    }
}
