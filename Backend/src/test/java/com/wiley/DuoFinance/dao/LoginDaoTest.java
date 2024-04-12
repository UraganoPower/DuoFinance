package com.wiley.DuoFinance.dao;

import com.wiley.DuoFinance.TestApplicationConfiguration;
import com.wiley.DuoFinance.exception.InvalidCredentialsException;
import com.wiley.DuoFinance.model.Credentials;
import com.wiley.DuoFinance.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
//@ActiveProfiles("test")
public class LoginDaoTest {

    @Autowired
    private LoginMySqlDao loginDao;

    @Autowired
    private JdbcTemplate jdbc;

    @BeforeEach
    public void setUp() {
        jdbc.update("INSERT INTO user (username, email, password) VALUES (?, ?, ?)", "testuser", "test@email.com", "password");
    }

    @AfterEach
    public void tearDown() {
        jdbc.update("DELETE FROM user");
    }

    @Test
    @DisplayName("Test correct credentials")
    public void testLoginSuccess() throws InvalidCredentialsException {
        Credentials credentials = new Credentials("test@email.com", "password");
        User actualUser = loginDao.login(credentials);
        assertEquals(credentials.getEmail(), actualUser.getEmail());
    }

    @Test
    @DisplayName("Test wrong credentials")
    public void testLoginFailure() {
        Credentials credentials = new Credentials("wrong@email.com", "wrongpassword");
        assertThrows(InvalidCredentialsException.class, () -> loginDao.login(credentials));
    }
}