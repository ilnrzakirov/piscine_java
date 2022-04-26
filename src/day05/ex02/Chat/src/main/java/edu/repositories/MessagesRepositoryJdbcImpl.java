package edu.repositories;

import edu.app.NotSavedSubEntityException;
import edu.models.Chatroom;
import edu.models.Message;
import edu.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {

    private DataSource dataSource;
    private static final String ERROR = "SQL Error";
    private static final String MNF = "Massage not found";
    private static final String REQUEST = "INSERT INTO chat.messages (author, chatroom, text, time) VALUES (?, ?, ?, current_timestamp);";
    private static final String REQUEST_ALL_MESSAGE = "SELECT * FROM chat.messages";

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
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
            connection = dataSource.getConnection();
        } catch (SQLException throwables) {
            return Optional.empty();
        }

        Message message = null;

        if (connection != null) {
            Statement statement = null;

            try {
                statement = connection.createStatement();
            } catch (SQLException throwables) {
                return Optional.empty();
            }

            ResultSet setMessage = null;
            try {
                setMessage = statement.executeQuery("SELECT * FROM chat.messages WHERE id= " + id + ";");
            } catch (SQLException throwables) {
                return Optional.empty();
            }

            try {
                setMessage.next();
                number = setMessage.getLong("id");
                author = setMessage.getLong("author");
                chatroomID = setMessage.getLong("chatroom");
                text = setMessage.getString("text");
                timestamp = setMessage.getTimestamp("time");
            } catch (SQLException | NullPointerException throwables) {
                return Optional.empty();
            }

            ResultSet setUser = null;

            try {
                setUser = statement.executeQuery("SELECT * FROM chat.users WHERE id= " + author + ";");
            } catch (SQLException throwables) {
                return Optional.empty();
            }

            try {
                setUser.next();
                String login = setUser.getString("login");
                String password = setUser.getString("password");
                user1 = new User(author, login, password);
            } catch (SQLException throwables) {
                return Optional.empty();
            }

            ResultSet setRoom = null;

            try {
                setRoom = statement.executeQuery("SELECT * FROM chat.rooms WHERE id= " + chatroomID + ";");
                setRoom.next();
                String name = setRoom.getString("name");
                chatroom = new Chatroom(chatroomID, name);
            } catch (SQLException throwables) {
                return Optional.empty();
            }

            message = new Message(number, user1, chatroom, text, timestamp);
            return Optional.of(message);
        }

        return Optional.empty();
    }

    @Override
    public void save(Message message) {
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.err.println(ERROR);
            System.exit(-1);
        }

        User userMessage = message.getAuthor();
        Chatroom chatroom = message.getRoom();
        String messageText = message.getText();
        PreparedStatement statement = null;

        if (connection == null) {
            System.err.println(ERROR);
            System.exit(-1);
        }
        try {
            statement = connection.prepareStatement(REQUEST);
            statement.setLong(1, userMessage.getId());
            statement.setLong(2, chatroom.getId());
            statement.setString(3, messageText);
            statement.execute();
            Statement statement1 = connection.createStatement();
            ResultSet newMessageSet;
            newMessageSet = statement1.executeQuery(REQUEST_ALL_MESSAGE);
            while (newMessageSet.next()) {
                if (newMessageSet.isLast()) {
                    break;
                }
            }
            Long newId = newMessageSet.getLong("id");
            message.setId(newId);
        } catch (SQLException throwables) {
            throw new NotSavedSubEntityException("Not Saved");
        }
    }
}