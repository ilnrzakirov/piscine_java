package day00.ex01;
import java.util.Scanner;

public class Program {
    public static void main(String[] argv){
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        int step = 1;
        boolean isPrime = true;
        int temp;

        if (number <= 1){
            System.err.println("Illegal Argument");
            System.exit(-1);
        }
        else if (number == 2){
            System.out.println("true " + 1);
            System.exit(0);
        }
        else{
            for (int i = 2; i * i <= number; i++){
                temp = number % i;
                if (temp == 0)
                {
                    isPrime = false;
                    break;
                }
                step++;
            }
        }
        System.out.println(isPrime + " " + step);
        System.exit(0);
    }
}