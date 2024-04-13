package com.wiley.DuoFinance.service;

import com.wiley.DuoFinance.exception.InvalidQuestionException;
import com.wiley.DuoFinance.model.Question;

public interface QuestionService {

    void validateQuestion(Question question) throws InvalidQuestionException;

    Question addQuestion(Question question);
}
