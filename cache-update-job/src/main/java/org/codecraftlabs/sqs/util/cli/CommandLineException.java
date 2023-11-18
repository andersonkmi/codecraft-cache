package org.codecraftlabs.sqs.util.cli;

public class CommandLineException extends Exception {
    public CommandLineException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
