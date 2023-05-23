package org.codecraftlabs.cache.service;

public class OperationNotPerformedException extends RuntimeException {
    public OperationNotPerformedException(String message) {
        super(message);
    }
}
