package edu.school21.sockets.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.repositories.UsersRepository;
import edu.school21.sockets.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    private static final String DB_URL = "jdbc:postgresql://localhost/";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";

    public static void main(String[] args) {
        if (args.length != 1 || args[0].isEmpty() || !args[0].startsWith("--port=")) {
            System.err.println("Bad argument");
            System.exit(-1);
        }

        String remString = args[0].replaceFirst("--port=", "");
        Integer port = null;
        try {
            port = Integer.parseInt(remString);
        } catch (NumberFormatException error){
            System.err.println("the argument is not a number");
            System.exit(-1);
        }

//        HikariDataSource dataSource = new HikariDataSource();
//        Connection connection = null;
//        dataSource.setJdbcUrl(DB_URL);
//        dataSource.setUsername(USER);
//        dataSource.setPassword(PASS);

        Server server = new Server(port);

        server.run();

    }
}
