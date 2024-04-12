package com.wiley.DuoFinance.dao;

import com.wiley.DuoFinance.TestApplicationConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
//@ActiveProfiles("test")
public class UserDaoTest {

    @Autowired
    private UserDaoTest userDao;

    @Autowired
    private JdbcTemplate jdbc;
}
