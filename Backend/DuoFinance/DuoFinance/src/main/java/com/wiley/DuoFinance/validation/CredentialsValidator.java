package com.wiley.DuoFinance.validation;

import com.wiley.DuoFinance.model.Credentials;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CredentialsValidator {

    private static Map<String, String> errors;

    public static boolean isValidCredentials(Credentials credentials) {

        errors = new HashMap<>();

        validateEmail(credentials.getEmail());
        validatePassword(credentials.getPassword());

        return errors.size() == 0;
    }

    public static Map<String, String> getErrors() {
        return errors;
    }

    private static void validateEmail(String email) {

        boolean isValid = true;
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        String message = "An email must be provided.";

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
        String message = "A password must be provided.";

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
