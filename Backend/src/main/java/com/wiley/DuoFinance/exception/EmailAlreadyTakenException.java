package com.wiley.DuoFinance.exception;

public class EmailAlreadyTakenException extends Exception {



    public EmailAlreadyTakenException() {
    }

    public EmailAlreadyTakenException(String message) {
        super(message);
    }
}
