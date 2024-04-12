package com.wiley.DuoFinance.exception;

import java.util.Map;

public class InvalidQuestionException extends Exception {

    private Map<String, String> errors;

    public InvalidQuestionException() {
    }

    public InvalidQuestionException(Map<String, String> errors) {
        this.errors = errors;
    }

    public InvalidQuestionException(String message) {
        super(message);
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
