package edu.school21.sockets.server;

import edu.school21.sockets.app.Main;
import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.services.UsersService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MultiThreadServer extends Thread {

    private  Socket serverSocket;

    public MultiThreadServer(Socket socket) {
        this.serverSocket = socket;
        start();
    }

    @Override
    public void run() {
        try {

            while (true) {
                ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
                OutputStream outputStream = serverSocket.getOutputStream();
                InputStream inputStream = serverSocket.getInputStream();
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
                    break;
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
                        break;
                    }

                    out.println("Successful!");
                    outputStream.close();
                    inputStream.close();
                    serverSocket.close();
                    break;
                }

                if (message.equals("signIn")) {
                    Main.serverList.add(this);
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
}
