package com.wiley.DuoFinance.service;

import com.wiley.DuoFinance.dao.UserDao;
import com.wiley.DuoFinance.exception.CannotLoginException;
import com.wiley.DuoFinance.exception.EmailAlreadyTakenException;
import com.wiley.DuoFinance.exception.InvalidUserException;
import com.wiley.DuoFinance.model.User;
import com.wiley.DuoFinance.security.HashUtility;
import com.wiley.DuoFinance.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public int addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public boolean isEmailAvailable(String email) {
        return userDao.isEmailAvailable(email);
    }

    @Override
    public void validateUser(User user) throws InvalidUserException, EmailAlreadyTakenException {

        if(!UserValidator.isValidUser(user)) {
            throw new InvalidUserException(UserValidator.getErrors());
        }

        if(!this.isEmailAvailable(user.getEmail())) {
            throw new EmailAlreadyTakenException();
        }

    }

    @Override
    public User getUserById(int userId) throws CannotLoginException {

        User user;

        user = userDao.getUserById(userId);

        if(user == null) {
            throw new CannotLoginException();
        }

        return user;
    }

    @Override
    public String encryptUserId(int userId) throws Exception {

        String userIdHash;

        userIdHash = HashUtility.encrypt(String.valueOf(userId));

        return userIdHash;
    }

    @Override
    public int decryptUserId(String userIdHash) throws CannotLoginException {

        int userId;

        try {
            userId = Integer.parseInt(HashUtility.decrypt(userIdHash));
        } catch (Exception e) {
            throw new CannotLoginException();
        }

        return userId;
    }
}
