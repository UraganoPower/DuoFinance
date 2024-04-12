package com.wiley.DuoFinance.service;


import com.wiley.DuoFinance.exception.CannotLoginException;
import com.wiley.DuoFinance.exception.EmailAlreadyTakenException;
import com.wiley.DuoFinance.exception.InvalidUserException;
import com.wiley.DuoFinance.model.User;

public interface UserService {

    int addUser(User user);
    boolean isEmailAvailable(String email);
    void validateUser(User user) throws InvalidUserException, EmailAlreadyTakenException;

    User getUserById(int userId) throws CannotLoginException;

    int decryptUserId(String userIdHash) throws CannotLoginException;

    String encryptUserId(int userId) throws Exception;
}
