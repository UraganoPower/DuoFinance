package com.wiley.DuoFinance.dao;

import com.wiley.DuoFinance.model.Role;
import com.wiley.DuoFinance.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

@Repository
public class UserMySqlDao implements UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public User addBasicUser(User basicUser) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query =
                "insert into user (username, email, password, roleId) " +
                "values (?, ?, ?, ?)";

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement ps = null;
            ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, basicUser.getUsername());
            ps.setString(2, basicUser.getEmail());
            ps.setString(3, basicUser.getPassword());
            ps.setInt(4, Role.BASIC);

            return ps;

        }, keyHolder);

        basicUser.setUserId(keyHolder.getKey().intValue());
        basicUser.setRoleId(Role.BASIC);

        return basicUser;
    }
}
