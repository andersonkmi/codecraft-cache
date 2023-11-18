package org.codecraftlabs.sqs.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.sqs.util.AppArguments;
import software.amazon.awssdk.services.sqs.SqsClient;

import javax.annotation.Nonnull;

public class SQSConsumerService {
    private static final Logger logger = LogManager.getLogger(SQSConsumerService.class);
    private SqsClient sqsClient;

    public SQSConsumerService() {
        sqsClient = SqsClient.builder().build();
    }

    public void execute(@Nonnull AppArguments arguments) throws AWSException {

    }
}
