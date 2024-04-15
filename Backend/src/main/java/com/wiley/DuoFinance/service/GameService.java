package com.wiley.DuoFinance.service;

import com.wiley.DuoFinance.exception.CannotLoginException;
import com.wiley.DuoFinance.exception.InvalidAnswersFormatException;
import com.wiley.DuoFinance.model.Answer;

import java.util.List;

public interface GameService {
    void validateAnswersFormat(List<Answer> answers) throws InvalidAnswersFormatException;

    int calculateResult(List<Answer> answers);

    void addGame(String userIdHash, int result) throws CannotLoginException;
}
