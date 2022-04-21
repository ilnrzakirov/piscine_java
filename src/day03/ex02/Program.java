package day03.ex02;

import java.util.Random;

public class Program {

    private static final String SIZE = "--arraySize=";
    private static final String COUNT = "--threadsCount=";
    private static final String ERROR_ARGUMENT = "Error : bad argument";
    private static final Integer MIN = -1000;
    private static final Integer MAX = 1000;

    public static void main(String[] args) {
        if (args.length != 2 || !args[0].startsWith(SIZE) || !args[1].startsWith(COUNT)){
            System.err.println(ERROR_ARGUMENT);
            System.exit(-1);
        }

        String inputArraySize = args[0].replaceFirst(SIZE, "");
        String inputThreadsCount = args[1].replaceFirst(COUNT, "");
        int arraySize = 0;
        int threadCount = 0;

        try{
            arraySize = Integer.parseInt(inputArraySize);
            threadCount = Integer.parseInt(inputThreadsCount);
        } catch (NumberFormatException error){
            System.err.println(ERROR_ARGUMENT);
            System.exit(-1);
        }

        if (arraySize < threadCount){
            System.err.println("Array size < thread count");
            System.exit(-1);
        }

        if (arraySize < 1 || threadCount < 1){
            System.err.println(ERROR_ARGUMENT);
            System.exit(-1);
        }

        Integer[] intArray = createArray(arraySize);
    }

    private static Integer[] createArray(int arraySize) {
        Integer[] intArray = new Integer[arraySize];
        int diff = MAX - MIN;
        Random random = new Random();

        for (int i = 0; i < arraySize; i++){
            intArray[i] = random.nextInt(diff + 1) + MIN;
        }
        return intArray;
    }
}
