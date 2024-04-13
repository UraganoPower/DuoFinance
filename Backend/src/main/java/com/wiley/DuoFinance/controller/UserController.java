package com.wiley.DuoFinance.controller;

import com.wiley.DuoFinance.model.User;
import com.wiley.DuoFinance.security.HashUtility;
import com.wiley.DuoFinance.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000" ,methods = {RequestMethod.POST}, allowCredentials = "true")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/user", produces = "application/json")
    public ResponseEntity<?> getUser(@RequestHeader(name = "userIdHash", required = true) String userIdHash) throws Exception {

        int userId;
        User user;

        userId = Integer.parseInt(HashUtility.decrypt(userIdHash));

        user = userService.getUserById(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header("userIdHash", userIdHash)
                .body(user);
    }

    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestBody User user, HttpServletRequest request) throws Exception {

        int userId;
        String userIdHash;

        userService.validateUser(user);
        userId = userService.addUser(user);

        userIdHash = HashUtility.encrypt(String.valueOf(userId));

        // Create a cookie with the userIdHash
        Cookie cookie = new Cookie("userIdHash", userIdHash);
        cookie.setMaxAge(7200); // 2 hours

        // Add the cookie to the response

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("userIdHash", userIdHash)
                .build();
    }
}
