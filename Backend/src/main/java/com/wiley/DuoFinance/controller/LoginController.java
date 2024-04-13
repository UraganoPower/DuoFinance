package com.wiley.DuoFinance.controller;

import com.wiley.DuoFinance.exception.AdminRoleRequiredException;
import com.wiley.DuoFinance.exception.BasicRoleRequiredException;
import com.wiley.DuoFinance.exception.CannotLoginException;
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
import com.wiley.DuoFinance.service.LoginService;
import com.wiley.DuoFinance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @GetMapping("/login")
    public ResponseEntity<?> isLogin(HttpServletRequest request) {

        if(Session.findUserId(request) == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
