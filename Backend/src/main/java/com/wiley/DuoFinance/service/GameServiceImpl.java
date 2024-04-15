package com.wiley.DuoFinance.service;

import com.wiley.DuoFinance.dao.GameDao;
import com.wiley.DuoFinance.exception.CannotLoginException;
import com.wiley.DuoFinance.exception.InvalidAnswersFormatException;
import com.wiley.DuoFinance.model.Answer;
import com.wiley.DuoFinance.model.Game;
import com.wiley.DuoFinance.validation.AnswersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    UserService userService;

    @Autowired
    GameDao gameDao;

    @Override
    public void validateAnswersFormat(List<Answer> answers) throws InvalidAnswersFormatException {

        if(!AnswersValidator.isValidAnswers(answers)) {
            throw new InvalidAnswersFormatException();
        }

    }

    @Override
    public int calculateResult(List<Answer> answers) {

        int result = 0;

        for(Answer answer : answers) {

            if(gameDao.isValidateAnswer(answer)) {
                result++;
            }
        }

        return result;
    }

    @Override
    public void addGame(String userIdHash, int result) throws CannotLoginException {

        int userId;

        userId = userService.decryptUserId(userIdHash);

        gameDao.addGame(userId, result);

    }

    @Override
    public List<Game> getAllGameByUserId(String userIdHash) throws CannotLoginException {

        int userId;

        userId = userService.decryptUserId(userIdHash);

        return gameDao.getAllGameByUserId(userId);
    }

    @Override
    public Double getAverageByUserId(String userIdHash) throws CannotLoginException {

        Double average;
        int userId;

        userId = userService.decryptUserId(userIdHash);

        average = gameDao.getAverageByUserId(userId);

        if(average == null) {
            average = 0.0;
        }

        return average;
    }
}
