package edu.school21.sockets.server;

import edu.school21.sockets.app.Main;
import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.services.MessageService;
import edu.school21.sockets.services.UsersService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class MultiThreadServer extends Thread {

    public static final String HELLO_FROM_SERVER = "Hello from Server!";
    public static final String SIGN_UP = "signUp";
    public static final String SIGN_IN = "signIn";
    public static final String COMMAND_NOT_FOUND = "Command not found";
    public static final String COMMAND_NOT_FOUND1 = "Command not found";
    public static final String SIGN_UP1 = "signUp";
    public static final String ENTER_USERNAME = "Enter username: ";
    public static final String ENTER_PASSWORD = "Enter password: ";
    public static final String USER_EXISTS = "user exists";
    public static final String SUCCESSFUL = "Successful!";
    public static final String ENTER_USERNAME1 = "Enter username: ";
    public static final String ENTER_PASSWORD1 = "Enter password: ";
    public static final String START_MESSAGING = "Start messaging";
    public static final String EXIT = "Exit";
    public static final String YOU_HAVE_LEFT_THE_CHAT = "You have left the chat.";
    public static final String INCORRECT_PASSWORD = "incorrect password";
    public static final String INCORRECT_LOGIN = "incorrect login";
    private  Socket serverSocket;
    private static ReentrantLock lock = new ReentrantLock();
    private PrintWriter out;

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
                out = new PrintWriter(outputStream, true);
                BufferedReader bufferedReaderIN = new BufferedReader(new InputStreamReader(inputStream));
                out.println(HELLO_FROM_SERVER);
                String message = bufferedReaderIN.readLine();

                if (message == null || !message.equals(SIGN_UP) && !message.equals(SIGN_IN)) {
                    System.err.println(COMMAND_NOT_FOUND);
                    out.println(COMMAND_NOT_FOUND1);
                    shutdown(inputStream, outputStream, serverSocket);;
                    break;
                }

                if (message.equals(SIGN_UP1)) {
                    out.println(ENTER_USERNAME);
                    String username = bufferedReaderIN.readLine();
                    out.println(ENTER_PASSWORD);
                    String password = bufferedReaderIN.readLine();
                    UsersService usersService = applicationContext.getBean(UsersService.class);

                    if (usersService.saveUser(username, password)) {
                        out.println(USER_EXISTS);
                        shutdown(inputStream, outputStream, serverSocket);
                        break;
                    }

                    out.println(SUCCESSFUL);
                    shutdown(inputStream, outputStream, serverSocket);
                    break;
                }

                if (message.equals("signIn")) {
                    out.println(ENTER_USERNAME1);
                    String login = bufferedReaderIN.readLine();
                    out.println(ENTER_PASSWORD1);
                    String password = bufferedReaderIN.readLine();
                    UsersService usersService = applicationContext.getBean(UsersService.class);
                    MessageService messageService = applicationContext.getBean(MessageService.class);

                    if (usersService.checkUser(login, password)){
                        if (usersService.singIn(login, password)){
                            lock.lock();
                            Main.serverList.add(this);
                            lock.unlock();
                            out.println(START_MESSAGING);

                            while (true){
                                String msg = bufferedReaderIN.readLine();

                                if (msg.equals(EXIT)){
                                    Main.serverList.remove(this);
                                    out.println(YOU_HAVE_LEFT_THE_CHAT);
                                    shutdown(inputStream, outputStream, serverSocket);
                                    break;
                                }
                                messageService.saveMessage(msg);
                                for (MultiThreadServer multiThreadServer : Main.serverList) {
                                    multiThreadServer.sendMsg(msg);
                                }
                            }
                        } else {
                            out.println(INCORRECT_PASSWORD);
                            shutdown(inputStream, outputStream, serverSocket);
                            break;
                        }
                    } else {
                        out.println(INCORRECT_LOGIN);
                        shutdown(inputStream, outputStream, serverSocket);
                        break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shutdown(InputStream inputStream, OutputStream outputStream, Socket serverSocket) {
        try {
            outputStream.close();
            inputStream.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMsg(String msg){
        out.println(msg);
    }

}
