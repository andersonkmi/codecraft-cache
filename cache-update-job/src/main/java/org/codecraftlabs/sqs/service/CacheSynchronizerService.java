package org.codecraftlabs.sqs.service;

import org.codecraftlabs.sqs.util.RestAPICaller;

import javax.annotation.Nonnull;

public class CacheSynchronizerService {
    private final SQSConsumerService sqsConsumerService;
    private final RestAPICaller restAPICaller;

    public CacheSynchronizerService(@Nonnull SQSConsumerService sqsConsumerService, @Nonnull RestAPICaller restAPICaller) {
        this.sqsConsumerService = sqsConsumerService;
        this.restAPICaller = restAPICaller;
    }
}
