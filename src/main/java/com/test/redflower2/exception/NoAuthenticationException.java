package com.test.redflower2.exception;

public class NoAuthenticationException extends Exception {
    public NoAuthenticationException() {
    }

    public NoAuthenticationException(String message) {
        super(message);
    }
}