package day02.ex00;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class Program {
    private static final String END = "42";

    public static void main(String[] args) {
        Map <String, String> signature = new HashMap<>();
        FileInputStream fileInputStream;
        try{
            fileInputStream = new FileInputStream("");
        } catch (FileNotFoundException error){
            System.err.println("file not found");
        }
    }
}
