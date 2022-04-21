package day03.ex00;

public class ThreadPrint extends Thread {

    private Integer count;
    private String  outputText;

    ThreadPrint(Integer count, String outputText){
        this.count = count;
        this.outputText = outputText;
    }

    public void run(){
        for (int i = 0; i < this.count; i++){
            System.out.println(this.outputText);
        }
    }
}
