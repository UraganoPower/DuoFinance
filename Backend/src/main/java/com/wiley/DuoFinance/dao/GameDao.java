package com.wiley.DuoFinance.dao;

import com.wiley.DuoFinance.model.Answer;

public interface GameDao {

    boolean isValidateAnswer(Answer answer);

    void addGame(int userId, int result);
}
