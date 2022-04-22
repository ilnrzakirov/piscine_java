package day03.ex01;

public class Producer {

    int lock = 0;

    public void printEgg(Integer count) throws InterruptedException {
        while (count > 0){
            synchronized (this){

                if (lock == 1){
                    wait();
                }

                System.out.println("Egg");
                this.lock = 1;
                notify();
                count--;
                Thread.sleep(1);
            }
        }
    }

    public void printHen(Integer count) throws InterruptedException {
        while (count > 0){
            synchronized (this){

                if (lock == 0){
                    wait();
                }

                System.out.println("Hen");
                this.lock = 0;
                notify();
                count--;
                Thread.sleep(1);
            }
        }
    }
}
