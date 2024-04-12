package com.wiley.DuoFinance.controller;

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
    public User getUser(@RequestHeader("userIdHash") String userIdHash) {

        User user = new User();

        //String userIdHash =

        System.out.println("!!!!!!!!!!!!!! " + userIdHash);


        //userService.getUserById(userId);

        return user;

    }

    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestBody User user) throws Exception {

        int userId;
        String userIdHash;

        userService.validateUser(user);
        userId = userService.addUser(user);

        userIdHash = HashUtility.encrypt(String.valueOf(userId));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("userIdHash", userIdHash)
                .build();
    }
}
