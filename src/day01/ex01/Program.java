package day01.ex01;

public class Program {
    public static void main(String[] args) {
        User user1 = new User(2400, "Tom");
        User user2 = new User(1100, "Tim");
        User user3 = new User(0, "Bob");
        User user4 = new User(-2400, "Jone");
        User user5 = new User(800, "Jack");
        user1.printInfo();
        user2.printInfo();
        user3.printInfo();
        user4.printInfo();
        user5.printInfo();
    }
}
