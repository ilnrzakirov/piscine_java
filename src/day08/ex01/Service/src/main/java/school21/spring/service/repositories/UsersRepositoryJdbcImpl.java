package school21.spring.service.repositories;

import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements  UsersRepository {

    private DataSource dataSource;

    @Override
    public User findById(Long id) {
        User user = new User();
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            StringBuilder stringBuilder = new StringBuilder();
            String email;
            stringBuilder.append("SELECT * FROM users WHERE id=" + id + ";");
            ResultSet userSet;
            userSet = statement.executeQuery(stringBuilder.toString());
            userSet.next();
            email = userSet.getString("email");
            user.setId(id);
            user.setEmail(email);
            return user;

        } catch (SQLException throwables) {
            System.err.println("User not found");
        }
        return null;
    }

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List <User> findAll() {
        List <User> userList = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            StringBuilder stringBuilder = new StringBuilder();
            String email;
            stringBuilder.append("SELECT * FROM users;");
            ResultSet userSet;
            userSet = statement.executeQuery(stringBuilder.toString());
            while (userSet.next()){
                User user = new User();
                user.setId(userSet.getLong("id"));
                user.setEmail(userSet.getString("email"));
                userList.add(user);
            }

            return userList;

        } catch (SQLException throwables) {
            System.err.println("User not found");
        }
        return null;
    }

    @Override
    public void save(User entity) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("INSERT INTO users (email) VALUES ('" + entity.getEmail() + "');");
            statement.execute(stringBuilder.toString());
        } catch (SQLException throwables) {
            System.err.println("Fail");
        }
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional findByEmail(String email) {
        return Optional.empty();
    }
}
