package com.wiley.DuoFinance.dao;

import com.wiley.DuoFinance.exception.QuestionUsedException;
import com.wiley.DuoFinance.model.Question;

import java.util.List;

public interface QuestionDao {

    Question addQuestion(Question question);

    List<Question> getRandomQuestions();

    void updateQuestion(Question question);

    void deleteQuestionById(int questionId) throws QuestionUsedException;

    List<Question> getAllQuestions();
    List<Question> searchByKeyWord(String keyWord);
}
