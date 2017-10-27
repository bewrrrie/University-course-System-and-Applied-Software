package com.examples.leshkov.sippo.extremum.exception;

public class OutOfFunctionDomainException extends Exception {
    public OutOfFunctionDomainException(String message) {
        super(message);
    }

    public OutOfFunctionDomainException(Exception e) {
        super(e);
    }

    public OutOfFunctionDomainException(String message, Exception e) {
        super(message, e);
    }
}
