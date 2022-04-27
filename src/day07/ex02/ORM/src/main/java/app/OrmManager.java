package app;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public class OrmManager {

    private static final String DB_URL = "jdbc:postgresql://localhost/";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";
    public static final String CONNECTION_ERROR = "Connection error";
    Connection connection;

    public OrmManager(){
        HikariDataSource dataSource = new HikariDataSource();
        Connection connection = null;
        dataSource.setJdbcUrl(DB_URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASS);

        try {
            connection = dataSource.getConnection();
        } catch (SQLException throwables) {
            System.err.println(CONNECTION_ERROR);
        }
    }

    public void save(Object entity){
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

    public void update(Object entity){

    }

    public <T> T findById(Long id, Class<T> aClass){

    }
}
