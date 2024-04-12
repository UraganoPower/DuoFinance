package com.wiley.DuoFinance.exception;

import java.util.Map;

public class InvalidUserException extends Exception {

    private Map<String, String> errors;

    public InvalidUserException() {
    }

    public InvalidUserException(Map<String, String> errors) {
        this.errors = errors;
    }

    public InvalidUserException(String message) {
        super(message);
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
