package com.wiley.DuoFinance.service;

import com.wiley.DuoFinance.TestApplicationConfiguration;
import com.wiley.DuoFinance.exception.CannotLoginException;
import com.wiley.DuoFinance.exception.EmailAlreadyTakenException;
import com.wiley.DuoFinance.exception.InvalidUserException;
import com.wiley.DuoFinance.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    @DisplayName("Test InvalidUserException")
    public void testValidateUser() {
        //Invalid username
        User invalidUser = new User();
        invalidUser.setUsername("te");
        invalidUser.setEmail("testuser@gmail.com");
        invalidUser.setPassword("password");
        assertThrows(InvalidUserException.class, () -> {
            userService.validateUser(invalidUser);
        });
        //Invalid email
        User invalidUser1 = new User();
        invalidUser1.setUsername("testuser");
        invalidUser1.setEmail("testusergmailcom");
        invalidUser1.setPassword("password");
        assertThrows(InvalidUserException.class, () -> {
            userService.validateUser(invalidUser1);
        });
        //Ivalid password
        User invalidUser2 = new User();
        invalidUser2.setUsername("testuser");
        invalidUser2.setEmail("testuser@gmail.com");
        invalidUser2.setPassword("pass");
        assertThrows(InvalidUserException.class, () -> {
            userService.validateUser(invalidUser2);
        });
    }

    @Test
    @DisplayName("Test EmailAlreadyTakenException")
    public void testValidateUserEmail() {
        User userWithTakenEmail = new User();
        userWithTakenEmail.setUsername("testuser");
        userWithTakenEmail.setEmail("basic@gmail.com");
        userWithTakenEmail.setPassword("password");
        assertThrows(EmailAlreadyTakenException.class, () -> {
            userService.validateUser(userWithTakenEmail);
        });
    }

    @Test
    @DisplayName("Test EmailAlreadyTakenException")
    public void testValidateUserUpdate() {
        //Invalid username
        User invalidUser = new User();
        invalidUser.setUsername("tes");
        invalidUser.setEmail("testuser@gmail.com");
        invalidUser.setPassword("password");
        assertThrows(InvalidUserException.class, () -> {
            userService.validateUserUpdate(invalidUser);
        });
    }

    @Test
    @DisplayName("Test Get User by id")
    public void testGetUserById() {

        assertDoesNotThrow(() -> {
            User user = userService.getUserById(1); //userId =1 -> admin
            assertNotNull(user, "User should not be null");
        });

        int invalidUserId = -1; //userId not in our database

        assertThrows(CannotLoginException.class, () -> {
            userService.getUserById(invalidUserId);
        });

    }

}
