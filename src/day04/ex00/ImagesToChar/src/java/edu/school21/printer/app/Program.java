package day04.ex00.ImagesToChar.src.java.edu.school21.printer.app;

import day04.ex00.ImagesToChar.src.java.edu.school21.printer.logic.ImagesToChar;

import java.io.FileInputStream;
import java.io.IOException;

public class Program {

    private static final String PATH_TO_BMP = "/Users/bcarlee/Desktop/Project/piscine_java/src/day04/it.bmp";
    private static final String FILE_NOT_FOUND = "File not found";
    private static final String BAD_ARGUMENT = "Bad argument";
    private static final String TYPE_ERROR = "Type error";
    private static final Integer WHITE = -1;
    private static final Integer BLACK = -16777216;
    private static final String BMP = ".bmp";

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println(BAD_ARGUMENT);
            System.exit(-1);
        }

        if (args[0].length() != 1 || args[1].length() != 1 || args[2].isEmpty()){
            System.err.println(BAD_ARGUMENT);
            System.exit(-1);
        }

        if (!args[2].endsWith(BMP)){
            System.err.println(TYPE_ERROR);
            System.exit(-1);
        }

        String pathToBMP = args[2];
        char symbolWhite = args[0].charAt(0);
        char symbolBlack = args[1].charAt(0);

        try (FileInputStream fileInputStream = new FileInputStream(pathToBMP)) {
            ImagesToChar image = new ImagesToChar(fileInputStream, symbolWhite, symbolBlack);
            for (int i = 0; i < image.getHeight(); i++){
                for ( int j = 0; j < image.getWidth(); j++){
                    if (image.getRGB(j, i) == WHITE){
                        System.out.print(image.getSymbolWhite());
                    } else {
                        System.out.print(image.getSymbolBlack());
                    }
                }
                System.out.println();
            }
        } catch (IOException error) {
            System.err.println(FILE_NOT_FOUND);
            System.exit(-1);
        }

    }
}
