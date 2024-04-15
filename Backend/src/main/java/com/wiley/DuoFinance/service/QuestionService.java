package com.wiley.DuoFinance.service;

import com.wiley.DuoFinance.exception.InvalidQuestionException;
import com.wiley.DuoFinance.exception.NoQuestionAvailableException;
import com.wiley.DuoFinance.model.Question;

import java.util.List;

public interface QuestionService {

    void validateQuestion(Question question) throws InvalidQuestionException;

    Question addQuestion(Question question);

    List<Question> getRandomQuestions() throws NoQuestionAvailableException;
}
