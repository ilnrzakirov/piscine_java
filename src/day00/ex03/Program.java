package day00.ex03;

import java.util.Scanner;

public class Program {
    public static void main(String[] argv) {
        Scanner scanner = new Scanner(System.in);
        String inputData = scanner.nextLine();
        int week = 1;
        long grade;
        long grades = 0;

        while (!inputData.equals("42") && week <= 18){
            inputData = inputData.trim();
            if (!inputData.equals("Week " + week)){
                System.err.println("IllegalArgument");
                System.exit(-1);
            }
            grades = getGrades(grades, week, scanner);
            System.out.println(grades);
            scanner.nextLine();
            inputData = scanner.nextLine();
            week++;
        }
        for (int i = 1; i < week; i++){
            System.out.print("Week " +i);
            grade = grades % 10;
            grades = grades / 10;
            for (int j = 0; j < grade; j++){
                System.out.print("=");
            }
            System.out.println(">");
        }
    }

    public static long getGrades(long grades, int week, Scanner scanner){
        int min;
        long result;
        long pow;

        pow = 1;

        for (int i = 1; i < week; i++){
            pow = pow * 10;
        }
        min = getMinGrade(scanner);
        result = grades + (min * pow);
        return  (result);
    }

    public static int getMinGrade(Scanner scanner){
        int min;
        int count;
        int inputNumber;

        min = scanner.nextInt();
        count = 0;
        while (count < 4){
            inputNumber = scanner.nextInt();
            if (inputNumber < min){
                min = inputNumber;
            }
            count++;
        }
        return (min);
    }
}
