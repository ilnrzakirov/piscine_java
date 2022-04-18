package day00.ex01;
import java.util.Scanner;

public class Program {
    public static void main(String[] argv){
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        int step = 1;
        boolean isPrime = true;

        if (number <= 1){
            System.err.println("Illegal Argument");
            System.exit(-1);
        }
        else if (number == 2){
            System.out.println("true " + 1);
            System.exit(0);
        }
        else{
            for (long i = 2; i * i <= number; i++){
                if (number % i == 0)
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