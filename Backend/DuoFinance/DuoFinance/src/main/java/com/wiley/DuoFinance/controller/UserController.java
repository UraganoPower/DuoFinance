package com.wiley.DuoFinance.controller;


import com.wiley.DuoFinance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/user")
    public String addUser() {
        return userService.addUser("Michael Capone");
    }
}
