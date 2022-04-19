package day00.ex04;
import java.util.Scanner;

public class Program {
    public static void main(String[] argv) {
        Scanner scanner = new Scanner(System.in);
        String inputData = scanner.nextLine();
        int[] charCount = new int[65535];
        char[] dataArray = inputData.toCharArray();
        for (int i = 0; i < inputData.length(); i++){
            charCount[dataArray[i]]++;
        }
        char [] resultList = new char[10];
        int [] countCharList = new int[10];
        char maxChar = ' ';
        int maxCount = 0;
        int index = 0;
        for (int j = 0; j < 10; j++){
            for (int i = 0; i < 65535; i++){
                if (charCount[i] > maxCount){
                    maxCount = charCount[i];
                    maxChar = (char) i;
                    index = i;
                }
            }
            countCharList[j] = charCount[index];
            resultList[j] = maxChar;
            charCount[index] = 0;
            maxChar = ' ';
            maxCount = 0;
        }
        if (charCount[0] > 999){
            System.err.println("Illegal Argument");
            System.exit(-1);
        }
        paintTable(resultList, countCharList);
        scanner.close();
    }

    public static void paintTable(char[] chars, int[] counts){
        int d = counts[0];
        System.out.println();
        System.out.println();
        for(int i = 0; i < 10; i++) {
            if(counts[i] * 10 / d == 10)
                System.out.print(counts[i] + "\t");
        }
        System.out.println();
        for (int i = 10; i > 0; i--) {
            for (int j = 0; j < 10; j++) {
                if (counts[j] * 10 / d >= i)
                    System.out.print("#\t");
                if (counts[j] * 10 / d == i - 1) {
                    if (counts[j] != 0) {
                        System.out.print(counts[j] + "\t");
                    }
                }
            }
            System.out.println();
        }
        for (int i = 0; i < 10; i++){
            System.out.print(chars[i] + "\t");
        }
    }
}
