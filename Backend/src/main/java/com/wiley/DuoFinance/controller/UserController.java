package com.wiley.DuoFinance.controller;

import com.wiley.DuoFinance.exception.CannotLoginException;
import com.wiley.DuoFinance.model.User;
import com.wiley.DuoFinance.security.HashUtility;
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

    @GetMapping("/user")
    public ResponseEntity<?> getUser(@RequestHeader(name = "userIdHash", required = true) String userIdHash) throws Exception {

        int userId;
        User user;

        userId = userService.decryptUserId(userIdHash);
        user = userService.getUserById(userId);
        user.setUserId(null);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header("userIdHash", userIdHash)
                .body(user);
    }

    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestBody User user) throws Exception {

        int userId;
        String userIdHash;

        userService.validateUser(user);
        userId = userService.addUser(user);
        userIdHash = userService.encryptUserId(userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("userIdHash", userIdHash)
                .build();
    }

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUserById(@RequestHeader(name = "userIdHash", required = true) String userIdHash) throws CannotLoginException {

        int userId;

        userId = userService.decryptUserId(userIdHash);

        userService.deleteUserById(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header("userIdHash", userIdHash)
                .build();
    }
}
