package com.wiley.DuoFinance.controller;

import com.wiley.DuoFinance.exception.AdminRoleRequiredException;
import com.wiley.DuoFinance.exception.BasicRoleRequiredException;
import com.wiley.DuoFinance.exception.CannotLoginException;
import com.wiley.DuoFinance.model.Credentials;
import com.wiley.DuoFinance.model.User;
import com.wiley.DuoFinance.service.LoginService;
import com.wiley.DuoFinance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Credentials credentials) throws Exception {

        User user;
        String userIdHash;

        user = loginService.login(credentials);
        userIdHash = userService.encryptUserId(user.getUserId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .header("userIdHash", userIdHash)
                .build();
    }

    @GetMapping("/basic")
    public String checkBasic(@RequestHeader(name = "userIdHash", required = true) String userIdHash) throws CannotLoginException, BasicRoleRequiredException {

        loginService.confirmBasicStatus(userIdHash);

        return "Welcome my dear BASIC user.";
    }

    @GetMapping("/admin")
    public String checkAdmin(@RequestHeader(name = "userIdHash", required = true) String userIdHash) throws CannotLoginException, AdminRoleRequiredException {

        loginService.confirmAdminStatus(userIdHash);

        return "Welcome my dear ADMIN user.";
    }
}
