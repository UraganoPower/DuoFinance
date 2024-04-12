package com.wiley.DuoFinance.service;

import com.wiley.DuoFinance.dao.UserDao;
import com.wiley.DuoFinance.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User addBasicUser(User user) {
        return userDao.addBasicUser(user);
    }

    @Override
    public boolean isEmailAvailable(String email) {
        return userDao.isEmailAvailable(email);
    }
}
