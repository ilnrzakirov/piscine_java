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
            maxCount = 0;
        }
        printMatrix(countCharList, resultList);
    }

    public static void printMatrix(int[] countCharList, char[] resultList){
        char[][] matrix  = new char[12][10];
        float k;

        for (int i = 0; i < 10; i++){
            matrix[11][i] = resultList[i];
        }
        if (countCharList[0] > 10){
            k = countCharList[0] / 10.0f;
        }
        else{
            k = 1;
        }
        for (int i = 0; i < 12; i++){
            for (int j = 0; j < 10; j++){
                if (i != 11) {
                    if (countCharList[j % 10] / k > j % 10) {
                        matrix[i][j] = '#';
                    }
                    if (i == 0 && j == 0) {
                        matrix[i][j] = '*';
                    }
                    if (countCharList[j % 10] / k == j % 10) {
                        matrix[i][j] = '*';
                    }
                    if (countCharList[j % 10] / k < j % 10) {
                        matrix[i][j] = ' ';
                    }
                }
            }
        }
        for (int i = 0; i < 12; i++) {
            System.out.println();
            for (int j = 0; j < 10; j++) {
                if (matrix[i][j] == 0){
                    continue;
                }
                System.out.print(matrix[i][j]);
            }
        }
    }
}
