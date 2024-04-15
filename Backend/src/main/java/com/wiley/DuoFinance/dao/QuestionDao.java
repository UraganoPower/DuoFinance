package com.wiley.DuoFinance.dao;

import com.wiley.DuoFinance.model.Question;

import java.util.List;

public interface QuestionDao {

    Question addQuestion(Question question);

    List<Question> getRandomQuestions();

    void updateQuestion(Question question);
}
