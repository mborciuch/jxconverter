package com.mbor.converterservice.exception;

public class ProcessingException extends RuntimeException {
    public ProcessingException() {
        super();
    }

    public ProcessingException(String message) {
        super(message);
    }
}
