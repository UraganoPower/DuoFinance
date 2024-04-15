package com.wiley.DuoFinance.controller;

import com.wiley.DuoFinance.exception.BasicRoleRequiredException;
import com.wiley.DuoFinance.exception.CannotLoginException;
import com.wiley.DuoFinance.exception.InvalidAnswersFormatException;
import com.wiley.DuoFinance.model.Answer;
import com.wiley.DuoFinance.security.Session;
import com.wiley.DuoFinance.service.GameService;
import com.wiley.DuoFinance.service.LoginService;
import com.wiley.DuoFinance.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.POST, RequestMethod.GET}, allowCredentials = "true")
public class GameController {

    @Autowired
    LoginService loginService;

    @Autowired
    GameService gameService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitAnswers(HttpServletRequest request, @RequestBody List<Answer> answers) throws CannotLoginException, BasicRoleRequiredException, InvalidAnswersFormatException {

        int result;

        String userIdHash = Session.getHash(request);

        loginService.confirmBasicStatus(userIdHash);
        gameService.validateAnswersFormat(answers);
        result = gameService.calculateResult(answers);
        gameService.addGame(userIdHash, result);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }



}
