package com.wiley.DuoFinance.service;

import com.wiley.DuoFinance.TestApplicationConfiguration;
import com.wiley.DuoFinance.exception.InvalidQuestionException;
import com.wiley.DuoFinance.exception.NoQuestionAvailableException;
import com.wiley.DuoFinance.exception.QuestionUsedException;
import com.wiley.DuoFinance.model.Question;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class QuestionServiceTest {

    @Autowired
    QuestionService questionService;

    @Test
    @DisplayName("Test InvalidQuestionException")
    public void testValidateQuestion() {
        //Invalid question text
        Question question1 = new Question();
        question1.setQuestionText("test");
        question1.setChoiceA("choice A");
        question1.setChoiceB("choice B");
        question1.setChoiceC("choice C");
        question1.setAnswer("choice B");

        assertThrows(InvalidQuestionException.class, () -> {
            questionService.validateQuestion(question1);
        });
        //Invalid choice
        Question question2 = new Question();
        question2.setQuestionText("test question");
        question2.setChoiceA("choice A");
        question2.setChoiceB("choi");
        question2.setChoiceC("choice C");
        question2.setAnswer("choice A");

        assertThrows(InvalidQuestionException.class, () -> {
            questionService.validateQuestion(question2);
        });
        //Invalid answer not from choices
        Question question3 = new Question();
        question3.setQuestionText("test question");
        question3.setChoiceA("choice A");
        question3.setChoiceB("choice B");
        question3.setChoiceC("choice C");
        question3.setAnswer("choice D");

        assertThrows(InvalidQuestionException.class, () -> {
            questionService.validateQuestion(question3);
        });
    }
        @Test
        @DisplayName("Test NoQuestionAvailableException")
        public void testGetRandomQuestions() {
            // No questions in our test database

            assertThrows(NoQuestionAvailableException.class, () -> {
                questionService.getRandomQuestions();
            });
        }

        @Test
        @DisplayName("Test getAllQuestions")
        public void testGetAllQuestions() {
            // No questions in our test database

            assertThrows(NoQuestionAvailableException.class, () -> {
                questionService.getAllQuestions();
            });
        }
}

