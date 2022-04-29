package edu.school21.sockets.server;

import edu.school21.sockets.models.User;
import edu.school21.sockets.services.UsersService;
import edu.school21.sockets.services.UsersServiceImpl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Integer port;

    public Server(Integer port) {
        this.port = port;
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
            out.println("Hello from Server!");
            BufferedReader bufferedReaderIN = new BufferedReader(new InputStreamReader(inputStream));
            String message = bufferedReaderIN.readLine();

            while (!message.equals("signUp")){
                message = bufferedReaderIN.readLine();
            }

            out.println("Enter username: ");
            message = bufferedReaderIN.readLine();

            while (message.isEmpty()){
                message = bufferedReaderIN.readLine();
            }

            user.setUsername(message);
            out.println("Enter password: ");
            message = bufferedReaderIN.readLine();

            while (message.isEmpty()){
                message = bufferedReaderIN.readLine();
            }

            user.setPassword(usersService.hashPassword(message));
            out.println("Successful!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
