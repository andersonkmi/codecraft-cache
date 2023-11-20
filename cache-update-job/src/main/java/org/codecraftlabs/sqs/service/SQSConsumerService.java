package org.codecraftlabs.sqs.service;

import org.codecraftlabs.cloudlift.sqs.SQSMessage;
import org.codecraftlabs.cloudlift.sqs.SQSService;
import org.codecraftlabs.sqs.util.JobConfiguration;
import org.json.JSONObject;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SQSConsumerService {
    private static final int INTERVAL_IN_SECONDS = 20;

    private final JobConfiguration jobConfiguration;
    private final SQSService sqsService;

    public SQSConsumerService(@Nonnull JobConfiguration jobConfiguration, @Nonnull SQSService sqsService) {
        this.jobConfiguration = jobConfiguration;
        this.sqsService = sqsService;
    }

    @Nonnull
    public Optional<Set<CacheOperation>> retrieveMessages() {
        if (jobConfiguration.getQueueUrl().isEmpty()) {
            return Optional.empty();
        }
        String sqsUrl = jobConfiguration.getQueueUrl().get();
        Optional<Set<SQSMessage>> messages = sqsService.receiveMessages(sqsUrl, INTERVAL_IN_SECONDS);
        if (messages.isEmpty()) {
            return Optional.empty();
        }
        sqsService.deleteMessages(sqsUrl, messages.get());

        Set<CacheOperation> operations = messages.get().stream().map(this::convert).collect(Collectors.toSet());
        return Optional.of(operations);
    }

    @Nonnull
    private CacheOperation convert(@Nonnull SQSMessage message) {
        String body = message.body();
        JSONObject json = new JSONObject(body);

        String operationCode = json.getString("operation");
        JSONObject cacheEntry = json.getJSONObject("cacheEntry");
        String key = cacheEntry.getString("key");
        String value = cacheEntry.getString("value");

        CacheEntry entry = new CacheEntry(key, value);
        return new CacheOperation(operationCode, entry);
    }
}
