package edu.repositories;

import edu.models.Chatroom;
import edu.models.Message;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository{

    private DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        Connection connection = null;
        long number;
        long author;
        long chatroomID;
        Timestamp timestamp;
        String text;
        Chatroom chatroom;


        try {
            connection= dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.exit(-1);
        }

        if (connection != null){
            Statement statement = null;

            try {
                statement = connection.createStatement();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            ResultSet set = null;
            try {
                set = statement.executeQuery("SELECT * FROM chat.messages WHERE id= " + id + ";" );
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                while (set.next()) {
                    number = set.getLong("id");
                    author = set.getLong("author");
                    chatroomID = set.getLong("chatroom");
                    text = set.getString("text");
                    timestamp = set.getTimestamp("time");
                    System.out.println(number + author + text + timestamp);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

        return Optional.empty();
    }
}
