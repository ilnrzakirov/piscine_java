package day02.ex00;

import javafx.beans.binding.When;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Program {

    private static final String END = "42";

    public static void main(String[] args){
        Map <String, String> signature = new HashMap<>();
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;

        try{
            fileInputStream = new FileInputStream("signatures.txt");
            fileOutputStream = new FileOutputStream("result.txt");
        } catch (FileNotFoundException error){
            System.err.println("file not found");
        }
        Scanner scanner = new Scanner(System.in);
        String inputLine = scanner.nextLine();
        while (inputLine.equals(END)){
            try {
                FileInputStream inputFile = new FileInputStream(inputLine);
            } catch (FileNotFoundException error){
                System.err.println("file not found");
            }
            inputLine = scanner.nextLine();
        }
        scanner.close();
    }
}
