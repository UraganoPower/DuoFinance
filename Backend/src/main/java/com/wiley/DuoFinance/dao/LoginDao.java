package com.wiley.DuoFinance.dao;

import com.wiley.DuoFinance.model.Credentials;
import com.wiley.DuoFinance.model.User;

public interface LoginDao {

    User login(Credentials credentials);

    boolean confirmBasicStatus(int userId);

    boolean confirmAdminStatus(int userId);
}
