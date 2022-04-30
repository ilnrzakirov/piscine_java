package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static java.sql.Types.BIGINT;
import static java.sql.Types.VARCHAR;

public class UsersRepositoryImpl implements UsersRepository {
    private JdbcTemplate jdbcTemplate;

    public UsersRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM users WHERE id=?", new Object[]{id}, new int[]{BIGINT}, new BeanPropertyRowMapper<>(User.class)).stream().findAny().orElse(null);
    }


    @Override
    public List <User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users", new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void save(User entity) {
        jdbcTemplate.update("INSERT INTO users (username, password) VALUES (?, ?)", entity.getUsername(), entity.getPassword());
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update("UPDATE users SET username=? WHERE id=?", entity.getUsername(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM users WHERE id=?", id);
    }

    @Override
    public Optional <User> findByUsername(String username) {
        return jdbcTemplate.query("SELECT * FROM users WHERE username=?", new Object[]{username}, new int[]{VARCHAR}, new BeanPropertyRowMapper<>(User.class)).stream().findAny();
    }
}
