package app;

import javafx.beans.binding.When;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static final String SERVER_PORT = "--server-port=";
    public static final String BAD_ARGUMENT = "Bad argument";
    public static final String THE_ARGUMENT_IS_NOT_A_NUMBER = "the argument is not a number";
    public static final String LOCALHOST = "localhost";
    public static final String SIGN_UP = "signUp";
    public static final String USER_EXISTS = "user exists";
    public static final String USER_EXISTS1 = "user exists";
    public static final String SIGN_IN = "signIn";
    public static final String INCORRECT_PASSWORD = "incorrect password";
    public static final String EXIT = "Exit";
    public static final String COMMAND_NOT_FOUND = "Command not found";

    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith(SERVER_PORT)){
            System.err.println(BAD_ARGUMENT);
            System.exit(-1);
        }

        String remString = args[0].replaceFirst(SERVER_PORT, "");
        Integer port = null;
        try {
            port = Integer.parseInt(remString);
        } catch (NumberFormatException error){
            System.err.println(THE_ARGUMENT_IS_NOT_A_NUMBER);
            System.exit(-1);
        }

        try {
            Socket socket = new Socket(LOCALHOST, port);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream, true);
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String answer = bufferedReader.readLine();
            System.out.println(answer);

            Scanner scanner = new Scanner(System.in);
            String type = scanner.nextLine();

            if (type.equals(SIGN_UP)) {
                printWriter.println(type);
                answer = bufferedReader.readLine();
                System.out.println(answer);

                String username = scanner.nextLine();
                printWriter.println(username);
                answer = bufferedReader.readLine();
                System.out.println(answer);

                String password = scanner.nextLine();
                printWriter.println(password);
                answer = bufferedReader.readLine();

                if (answer.equals(USER_EXISTS)) {
                    shutdown(inputStream, outputStream, socket, scanner);
                    System.err.println(USER_EXISTS1);
                    System.exit(-1);
                }
                System.out.println(answer);
                shutdown(inputStream, outputStream, socket, scanner);
                System.exit(-1);
            }

            if (type.equals(SIGN_IN)){
                printWriter.println(type);
                answer = bufferedReader.readLine();
                System.out.println(answer);

                String username = scanner.nextLine();
                printWriter.println(username);
                answer = bufferedReader.readLine();
                System.out.println(answer);

                String password = scanner.nextLine();
                printWriter.println(password);
                answer = bufferedReader.readLine();
                System.out.println(answer);

                if (answer.equals(INCORRECT_PASSWORD)){
                    shutdown(inputStream, outputStream, socket, scanner);
                    System.exit(-1);
                }

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                String str = bufferedReader.readLine();
                                if (str == null){
                                    shutdown(inputStream, outputStream, socket, scanner);
                                    System.exit(-1);
                                }
                                System.out.println(str);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                thread.start();

                while (true){
                    String msg = scanner.nextLine();
                    printWriter.println(msg);
                    if (msg.equals(EXIT)){
                        shutdown(inputStream, outputStream, socket, scanner);
                        System.exit(-1);
                    }
                }
            }

            System.err.println(COMMAND_NOT_FOUND);
            shutdown(inputStream, outputStream, socket, scanner);
            System.exit(-1);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void shutdown(InputStream inputStream, OutputStream outputStream, Socket socket, Scanner scanner) {
        try {
            socket.close();
            inputStream.close();
            outputStream.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
