package day03.ex00;

public class Program {

    private static final String COUNT = "--count=";
    private static final String ERROR_ARGUMENT = "Error : bad argument";

    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith(COUNT)){
            System.err.println(ERROR_ARGUMENT);
            System.exit(-1);
        }

        String inputLine = args[0].replaceFirst(COUNT, "");

        Integer inputCount = 0;

        try{
            inputCount = Integer.parseInt(inputLine);
        } catch (NumberFormatException error){
            System.err.println(ERROR_ARGUMENT);
        }

    }
}
