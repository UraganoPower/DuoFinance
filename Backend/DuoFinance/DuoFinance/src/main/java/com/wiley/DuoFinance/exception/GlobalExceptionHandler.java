package com.wiley.DuoFinance.exception;


import com.wiley.DuoFinance.util.JsonGenerator;
import com.wiley.DuoFinance.validation.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<?> handleInvalidUSerException(InvalidUserException ex) {

        Map<String, Object> errors = new HashMap<>();

        errors.put("errors", UserValidator.getErrors());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ResponseEntity<?> handleEmailAlreadyTakenException(EmailAlreadyTakenException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(JsonGenerator.formatSingleError("email", "The email already taken"));
    }

}
