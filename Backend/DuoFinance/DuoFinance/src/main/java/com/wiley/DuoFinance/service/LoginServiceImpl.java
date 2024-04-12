package com.wiley.DuoFinance.service;

import com.wiley.DuoFinance.dao.LoginDao;
import com.wiley.DuoFinance.exception.InvalidCredentialsException;
import com.wiley.DuoFinance.model.Credentials;
import com.wiley.DuoFinance.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    LoginDao loginDao;

    @Override
    public User login(Credentials credentials) throws InvalidCredentialsException {
        return loginDao.login(credentials);
    }
}
