package com.wiley.DuoFinance.validation;

import com.wiley.DuoFinance.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class UserValidator {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static Map<String, String> errors;

    public static boolean isValidUser(User user) {

        errors = new HashMap<>();

        validateUsername(user.getUsername());
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());

        return errors.size() == 0;
    }

    public static Map<String, String> getErrors() {
        return errors;
    }

    private static void validateUsername(String username) {

        boolean isValid = true;
        String message = "The username must be between 5 and 100 characters.";

        if(username == null) {
            isValid = false;
        } else {
            if(username.isBlank()) isValid = false;
            if(username.isEmpty()) isValid = false;
            if(username.length() < 5 || username.length() > 100) isValid = false;
        }

        if(!isValid) {
            errors.put("username", message);
        }
    }

    private static void validateEmail(String email) {

        boolean isValid = true;
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        String message = "The email must be valid and between 5 and 100 characters.";

        if(email == null) {
            isValid = false;
        } else {
            if(email.isBlank()) isValid = false;
            if(email.isEmpty()) isValid = false;
            if(email.length() < 5 || email.length() > 100) isValid = false;
            if(!email.matches(emailRegex)) isValid = false;
        }

        if(!isValid) {
            errors.put("email", message);
        }
    }

    private static void validatePassword(String password) {

        boolean isValid = true;
        String message = "The password must be between 5 and 100 characters.";

        if(password == null) {
            isValid = false;
        } else {
            if(password.isBlank()) isValid = false;
            if(password.isEmpty()) isValid = false;
            if(password.length() < 5 || password.length() > 100) isValid = false;
        }

        if(!isValid) {
            errors.put("password", message);
        }
    }

}
