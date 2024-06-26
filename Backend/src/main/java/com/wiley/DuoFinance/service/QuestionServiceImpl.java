package com.wiley.DuoFinance.service;

import com.wiley.DuoFinance.dao.QuestionDao;
import com.wiley.DuoFinance.exception.InvalidQuestionException;
import com.wiley.DuoFinance.exception.NoQuestionAvailableException;
import com.wiley.DuoFinance.exception.QuestionUsedException;
import com.wiley.DuoFinance.model.Question;
import com.wiley.DuoFinance.validation.QuestionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionDao questionDao;

    @Override
    public void validateQuestion(Question question) throws InvalidQuestionException {

        if(!QuestionValidator.isValidQuestion(question)) {
            throw new InvalidQuestionException();
        }
    }

    @Override
    public Question addQuestion(Question question) {
        return questionDao.addQuestion(question);
    }

    @Override
    public List<Question> getRandomQuestions() throws NoQuestionAvailableException {

        List<Question> randomQuestions;

        randomQuestions = questionDao.getRandomQuestions();

        if(randomQuestions.isEmpty()) {
            throw new NoQuestionAvailableException();
        }

        for(Question randomQuestion : randomQuestions) {
            randomQuestion.setAnswer("");
        }

        return randomQuestions;
    }

    @Override
    public List<Question> getAllQuestions() throws NoQuestionAvailableException {

        List<Question> questions;

        questions = questionDao.getAllQuestions();

        if(questions.isEmpty()) {
            throw new NoQuestionAvailableException();
        }

        return questions;
    }

    @Override
    public List<Question> searchByKeyword(String keyword) {

        List<Question> questions;

        return questions = questionDao.searchByKeyword(keyword);
    }

    @Override
    public void updateQuestion(Question question) {
        questionDao.updateQuestion(question);
    }

    @Override
    public void deleteQuestionById(int questionId) throws QuestionUsedException {
        questionDao.deleteQuestionById(questionId);
    }
}
