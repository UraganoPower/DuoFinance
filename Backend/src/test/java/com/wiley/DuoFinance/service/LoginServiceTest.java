package com.wiley.DuoFinance.service;

import com.wiley.DuoFinance.TestApplicationConfiguration;
import com.wiley.DuoFinance.exception.CannotLoginException;
import com.wiley.DuoFinance.model.Credentials;
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
public class LoginServiceTest {

    @Autowired
    LoginService loginService;


    @Test
    @DisplayName("Test CannotLoginException")
    public void testLogin() {

        Credentials invalidCredentials = new Credentials("invalid@gmail.com", "wrongpassword");

        assertThrows(CannotLoginException.class, () -> {
            loginService.login(invalidCredentials);
        });
    }

    @Test
    @DisplayName("Test BasicRoleRequiredException")
    public void testConfirmBasicStatus() {
        String userIdHash = "S74VuQEPX5qqx2ugJwAynA"; // basic user -> id =2

        assertDoesNotThrow(() -> {
            loginService.confirmBasicStatus(userIdHash);
        });
    }

    @Test
    @DisplayName("Test AdminRoleRequiredException")
    public void testConfirmAdminStatus() {
        String userIdHash = "RVytkQgd6bE0TlDCW1vRxA"; // admin user -> id =1

        assertDoesNotThrow(() -> {
            loginService.confirmAdminStatus(userIdHash);
        });
    }
}
