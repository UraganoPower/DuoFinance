package com.wiley.DuoFinance.dao;

import com.wiley.DuoFinance.model.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GameMySqlDao implements GameDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public boolean isValidateAnswer(Answer answer) {

        int count;
        String query = "select count(*) from question where questionId = ? and answer = ?";

        count = jdbcTemplate.queryForObject(query, Integer.class, answer.getQuestionId(), answer.getAnswer());

        return count != 0;
    }
}
