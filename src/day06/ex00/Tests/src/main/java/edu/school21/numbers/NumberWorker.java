package edu.school21.numbers;

public class NumberWorker {
    private static final String ERROR = "Illegal Number";

    public boolean isPrime(int number){
        int temp;

        if (number <= 1){
            throw new IllegalNumberException(ERROR);
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

    public int  digitsSum(int number){
        if (number < 0){
            number = -number;
        }
        int result = 0;

        while (number != 0){
            result += number % 10;
            number = number / 10;
        }
        return (result);
    }

}
