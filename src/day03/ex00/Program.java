package day03.ex00;

public class Program {

    private static final String COUNT = "--count=";
    private static final String ERROR_ARGUMENT = "Error : bad argument";

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 1 || !args[0].startsWith(COUNT)){
            System.err.println(ERROR_ARGUMENT);
            System.exit(-1);
        }

        String inputLine = args[0].replaceFirst(COUNT, "");
        int inputCount = 0;

        try{
            inputCount = Integer.parseInt(inputLine);
        } catch (NumberFormatException error){
            System.err.println(ERROR_ARGUMENT);
            System.exit(-1);
        }

        if (inputCount < 1){
            System.err.println(ERROR_ARGUMENT);
            System.exit(-1);
        }

        ThreadPrint ThreadOne = new ThreadPrint(inputCount, "Egg");
        ThreadPrint ThreadTwo = new ThreadPrint(inputCount, "Hen");
        ThreadOne.start();
        ThreadTwo.start();
        ThreadOne.join();
        ThreadTwo.join();

        for (int i = 0; i < inputCount; i++){
            System.out.println("Human");
        }
    }
}
