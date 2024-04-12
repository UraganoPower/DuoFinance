package com.wiley.DuoFinance.dao;

import com.wiley.DuoFinance.exception.InvalidCredentialsException;
import com.wiley.DuoFinance.mapper.UserMapper;
import com.wiley.DuoFinance.model.Credentials;
import com.wiley.DuoFinance.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginMySqlDao implements LoginDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public User login(Credentials credentials) throws InvalidCredentialsException {

        User user;

        String query = "select * from user where email = ? and password = ?";

        try {
            user = jdbcTemplate.queryForObject(query, new UserMapper(), credentials.getEmail(), credentials.getPassword());
        } catch(EmptyResultDataAccessException ex) {
            throw new InvalidCredentialsException();
        }

        return user;
    }

}
