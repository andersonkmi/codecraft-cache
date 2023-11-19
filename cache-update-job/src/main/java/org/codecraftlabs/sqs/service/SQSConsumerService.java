package org.codecraftlabs.sqs.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.cloudlift.sqs.SQSMessage;
import org.codecraftlabs.cloudlift.sqs.SQSService;
import org.codecraftlabs.sqs.util.JobConfiguration;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;

public class SQSConsumerService {
    private static final Logger logger = LogManager.getLogger(SQSConsumerService.class);

    private final JobConfiguration jobConfiguration;
    private final SQSService sqsService;

    public SQSConsumerService(@Nonnull JobConfiguration jobConfiguration, @Nonnull SQSService sqsService) {
        this.jobConfiguration = jobConfiguration;
        this.sqsService = sqsService;
    }

    public void execute() throws AWSException {
        if (jobConfiguration.getQueueUrl().isEmpty()) {
            return;
        }
        String sqsUrl = jobConfiguration.getQueueUrl().get();
        Optional<Set<SQSMessage>> messages = sqsService.receiveMessages(sqsUrl, 20);
        if (messages.isEmpty()) {
            return;
        }
        sqsService.deleteMessages(sqsUrl, messages.get());
        messages.get().forEach(this::logMessage);
    }

    private void logMessage(@Nonnull SQSMessage message) {
        logger.info("Message from the queue: " + message.body());
    }
}
