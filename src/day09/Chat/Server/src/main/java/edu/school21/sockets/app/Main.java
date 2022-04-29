package edu.school21.sockets.app;

import edu.school21.sockets.server.Server;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1 || args[0].isEmpty()) {
            System.err.println("Bad argument");
            System.exit(-1);
        }

        Integer port = null;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException error){
            System.err.println("the argument is not a number");
            System.exit(-1);
        }

        Server server = new Server(port);
        server.run();

    }
}
