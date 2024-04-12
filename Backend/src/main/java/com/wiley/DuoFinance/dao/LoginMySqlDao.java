package com.wiley.DuoFinance.dao;

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
    public User login(Credentials credentials) {

        User user;

        String query = "select * from user where email = ? and password = ?";

        try {
            user = jdbcTemplate.queryForObject(query, new UserMapper(), credentials.getEmail(), credentials.getPassword());
        } catch(EmptyResultDataAccessException ex) {
            user = null;
        }

        return user;
    }

    @Override
    public boolean confirmBasicStatus(int userId) {

        String query = "select exists (select * from user join role on user.roleId = role.roleId where user.userId = ? and role.roleId = ?)";


        return jdbcTemplate.queryForObject(query, Boolean.class, userId, 2);
    }

    @Override
    public boolean confirmAdminStatus(int userId) {

        String query = "select exists (select * from user join role on user.roleId = role.roleId where user.userId = ? and role.roleId = ?)";

        return jdbcTemplate.queryForObject(query, Boolean.class, userId, 1);
    }
}
