package com.wiley.DuoFinance.dao;

import com.wiley.DuoFinance.TestApplicationConfiguration;
import com.wiley.DuoFinance.exception.QuestionUsedException;
import com.wiley.DuoFinance.mapper.QuestionMapper;
import com.wiley.DuoFinance.model.Question;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class QuestionDaoTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private QuestionMySqlDao questionDao;

    private Question testQuestion;
    private Question testQuestion1;
    private Question testQuestion2;

    @BeforeEach
    public void setUp(){
        testQuestion = new Question();

        testQuestion.setQuestionText("test question # 1");
        testQuestion.setChoiceA("choice A");
        testQuestion.setChoiceB("choice B");
        testQuestion.setChoiceC("choice C");
        testQuestion.setAnswer("answer");
        //Add the test question
        questionDao.addQuestion(testQuestion);

        testQuestion1 = new Question();

        testQuestion1.setQuestionText("test question # 2");
        testQuestion1.setChoiceA("choice A");
        testQuestion1.setChoiceB("choice B");
        testQuestion1.setChoiceC("choice C");
        testQuestion1.setAnswer("answer");
        //Add the test question
        questionDao.addQuestion(testQuestion1);

        testQuestion2 = new Question();

        testQuestion2.setQuestionText("test question # 3");
        testQuestion2.setChoiceA("choice A");
        testQuestion2.setChoiceB("choice B");
        testQuestion2.setChoiceC("choice C");
        testQuestion2.setAnswer("answer");
        //Add the test question
        questionDao.addQuestion(testQuestion2);
    }

    @AfterEach
    public void tearDown(){
        jdbc.update("DELETE FROM question WHERE questionId = ?", testQuestion.getQuestionId());
        jdbc.update("DELETE FROM question WHERE questionId = ?", testQuestion1.getQuestionId());
        jdbc.update("DELETE FROM question WHERE questionId = ?", testQuestion2.getQuestionId());
    }

    @Test
    @DisplayName("Test adding a question")
    public void testAddQuestion() {
        Question question = new Question();
        question.setQuestionText("test new question");
        question.setChoiceA("choice A");
        question.setChoiceB("choice B");
        question.setChoiceC("choice C");
        question.setAnswer("answer");
        Question addedQuestion = questionDao.addQuestion(question);
        assertNotNull(addedQuestion.getQuestionId(), "Inserted question ID should not be null");
        jdbc.update("DELETE FROM question WHERE questionId = ?", question.getQuestionId());
    }

    @Test
    @DisplayName("Test getting random questions")
    public void testGetRandomQuestions() {
        List<Question> questions = questionDao.getRandomQuestions();
        assertEquals(3, questions.size(), "Should return 3 questions");
    }

    @Test
    @DisplayName("Test updating a question")
    public void testUpdateQuestion() {
        testQuestion.setQuestionText("test updated question");
        questionDao.updateQuestion(testQuestion);

        Question updatedQuestion = jdbc.queryForObject("SELECT * FROM question WHERE questionId = ?", new QuestionMapper(), testQuestion.getQuestionId());
        assertEquals("test updated question", updatedQuestion.getQuestionText(), "Question text should be updated");
    }
    @Test
    @DisplayName("Test deleting a question by ID")
    public void testDeleteQuestionById() throws QuestionUsedException {
        // Insert a test question that is not used in any game
        Question question = new Question();
        question.setQuestionText("test delete question");
        question.setChoiceA("choice A");
        question.setChoiceB("choice B");
        question.setChoiceC("choice C");
        question.setAnswer("answer");
        Question addedQuestion = questionDao.addQuestion(question);

        // Delete the test question
        questionDao.deleteQuestionById(addedQuestion.getQuestionId());

        // Try to retrieve the deleted question
        List<Question> questions = jdbc.query("SELECT * FROM question WHERE questionId = ?", new QuestionMapper(), addedQuestion.getQuestionId());
        assertTrue(questions.isEmpty(), "Question should be null after deletion");
    }

}
