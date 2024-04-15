package com.wiley.DuoFinance.mapper;

import com.wiley.DuoFinance.model.Question;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionMapper implements RowMapper<Question> {

    @Override
    public Question mapRow(ResultSet rs, int rowNum) throws SQLException {

        Question question;

        question = new Question();

        question.setQuestionId(rs.getInt("questionId"));
        question.setQuestionText(rs.getString("questionText"));
        question.setChoiceA(rs.getString("choiceA"));
        question.setChoiceB(rs.getString("choiceC"));
        question.setChoiceC(rs.getString("choiceC"));
        question.setAnswer(rs.getString("answer"));

        return question;
    }
}
