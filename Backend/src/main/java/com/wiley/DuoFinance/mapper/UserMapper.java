package com.wiley.DuoFinance.mapper;

import com.wiley.DuoFinance.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        User user = new User();

        user.setUserId(null);
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setRoleId(rs.getInt("roleId"));

        return user;
    }
}
