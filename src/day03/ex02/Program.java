package day03.ex02;

import java.util.Arrays;
import java.util.Random;

public class Program {

    private static final String SIZE = "--arraySize=";
    private static final String COUNT = "--threadsCount=";
    private static final String ERROR_ARGUMENT = "Error : bad argument";
    private static final Integer MIN = -1000;
    private static final Integer MAX = 1000;

    public static void main(String[] args) throws InterruptedException {
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
        createThreads(arraySize, threadCount, intArray);
    }

    private static void createThreads(int arraySize, int threadCount, Integer[] intArray) throws InterruptedException {
        pintSum(intArray);
        Integer range = arraySize / threadCount;
        Integer diff = arraySize % threadCount;
        Integer[] sumList = new Integer[threadCount];
        if (threadCount > arraySize / 2){
            runThread(range, arraySize, threadCount, intArray);
        }

        if (diff == 0){
            Integer start = 0;

            for (int i = 0; i < threadCount; i++) {
                Integer st = start;
                Integer index = i;
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        Integer sum = 0;
                        Integer end = 0;

                        for (int j = st; j < range + st; j++) {
                            sum += intArray[j];
                            end =j;
                        }
                        System.out.println("Thread " + (index + 1) + " form " + st + " to " +
                                end + " sum is " + sum);
                        sumList[index] = sum;
                    }
                });

                thread.start();
                start += range;
            }

        } else {
            Integer start = 0;

            for (int i = 0; i < threadCount; i++) {
                Integer st = start;
                Integer index = i;
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        Integer sum = 0;
                        Integer end = 0;

                        for (int j = st; j < range + st + 1; j++) {
                            if (j > intArray.length - 1) {
                                break;
                            }
                            sum += intArray[j];
                            end = j;
                        }

                        System.out.println("Thread " + (index + 1) + " form " + st + " to " +
                                (end) + " sum is " + sum);
                        sumList[index] = sum;
                    }
                });

                thread.start();
                start += range + 1;
            }
        }

        Thread.sleep(100);
        printResult(sumList);
    }

    private static void runThread(Integer range, int arraySize, int threadCount, Integer[] intArray) throws InterruptedException {
        Integer[] sumList = new Integer[threadCount];
        Integer start = 0;

        for (int i = 0; i < threadCount; i++){
            Integer st = start;
            Integer index = i;
            if (i < threadCount -1) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Thread " + (index + 1) + " form " + st + " to " +
                                st + " sum is " + intArray[index]);
                        sumList[index] = intArray[index];
                    }
                });
                thread.start();
                start++;
            }
            else{
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Integer sum = 0;

                        for (int i = threadCount - 1; i < arraySize; i++){
                            sum += intArray[i];
                        }
                        sumList[threadCount -1] = sum;
                        System.out.println("Thread " + (threadCount) + " form " + (threadCount -1) + " to " +
                                (arraySize -1) + " sum is " + intArray[threadCount -1]);
                    }
                });
                thread.start();
            }
        }
        Thread.sleep(100);
        printResult(sumList);
        System.exit(0);
    }

    private static void printResult(Integer[] sumList) {
        Integer result = 0;

        for (Integer integer : sumList) {
            result += integer;
        }

        System.out.println("Sum by threads: " + result );
    }

    private static void pintSum(Integer[] intArray) {
        Integer sumArray = 0;

        for (Integer integer : intArray) {
            sumArray += integer;
        }

        System.out.println("Sum: " + sumArray);
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
