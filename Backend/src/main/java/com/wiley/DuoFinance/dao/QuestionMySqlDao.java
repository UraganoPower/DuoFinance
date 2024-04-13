package com.wiley.DuoFinance.dao;

import com.wiley.DuoFinance.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
}
