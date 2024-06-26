package com.wiley.DuoFinance.exception;


import com.wiley.DuoFinance.util.JsonGenerator;
import com.wiley.DuoFinance.validation.QuestionValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<?> handleInvalidUSerException(InvalidUserException ex) {

        Map<String, Object> errors = new HashMap<>();

        errors.put("errors", ex.getErrors());

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

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<?> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(JsonGenerator.formatSingleError("login", "You need to login in."));
    }

    @ExceptionHandler(CannotLoginException.class)
    public ResponseEntity<?> handleCannotLoginException(CannotLoginException ex) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(JsonGenerator.formatSingleError("login", "There was an error with authentication."));
    }

    @ExceptionHandler(BasicRoleRequiredException.class)
    public ResponseEntity<?> handleBasicRoleRequiredException(BasicRoleRequiredException ex) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(JsonGenerator.formatSingleError("role", "You must be login as a BASIC user."));
    }

    @ExceptionHandler(AdminRoleRequiredException.class)
    public ResponseEntity<?> handleAdminRoleRequiredException(AdminRoleRequiredException ex) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(JsonGenerator.formatSingleError("role", "You must be login as an ADMIN user."));
    }

    @ExceptionHandler(InvalidQuestionException.class)
    public ResponseEntity<?> handleInvalidQuestionException(InvalidQuestionException ex) {

        Map<String, Object> errors = new HashMap<>();

        errors.put("errors", QuestionValidator.getErrors());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    @ExceptionHandler(NoQuestionAvailableException.class)
    public ResponseEntity<?> handleNoQuestionAvailableException(NoQuestionAvailableException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(JsonGenerator.formatSingleError("questions", "The database contains no questions."));
    }

    @ExceptionHandler(QuestionUsedException.class)
    public ResponseEntity<?> handleQuestionUsedException(QuestionUsedException ex) {

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(JsonGenerator.formatSingleError("question", "The question cannot be deleted because it has already been used."));
    }

    @ExceptionHandler(InvalidAnswersFormatException.class)
    public ResponseEntity<?> handleInvalidAnswersFormatException(InvalidAnswersFormatException ex) {

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(JsonGenerator.formatSingleError("answers", "You need to submit all three answers with the question ID."));
    }
}
