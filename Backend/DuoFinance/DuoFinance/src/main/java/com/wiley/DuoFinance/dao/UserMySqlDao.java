package com.wiley.DuoFinance.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserMySqlDao implements UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public String addUser(String user) {

        String query =
                "insert into user (username, email, password, roleId) " +
                "values (?, ?, ?, ?)";

        jdbcTemplate.update(query, )

        return user;
    }
}
