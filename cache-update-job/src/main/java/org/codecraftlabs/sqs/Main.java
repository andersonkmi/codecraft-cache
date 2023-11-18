package org.codecraftlabs.sqs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codecraftlabs.sqs.service.AWSException;
import org.codecraftlabs.sqs.service.SQSConsumerService;
import org.codecraftlabs.sqs.util.AppArguments;
import org.codecraftlabs.sqs.util.CommandLineException;
import org.codecraftlabs.sqs.util.CommandLineUtil;

import static org.codecraftlabs.sqs.util.CommandLineUtil.help;

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
            CommandLineUtil commandLineUtil = new CommandLineUtil();
            AppArguments arguments = commandLineUtil.parse(args);

            while (true) {
                var serviceExecutor = new SQSConsumerService();
                serviceExecutor.execute(arguments);

                if (isVMShuttingDown) {
                    signalReadyToExit();
                    break;
                }
            }
            logger.info("Finishing app");
            unregisterShutdownHook();
        } catch (AWSException exception) {
            logger.error(exception.getMessage(), exception);
        } catch (IllegalArgumentException | CommandLineException exception) {
            logger.error("Failed to parse command line options", exception);
            help();
        }
    }

    public static void main(String[] args) {
        new Main().start(args);
    }
}
