package edu.repositories;

import edu.models.Message;

import javax.sql.DataSource;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository{

    private DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        return Optional.empty();
    }
}
