package com.wiley.DuoFinance.dao;

import com.wiley.DuoFinance.model.User;

public interface UserDao {

    int addUser(User user);

    boolean isEmailAvailable(String email);
}
