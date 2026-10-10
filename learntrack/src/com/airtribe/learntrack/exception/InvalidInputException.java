package com.airtribe.learntrack.exception;

/** Thrown when the user supplies input that is empty, malformed or breaks a business rule. */
public class InvalidInputException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidInputException(String message) {
        super(message);
    }
}
