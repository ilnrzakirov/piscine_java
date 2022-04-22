package day04.ex00.ImagesToChar.src.java.edu.school21.printer.app;

import java.io.FileInputStream;
import java.io.IOException;

public class Program {

    private static final String PATH_TO_BMP = "/Users/bcarlee/Desktop/Project/piscine_java/src/day04/it.bmp";
    private static final String FILENOTFOUND = "File not found";

    public static void main(String[] args) {
        try (FileInputStream fileInputStream = new FileInputStream(PATH_TO_BMP)) {
            Integer in;
        } catch (IOException error) {
            System.err.println(FILENOTFOUND);
        }

    }
}
