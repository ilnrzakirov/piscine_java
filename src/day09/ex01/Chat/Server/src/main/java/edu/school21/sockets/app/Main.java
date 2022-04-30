package edu.school21.sockets.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.repositories.UsersRepository;
import edu.school21.sockets.server.MultiThreadServer;
import edu.school21.sockets.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Main {

    private static final String DB_URL = "jdbc:postgresql://localhost/";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";
    public static LinkedList<MultiThreadServer> serverList = new LinkedList<>();

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

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true){
                Socket socket =serverSocket.accept();
                new MultiThreadServer(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
