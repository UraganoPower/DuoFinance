package com.wiley.DuoFinance.dao;

import com.wiley.DuoFinance.model.User;

public interface UserDao {

    User addBasicUser(User user);

    boolean isEmailAvailable(String email);
}
