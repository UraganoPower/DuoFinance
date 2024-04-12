package com.wiley.DuoFinance.dao;

import com.wiley.DuoFinance.mapper.UserMapper;
import com.wiley.DuoFinance.model.Role;
import com.wiley.DuoFinance.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
    public int addUser(User user) {

        int userId;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query =
                "insert into user (username, email, password, roleId) " +
                "values (?, ?, ?, ?)";

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement ps = null;
            ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail().toLowerCase());
            ps.setString(3, user.getPassword());
            ps.setInt(4, Role.BASIC);

            return ps;

        }, keyHolder);

        userId = keyHolder.getKey().intValue();

        return userId;
    }

    @Override
    public boolean isEmailAvailable(String email) {

        String query = "select not exists(select * from user where email = ?)";

        return jdbcTemplate.queryForObject(query, Boolean.class, email);
    }

    @Override
    public User getUserById(int userId) {

        User user;

        String query = """
                select *
                from user
                where userId = ?;
                """;

        try {
            user = jdbcTemplate.queryForObject(query, new UserMapper(), userId);
        } catch(EmptyResultDataAccessException ex) {
            user = null;
        }

        return user;
    }

    @Override
    public void deleteUserById(int userId) {

        String query = "delete from user where userId = ?";

        jdbcTemplate.update(query, userId);
    }
}
