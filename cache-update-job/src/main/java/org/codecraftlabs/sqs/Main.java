package org.codecraftlabs.sqs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.cloudlift.sqs.SQSService;
import org.codecraftlabs.sqs.service.CacheSynchronizerService;
import org.codecraftlabs.sqs.service.SQSConsumerService;
import org.codecraftlabs.sqs.util.JobConfiguration;
import org.codecraftlabs.sqs.util.PropertiesFileReader;
import org.codecraftlabs.sqs.util.RestAPICaller;
import org.codecraftlabs.sqs.util.cli.AppArguments;
import org.codecraftlabs.sqs.util.cli.CommandLineException;
import org.codecraftlabs.sqs.util.cli.CommandLineUtil;

import javax.annotation.Nonnull;

import static org.codecraftlabs.sqs.util.cli.CommandLineUtil.help;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private boolean isVMShuttingDown = false;
    private boolean readyToExit = false;
    private Thread shutdownThread;

    public Main() {
        registerShutdownHook();
    }

    private synchronized void signalReadyToExit() {
        this.readyToExit = true;
        this.notify();
    }

    private void registerShutdownHook() {
        logger.info("Registering shutdown hook");

        this.shutdownThread = new Thread("ShutdownHook") {
            public void run() {
                synchronized(this) {
                    if (!readyToExit) {
                        isVMShuttingDown = true;
                        logger.info("Control-C detected... Terminating process, please wait.");
                        try {
                            // Wait up to 1.5 secs for a record to be processed.
                            wait(1500);
                        } catch (InterruptedException ignore) {
                            // ignore exception
                        }

                        if (!readyToExit) {
                            logger.info("Main processing interrupted.");
                        }
                    }
                }
            }
        };

        Runtime.getRuntime().addShutdownHook(this.shutdownThread);
    }

    /** Unregister the shutdown hook. */
    private void unregisterShutdownHook() {
        logger.warn("Unregistering shutdown hook");
        try {
            Runtime.getRuntime().removeShutdownHook(this.shutdownThread);
        } catch (IllegalStateException ignore) {
            // VM already was shutting down
        }
    }

    public void start(String[] args) {
        logger.info("Starting app");
        try {
            CacheSynchronizerService cacheSynchronizerService = createService(args);
            while (true) {
                cacheSynchronizerService.processCacheSynchronization();
                if (isVMShuttingDown) {
                    signalReadyToExit();
                    break;
                }
            }
            logger.info("Finishing app");
            unregisterShutdownHook();
        } catch (IllegalArgumentException | CommandLineException exception) {
            logger.error("Failed to parse command line options", exception);
            help();
        }
    }

    @Nonnull
    private CacheSynchronizerService createService(String[] args) throws CommandLineException {
        CommandLineUtil commandLineUtil = new CommandLineUtil();
        AppArguments arguments = commandLineUtil.parse(args);
        String configFileName = arguments.option(AppArguments.CONFIGURATION_FILE);
        JobConfiguration jobConfiguration = new JobConfiguration(new PropertiesFileReader(configFileName));
        SQSConsumerService sqsConsumerService = new SQSConsumerService(jobConfiguration, SQSService.builder().build());
        RestAPICaller restAPICaller = new RestAPICaller(jobConfiguration);
        return new CacheSynchronizerService(sqsConsumerService, restAPICaller);
    }

    public static void main(String[] args) {
        new Main().start(args);
    }
}
