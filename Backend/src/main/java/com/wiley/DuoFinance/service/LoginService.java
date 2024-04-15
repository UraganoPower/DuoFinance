package com.wiley.DuoFinance.service;

import com.wiley.DuoFinance.exception.AdminRoleRequiredException;
import com.wiley.DuoFinance.exception.BasicRoleRequiredException;
import com.wiley.DuoFinance.exception.CannotLoginException;
import com.wiley.DuoFinance.model.Answer;
import com.wiley.DuoFinance.model.Credentials;
import com.wiley.DuoFinance.model.User;

import java.util.List;

public interface LoginService {

    User login(Credentials credentials) throws CannotLoginException;

    void confirmBasicStatus(String userIdHash) throws CannotLoginException, BasicRoleRequiredException;

    void confirmAdminStatus(String userIdHash) throws CannotLoginException, AdminRoleRequiredException;
}
