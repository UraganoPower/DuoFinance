package com.wiley.DuoFinance.controller;

import com.wiley.DuoFinance.model.User;
import com.wiley.DuoFinance.service.UserService;
import com.wiley.DuoFinance.util.JsonGenerator;
import com.wiley.DuoFinance.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/basic-user")
    public ResponseEntity<?> addBasicUser(@RequestBody User basicUser) {

        User newBasicUser;

        if(!UserValidator.isValidUser(basicUser)) {

            Map<String, Object> errors = new HashMap<>();

            errors.put("errors", UserValidator.getErrors());

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errors);
        }

        if(!userService.isEmailAvailable(basicUser.getEmail())) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(JsonGenerator.formatSingleError("email", "The email already taken"));
        }

        newBasicUser = userService.addBasicUser(basicUser);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("userId", String.valueOf(basicUser.getUserId()))
                .body(newBasicUser);
    }
}
