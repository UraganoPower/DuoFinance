package com.wiley.DuoFinance.controller;

import com.wiley.DuoFinance.exception.CannotLoginException;
import com.wiley.DuoFinance.exception.InvalidUserException;
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
@CrossOrigin(origins = "http://localhost:3000" ,methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE}, allowCredentials = "true")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/user", produces = "application/json")
    public ResponseEntity<?> getUser(HttpServletRequest request) throws Exception {
        User user;

        //look if the user is logged in
        String userIdHash = Session.getHash(request);

        int userId = userService.decryptUserId(userIdHash);

        //get the user
        user = userService.getUserById(userId);
        user.setUserId(null);

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
        Cookie sessionCookie = Session.add(userIdHash);

        //add the session cookie to the response
        response.addCookie(sessionCookie);

        // Add the cookie to the response

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/user")
    public ResponseEntity<?> updateUserById(HttpServletRequest request, @RequestBody User user) throws CannotLoginException, InvalidUserException {

        String userIdHash = Session.getHash(request);
        final int userId = userService.decryptUserId(userIdHash);

        userService.validateUserUpdate(user);
        userService.updateUser(userId, user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUserById(HttpServletRequest request, HttpServletResponse response) throws CannotLoginException {


        String userIdHash = Session.getHash(request);
        //check if the user is logged in

        final int userId = userService.decryptUserId(userIdHash);

        userService.deleteUserById(userId);

        Cookie removeCookie = Session.remove();

        response.addCookie(removeCookie);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
