package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class MessageRepositoryImpl implements MessageRepository{

    private JdbcTemplate jdbcTemplate;

    public MessageRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean save(String message) {
        jdbcTemplate.update("INSERT INTO message (datetime, text) VALUES (current_timestamp, ?)", message);
        return true;
    }
}
