package com.wiley.DuoFinance.dao;

import com.wiley.DuoFinance.TestApplicationConfiguration;
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

import static org.junit.jupiter.api.Assertions.*;

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

    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    @DisplayName("Test correct credentials")
    public void testLoginSuccess() {
        Credentials credentials = new Credentials("admin@gmail.com", "password");
        User actualUser = loginDao.login(credentials);
        assertEquals(credentials.getEmail(), actualUser.getEmail(),"Email should match in the database");
    }

    @Test
    @DisplayName("Test wrong credentials")
    public void testLoginFailure() {
        Credentials credentials = new Credentials("wrong@email.com", "wrongpassword");
        User actualUser = loginDao.login(credentials);
        assertNull(actualUser, "User should be null since the records does not exist in the database");
    }

    @Test
    @DisplayName("Test if basic user")
    public void testConfirmBasicStatus() {
        int basicUserId = 2; // userId = 2 -> user (from our database)
        boolean isBasicUser = loginDao.confirmBasicStatus(basicUserId);
        assertTrue(isBasicUser, "User should have basic status");

        int nonRoleId = 1; // userId that does not match basic
        boolean isNonBasicUser = loginDao.confirmBasicStatus(nonRoleId);
        assertFalse(isNonBasicUser, "User should not have basic status");
    }

    @Test
    @DisplayName("Test if admin user")
    public void testConfirmAdminStatus() {
        int adminUserId = 1; // userId = 1 -> admin (from our database)
        boolean isAdminUser = loginDao.confirmAdminStatus(adminUserId);
        assertTrue(isAdminUser, "User should have admin status");

        int nonRoleId = 2; // userId that does not match admin
        boolean isNonAdminUser = loginDao.confirmAdminStatus(nonRoleId);
        assertFalse(isNonAdminUser, "User should not have admin status");
    }
}