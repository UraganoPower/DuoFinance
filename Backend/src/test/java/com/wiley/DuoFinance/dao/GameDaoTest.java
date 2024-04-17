package com.wiley.DuoFinance.dao;

import com.wiley.DuoFinance.TestApplicationConfiguration;
import com.wiley.DuoFinance.mapper.GameMapper;
import com.wiley.DuoFinance.model.Answer;
import com.wiley.DuoFinance.model.Game;
import com.wiley.DuoFinance.model.Question;
import com.wiley.DuoFinance.model.User;
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
public class GameDaoTest {
    @Autowired
    UserDao userDao;
    @Autowired
    GameDao gameDao;
    @Autowired
    QuestionDao questionDao;

    @Autowired
    private JdbcTemplate jdbc;

    private Question testQuestion;
    private Game testGame;
    private Game testGame2;
    private int testUserId;

    @BeforeEach
    public void setUp() {
        //Adding a user
        User testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("testuser@gmail.com");
        testUser.setPassword("password");
        testUserId = userDao.addUser(testUser);

        //Adding a question
        testQuestion = new Question();

        testQuestion.setQuestionText("test question for game");
        testQuestion.setChoiceA("choice A");
        testQuestion.setChoiceB("choice B");
        testQuestion.setChoiceC("choice C");
        testQuestion.setAnswer("choice B");
        //Add the test question
        questionDao.addQuestion(testQuestion);

        //Adding 2 new games
        testGame = new Game();
        testGame.setScore(3);
        //Basic user -> ID =2.
        gameDao.addGame(2, testGame.getScore());

        testGame2 = new Game();

        testGame2.setScore(2);
         //Basic user -> ID =2.
        gameDao.addGame(2, testGame2.getScore());
    }

    @AfterEach
    public void tearDown() {
        jdbc.update("DELETE FROM user WHERE userId = ?", testUserId);
        jdbc.update("DELETE FROM question");
        jdbc.update("DELETE FROM game");
    }

    @Test
    @DisplayName("Test validating an answer")
    public void testIsValidateAnswer(){
        //Passing a valid answer
        Answer answer = new Answer();
        answer.setQuestionId(testQuestion.getQuestionId());
        answer.setAnswer("choice B");

        boolean result = gameDao.isValidateAnswer(answer);

        assertTrue(result, "Answer should be correct");

        //Passing an invalid answer
        Answer answer1 = new Answer();
        answer1.setQuestionId(testQuestion.getQuestionId());
        answer1.setAnswer("choice C");

        boolean result1 = gameDao.isValidateAnswer(answer1);

        assertFalse(result1, "Answer should not be correct");
    }
    @Test
    @DisplayName("Test adding a game")
    public void testAddGame() {
        Game addedgame = new Game();
        addedgame.setScore(1);

        gameDao.addGame(testUserId, addedgame.getScore());

        String query = "SELECT * FROM game WHERE userId = ?";
        Game game = jdbc.queryForObject(query, new GameMapper(), testUserId);

        assertEquals(1, game.getScore(), "Score should be equal to 1");
        assertNotEquals(2, game.getScore(), "Score should not be correct");
    }
    @Test
    @DisplayName("Test getting all games by user ID")
    public void testGetAllGameByUserId() {
        // userId = 2 has 2 games played
        List<Game> games = gameDao.getAllGameByUserId(2);

        assertNotNull(games, "Games list should not be null");
        assertFalse(games.isEmpty(), "Games list should not be empty");
        assertEquals(2, games.size(), "Games list size should be 2");
    }

    @Test
    @DisplayName("Test getting average score by user ID")
    public void testGetAverageByUserId() {
        Double average = gameDao.getAverageByUserId(2);

        assertNotNull(average, "Average should not be null");
        assertEquals((testGame.getScore() + testGame2.getScore()) / 2.0, average, "Average should be correct");
    }
}
