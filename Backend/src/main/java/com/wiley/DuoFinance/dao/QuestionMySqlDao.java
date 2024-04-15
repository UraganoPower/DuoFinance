package com.wiley.DuoFinance.dao;

import com.wiley.DuoFinance.exception.QuestionUsedException;
import com.wiley.DuoFinance.mapper.QuestionMapper;
import com.wiley.DuoFinance.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class QuestionMySqlDao implements QuestionDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Question addQuestion(Question question) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = """
                insert into question (questionText, choiceA, choiceB, choiceC, answer)
                values (?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement ps = null;
            ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, question.getQuestionText());
            ps.setString(2, question.getChoiceA());
            ps.setString(3, question.getChoiceB());
            ps.setString(4, question.getChoiceC());
            ps.setString(5, question.getAnswer());

            return ps;

        }, keyHolder);

        question.setQuestionId(keyHolder.getKey().intValue());

        return question;
    }

    @Override
    public List<Question> getRandomQuestions() {

        List<Question> randomQuestions;
        String query = "select * from question order by rand() limit 3";

        randomQuestions = jdbcTemplate.query(query, new QuestionMapper());

        return randomQuestions;
    }

    @Override
    public void updateQuestion(Question question) {
        
        String query = "update question set questionText = ?, choiceA = ?, choiceB = ?, choiceC = ?, answer = ? " +
                "where questionId = ?";

        jdbcTemplate.update(query, question.getQuestionText(), question.getChoiceA(), question.getChoiceB(), question.getChoiceC(),
                question.getAnswer(), question.getQuestionId());
    }

    @Override
    public void deleteQuestionById(int questionId) throws QuestionUsedException {

        String query = "delete from question where questionId = ?";

        if(!isQuestionUsed(questionId)) {
            jdbcTemplate.update(query, questionId);
        } else {
            throw new QuestionUsedException();
        }
    }

    private boolean isQuestionUsed(int questionId) {

        int count;
        String query = "select count(*) from game_question where questionId = ?";

        count = jdbcTemplate.queryForObject(query, Integer.class, questionId);

        return count != 0;
    }
}
