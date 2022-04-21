package day02.ex02;

import javafx.beans.binding.When;

import java.io.File;
import java.util.Scanner;

public class Program {

    private static final String ERRORDIR = "Error: directory not found";

    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--current-folder")){
            System.err.println("invalid command");
            System.exit(-1);
        }

        String inputPath = args[0].replaceFirst("--current-folder=", "");
        if (inputPath.isEmpty()){
            System.err.println(ERRORDIR);
        }
        File directory = new File(inputPath);

        if (!directory.isDirectory()){
            System.err.println(ERRORDIR);
            System.exit(-1);
        }

        Scanner scanner = new Scanner(System.in);
        String inputLine = scanner.nextLine();
        while (!inputLine.equals("exit"))
        {
            inputLine = scanner.nextLine();
        }
        scanner.close();
    }
}
