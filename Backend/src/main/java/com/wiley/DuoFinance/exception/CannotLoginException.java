package com.wiley.DuoFinance.exception;

public class CannotLoginException extends Exception {

    public CannotLoginException() {
    }

    public CannotLoginException(String message) {
        super(message);
    }
}
