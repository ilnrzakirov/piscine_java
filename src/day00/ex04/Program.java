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
                    maxChar = (char) i;
                    index = i;
                }
            }
            countCharList[j] = charCount[index];
            resultList[j] = maxChar;
            charCount[index] = 0;
        }
        System.out.println(resultList);
        for (int f = 0; f < 10; f++){
            System.out.print(countCharList[f]);
        }
    }
}
