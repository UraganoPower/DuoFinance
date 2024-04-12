package com.wiley.DuoFinance.controller;

import com.wiley.DuoFinance.exception.InvalidCredentialsException;
import com.wiley.DuoFinance.model.Credentials;
import com.wiley.DuoFinance.model.User;
import com.wiley.DuoFinance.security.HashUtility;
import com.wiley.DuoFinance.service.LoginService;
import com.wiley.DuoFinance.util.JsonGenerator;
import com.wiley.DuoFinance.validation.CredentialsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.POST}, allowCredentials = "true")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Credentials credentials) throws Exception {

        User user;
        String userIdHash;

        if(!CredentialsValidator.isValidCredentials(credentials)) {

            Map<String, Object> errors = new HashMap<>();

            errors.put("errors", CredentialsValidator.getErrors());

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errors);
        }

        try {
            user = loginService.login(credentials);
            userIdHash = HashUtility.encrypt(String.valueOf(user.getUserId()));
            user.setUserId(null);
            user.setPassword(null);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("userIdHash", userIdHash)
                    .body(user);

        } catch (InvalidCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(JsonGenerator.formatSingleError("login", "The provided email and password did not match."));
        }
    }
}
