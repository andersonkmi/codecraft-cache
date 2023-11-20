package org.codecraftlabs.sqs.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.cloudlift.sqs.SQSMessage;
import org.codecraftlabs.cloudlift.sqs.SQSService;
import org.codecraftlabs.sqs.util.JobConfiguration;
import org.json.JSONObject;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class SQSConsumerService {
    private static final Logger logger = LogManager.getLogger(SQSConsumerService.class);
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
        logger.info("Converting message: '" + message.body() + "'");
        String body = message.body();
        StringTokenizer tokenizer = new StringTokenizer(body, ",");

        String operationCode = tokenizer.nextToken();
        String key = tokenizer.nextToken();
        String value = tokenizer.nextToken();

        CacheEntry entry = new CacheEntry(key, value);
        return new CacheOperation(operationCode, entry);
    }
}
