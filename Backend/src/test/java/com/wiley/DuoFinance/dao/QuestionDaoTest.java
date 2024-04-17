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
    private QuestionDao questionDao;

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
        testQuestion.setAnswer("choice A");
        //Add the test question
        questionDao.addQuestion(testQuestion);

        testQuestion1 = new Question();

        testQuestion1.setQuestionText("test question # 2");
        testQuestion1.setChoiceA("choice A");
        testQuestion1.setChoiceB("choice B");
        testQuestion1.setChoiceC("choice C");
        testQuestion1.setAnswer("choice B");
        //Add the test question
        questionDao.addQuestion(testQuestion1);

        testQuestion2 = new Question();

        testQuestion2.setQuestionText("test question # 3");
        testQuestion2.setChoiceA("choice A");
        testQuestion2.setChoiceB("choice B");
        testQuestion2.setChoiceC("choice C");
        testQuestion2.setAnswer("choice C");
        //Add the test question
        questionDao.addQuestion(testQuestion2);
    }

    @AfterEach
    public void tearDown(){
        jdbc.update("DELETE FROM question");
    }

    @Test
    @DisplayName("Test adding a question")
    public void testAddQuestion() {
        Question question = new Question();
        question.setQuestionText("test new question");
        question.setChoiceA("choice A");
        question.setChoiceB("choice B");
        question.setChoiceC("choice C");
        question.setAnswer("choice B");
        Question addedQuestion = questionDao.addQuestion(question);

        // check that addedQuestion is not null
        assertNotNull(addedQuestion, "Added question should not be null");

        // check other properties
        assertEquals("test new question", addedQuestion.getQuestionText(), "Question text should match");
        assertEquals("choice A", addedQuestion.getChoiceA(), "Choice A should match");
        assertEquals("choice B", addedQuestion.getChoiceB(), "Choice B should match");
        assertEquals("choice C", addedQuestion.getChoiceC(), "Choice C should match");
        assertEquals("choice B", addedQuestion.getAnswer(), "Answer should match");
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
        question.setAnswer("choice A");
        Question addedQuestion = questionDao.addQuestion(question);

        // Delete the test question
        questionDao.deleteQuestionById(addedQuestion.getQuestionId());

        // Try to retrieve the deleted question
        List<Question> questions = jdbc.query("SELECT * FROM question WHERE questionId = ?", new QuestionMapper(), addedQuestion.getQuestionId());
        assertTrue(questions.isEmpty(), "Question should be null after deletion");
    }

    @Test
    @DisplayName("Test searching question by keyword")
    public void testSearchByKeyword() {
        // from test question 1
        String keyword = "# 1";
        List<Question> questions = questionDao.searchByKeyword(keyword);
        //Should return at least 1 result
        assertFalse(questions.isEmpty(), "Should return questions containing the keyword");

        // No question with this keyword "nonexistentword"
        keyword = "nonexistentword";
        questions = questionDao.searchByKeyword(keyword);
        //Should return empty list
        assertTrue(questions.isEmpty(), "Should not return any questions as the keyword does not exist in any question");
    }


}
