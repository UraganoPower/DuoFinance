package com.wiley.DuoFinance.validation;

import com.wiley.DuoFinance.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserUpdateValidator {

    private static Map<String, String> errors;

    public static boolean isValidUser(User user) {

        errors = new HashMap<>();

        validateUsername(user.getUsername());

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
}
