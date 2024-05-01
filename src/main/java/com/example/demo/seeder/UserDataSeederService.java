package com.example.demo.seeder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * This is for testing purpose
 */
@Component
public class UserDataSeederService {
    Logger log = LoggerFactory.getLogger(UserDataSeederService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void Seed() {
        log.info("Seeding user data");

        String sql = "INSERT INTO user (username, email, password, created_at) VALUES (?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                log.info("Seeding user data: " + i);
                String username = "user" + (i + 1);
                String email = "user" + (i + 1) + "@example.com";
                String password = "password";
                LocalDateTime createdAt = LocalDateTime.now();

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);
                preparedStatement.setObject(4, createdAt);
            }

            @Override
            public int getBatchSize() {
                return 1000;
            }
        });
    }

}
