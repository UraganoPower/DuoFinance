package com.wiley.DuoFinance.service;

import com.wiley.DuoFinance.dao.LoginDao;
import com.wiley.DuoFinance.exception.AdminRoleRequiredException;
import com.wiley.DuoFinance.exception.BasicRoleRequiredException;
import com.wiley.DuoFinance.exception.CannotLoginException;
import com.wiley.DuoFinance.model.Credentials;
import com.wiley.DuoFinance.model.User;
import com.wiley.DuoFinance.validation.CredentialsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    LoginDao loginDao;

    @Autowired
    UserService userService;

    @Override
    public User login(Credentials credentials) throws CannotLoginException {

        User user;

        if(!CredentialsValidator.isValidCredentials(credentials)) {
            throw new CannotLoginException();
        }

        user = loginDao.login(credentials);

        if(user == null) {
            throw new CannotLoginException();
        }

        return user;
    }

    @Override
    public void confirmBasicStatus(String userIdHash) throws CannotLoginException, BasicRoleRequiredException {

        int userId;

        userId = userService.decryptUserId(userIdHash);

        if(!loginDao.confirmBasicStatus(userId)) {
            throw new BasicRoleRequiredException();
        }
    }

    @Override
    public void confirmAdminStatus(String userIdHash) throws CannotLoginException, AdminRoleRequiredException {
        int userId;

        userId = userService.decryptUserId(userIdHash);

        if(!loginDao.confirmAdminStatus(userId)) {
            throw new AdminRoleRequiredException();
        }
    }
}
