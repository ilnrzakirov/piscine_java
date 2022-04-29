package edu.school21.sockets.server;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepositoryImpl;
import edu.school21.sockets.services.UsersServiceImpl;

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

    public void run(){
        try {
            User user = new User();
            UsersServiceImpl usersService = new UsersServiceImpl();
            serverSocket = new ServerSocket(this.port);
            Socket socket = serverSocket.accept();
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            PrintWriter out = new PrintWriter(outputStream, true);
            BufferedReader bufferedReaderIN = new BufferedReader(new InputStreamReader(inputStream));

            out.println("Hello from Server!");
            String message = bufferedReaderIN.readLine();
            if (!message.equals("signUp")){
                System.err.println("Command not found");
                out.println("Command not found");
                serverSocket.close();
                outputStream.close();
                inputStream.close();
                System.exit(-1);
            }

            out.println("Enter username: ");
            String username = bufferedReaderIN.readLine();
            out.println("Enter password: ");
            String password = bufferedReaderIN.readLine();
            out.println("Successful!");
            user.setUsername(username);
            user.setPassword(usersService.hashPassword(password));
            UsersRepositoryImpl usersRepository = new UsersRepositoryImpl(dataSource);
            usersRepository.save(user);
            serverSocket.close();
            outputStream.close();
            inputStream.close();

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
