package edu.repositories;

import edu.models.Chatroom;
import edu.models.Message;
import edu.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository{

    private DataSource dataSource;
    private static final String ERROR = "SQL Error";
    private static final String MNF = "Massage not found";

    public MessagesRepositoryJdbcImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        Connection connection = null;
        long number = 0;
        long author = 0;
        long chatroomID = 0;
        Timestamp timestamp = null;
        String text = null;
        User user1 = null;
        Chatroom chatroom = null;


        try {
            connection= dataSource.getConnection();
        } catch (SQLException throwables) {
            System.err.println(ERROR);
            System.exit(-1);
        }

        Message message = null;

        if (connection != null){
            Statement statement = null;

            try {
                statement = connection.createStatement();
            } catch (SQLException throwables) {
                System.err.println(ERROR);
                System.exit(-1);
            }

            ResultSet setMessage = null;
            try {
                setMessage = statement.executeQuery("SELECT * FROM chat.messages WHERE id= " + id + ";" );
            } catch (SQLException throwables) {
                System.err.println(MNF);
                System.exit(-1);
            }

            try {
                setMessage.next();
                number = setMessage.getLong("id");
                author = setMessage.getLong("author");
                chatroomID = setMessage.getLong("chatroom");
                text = setMessage.getString("text");
                timestamp = setMessage.getTimestamp("time");
            } catch (SQLException | NullPointerException throwables) {
                System.err.println(MNF);
                System.exit(-1);
            }

            ResultSet setUser = null;

            try {
                setUser = statement.executeQuery("SELECT * FROM chat.users WHERE id= " + author + ";");
            } catch (SQLException throwables) {
                System.err.println(ERROR);
                System.exit(-1);
            }

            try {
                setUser.next();
                String login = setUser.getString("login");
                String password = setUser.getString("password");
                user1 = new User(author, login, password);
            } catch (SQLException throwables) {
                System.err.println(ERROR);
                System.exit(-1);
            }

            ResultSet setRoom = null;

            try {
                setRoom = statement.executeQuery("SELECT * FROM chat.rooms WHERE id= " + chatroomID + ";");
                setRoom.next();
                String name = setRoom.getString("name");
                chatroom = new Chatroom(chatroomID, name);
            } catch (SQLException throwables) {
                System.err.println(ERROR);
                System.exit(-1);
            }

            message = new Message(number, user1, chatroom, text, timestamp);
            return Optional.of(message);
        }

        return Optional.empty();
    }
}
