package day03.ex01;

public class Program {

    private static final String COUNT = "--count=";
    private static final String ERROR_ARGUMENT = "Error : bad argument";

    public static void main(String[] args) {
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
        final Integer count = inputCount;

        Producer producer = new Producer();
        Thread TreadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    producer.printEgg(count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread TreadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    producer.printHen(count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        TreadOne.start();
        TreadTwo.start();
    }
}
