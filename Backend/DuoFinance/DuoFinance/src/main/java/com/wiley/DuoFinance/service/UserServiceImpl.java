package com.wiley.DuoFinance.service;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public String addUser(String user) {
        return "The user has been created.";
    }
}
