package org.codecraftlabs.sqs.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.sqs.util.RestAPICaller;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;

public class CacheSynchronizerService {
    private static final Logger logger = LogManager.getLogger(CacheSynchronizerService.class);
    private final SQSConsumerService sqsConsumerService;
    private final RestAPICaller restAPICaller;

    public CacheSynchronizerService(@Nonnull SQSConsumerService sqsConsumerService, @Nonnull RestAPICaller restAPICaller) {
        this.sqsConsumerService = sqsConsumerService;
        this.restAPICaller = restAPICaller;
    }

    public void processCacheSynchronization() {
        Optional<Set<CacheOperation>> cacheOperations = sqsConsumerService.retrieveMessages();
        cacheOperations.ifPresent(operations -> operations.forEach(this::processSynchronization));
    }

    private void processSynchronization(@Nonnull CacheOperation cacheOperation) {
        logger.info("Performing synchronization for '" + cacheOperation.cacheEntry()+ "'");
        switch (cacheOperation.operation()) {
            case "INSERT", "UPDATE" -> this.restAPICaller.submitUpdates("v1/cache", cacheOperation.cacheEntry());
            case "DELETE" -> this.restAPICaller.submitDeletes("v1/cache", cacheOperation.cacheEntry().key());
        }
    }
}
