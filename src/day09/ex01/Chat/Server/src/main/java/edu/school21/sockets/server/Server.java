package edu.school21.sockets.server;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.services.UsersService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private final Integer port;
    private DataSource dataSource;
    private static final String PATH_SC = "src/main/resources/schema.sql";
    private static final String PATH_DB = "src/main/resources/data.sql";

    public Server(Integer port, DataSource dataSource) {
        this.port = port;
        this.dataSource = dataSource;
    }

    public Server(Integer port) {
        this.port = port;
    }

    private void createTable(){
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        getData(connection);
    }


    public void run(){
        try {

            while (true) {
                ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
                serverSocket = new ServerSocket(this.port);
                Socket socket = serverSocket.accept();
                OutputStream outputStream = socket.getOutputStream();
                InputStream inputStream = socket.getInputStream();
                PrintWriter out = new PrintWriter(outputStream, true);
                BufferedReader bufferedReaderIN = new BufferedReader(new InputStreamReader(inputStream));
                out.println("Hello from Server!");
                String message = bufferedReaderIN.readLine();

                if (message == null || !message.equals("signUp") && !message.equals("signIn")) {
                    System.err.println("Command not found");
                    out.println("Command not found");
                    outputStream.close();
                    inputStream.close();
                    serverSocket.close();
                    socket.close();
                    continue;
                }

                if (message.equals("signUp")) {
                    out.println("Enter username: ");
                    String username = bufferedReaderIN.readLine();
                    out.println("Enter password: ");
                    String password = bufferedReaderIN.readLine();
                    UsersService usersService = applicationContext.getBean(UsersService.class);

                    if (usersService.saveUser(username, password)) {
                        out.println("user exists");
                        outputStream.close();
                        inputStream.close();
                        serverSocket.close();
                        socket.close();
                        continue;
                    }

                    out.println("Successful!");
                    outputStream.close();
                    inputStream.close();
                    serverSocket.close();
                    socket.close();
                    continue;
                }

                if (message.equals("signIn")) {
                    out.println("Enter username: ");
                    String login = bufferedReaderIN.readLine();
                    out.println("Enter password: ");
                    String password = bufferedReaderIN.readLine();
                    UsersService usersService = applicationContext.getBean(UsersService.class);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getData(Connection connection) {
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

    public DataSource getDataSource() {
        return dataSource;
    }
}
