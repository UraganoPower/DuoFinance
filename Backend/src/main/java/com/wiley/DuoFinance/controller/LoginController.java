package com.wiley.DuoFinance.controller;

import com.wiley.DuoFinance.exception.AdminRoleRequiredException;
import com.wiley.DuoFinance.exception.BasicRoleRequiredException;
import com.wiley.DuoFinance.exception.CannotLoginException;
import com.wiley.DuoFinance.model.Credentials;
import com.wiley.DuoFinance.model.User;
import com.wiley.DuoFinance.security.Session;
import com.wiley.DuoFinance.service.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.wiley.DuoFinance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.POST, RequestMethod.GET}, allowCredentials = "true")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Credentials credentials, HttpServletResponse response) throws Exception {

        User user;
        String userHash;

        user = loginService.login(credentials);
        userHash = userService.encryptUserId(user.getUserId());

        Cookie sessionCookie = Session.add(userHash);

        response.addCookie(sessionCookie);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) throws Exception {
        Cookie removeCookie = Session.remove();

        response.addCookie(removeCookie);

        return ResponseEntity
                .status(HttpStatus.OK)
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

    @GetMapping("/login")
    public ResponseEntity<?> isLogin(HttpServletRequest request) throws CannotLoginException {
        Session.getHash(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
