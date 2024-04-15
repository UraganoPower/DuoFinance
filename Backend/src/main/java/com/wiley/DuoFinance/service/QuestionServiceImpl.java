package com.wiley.DuoFinance.service;

import com.wiley.DuoFinance.dao.QuestionDao;
import com.wiley.DuoFinance.exception.InvalidQuestionException;
import com.wiley.DuoFinance.exception.NoQuestionAvailableException;
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
}
