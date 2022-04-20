package day02.ex00;

import javafx.beans.binding.When;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Program {

    private static final String END = "42";
    private static final Integer BYTE_SIZE = 8;
    private static final char[] HEX = "0123456789ABCDEF".toCharArray();

    public static void main(String[] args){
        Map <String, String> signature = new HashMap<>();
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;

        try{
            fileInputStream = new FileInputStream("signatures.txt");
            fileOutputStream = new FileOutputStream("result.txt");
            Scanner newScanner = new Scanner(fileInputStream);

            while (newScanner.hasNextLine()){
                String line = newScanner.nextLine();
                String[] lineArray = line.split(",");
                signature.put(lineArray[0], lineArray[1].replaceAll("\\s+", ""));
            }
            newScanner.close();
            fileInputStream.close();
        } catch (Exception error){
            System.err.println("file not found");
            System.exit(-1);
        }

        Scanner scanner = new Scanner(System.in);
        String inputLine = scanner.nextLine();

        while (!inputLine.equals(END)){
            try {
                FileInputStream inputFile = new FileInputStream(inputLine);
                byte[] bytes = new byte[BYTE_SIZE];
                inputFile.read(bytes,0, BYTE_SIZE);
                String fileSignature = bytesToHex(bytes);
                findSignature(fileSignature, signature);
            } catch (Exception error){
                System.err.println("file not found");
            }

            inputLine = scanner.nextLine();
        }

        scanner.close();
    }

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];

        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX[v >>> 4];
            hexChars[j * 2 + 1] = HEX[v & 0x0F];
        }

        return new String(hexChars);
    }

    private static void findSignature(String signature, Map<String, String> signatureMap){
        FileOutputStream fileOutputStream;

        try{
            fileOutputStream = new FileOutputStream("result.txt", true);

            for (Map.Entry<String, String> fileSignature : signatureMap.entrySet()) {
                if (signature.contains(fileSignature.getValue())){
                    fileOutputStream.write(fileSignature.getKey().getBytes());
                    fileOutputStream.write('\n');
                    System.out.println("PROCESSED");
                }
            }
        } catch (Exception error){
            System.err.println("file not found");
        }
    }
}
