package day02.ex01;

import java.io.FileInputStream;
import java.util.*;

public class Program {
    private static final String FNF = "File not found";

    public static void main(String[] args) {
        if (args.length != 2){
            System.err.println("Bad Argument");
            System.exit(-1);
        }

        String dataFileA = getAllLine(args[0]);
        String dataFileB = getAllLine(args[1]);
        if (dataFileA.isEmpty() && dataFileB.isEmpty()){
            System.exit(0);
        }

        ArrayList<String> arrayDataFileA = new ArrayList<>(Arrays.asList(dataFileA.split("\\s+")));
        ArrayList<String> arrayDataFileB = new ArrayList<>(Arrays.asList(dataFileB.split("\\s+")));
        Map<String, Integer> frequencyWordA;
        Map<String, Integer> frequencyWordB;
        frequencyWordA = getCountWord(arrayDataFileA);
        frequencyWordB = getCountWord(arrayDataFileB);
    }

    public static String getAllLine(String filename){
        StringBuilder sb = new StringBuilder();
        String line;

        try(FileInputStream fileInputStream = new FileInputStream(filename)){
            Scanner scanner = new Scanner(fileInputStream);
            while (scanner.hasNextLine()){
                line = scanner.nextLine();
                sb.append(line);
            }
        } catch (Exception error){
            System.err.println(FNF);
            System.exit(-1);
        }
        return sb.toString();
    }

    public static Map<String, Integer> getCountWord(ArrayList<String> wordList){
        Map<String, Integer> frequencyWord = new HashMap<>();

        for (String word : wordList) {
            Integer frequency = Collections.frequency(wordList, word);
            frequencyWord.put(word, frequency);
        }

        return frequencyWord;
    }
}
