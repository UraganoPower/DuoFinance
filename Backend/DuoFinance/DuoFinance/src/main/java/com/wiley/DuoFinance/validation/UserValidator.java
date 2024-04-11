package com.wiley.DuoFinance.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    private static  final int MAX_LENGTH = 100;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    public static String validateUser(String username, String email, String password) {
        if (username.length() > MAX_LENGTH) {
            return "Error: Username cannot be more than " + MAX_LENGTH + " characters.";
        }
        if (email.length() > MAX_LENGTH) {
            return "Error: Email cannot be more than " + MAX_LENGTH + " characters.";
        }
        if (password.length() > MAX_LENGTH) {
            return "Error: Password cannot be more than " + MAX_LENGTH + " characters.";
        }
        //Creating a pattern object and compiles the given regular expression into a pattern
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        //Check if the email matches the Email REGEX pattern
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()){
            return "Error: Email is not in valid format.";
        }
        return "Success: User input is valid.";
    }
}
