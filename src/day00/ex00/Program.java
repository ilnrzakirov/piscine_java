package day00.ex00;

public class Program{
    public static void main(String[] argv){
        int number;
        int result;

        result = 0;
        number = 546894;
        result += number % 10;
        while (number > 10){
            number = number / 10;
            result += number % 10;
        }
        System.out.println(result);
    }
}