package app;

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
            printWriter.println(type);
            answer = bufferedReader.readLine();
            System.out.println(answer);

            if (answer.equals("Command not found")){
                socket.close();
                inputStream.close();
                outputStream.close();
                System.exit(-1);
            }

            String username = scanner.nextLine();
            printWriter.println(username);
            answer = bufferedReader.readLine();
            System.out.println(answer);

            String password = scanner.nextLine();
            printWriter.println(password);
            answer = bufferedReader.readLine();
            System.out.println(answer);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
