package day02.ex02;

import java.io.File;
import java.util.Scanner;

public class Program {

    private static final String ERRORDIR = "Error: directory not found";
    private static final String CURRENTF = "--current-folder=";
    private static final String TOMANY = "To many arguments";

    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith(CURRENTF)){
            System.err.println("invalid command");
            System.exit(-1);
        }

        String inputPath = args[0].replaceFirst(CURRENTF, "");
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
            if (!inputLine.isEmpty()) {
                runCommand(inputLine, directory);
            }
            inputLine = scanner.nextLine();
        }
        scanner.close();
    }

    private static void runCommand(String inputLine, File directory) {
        String[] inputCommand = inputLine.split("\\s+");
        switch (inputCommand[0]) {
            case "ls":
                if (inputCommand.length == 1) {
                    runLs(inputCommand, directory);
                } else {
                    System.err.println(TOMANY);
                }
                break;
            case "cd":
                if (inputCommand.length == 1){
                    return;
                } else if (inputCommand.length == 2) {
                    runCd(inputCommand, directory);
                } else {
                    System.err.println(TOMANY);
                }
                break;
            case "mv":
                if (inputCommand.length == 3){
                    ruMv(inputCommand, directory);
                }
                else {
                    System.err.println("Need 2 arguments");
                }
                break;
            default:
                System.err.println("Unknown command");
        }
    }

    private static void ruMv(String[] inputCommand, File directory) {
    }

    private static void runCd(String[] inputCommand, File directory) {
    }

    private static void runLs(String[] inputCommand, File directory) {

    }

}
