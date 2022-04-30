package app;

import javafx.beans.binding.When;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--server-port=")){
            System.err.println("Bad argument");
            System.exit(-1);
        }

        String remString = args[0].replaceFirst("--server-port=", "");
        Integer port = null;
        try {
            port = Integer.parseInt(remString);
        } catch (NumberFormatException error){
            System.err.println("the argument is not a number");
            System.exit(-1);
        }

        try {
            Socket socket = new Socket("localhost", port);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream, true);
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String answer = bufferedReader.readLine();
            System.out.println(answer);

            Scanner scanner = new Scanner(System.in);
            String type = scanner.nextLine();

            if (type.equals("signUp")) {
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

                if (answer.equals("user exists")) {
                    shutdown(inputStream, outputStream, socket, scanner);
                    System.err.println("user exists");
                    System.exit(-1);
                }
                System.out.println(answer);
                shutdown(inputStream, outputStream, socket, scanner);
                System.exit(-1);
            }

            if (type.equals("signIn")){
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

                if (answer.equals("incorrect password")){
                    shutdown(inputStream, outputStream, socket, scanner);
                    System.exit(-1);
                }

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                String str = bufferedReader.readLine();
                                System.out.println(str);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                while (true){
                    String msg = scanner.nextLine();
                    printWriter.println(msg);
                    if (msg.equals("Exit")){
                        shutdown(inputStream, outputStream, socket, scanner);
                        System.exit(-1);
                    }
                }
            }

            System.err.println("Command not found");
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
