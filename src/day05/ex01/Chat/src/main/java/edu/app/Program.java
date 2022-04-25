package edu.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.models.Message;
import edu.repositories.MessagesRepositoryJdbcImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Program {

    private static final String DB_URL = "jdbc:postgresql://localhost/";
    private static final String USER = "postgres";
    private static final String PASS = "";
    private static final String PATH_SC = "src/main/resources/schema.sql";
    private static final String PATH_DB = "src/main/resources/data.sql";
    private static final String PR = "enter the id";
    private static final String MNF = "Message not found";

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
        System.out.println(PR);
        Scanner scanner = new Scanner(System.in);
        Long messageID = 0L;

        try {
            messageID = scanner.nextLong();
        } catch (InputMismatchException error){
            System.err.println(MNF);
            System.exit(-1);
        }

        MessagesRepositoryJdbcImpl messagesRepositoryJdbc = new MessagesRepositoryJdbcImpl(dataSource);

        if (messagesRepositoryJdbc.findById(messageID).isPresent()) {
            Message message = messagesRepositoryJdbc.findById(messageID).get();
            System.out.println(message);
        }

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
