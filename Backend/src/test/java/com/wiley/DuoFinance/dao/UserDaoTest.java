package com.wiley.DuoFinance.dao;

import com.wiley.DuoFinance.TestApplicationConfiguration;
import com.wiley.DuoFinance.model.Role;
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
public class UserDaoTest {

    @Autowired
    private UserMySqlDao userDao;

    @Autowired
    private JdbcTemplate jdbc;

    private int testUserId;
    @BeforeEach
    public void setUp() {
        // Insert a test user before each test
        User testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("testuser@gmail.com");
        testUser.setPassword("password");
        testUserId = userDao.addUser(testUser);
    }

    @AfterEach
    public void tearDown() {
        // Delete the test user after each test
        userDao.deleteUserById(testUserId);
    }
    @Test
    @DisplayName("Test adding a user")
    public void testAddUser(){
        //Creating a user to be added
        User user = new User();
        user.setUsername("test1");
        user.setEmail("test1@email.com");
        user.setPassword("password");
        int userId = userDao.addUser(user);
        //If userId positive it means that it was created
        assertTrue(userId > 0, "userId should be positive");

        User insertedUser = userDao.getUserById(userId);
        assertEquals(user.getUsername(), insertedUser.getUsername(), "Inserted user's username should match the original");
        assertEquals(user.getEmail(), insertedUser.getEmail(), "Inserted user's email should match the original");
        userDao.deleteUserById(userId); //deleting it so it doesnt save in our database
    }
    @Test
    @DisplayName("Test deleting a user by ID")
    public void testDeleteUserById() {
        User user = new User();
        user.setUsername("test2");
        user.setEmail("test2@email.com");
        user.setPassword("password");
        int userId = userDao.addUser(user);

        userDao.deleteUserById(userId);

        User deletedUser = userDao.getUserById(userId);
        assertNull(deletedUser, "User should be null after deletion");
    }
    @Test
    @DisplayName("Test checking email availability")
    public void testIsEmailAvailable() {
        String existingEmail = "admin@gmail.com"; //Available in our database
        boolean isAvailable = userDao.isEmailAvailable(existingEmail);
        assertFalse(isAvailable, "Email should be already taken");

        String nonExistingEmail = "nonexistent@email.com"; //non existent is our database
        boolean notAvailable = userDao.isEmailAvailable(nonExistingEmail);
        assertTrue(notAvailable, "Email should be available to create");
    }

    @Test
    @DisplayName("Test getting a user by ID")
    public void testGetUserById() {
        User user = userDao.getUserById(testUserId);
        assertNotNull(user, "User should not be null");

        int nonExistentUserId = 9999; // ID not available in our database
        user = userDao.getUserById(nonExistentUserId);
        assertNull(user, "User should be null");
    }

}

