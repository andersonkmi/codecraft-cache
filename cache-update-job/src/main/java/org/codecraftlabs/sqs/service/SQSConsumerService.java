package org.codecraftlabs.sqs.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.sqs.util.JobConfiguration;

import javax.annotation.Nonnull;

public class SQSConsumerService {
    private static final Logger logger = LogManager.getLogger(SQSConsumerService.class);

    private final JobConfiguration jobConfiguration;

    public SQSConsumerService(@Nonnull JobConfiguration jobConfiguration) {
        this.jobConfiguration = jobConfiguration;
    }

    public void execute() throws AWSException {

    }
}
