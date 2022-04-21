package day02.ex02;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Program {

    private static final String ERRORDIR = "Error: directory not found";
    private static final String CURRENTF = "--current-folder=";
    private static final String TOMANY = "To many arguments";
    private static final String CD = "cd";
    private static final String LS = "ls";
    private static final String MV = "mv";
    private static final String END = "exit";
    private static final String ERROR = "Error";
    private static final String NOFILE = "No such directory";
    private static final String KB = " KB";
    private static File directory;

    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith(CURRENTF)){
            System.err.println("invalid command");
            System.exit(-1);
        }

        String inputPath = args[0].replaceFirst(CURRENTF, "");
        if (inputPath.isEmpty()){
            System.err.println(ERRORDIR);
        }
        directory = new File(inputPath);

        if (!directory.isDirectory()){
            System.err.println(ERRORDIR);
            System.exit(-1);
        }

        Scanner scanner = new Scanner(System.in);
        String inputLine = scanner.nextLine();
        while (!inputLine.equals(END))
        {
            if (!inputLine.isEmpty()) {
                runCommand(inputLine);
            }
            inputLine = scanner.nextLine();
        }
        scanner.close();
    }

    private static void runCommand(String inputLine) {
        String[] inputCommand = inputLine.split("\\s+");
        switch (inputCommand[0]) {
            case LS:
                if (inputCommand.length == 1) {
                    runLs();
                } else if (inputCommand.length == 2){
                    runLs(inputCommand);
                } else {
                    System.err.println(TOMANY);
                }
                break;
            case CD:
                if (inputCommand.length == 1){
                    return;
                } else if (inputCommand.length == 2) {
                    runCd(inputCommand);
                } else {
                    System.err.println(TOMANY);
                }
                break;
            case MV:
                if (inputCommand.length == 3){
                    runMv(inputCommand);
                }
                else {
                    System.err.println("Need 2 arguments");
                }
                break;
            default:
                System.err.println("Unknown command");
        }
    }

    private static void runMv(String[] inputCommand) {
        String pathFile = getPath(inputCommand[1]);
        String pathDir = getPath(inputCommand[2]);
        File currentPathFile = new File(pathFile);
        File currentPathDir = new File(pathDir);
        String pathTo = pathDir + File.separator + currentPathFile.getName();
        System.out.println(pathFile);
        System.out.println(pathTo);
        try {
            if (currentPathFile.exists() && currentPathDir.isDirectory()){
                Files.move(Paths.get(pathFile), Paths.get(pathTo));
            } else {
                System.err.println(NOFILE);
            }
        } catch (IOException error) {
            System.err.println(ERROR);
        }

    }

    private static void runCd(String[] inputCommand) {
        String newPath = getPath(inputCommand[1]);
        File currentPath = new File(newPath);
        if (currentPath.isDirectory()){
            directory = currentPath;
        } else {
            System.err.println(ERRORDIR);
        }
    }

    private static void runLs(String[] inputCommand) {
        String newPath = getPath(inputCommand[1]);
        File currentPath = new File(newPath);
        if (!currentPath.isDirectory()){
            System.err.println(ERRORDIR);
        }
        File[] files = listFiles(currentPath);
        for (File file : files) {
            System.out.print(file.getName());
            System.out.print(" ");
            System.out.print(getSize(file) / 1000);
            System.out.println(KB);
        }
    }

    private static void runLs() {
        File[] files = listFiles(directory);
        for (File file : files) {
            System.out.print(file.getName());
            System.out.print(" ");
            System.out.print(getSize(file) / 1000);
            System.out.println(KB);
        }
    }

    private static String getPath(String name) {
        if (name.startsWith("/")) {
            return name;
        } else if (name.startsWith("./")) {
            return  directory + name.substring(1);
        }
        return directory + "/" + name;
    }

    private static File[] listFiles(File file) {
        File[] files = file.listFiles();
        if (files == null) {
            return new File[0];
        }
        return files;
    }

    private static long getSize(File file) {
        if (!file.isDirectory()) {
            return file.length();
        }
        long size = 0;
        for (File f : listFiles(file)) {
            size += getSize(f);
        }
        return size;
    }

}
