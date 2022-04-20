package day02.ex01;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
            System.out.println("Similarity = 1");
            System.exit(0);
        }
        if (dataFileA.isEmpty() || dataFileB.isEmpty()){
            System.out.println("Similarity = 0");
            System.exit(0);
        }

        ArrayList<String> arrayDataFileA = new ArrayList<>(Arrays.asList(dataFileA.split("\\s+")));
        ArrayList<String> arrayDataFileB = new ArrayList<>(Arrays.asList(dataFileB.split("\\s+")));
        Map<String, Integer> frequencyWordA;
        Map<String, Integer> frequencyWordB;
        frequencyWordA = getCountWord(arrayDataFileA);
        frequencyWordB = getCountWord(arrayDataFileB);
        HashSet<String> dictionary = new HashSet<>(frequencyWordA.keySet());
        dictionary.addAll(frequencyWordB.keySet());
        writeByOutputFile(dictionary);
        createVector(dictionary, frequencyWordA, frequencyWordB);
    }

    private static void createVector(HashSet<String> dictionary, Map<String, Integer> frequencyWordA, Map<String, Integer> frequencyWordB) {
        ArrayList<String> arrayList = new ArrayList<>(dictionary);
        Vector<Integer> vectorA = new Vector<>(dictionary.size());
        Vector<Integer> vectorB = new Vector<>(dictionary.size());

    }

    private static void writeByOutputFile(HashSet<String> dictionary) {
        try (FileOutputStream fileOutputStream = new FileOutputStream("dictionary.txt", true)){
            for (String word : dictionary) {
                fileOutputStream.write(word.getBytes());
                fileOutputStream.write('\n');
            }
        }catch (Exception error){
            System.exit(-1);
        }
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
