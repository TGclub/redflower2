package com.test.redflower2.handle.exception;

public class NoAuthenticationException extends Exception{
    public NoAuthenticationException() {
    }

    public NoAuthenticationException(String message) {
        super(message);
    }
}