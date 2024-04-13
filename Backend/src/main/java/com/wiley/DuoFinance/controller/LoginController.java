package com.wiley.DuoFinance.controller;

import com.wiley.DuoFinance.exception.InvalidCredentialsException;
import com.wiley.DuoFinance.model.Credentials;
import com.wiley.DuoFinance.model.User;
import com.wiley.DuoFinance.security.HashUtility;
import com.wiley.DuoFinance.security.Session;
import com.wiley.DuoFinance.service.LoginService;
import com.wiley.DuoFinance.util.JsonGenerator;
import com.wiley.DuoFinance.validation.CredentialsValidator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.POST, RequestMethod.GET}, allowCredentials = "true")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Credentials credentials, HttpServletResponse response) throws Exception {

        User user;
        String userHash;

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
            final String userKey = String.valueOf(user.getEmail());
            userHash = HashUtility.encrypt(userKey);

            //create the user session in memory
            Cookie sessionCookie = Session.add(userHash, userKey);

            //add the session cookie to the response
            response.addCookie(sessionCookie);

            user.setPassword(null);
            //todo: {i remove user.setId(null) because in all me test the id was already set to null}
            //remove this comment if you seen and agree with this.

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(user);

        } catch (InvalidCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(JsonGenerator.formatSingleError("login", "The provided email and password did not match."));
        }
    }


    @GetMapping("/login")
    public ResponseEntity<?> isLogin(HttpServletRequest request) {

        if(Session.findCookie(request) == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
