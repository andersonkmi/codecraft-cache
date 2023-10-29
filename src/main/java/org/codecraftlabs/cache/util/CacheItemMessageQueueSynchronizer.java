package org.codecraftlabs.cache.util;

import org.codecraftlabs.cache.config.CustomConfig;
import org.codecraftlabs.cache.model.CacheItemOperation;
import org.codecraftlabs.cloudlift.sqs.SQSException;
import org.codecraftlabs.cloudlift.sqs.SQSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component("cacheItemMessageQueueSynchronizer")
public class CacheItemMessageQueueSynchronizer implements CacheItemSynchronizer {
    private static final Logger logger = LoggerFactory.getLogger(CacheItemMessageQueueSynchronizer.class);
    private final CustomConfig customConfig;

    public CacheItemMessageQueueSynchronizer(CustomConfig customConfig) {
        this.customConfig = customConfig;
    }

    public void submitCacheOperation(@Nonnull CacheItemOperation cacheItemOperation) {
        try {
            logger.info("Submitting cache updates into the queue: " + cacheItemOperation.toJson());
            SQSService sqsService = SQSService.builder().build();
            String sqsUrl = customConfig.getSqsUrl();
            sqsService.sendMessage(sqsUrl, cacheItemOperation.toJson());
        } catch (SQSException exception) {
            logger.error("Failed to submit message", exception);
        }
    }
}
