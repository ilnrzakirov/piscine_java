package day03.ex02;

public class Program {

    private static final String SIZE = "--arraySize=";
    private static final String COUNT = "--threadsCount";
    private static final String ERROR_ARGUMENT = "Error : bad argument";

    public static void main(String[] args) {
        if (args.length != 2 || !args[0].startsWith(SIZE) || !args[1].startsWith(COUNT)){
            System.err.println(ERROR_ARGUMENT);
            System.exit(-1);
        }

        String inputArraySize = args[0].replaceFirst(SIZE, "");
        String inputThreadsCount = args[1].replaceFirst(SIZE, "");
        int arraySize = 0;
        int threadCount = 0;

        try{
            arraySize = Integer.parseInt(inputArraySize);
            threadCount = Integer.parseInt(inputThreadsCount);
        } catch (NumberFormatException error){
            System.err.println(ERROR_ARGUMENT);
            System.exit(-1);
        }

        if (arraySize < 1 || threadCount < 1){
            System.err.println(ERROR_ARGUMENT);
            System.exit(-1);
        }
    }
}
