package com.wiley.DuoFinance.controller;

import com.wiley.DuoFinance.exception.*;
import com.wiley.DuoFinance.model.Question;
import com.wiley.DuoFinance.security.Session;
import com.wiley.DuoFinance.service.LoginService;
import com.wiley.DuoFinance.service.QuestionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT}, allowCredentials = "true")
public class QuestionController {

    @Autowired
    LoginService loginService;

    @Autowired
    QuestionService questionService;

    @GetMapping("/random-questions")
    public ResponseEntity<?> getRandomQuestions(HttpServletRequest request) throws CannotLoginException, BasicRoleRequiredException, NoQuestionAvailableException {

        List<Question> randomQuestions;

        String userIdHash = Session.getHash(request);

        loginService.confirmBasicStatus(userIdHash);

        randomQuestions = questionService.getRandomQuestions();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(randomQuestions);
    }

    @GetMapping("/question")
    public ResponseEntity<?> getAllQuestions(HttpServletRequest request) throws CannotLoginException, AdminRoleRequiredException, NoQuestionAvailableException {

        List<Question> questions;

        String userIdHash = Session.getHash(request);

        loginService.confirmAdminStatus(userIdHash);

        questions = questionService.getAllQuestions();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(questions);
    }

    @PostMapping("/question")
    public ResponseEntity<?> addQuestion(HttpServletRequest request, @RequestBody Question question) throws CannotLoginException, AdminRoleRequiredException, InvalidQuestionException {

        Question newQuestion;

        String userIdHash = Session.getHash(request);

        loginService.confirmAdminStatus(userIdHash);

        questionService.validateQuestion(question);
        newQuestion = questionService.addQuestion(question);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newQuestion);
    }

    @PutMapping("/question")
    public ResponseEntity<?> updateQuestion(HttpServletRequest request, @RequestBody Question question) throws CannotLoginException, AdminRoleRequiredException, InvalidQuestionException {

        String userIdHash = Session.getHash(request);

        loginService.confirmAdminStatus(userIdHash);

        questionService.validateQuestion(question);
        questionService.updateQuestion(question);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/question/{questionId}")
    public ResponseEntity<?> deleteQuestionById(HttpServletRequest request, @PathVariable int questionId) throws CannotLoginException, AdminRoleRequiredException, QuestionUsedException {

        String userIdHash = Session.getHash(request);

        loginService.confirmAdminStatus(userIdHash);

        questionService.deleteQuestionById(questionId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/question/search/{keyword}")
    public ResponseEntity<?> searchQuestion(HttpServletRequest request, @PathVariable String keyword) throws CannotLoginException, AdminRoleRequiredException {

        List<Question> questions;

        String userIdHash = Session.getHash(request);

        loginService.confirmAdminStatus(userIdHash);

        questions = questionService.searchByKeyword(keyword);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(questions);
    }


}
