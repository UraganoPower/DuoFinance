package com.wiley.DuoFinance.service;

import com.wiley.DuoFinance.exception.InvalidCredentialsException;
import com.wiley.DuoFinance.model.Credentials;
import com.wiley.DuoFinance.model.User;

public interface LoginService {

    User login(Credentials credentials) throws InvalidCredentialsException;

}
