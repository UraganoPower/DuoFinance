package com.wiley.DuoFinance.controller;

import com.wiley.DuoFinance.exception.AdminRoleRequiredException;
import com.wiley.DuoFinance.exception.CannotLoginException;
import com.wiley.DuoFinance.exception.InvalidQuestionException;
import com.wiley.DuoFinance.model.Question;
import com.wiley.DuoFinance.service.LoginService;
import com.wiley.DuoFinance.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class QuestionController {

    @Autowired
    LoginService loginService;

    @Autowired
    QuestionService questionService;

    @PostMapping("/question")
    public ResponseEntity<?> addQuestion(@RequestHeader(name = "userIdHash", required = true) String userIdHash, @RequestBody Question question) throws CannotLoginException, AdminRoleRequiredException, InvalidQuestionException {

        Question newQuestion;

        loginService.confirmAdminStatus(userIdHash);

        questionService.validateQuestion(question);
        newQuestion = questionService.addQuestion(question);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newQuestion);
    }

}
