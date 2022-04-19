package day00.ex02;

import java.util.Scanner;

public class Program {
    public static void main(String[] argv) {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        int amountCoffee = 0;
        int sumOfDigit;

        while (number != 42){
            sumOfDigit = getSumOfDigit(number);
            if (isPrime(sumOfDigit)){
                amountCoffee++;
            }
            number = scanner.nextInt();
        }
        System.out.println("Count of coffee-request – " + amountCoffee);
        scanner.close();
    }

    public static int getSumOfDigit(int number){
        int result = 0;

        result += number % 10;
        while (number > 10){
            number = number / 10;
            result += number % 10;
        }
        return (result);
    }

    public static boolean isPrime(int number){
        int temp;

        if (number <= 1){
            return (false);
        }
        else if (number == 2){
            return (true);
        }
        else{
            for (int i = 2; i * i <= number + 1; i++){
                temp = number % i;
                if (temp == 0)
                {
                    return (false);
                }
            }
        }
        return (true);
    }
}
