package com.wiley.DuoFinance.service;

import com.wiley.DuoFinance.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public String addUser(String user) {
        userDao.addUser(user);
    }
}
