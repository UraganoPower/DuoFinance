package com.wiley.DuoFinance.dao;

import com.wiley.DuoFinance.model.Answer;
import com.wiley.DuoFinance.model.Game;

import java.util.List;

public interface GameDao {

    boolean isValidateAnswer(Answer answer);

    void addGame(int userId, int result);

    List<Game> getAllGameByUserId(int userId);
}
