package com.wiley.DuoFinance.controller;

import com.wiley.DuoFinance.model.User;
import com.wiley.DuoFinance.security.HashUtility;
import com.wiley.DuoFinance.security.Session;
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
    public ResponseEntity<?> getUser(HttpServletRequest request) throws Exception {
        User user;

        //look if the user is logged in
        String userId = Session.findUserId(request);

        if (userId == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        //get the user
        user = userService.getUserById(Integer.parseInt(userId));

        //return the user
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestBody User user, HttpServletResponse response) throws Exception {

        String userIdHash;

        userService.validateUser(user);
        final String userId = String.valueOf(userService.addUser(user));

        userIdHash = HashUtility.encrypt(userId);

        //create the user session in memory
        Cookie sessionCookie = Session.add(userIdHash, userId);

        //add the session cookie to the response
        response.addCookie(sessionCookie);

        // Add the cookie to the response

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("userIdHash", userIdHash)
                .build();
    }
}
