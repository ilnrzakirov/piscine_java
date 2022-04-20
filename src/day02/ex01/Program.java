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
        Integer[] vectorA = new Integer[arrayList.size()];
        Integer[] vectorB = new Integer[arrayList.size()];
        Collections.sort(arrayList);
        for (int i = 0; i < arrayList.size(); i++) {
            if (frequencyWordA.get(arrayList.get(i)) == null)
                vectorA[i] = 0;
            else {
                vectorA[i] = frequencyWordA.get(arrayList.get(i));
            }

            if (frequencyWordB.get(arrayList.get(i)) == null)
                vectorB[i] = 0;
            else {
                vectorB[i] = frequencyWordB.get(arrayList.get(i));
            }
        }
        double result = createResult(vectorA, vectorB, arrayList);
        String similarity = String.format("%.3f",result);
        System.out.println("Similarity = " + similarity.substring(0, similarity.length() - 1));
    }

    private static double createResult(Integer[] vectorA, Integer[] vectorB, ArrayList<String> arrayList) {
        Integer numerator = createNumerator(vectorA, vectorB, arrayList);
        double denominator = createDenominator(vectorA, vectorB, arrayList);
        return numerator / denominator;
    }

    private static double createDenominator(Integer[] vectorA, Integer[] vectorB, ArrayList<String> arrayList) {
        double denominatorA = 0;
        for (int i = 0; i < arrayList.size(); i++){
            denominatorA += vectorA[i] * vectorA[i];
        }
        double denominatorB = 0;
        for (int i = 0; i < arrayList.size(); i++){
            denominatorB += vectorB[i] * vectorB[i];
        }
        return Math.sqrt(denominatorA) * Math.sqrt(denominatorB);
    }

    private static Integer createNumerator(Integer[] vectorA, Integer[] vectorB, ArrayList<String> arrayList) {
        int sumOfMulRess = 0;
        for (int i = 0; i < arrayList.size(); i++)
            sumOfMulRess += vectorA[i] * vectorB[i];
        return sumOfMulRess;
    }

    private static void writeByOutputFile(HashSet<String> dictionary) {
        try (FileOutputStream fileOutputStream = new FileOutputStream("dictionary.txt")){
            for (String word : dictionary) {
                fileOutputStream.write(word.getBytes());
                fileOutputStream.write('\n');
            }
        }catch (Exception error){
            System.exit(-1);
        }
    }

    private static double similarity(ArrayList<Integer> a, ArrayList<Integer> b) {
        int numerator = 0;
        for (int i = 0; i < a.size(); ++i)
            numerator += a.get(i) * b.get(i);
        int left = 0;
        for (Integer ai : a)
            left += ai * ai;
        int right = 0;
        for (Integer bi : b)
            right += bi * bi;
        double denominator = Math.sqrt(left) * Math.sqrt(right);
        if (denominator == 0)
            return 0;
        return numerator / denominator;
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
