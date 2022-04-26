package edu.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.models.Chatroom;
import edu.models.Message;
import edu.models.User;
import edu.repositories.MessagesRepositoryJdbcImpl;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Program {

    private static final String DB_URL = "jdbc:postgresql://localhost/";
    private static final String USER = "postgres";
    private static final String PASS = "";
    private static final String PATH_SC = "src/main/resources/schema.sql";
    private static final String PATH_DB = "src/main/resources/data.sql";

    public static void main(String[] args) {

        HikariDataSource dataSource = new HikariDataSource();
        Connection connection = null;
        dataSource.setJdbcUrl(DB_URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASS);

        try {
            connection = dataSource.getConnection();

            if (connection == null){
                System.err.println("Failed to make connection to database");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        getData(connection);
        MessagesRepositoryJdbcImpl messagesRepositoryJdbc = new MessagesRepositoryJdbcImpl(dataSource);
        User user1 = new User(2, "Banny", "pass");
        Chatroom chatroom = new Chatroom(1, "random");
        Message message = new Message(null, user1, chatroom, "message mesge", Timestamp.valueOf(LocalDateTime.now()));
        Message message1 = new Message(null, user1, chatroom, "messsge", Timestamp.valueOf(LocalDateTime.now()));
        messagesRepositoryJdbc.save(message);
        messagesRepositoryJdbc.save(message1);
        System.out.println(message.getId());
        System.out.println(message1.getId());
        dataSource.close();
    }

    private static void getData(Connection connection) {
        Statement statement = null;

        try {
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        List<String> allLineSchema = null;
        List<String> allLineData = null;

        try {
            allLineSchema = Files.readAllLines(Paths.get(PATH_SC));
            allLineData = Files.readAllLines(Paths.get(PATH_DB));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        if (allLineData  == null || allLineSchema == null){
            System.err.println("Empty file");
            System.exit(-1);
        }

        for (String line : allLineSchema) {

            try {
                statement.execute(line);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.exit(-1);
            }
        }

        for (String line : allLineData) {

            try {
                statement.execute(line);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.exit(-1);
            }
        }

    }

}
