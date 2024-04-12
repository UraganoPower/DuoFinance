package com.wiley.DuoFinance.controller;

import com.wiley.DuoFinance.exception.AdminRoleRequiredException;
import com.wiley.DuoFinance.exception.CannotLoginException;
import com.wiley.DuoFinance.model.Question;
import com.wiley.DuoFinance.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class QuestionController {

    @Autowired
    LoginService loginService;

    @PostMapping("/question")
    public ResponseEntity<?> addQuestion(@RequestHeader(name = "userIdHash", required = true) String userIdHash, @RequestBody Question question) throws CannotLoginException, AdminRoleRequiredException {

        loginService.confirmAdminStatus(userIdHash);



    }

}
