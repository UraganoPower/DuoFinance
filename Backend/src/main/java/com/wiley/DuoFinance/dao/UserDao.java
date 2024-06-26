package com.wiley.DuoFinance.dao;

import com.wiley.DuoFinance.model.User;

public interface UserDao {

    int addUser(User user);

    boolean isEmailAvailable(String email);

    User getUserById(int userId);

    void deleteUserById(int userId);

    void updateUser(int userId, User user);
}
