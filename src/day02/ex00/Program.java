package day02.ex00;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Program {

    private static final String END = "42";
    private static final Integer BYTE_SIZE = 8;
    private static final char[] HEX = "0123456789ABCDEF".toCharArray();
    private static final String SIGNATURE_FILE = "signatures.txt";
    private static final String RESULT_FILE = "result.txt";
    private static final String PROCESSED = "PROCESSED";
    private static final String UNDEFINED = "UNDEFINED";
    private static final String FNF = "File not found";

    public static void main(String[] args){
        Map <String, String> signature = new HashMap<>();
        FileInputStream fileInputStream;

        try{
            fileInputStream = new FileInputStream(SIGNATURE_FILE);
            Scanner newScanner = new Scanner(fileInputStream);

            while (newScanner.hasNextLine()){
                String line = newScanner.nextLine();
                String[] lineArray = line.split(",");
                signature.put(lineArray[0], lineArray[1].replaceAll("\\s+", ""));
            }
            newScanner.close();
            fileInputStream.close();
        } catch (Exception error){
            System.err.println(FNF);
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
                System.err.println(FNF);
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
            fileOutputStream = new FileOutputStream(RESULT_FILE, true);

            for (Map.Entry<String, String> fileSignature : signatureMap.entrySet()) {
                if (signature.contains(fileSignature.getValue())){
                    fileOutputStream.write(fileSignature.getKey().getBytes());
                    fileOutputStream.write('\n');
                    System.out.println(PROCESSED);
                    return;
                }
            }
            System.out.println(UNDEFINED);
        } catch (Exception error){
            System.err.println(FNF);
        }
    }
}
