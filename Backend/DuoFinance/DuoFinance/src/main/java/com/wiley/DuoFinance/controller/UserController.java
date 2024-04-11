package com.wiley.DuoFinance.controller;

import com.wiley.DuoFinance.model.User;
import com.wiley.DuoFinance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/basic-user")
    public ResponseEntity<?> addBasicUser(@RequestBody User basicUser) {

        User newBasicUser;

        newBasicUser = userService.addBasicUser(basicUser);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("userId", String.valueOf(basicUser.getUserId()))
                .body(newBasicUser);
    }
}
